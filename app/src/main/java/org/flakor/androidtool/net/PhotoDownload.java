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

package org.flakor.androidtool.net.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.taqi.supervisor.utils.HttpUtil;

import java.util.ArrayList;

/**
 * Created by saint(saint@aliyun.com) on 11/27/13.
 */
public class PhotoDownload implements Runnable
{
    private ArrayList<String> photoNames;
    private int branchId;

    public PhotoDownload(ArrayList<String> photoNames, int branchId)
    {
        this.photoNames = photoNames;
        this.branchId = branchId;
    }

    @Override
    public void run()
    {
        boolean result = false;
        for(String name : photoNames)
        {
            result = HttpUtil.downLoadHistoryPhoto(name,branchId);
            if(!result)
                break;
        }

        Looper main = Looper.getMainLooper();
        MyHandler handler = new MyHandler(main);

        if(result)
        {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
        else
        {
            Message message = new Message();
            message.what = 0;
            handler.sendMessage(message);
        }
    }

    class MyHandler extends Handler
    {
        MyHandler(Looper looper)
        {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                break;
                case 1:
                break;
            }

        }
    }
}
