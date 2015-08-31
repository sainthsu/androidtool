package org.flakor.androidtool.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.taqi.supervisor.Debug;

/**
 * Created by saint on 11/18/13.
 */
public class NumPicker extends TextView
{
    private Paint paint;
    private int min = 0;
    private int max = 10;
    private int step = 1;

    private float prePoint;
    private boolean moved = false;

    public NumPicker(Context context)
    {
        super(context);
        this.setText(String.valueOf(min));
        // 首先定义一个paint
        paint = new Paint();
        // 设置颜色
        paint.setColor(Color.RED);
        // 设置样式-填充
        paint.setStyle(Paint.Style.STROKE);
    }

    public NumPicker(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public NumPicker(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.setText(String.valueOf(min));
        // 首先定义一个paint
        paint = new Paint();
        // 设置颜色
        paint.setColor(Color.BLUE);
        // 设置样式-填充
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                prePoint = event.getX();
                Debug.info("NumPicker","prePoint"+prePoint);
                break;
            case MotionEvent.ACTION_MOVE:
                moved = true;
                break;
            case MotionEvent.ACTION_UP:
                if(moved)
                {

                    float nowPoint = event.getX();
                    int current = Integer.valueOf(getText().toString());
                    if(nowPoint - prePoint < 0)
                    {
                        Debug.info("NumPicker","curPoint"+nowPoint);
                        if(current+step < max)
                        {
                            setText(String.valueOf(current+step));
                        }
                        else
                        {
                            setText(String.valueOf(max));
                        }
                    }
                    else if (nowPoint - prePoint > 0)
                    {
                        if(current-step > min)
                        {
                            setText(String.valueOf(current-step));
                        }
                        else
                        {
                            setText(String.valueOf(min));
                        }
                    }
                    moved = false;
                }
                break;
        }

        return true;
    }

    public void setMin(int min)
    {
        this.min = min;
        this.setText(String.valueOf(min));
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public void setStep(int step)
    {
        this.step = step;
    }

    public void plus()
    {
        int current = Integer.valueOf(getText().toString());

        if(current+step < max)
        {
             setText(String.valueOf(current + step));
        }
        else
        {
             setText(String.valueOf(max));
        }

    }

    public void minus()
    {
        int current = Integer.valueOf(getText().toString());

        if(current-step > min)
        {
            setText(String.valueOf(current-step));
        }
        else
        {
            setText(String.valueOf(min));
        }
    }
}
