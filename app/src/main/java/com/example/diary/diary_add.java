package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class diary_add extends AppCompatActivity {

    //宣告物件
    private SQLiteDatabase db = null;
    private EditText txt_topic;
    private EditText txt_date;
    private EditText txt_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        txt_topic = (EditText)findViewById(R.id.ed_topic);
        txt_date = (EditText)findViewById(R.id.ed_date);
        txt_content = (EditText)findViewById(R.id.ed_content);

        //建立SQLite 資料庫的實體檔案
        db = openOrCreateDatabase("data.db", Context.MODE_PRIVATE,null);

        //建立資料表的SQL語法
        String strcreatedb = "CREATE TABLE IF NOT EXISTS " +
                "mytable (_id INTEGER PRIMARY KEY, topic TEXT, date TEXT, content TEXT) ";

        //執行SQL語法，建立資料表
        db.execSQL(strcreatedb);
    }

    public void prc_btnsave(View v)
    {
        String strinsertsql = "INSERT INTO mytable (topic,date,content) values " +
                "('" + txt_topic.getText().toString() + "','" + txt_date.getText().toString() + "','" + txt_content.getText().toString() + "')";

        db.execSQL(strinsertsql);

        //完成新增後，要呼叫列表的Activity，利用Intent物件。
        Intent it = new Intent(this,MainActivity.class);

        startActivity(it);

        //關閉本身的Activity。
        db.close();
        this.finish();
    }

    public void prc_cancel(View v)
    {
        //按下「取消」，就呼叫列表的Activity，利用Intent物件。
        Intent it = new Intent(this,MainActivity.class);

        startActivity(it);

        //關閉本身的Activity。
        db.close();
        this.finish();
    }
}