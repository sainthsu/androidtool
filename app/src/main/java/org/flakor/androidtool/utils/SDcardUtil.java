package org.flakor.androidtool.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class SDcardUtil
{
    private String SDPATH;

    public File createSDFile(String fileName) throws Exception
    {
        File file = new File(SDPATH + fileName);
        System.out.println("file path:" + file.getAbsolutePath());
        if (!file.exists())
        {
            file.createNewFile();
        }
        else
        {
            file.delete();
            file.createNewFile();
        }
        return file;
    }

    public boolean isFileExist(String fileName)
    {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    public File createSDDir(String dirName)
    {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist)
        {
            SDPATH = Environment.getExternalStorageDirectory() + File.separator;
            System.out.println(SDPATH);
        }
        File dir = new File(SDPATH + dirName);
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        return dir;
    }

    public File Write2SDFromInput(String path, String fileName,InputStream input)
    {

        File file = null;
        OutputStream output = null;
        try
        {
            createSDDir(path);
            file = createSDFile(path + File.separator+fileName);
            output = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = input.read(b)) != -1)
            {
                output.write(b, 0, len);
            }
            output.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                output.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String getPhotoHome()
    {
        String path = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist)
        {
            String sdPath = Environment.getExternalStorageDirectory() + File.separator;
            path = sdPath + "supervisor/photo/";

            File imagePath = new File(path);
            if (!imagePath.exists())
            { // 如果目录不存在，则创建一个名为"finger"的目录
                imagePath.mkdirs();
            }
        }

        return path;
    }

    public static String getDocHome()
    {
        String path = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist)
        {
            String sdPath = Environment.getExternalStorageDirectory() + File.separator;
            path = sdPath + "supervisor/doc/";

            File imagePath = new File(path);
            if (!imagePath.exists())
            { // 如果目录不存在，则创建一个名为"finger"的目录
                imagePath.mkdirs();
            }
        }

        return path;
    }

    public static boolean deletePhoto(String filename)
    {
        boolean result = false;
        String fileUrl = getPhotoHome()+filename;
        File file = new File(fileUrl);
        result = file.delete();
        return result;
    }
}
