package org.flakor.androidtool.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taqi.supervisor.R;
import com.taqi.supervisor.json.Branch;

import java.util.ArrayList;

/**
 * Created by saint on 11/14/13.
 */
public class ListAdapter extends BaseAdapter
{
    private ArrayList<Branch> branches;
    private Context context;

    public ListAdapter(ArrayList<Branch> branches, Context context)
    {
        this.branches = branches;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return branches.size();
    }

    @Override
    public Object getItem(int position)
    {
        return branches.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        ViewHolder holder;

        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.store_item,null);
            holder = new ViewHolder();

            holder.name = (TextView) view.findViewById(R.id.store_name);
            holder.address = (TextView) view.findViewById(R.id.store_address);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        Branch branch = branches.get(position);
        holder.name.setText(branch.getName());
        holder.address.setText(branch.getAddress());
        return view;
    }

    class ViewHolder
    {
        public TextView name;
        public TextView address;
    }
}
