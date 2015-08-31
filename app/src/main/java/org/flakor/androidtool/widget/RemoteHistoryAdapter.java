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

package org.flakor.androidtool.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taqi.supervisor.AssessActivity;
import com.taqi.supervisor.MyApplication;
import com.taqi.supervisor.R;
import com.taqi.supervisor.json.AssessTable;
import com.taqi.supervisor.json.Branch;

import java.util.ArrayList;

/**
 * Created by saint on 11/24/13.
 */
public class RemoteHistoryAdapter extends BaseAdapter
{

    private Context context;
    private ArrayList<AssessTable> assessHistory;
    private ArrayList<String> historyTime;
    private ArrayList<Branch> branches;

    public RemoteHistoryAdapter(Context context)
    {
        this.context = context;
        MyApplication application = (MyApplication) context.getApplicationContext();
        this.assessHistory = application.getAssessHistory();
        this.historyTime = application.getHistoryTime();
        branches = application.getBranchArray();
    }

    @Override
    public int getCount()
    {
        return historyTime.size();
    }

    @Override
    public Object getItem(int position)
    {
        return assessHistory.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = new ViewHolder();
        View view = convertView;
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.history_item,null);
            holder.title = (TextView) view.findViewById(R.id.history_title);
            holder.time = (TextView) view.findViewById(R.id.history_time);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }


        AssessTable assessTable = assessHistory.get(position);
        String name = null;
        name = assessTable.getTableName();

        int branchPosition = assessTable.getAssess().getBranch();
        Branch branch = getBranch(branchPosition);
        StringBuffer buffer = new StringBuffer();
        buffer.append(name);
        buffer.append("(");
        buffer.append(branch.getName());
        buffer.append(")");

        holder.title.setText(new String(buffer));

        String time = historyTime.get(position);
        holder.time.setText(time);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, AssessActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("type", AssessActivity.ASSESS_HISTORY);

                context.startActivity(intent);
            }
        });

        return view;
    }

    private Branch getBranch(int id)
    {
        for(Branch b:branches)
        {
            if(b.getId() == id)
                return b;
        }

        return null;
    }

    class ViewHolder
    {
        TextView title;
        TextView time;
    }
}
