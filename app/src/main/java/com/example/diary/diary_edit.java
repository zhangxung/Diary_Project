package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class diary_edit extends AppCompatActivity {

    //宣告物件
    private SQLiteDatabase db = null;
    private EditText txt_topic;
    private EditText txt_date;
    private EditText txt_content;
    private TextView lbl_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);

        txt_topic = (EditText)findViewById(R.id.ed_topic);
        txt_date = (EditText)findViewById(R.id.ed_date);
        txt_content = (EditText)findViewById(R.id.ed_content);
        lbl_id = (TextView)findViewById(R.id.lblid);

        //建立SQLite 資料庫的實體檔案
        db = openOrCreateDatabase("data.db", Context.MODE_PRIVATE,null);

        //建立資料表的SQL語法
        String strcreatedb = "CREATE TABLE IF NOT EXISTS " +
                "mytable (_id INTEGER PRIMARY KEY, topic TEXT, date TEXT, content TEXT) ";

        //執行SQL語法，建立資料表
        db.execSQL(strcreatedb);

        //再取得編輯的資料。
        Intent it2 = getIntent();
        String strcustid = it2.getStringExtra("pid");

        //查詢資料
        Cursor cursor = db.rawQuery("SELECT * FROM mytable where _id=" + strcustid ,null);

        int int_id = 0;
        String strtopic = "";
        String strdate = "";
        String strcontent = "";

        //如果查詢有資料的話，就跟ListView做連結
        if (cursor != null && cursor.getCount() >=0)
        {

            while (cursor.moveToNext()) {
                int_id = cursor.getInt(0);
                strtopic = cursor.getString(1);
                strdate = cursor.getString(2);
                strcontent = cursor.getString(3);
            }
        }
        txt_topic.setText(strtopic);
        txt_date.setText(strdate);
        txt_content.setText(strcontent);
        lbl_id.setText(String.valueOf(int_id));
    }

    public void prc_btnupdate(View v)
    {

        String strupdatesql = "UPDATE mytable SET " +
                "topic='" + txt_topic.getText().toString() + "', " +
                "date='" + txt_date.getText().toString() + "', " +
                "content='" + txt_content.getText().toString() + "' " +
                "WHERE _id=" + lbl_id.getText();

        db.execSQL(strupdatesql);

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