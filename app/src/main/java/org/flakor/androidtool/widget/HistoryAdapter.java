/*
 * HistoryAdapter.java
 * Created on 11/24/13 9:37 PM
 *
 * ver0.0.1beta 11/24/13 saint
 * Copyright (c) 2013 Saint Hsu All Rights Reserved.
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
import com.taqi.supervisor.database.History;
import com.taqi.supervisor.json.Branch;
import com.taqi.supervisor.json.JsonConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saint on 11/24/13.
 */
public class HistoryAdapter extends BaseAdapter
{

    private Context context;
    ArrayList<History> histories;
    ArrayList<Branch> branches;

    public HistoryAdapter(Context context, ArrayList<History> histories)
    {
        this.context = context;
        this.histories = histories;
        MyApplication application = (MyApplication) context.getApplicationContext();
        this.branches = application.getBranchArray();
    }

    @Override
    public int getCount()
    {
        return histories.size();
    }

    @Override
    public Object getItem(int position)
    {
        return histories.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
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

        History history = histories.get(position);
        final String json = history.getJson();
        String name = null;
        try
        {
            JSONObject object = new JSONObject(json);
            name = object.getString(JsonConstants.KEY_TABLE_NAME);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        int branchPosition = history.getBranch();
        Branch branch = getBranch(branchPosition);
        StringBuffer buffer = new StringBuffer();
        buffer.append(name);
        buffer.append("(");
        buffer.append(branch.getName());
        buffer.append(")");
        holder.title.setText(new String(buffer));
        holder.time.setText(history.getSaveTime());

        return view;
    }

    class ViewHolder
    {
        TextView title;
        TextView time;
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
}
