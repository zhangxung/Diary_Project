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
    //連結介面元件參數宣告
    private SQLiteDatabase db = null; //資料庫名稱
    private EditText topic; //日記的主題
    private TextView date; //顯示選擇日期
    private EditText content; //日記的內文
    private TextView id; //編號

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //設定連接的介面佈局檔
        setContentView(R.layout.activity_diary_edit);
        //連結介面元件
        topic = (EditText)findViewById(R.id.diary_topic);
        date = (TextView)findViewById(R.id.result_date);
        content = (EditText)findViewById(R.id.diary_content);
        id = (TextView)findViewById(R.id.Id);
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
        topic.setText(strtopic);
        date.setText(strdate);
        content.setText(strcontent);
        id.setText(String.valueOf(int_id));
    }

    //更新日記按鈕設定
    public void btnUpdate(View v)
    {
        String strupdatesql = "UPDATE mytable SET " +
                "topic='" + topic.getText().toString() + "', " +
                "date='" + date.getText().toString() + "', " +
                "content='" + content.getText().toString() + "' " +
                "WHERE _id=" + id.getText();
        db.execSQL(strupdatesql);
        //完成新增後，要呼叫列表的Activity，利用Intent物件。
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
        //關閉本身的Activity。
        db.close();
        this.finish();
    }

    //取消編輯按鈕設定
    public void btnCancel(View v) {
        //按下「取消」，就呼叫列表的Activity，利用Intent物件。
        Intent it = new Intent(this,MainActivity.class);
        startActivity(it);
        //關閉本身的Activity。
        db.close();
        this.finish();
    }
}
