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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.flakor.androidtool.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 *
 * @author Saint Hsu
 */
public class ScreenInfo 
{
    DisplayMetrics metrics;
    public float density;
    public float scaledDensity; //use for font

    public int widthPx;
    public int heightPx;

    /*
     *  android:anyDensity="false"
          <supports-screens android:smallScreens="true"
		android:normalScreens="true"
		android:largeScreens="true"
		android:resizeable="true"
		android:anyDensity="false" />
     */
    
    public ScreenInfo(Context context)
    {
        metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        density = metrics.density;
        scaledDensity = metrics.scaledDensity;
        widthPx = metrics.widthPixels;
        heightPx = metrics.heightPixels;

        //widthPx = (int) (metrics.widthPixels * density + 0.5f);

        Log.e("ScreenInfo", "density=" + density + ",widthPx=" + widthPx + ",heightPx=" + heightPx);
    }

    public float pixelToDip(int px)
    {
        return px/density + 0.5f;
    }

    public int pipToPixel(float dip)
    {
        return (int) (dip*density + 0.5f);
    }

    public float pixelToSp(int px)
    {
        return px/scaledDensity + 0.5f;
    }

    public int spToPixel(float dip)
    {
        return (int) (dip*scaledDensity + 0.5f);
    }

}