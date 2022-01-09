package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    //宣告物件
    private SQLiteDatabase db = null;
    ListView list;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView)findViewById(R.id.list);
        btn_add = (Button) findViewById(R.id.btn_add);

        //建立SQLite 資料庫的實體檔案
        db = openOrCreateDatabase("data.db", Context.MODE_PRIVATE,null);

        //建立資料表的SQL語法
        String strcreatedb = "CREATE TABLE IF NOT EXISTS " +
                "mytable (_id INTEGER PRIMARY KEY, topic TEXT, date TEXT, content TEXT) ";

        //執行SQL語法，建立資料表
        db.execSQL(strcreatedb);

        //查詢資料
        Cursor cursor = db.rawQuery("SELECT * FROM mytable",null);

        //如果查詢有資料的話，就跟ListView做連結
        if (cursor != null && cursor.getCount() >=0)
        {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    new String[] {"_id","topic"},
                    new int[] {android.R.id.text1,android.R.id.text2},
                    0);

            list.setAdapter(adapter);
            //db.close();
        }
        list.setOnItemClickListener(this);
    }
    public void prc_btnadd(View v)
    {
        //按下新增時，要呼叫新增的Activity，利用Intent物件。
        Intent it = new Intent(this,diary_add.class);

        startActivity(it);

        //關閉本身的Activity。
        db.close();
        this.finish();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //取得按下ListView的那個Item的值。
        TextView txtid = (TextView) view.findViewById(android.R.id.text1);

        //按下Item時，要呼叫編輯的Activity，利用Intent物件帶參數過去。
        Intent it = new Intent(this,diary_edit.class);

        it.putExtra("pid",txtid.getText().toString());

        startActivity(it);

        //關閉本身的Activity。
        db.close();
        this.finish();
    }
}