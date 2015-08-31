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

package org.flakor.androidtool.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.taqi.supervisor.Debug;
import com.taqi.supervisor.json.AssessTable;
import com.taqi.supervisor.json.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by saint(saint@aliyun.com) on 11/22/13.
 */
public class AssessService
{
    private DBOpenHelper openHelper;

    public AssessService(Context context)
    {
        openHelper = new DBOpenHelper(context);
    }

    /**
     *
     * @param tablePosition 表在数组中的位置，作为唯一标识符
     * @param directId 督导ID
     * @param branchId 分店ID
     * @return
     */
    public AssessTable getAssessTable(int tablePosition,int directId,int branchId)
    {
        AssessTable table = null;
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select jsonStr from assessTable where tablePosition=? and directId=? and branchId=?",
                new String[]{String.valueOf(tablePosition),String.valueOf(directId),String.valueOf(branchId)});
        if(cursor.moveToFirst())
        {
            String jsonStr = cursor.getString(0);
            Debug.error("database",jsonStr);
            if(jsonStr != null)
            try
            {
                JSONObject object = new JSONObject(jsonStr);
                table = JsonParser.parseAssessTable(object,true);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }

        cursor.close();
        db.close();
        return table;
    }

    public void saveAssessTable(int tablePosition,int directId,int branchId,String jsonStr)
    {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();

        try
        {
            db.execSQL("delete from assessTable where tablePosition=? and directId=? and branchId=?", new Object[]{tablePosition,directId,branchId});
            db.execSQL("insert into assessTable(tablePosition,directId, branchId, jsonStr) values(?,?,?,?)",
                        new Object[]{tablePosition,directId, branchId, jsonStr});
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }

        db.close();
    }

    public ArrayList<History> getAssessHistory()
    {
        ArrayList<History> histories = new ArrayList<History>();
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select jsonStr,saveTime,branchId,tablePosition,directId from assessTable",null);
        if(cursor.getCount() == 0)
        {
            cursor.close();
            db.close();
            return histories;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            String json = cursor.getString(0);
            String time = cursor.getString(1);
            int branch = cursor.getInt(2);
            int position = cursor.getInt(3);
            int direct = cursor.getInt(4);

            History history = new History(json,time);
            history.setBranch(branch);
            history.setPosition(position);
            history.setDirect(direct);

            histories.add(history);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return histories;
    }

    public void updateAssessTable(int tablePosition,int directId,int branchId,String jsonStr)
    {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();

        try
        {
            db.execSQL("update assessTable set jsonStr=? where tablePosition=? and directId=? and branchId=?",
                        new Object[]{jsonStr,tablePosition, directId, branchId});
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }

        db.close();
    }

    public void deleteAssessTable(int tablePosition,int directId,int branchId)
    {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("delete from assessTable where tablePosition=? and directId=? and branchId=?", new Object[]{tablePosition,directId,branchId});
        db.close();
    }

    public void savePhoto(int directId,int branchId, int group,int child,int item)
    {
        /*
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();

        try
        {
            db.execSQL("insert into assessTable(directId, branchId, jsonStr) values(?,?,?)",
                    new Object[]{directId, branchId, jsonStr});
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }

        db.close();
        */
    }

    public void deletePhoto(String name)
    {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("delete from assessPhoto where photoName=?", new Object[]{name});
        db.close();
    }

}
