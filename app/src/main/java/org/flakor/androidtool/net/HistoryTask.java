/*
 * Copyright (c) 2013. Saint Hsu(saint@aliyun.com) Hangzhou Taqi Tech Ltd
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.flakor.androidtool.net.net;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taqi.supervisor.Debug;
import com.taqi.supervisor.MyApplication;
import com.taqi.supervisor.R;
import com.taqi.supervisor.json.JsonParser;
import com.taqi.supervisor.widget.RemoteHistoryAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saint on 11/8/13.
 */
public class HistoryTask extends AsyncTask<String,Integer,Integer>
{
    private Context context;
    private MyApplication application;
    private Dialog dialog;

    public HistoryTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        showDialog("正在请求历史...");
    }

    @Override
    protected void onPostExecute(Integer i)
    {
        if(i < 0)
        {
            if(dialog != null)
                dialog.dismiss();
            Toast.makeText(context, "请求失败！", Toast.LENGTH_SHORT).show();
        }
        else if(i >= 0)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_history,null);
            ListView list = (ListView) view.findViewById(R.id.history_list);
            if(i == 0)
            {
                TextView noHistory = (TextView) view.findViewById(R.id.no_history);
                noHistory.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
            }
            else
            {
                RemoteHistoryAdapter adapter = new RemoteHistoryAdapter(context);
                list.setAdapter(adapter);
            }
            TextView title = (TextView) view.findViewById(R.id.dialog_title);
            title.setText("服务器考核历史");
            Button okBtn = (Button) view.findViewById(R.id.ok_btn);
            okBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.setContentView(view);

            Toast.makeText(context, "请求成功！", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(dialog != null)
                dialog.dismiss();
            Toast.makeText(context, "发生未知错误，请重试！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        String branchId = params[0];
        String startTime = params[1];
        String endTime = params[2];

        Debug.error("HistoryTask","branchid:"+branchId+";starttime:"+startTime+";endtime:"+endTime);
        HttpPost httpPost = new HttpPost(HttpThread.SERVER_URL_REMOTE);
        List<NameValuePair> list=new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair(HttpThread.POST_FUNC, HttpThread.ACTION_HISTORY));
        list.add(new BasicNameValuePair(HttpThread.POST_BID, branchId));
        list.add(new BasicNameValuePair(HttpThread.POST_START_TIME, startTime));
        list.add(new BasicNameValuePair(HttpThread.POST_END_TIME, endTime));

        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
            HttpClient client = new DefaultHttpClient();
            //请求超时
            client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
            //读取超时
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000);
            HttpResponse response=client.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity=response.getEntity();
                String result= EntityUtils.toString(entity, HTTP.UTF_8);

                Debug.error("HistoryTask",result);
                application = (MyApplication) context.getApplicationContext();
                int resultCode = JsonParser.parseHistory(result, application);

                return resultCode;
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    private void showDialog(String text)
    {
        dialog = new Dialog(context,R.style.dialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog,null);
        TextView textView = (TextView)view.findViewById(R.id.progress_text);
        textView.setText(text);

        dialog.setContentView(view);
        dialog.show();
    }
}
