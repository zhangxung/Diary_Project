package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    //宣告物件
    private SQLiteDatabase db = null;
    private ListView diary_list;
    private Button add_diary;

    //定義生命週期Tag標籤
    //private static final String LOG_TAG="ActivityLifeCycle";
    //private Button add_diary, exit_diary;
    //宣告前往新增日記頁面
    //public Intent intent_toadd,intent_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(LOG_TAG,"MainActivity.onCreate");
        setContentView(R.layout.activity_main);

        diary_list = (ListView) findViewById(R.id.diary_list);
        add_diary = (Button) findViewById(R.id.add_diary);

        //建立SQLite 資料庫的實體檔案
        db = openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);

        //建立資料表的SQL語法
        String strcreatedb = "CREATE TABLE IF NOT EXISTS " +
                "mytable (_id INTEGER PRIMARY KEY, topic TEXT, date TEXT, content TEXT) ";

        //執行SQL語法，建立資料表
        db.execSQL(strcreatedb);

        //查詢資料
        Cursor cursor = db.rawQuery("SELECT * FROM mytable", null);

        //

        //如果查詢有資料的話，就跟ListView做連結
        if (cursor != null && cursor.getCount() >= 0) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    new String[]{"topic", "date"},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    0);

            diary_list.setAdapter(adapter);
            //db.close();
        }
        diary_list.setOnItemClickListener(this::onItemClick);

        //exit_diary=(Button)findViewById(R.id.exit_button);

//        add_diary.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                intent_toadd=new Intent(MainActivity.this,diary_add.class);
//                startActivity(intent_toadd);
//            }
//        });
//        exit_diary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent_back=new Intent(MainActivity.this,diary_cover.class);
//                startActivity(intent_back);
//            }
//        });
    }

    public void btnAdd(View v) {
        //按下新增時，要呼叫新增的Activity，利用Intent物件。
        Intent it = new Intent(this, diary_add.class);

        startActivity(it);

        //關閉本身的Activity。
        db.close();
        this.finish();
    }

    //@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //取得按下ListView的那個Item的值。
        TextView _id = (TextView) view.findViewById(android.R.id.text1);

        //按下Item時，要呼叫編輯的Activity，利用Intent物件帶參數過去。
        Intent it = new Intent(this, diary_edit.class);

        it.putExtra("pid", _id.getText().toString());

        startActivity(it);

        //關閉本身的Activity。
        db.close();
        this.finish();
    }

    public void btnBack(View v) {
        //按下新增時，要呼叫新增的Activity，利用Intent物件。
        Intent it = new Intent(this, diary_cover.class);

        startActivity(it);

        //關閉本身的Activity。
        db.close();
        this.finish();
    }
}


