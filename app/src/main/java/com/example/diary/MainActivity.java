package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{
    //定義生命週期Tag標籤
    private static final String LOG_TAG="ActivityLifeCycle";
    private Button add_diary,exit_diary;
    private ListView diary_list;
    //宣告前往新增日記頁面
    public Intent intent_toadd,intent_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"MainActivity.onCreate");
        setContentView(R.layout.activity_main);

        private SQLiteDatabase db = null;
        add_diary=(Button)findViewById(R.id.add_diary);
        exit_diary=(Button)findViewById(R.id.exit_button);
        diary_list = (ListView) findViewById(R.id.diary_list);


        db = openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);

        String strcreatedb = "CREATE TABLE myTable(_id INTEGER PRIMARY KEY,topic text NOT NULL ,date text NOT NULL,content text NOT NULL)";

        db.execSQL(strcreatedb);

        String str1 = "INSERT INTO myTable (topic,date,content) values " +
                "('a','20201202','abc')";
        db.execSQL(str1);

        str1 = "INSERT INTO myTable (topic,date,content) values " +
                "('b','20201203','bcd')";
        db.execSQL(str1);

        Cursor cursor = db.rawQuery("SELECT * FROM myTable",null);

        if (cursor != null && cursor.getCount() >=0) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    new String[]{"topic", "date"},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    0);

            diary_list.setAdapter(adapter);
            db.close();
        }
        diary_list.setOnItemClickListener(this);
        /*
        Button toadd_diary=(Button) findViewById(R.id.add_diary);
        Button back_cover=(Button)findViewById(R.id.exit_button);
         */
        //點擊新增日記按鈕
        add_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_toadd=new Intent(MainActivity.this,diary_add.class);
                startActivity(intent_toadd);
            }
        });
        //點擊離開主畫面(我的日記)
        exit_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_back=new Intent(MainActivity.this,diary_cover.class);
                startActivity(intent_back);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG,"MainActivity.onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG,"MainActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG,"MainActivity.onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG,"MainActivity.onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"MainActivity.onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG,"MainActivity.onRestart");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //取得按下ListView的那個Item的值。
        TextView txtid = (TextView) view.findViewById(android.R.id.text1);

        //按下Item時，要呼叫編輯的Activity，利用Intent物件帶參數過去。
        Intent it = new Intent(this,dairy_edit.class);
        it.putExtra("pid",txtid.getText().toString());
        startActivity(it);

        //關閉本身的Activity。
        this.finish();

    }
}