package org.flakor.androidtool.widget;

import android.view.View;

import com.taqi.supervisor.R;

/**
 * Created by saint on 11/6/13.
 */
public abstract class ActionListener implements View.OnClickListener
{
    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == R.id.left_icon)
        {
            leftOnClick(v);
        }
        else if(id == R.id.right_icon)
        {
            rightOnClick(v);
        }
    }

    public abstract void leftOnClick(View v);
    public abstract void rightOnClick(View v);

}
