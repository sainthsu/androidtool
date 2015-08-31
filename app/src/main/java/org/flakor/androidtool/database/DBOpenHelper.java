package org.flakor.androidtool.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by saint on 11/21/13.
 */
public class DBOpenHelper extends SQLiteOpenHelper
{
    private static final String DBNAME = "supervisor.db";
    private static final int VERSION = 1;

    public DBOpenHelper(Context context)
    {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog (id integer primary key autoincrement, downpath varchar(100), threadid INTEGER, downlength INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS assessTable (id integer primary key autoincrement, tablePosition INTEGER,branchId INTEGER, directId INTEGER, jsonStr TEXT,saveTime DATETIME DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')))");
        //db.execSQL("CREATE TABLE IF NOT EXISTS assessPhoto (id integer primary key autoincrement, tableId INTEGER, group INTEGER, child INTEGER, item INTEGER, photoName varchar(100),saveTime DATETIME)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS filedownlog");
        db.execSQL("DROP TABLE IF EXISTS assessTable");
        db.execSQL("DROP TABLE IF EXISTS assessPhoto");
        onCreate(db);
    }
}
