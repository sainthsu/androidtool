package org.flakor.androidtool.net.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.taqi.supervisor.Debug;
import com.taqi.supervisor.utils.SDcardUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saint on 11/7/13.
 */
public class HttpThread extends Thread
{
    public final static String TAG = "httpThread";

    public final static String SERVER_HOST_REMOTE = "http://dudao.hztqkj.com/";
    public final static String SERVER_URL_REMOTE = "http://dudao.hztqkj.com/a.aspx";

    //public final static String SERVER_HOST_REMOTE = "http://192.168.1.5/";
    //public final static String SERVER_URL_REMOTE = "http://192.168.1.5/a.aspx";
    public final static String SERVER_URL_DOWNLOAD_PHOTO = SERVER_HOST_REMOTE+"UploadFile/Assess/";

    public final static String POST_FUNC = "func";
    public final static String ACTION_LOGIN = "login";
    public final static String ACTION_POST = "postAssess";
    public final static String ACTION_HISTORY = "getAssess";

    public final static String POST_COMPANY = "company";
    public final static String POST_NAME = "name";
    public final static String POST_PWD = "pwd";
    public final static String POST_SRC = "src";
    public final static String POST_ASSESS = "assess";
    public final static String POST_START_TIME = "start";
    public final static String POST_END_TIME = "end";
    public final static String POST_BID = "bid";

    String result="";
    private String json,src;
    private Handler handler;

    public HttpThread(Handler handler)
    {
        super();
        this.handler = handler;
    }

    public void postAssess(String json,String src)
    {
        this.json = json;
        this.src = src;
        this.start();
    }

    @Override
    public void run()
    {
            Map<String, String> params = new HashMap<String, String>();
            params.put(POST_ASSESS, json);
            params.put(POST_FUNC,ACTION_POST);

            Map<String, File> files = new HashMap<String, File>();

            if(src.length() == 0 || src ==null || src.equals(""))
            {
                params.put(POST_SRC, "");
                files = null;
            }
            else
            {
                String photoPath = SDcardUtil.getPhotoHome();
                String[] photos = src.split("\\$");
                int length = photos.length;
                for(int i=0;i<length;i++)
                {
                    String name = photos[i];
                    Debug.error("HttpThread","image :"+name);
                    files.put(name, new File(photoPath+name));
                }

                params.put(POST_SRC, src);
            }

            try
            {
                result = HttpFileUpTool.post(SERVER_URL_REMOTE, params, files);
                Message message = new Message();
                message.what = 1;
                message.obj = result;

                handler.sendMessage(message);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                Log.i(TAG, e.toString());
            }

    }
}
