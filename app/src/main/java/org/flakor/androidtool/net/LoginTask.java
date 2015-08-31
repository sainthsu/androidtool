package org.flakor.androidtool.net.net;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taqi.supervisor.MainActivity;
import com.taqi.supervisor.MyApplication;
import com.taqi.supervisor.R;
import com.taqi.supervisor.json.JsonParser;

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
public class LoginTask extends AsyncTask<String,Integer,Integer>
{
    private Context context;
    private Dialog dialog;

    public LoginTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        dialog = new Dialog(context,R.style.dialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog,null);
        TextView textView = (TextView)view.findViewById(R.id.progress_text);
        textView.setText("正在登入...");

        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Integer i)
    {
        if(i == 0)
        {
            if(dialog != null)
                dialog.dismiss();
            Toast.makeText(context, "登入失败，请检查帐号密码！", Toast.LENGTH_SHORT).show();
        }
        else if(i == 1)
        {
            Intent intent = new Intent(context,MainActivity.class);
            context.startActivity(intent);
            if(dialog != null)
                dialog.dismiss();
            Toast.makeText(context, "登入成功！", Toast.LENGTH_SHORT).show();
            ((Activity)context).finish();
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
        String company = params[0];
        String name = params[1];
        String pwd = params[2];

        HttpPost httpPost = new HttpPost(HttpThread.SERVER_URL_REMOTE);
        List<NameValuePair> list=new ArrayList<NameValuePair>();

        list.add(new BasicNameValuePair(HttpThread.POST_FUNC, HttpThread.ACTION_LOGIN));
        list.add(new BasicNameValuePair(HttpThread.POST_COMPANY, company));
        list.add(new BasicNameValuePair(HttpThread.POST_NAME, name));
        list.add(new BasicNameValuePair(HttpThread.POST_PWD, pwd));

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

                MyApplication application = (MyApplication) context.getApplicationContext();
                int resultCode = JsonParser.parse(result,application);

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
}
