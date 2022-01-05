package com.example.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDiary_SQL extends SQLiteOpenHelper {
    //為資料庫命名
    private static final String name="mydatabase.db";
    private static final int version=1;
    public MyDiary_SQL(Context context){
        super(context,name ,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //PRTMARY KEY 此表示唯一的值，不能重複
        //NOT NULL 不能是空值

        //新增表格
        db.execSQL("CREATE TABLE myTable(topic text PRIMARY KEY NOT NULL,date text NOT NULL,content text NOT NULL);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        //實現刪除指定動作
        db.execSQL("DROP TABLE IF EXISTS myTable");
        onCreate(db);
    }
}
