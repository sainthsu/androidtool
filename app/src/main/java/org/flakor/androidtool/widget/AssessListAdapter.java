package org.flakor.androidtool.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taqi.supervisor.AssessActivity;
import com.taqi.supervisor.AssessItem;
import com.taqi.supervisor.R;
import com.taqi.supervisor.json.Assess;
import com.taqi.supervisor.json.AssessItems;
import com.taqi.supervisor.json.Item;
import com.taqi.supervisor.json.JsonConstants;
import com.taqi.supervisor.json.SecondClass;
import com.taqi.supervisor.widget.IphoneTreeView.IphoneTreeHeaderAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by saint on 11/15/13.
 */
public class AssessListAdapter extends BaseExpandableListAdapter implements IphoneTreeHeaderAdapter
{
    // Sample data set. children[i] contains the children (String[]) for
    // groups[i].
    private HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();
    private Context context;
    private LayoutInflater inflater;

    private Assess assess;
    private int type;

    //private ArrayList<String> groupTitles;
    //private ArrayList<String> childTitles;

    public AssessListAdapter(Context context, Assess assess ,int type)
    {
        this.context = context;
        this.assess = assess;
        this.type = type;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount()
    {
        return assess.getAssessItems().size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        AssessItems items = assess.getAssessItems().get(groupPosition);
        ArrayList<SecondClass> secondClass = items.getSecondClass();
        int length = secondClass.size();

        return length;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return assess.getAssessItems().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String str =  assess.getAssessItems().get(groupPosition).getFirstClass();
        View view = inflater.inflate(R.layout.list_group, null);
        TextView title = (TextView) view.findViewById(R.id.group_name);
        title.setText(str);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        SecondClass secondClass = assess.getAssessItems().get(groupPosition).getSecondClass().get(childPosition);
        String topLine = secondClass.getItem();
        if(!topLine.equals(""))
        {
            View top = inflater.inflate(R.layout.list_first_child,null);
            TextView title = (TextView) top.findViewById(R.id.first_child);
            title.setText(topLine);
            layout.addView(top);
        }

        int size = secondClass.getItems().size();
        for(int i=0 ; i < size ; i++)
        {
            Item itemTmp = secondClass.getItems().get(i);
            String name = (String)itemTmp.getValue(JsonConstants.KEY_NAME);
            int state = itemTmp.getState();

            View view = inflater.inflate(R.layout.list_second_child,null);
            TextView text = (TextView)view.findViewById(R.id.child_item);
            ImageView imageView = (ImageView)view.findViewById(R.id.right_img);
            TextView score = (TextView)view.findViewById(R.id.right_score);

            text.setText(name);
            imageView.setVisibility(View.VISIBLE);
            score.setVisibility(View.GONE);
            if(state == 1)
            {
                imageView.setVisibility(View.GONE);

                StringBuffer buffer = new StringBuffer();
                buffer.append("(");
                buffer.append(itemTmp.getValue(JsonConstants.KEY_FRACTION_S));
                buffer.append("/");
                buffer.append(itemTmp.getValue(JsonConstants.KEY_SCORE));
                buffer.append(")");
                score.setText(buffer);
                score.setVisibility(View.VISIBLE);
            }
            view.setOnClickListener(new AssessItemListener(groupPosition,childPosition,i));
            layout.addView(view);
        }

        return layout;
    }

    class AssessItemListener implements View.OnClickListener
    {
        int group,child,item;

        AssessItemListener(int group, int child, int item)
        {
            this.group = group;
            this.child = child;
            this.item = item;
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context,AssessItem.class);
            Item itemObject = assess.getAssessItems().get(group).getSecondClass().get(child).getItems().get(item);
            intent.putExtra("group",group);
            intent.putExtra("child",child);
            intent.putExtra("item",item);
            intent.putExtra("itemObject",itemObject);
            intent.putExtra("type",type);

            ((Activity)context).startActivityForResult(intent, AssessActivity.ASSESS_ITEM);
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    @Override
    public void configureTreeHeader(View header, int groupPosition, int childPosition, int alpha)
    {
        String name = assess.getAssessItems().get(groupPosition).getFirstClass();
        ((TextView) header.findViewById(R.id.group_name))
                .setText(name);
    }

    @Override
    public void onHeadViewClick(int groupPosition, int status)
    {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getHeadViewClickStatus(int groupPosition)
    {
        if (groupStatusMap.containsKey(groupPosition))
        {
            return groupStatusMap.get(groupPosition);
        }
        else
        {
            return 0;
        }
    }
}
