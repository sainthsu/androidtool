package org.flakor.androidtool.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.flakor.androidtool.R;

/**
 * Created by saint on 11/5/13.
 */
public class ActionBar extends LinearLayout
{
    private static final String TAG = "ActionBar";
    private static final boolean Debug = false;

    private Context context;

    //private Drawable iconDrawable;
    private ImageView icon;
    private TextView title;
    private ImageView menu;

    private ActionListener listener;

    public ActionBar(Context context)
    {
        super(context);
        this.context = context;
        initView();
    }

    public ActionBar(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ActionBar(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomActionBar);

        String titleText = "";
        int color = 0xffffff;
        float size = 20;
        int iconDrawable = R.drawable.menu_normal;
        int menuDrawable = R.drawable.triangle;

        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomActionBar_iconMargin:
                    break;
                case R.styleable.CustomActionBar_titleText:
                    titleText = a.getString(attr);
                    break;
                case R.styleable.CustomActionBar_titleColor:
                    color = a.getColor(attr,0xffffff);
                    break;
                case R.styleable.CustomActionBar_titleSize:
                    size = a.getDimensionPixelSize(attr,20);
                    break;
                case R.styleable.CustomActionBar_iconDrawable:
                    iconDrawable = a.getResourceId(attr,R.drawable.menu_normal);
                    break;
                case R.styleable.CustomActionBar_menuDrawable:
                    menuDrawable = a.getResourceId(attr,R.drawable.triangle);
                    break;
            }
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        this.setOrientation(HORIZONTAL);

        LayoutParams iconParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iconParams.setMargins(10,10,10,10);
        icon = new ImageView(context);
        icon.setImageResource(iconDrawable);
        icon.setLayoutParams(iconParams);
        this.addView(icon);

        LayoutParams titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.gravity = Gravity.CENTER;
        title = new TextView(context);
        title.setText(titleText);
        title.setTextColor(color);
        title.setTextSize(size);
        title.setLayoutParams(titleParams);
        this.addView(title);

        LayoutParams menuParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menu = new ImageView(context);
        menu.setImageResource(menuDrawable);
        menu.setVisibility(View.INVISIBLE);
        menu.setLayoutParams(menuParams);
        this.addView(menu);

        a.recycle();

    }

    private void initView()
    {
        View view = LayoutInflater.from(context).inflate(R.layout.action_bar,null);
        icon = (ImageView)view.findViewById(R.id.left_icon);
        title = (TextView)view.findViewById(R.id.middle_title);
        menu = (ImageView)view.findViewById(R.id.right_icon);
        this.addView(icon);
        this.addView(title);
        this.addView(menu);
    }

    public void setListener(ActionListener listener)
    {
        if(this.listener == listener)
        {
            return;
        }
        this.listener = listener;
        this.icon.setOnClickListener(this.listener);
        this.menu.setOnClickListener(this.listener);
    }

    public void setTitle(String titleStr)
    {
        title.setText(titleStr);
    }

    public void setTitle(int resString)
    {
        title.setText(resString);
    }

    public void setIcon(int resId)
    {
        this.icon.setImageResource(resId);
    }

    public void setMenu(int resId)
    {
        this.menu.setImageResource(resId);
    }

}
