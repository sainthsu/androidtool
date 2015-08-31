package org.flakor.androidtool.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taqi.supervisor.R;
import com.taqi.supervisor.json.AssessTable;
import com.taqi.supervisor.json.Doc;

import java.util.ArrayList;

/**
 * Created by saint on 11/7/13.
 */
public class DocAdapter extends BaseAdapter
{
    private Context context;
    private TextView textTmp;
    private ArrayList<Doc> docs;

    public DocAdapter(Context context,ArrayList<Doc> array)
    {
        this.context = context;
        this.docs = array;
    }

    @Override
    public int getCount()
    {
        return docs.size();
    }

    @Override
    public Object getItem(int position)
    {
        return docs.get(position);
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
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.grid_cell,null);
            textTmp = (TextView) view.findViewById(R.id.file_name);
            view.setTag(textTmp);
        }
        else
        {
            textTmp = (TextView) view.getTag();
        }

        Doc doc = docs.get(position);
        textTmp.setText(doc.getName());
        return view;
    }

}
