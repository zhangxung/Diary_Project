package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //宣告物件
    private SQLiteDatabase db = null;
    private ListView diary_list;
    private Button add_diary;
    private String strid="";
    public Cursor cursor;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        cursor = db.rawQuery("SELECT * FROM mytable", null);
        //如果查詢有資料的話，就跟ListView做連結
        if (cursor != null && cursor.getCount() >= 0) {
            adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    new String[]{"_id", "topic"},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    0);
            diary_list.setAdapter(adapter);
            //db.close();
        }
        //監聽列表是否有被點選
        diary_list.setOnItemClickListener(this::onItemClick);
        //長按事件
        diary_list.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //定義AlertDialog.Builder 物件，當長按列表項的時候彈出確認刪除對話方塊
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("確定刪除?");
                builder.setTitle("刪除本篇日記");
                TextView tx_id=(TextView)view.findViewById(android.R.id.text1);//測試
                Integer count=cursor.getCount();//測試
                //選取現在目前資料所在位置
                Integer in_id=cursor.getPosition();
                Integer idd=in_id+1;
                //測試顯示訊息用
                prc_showmessage("id:"+tx_id.getText().toString()+" ");
                prc_showmessage("count:"+count+" ");
                //新增AlertDialog.Builder物件的setPositiveButton()方法

                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String d_data="DELETE from myTable WHERE _id="+idd;
                        db.execSQL(d_data);
                        //透過執行緒，更新ListView的資料
                        new RefreshList().execute();
                    }
                //新增AlertDialog.Builder物件的setNegativeButton()方法
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                //要設定為true，才不會觸發到onitemclick
                return true;
            }
             class RefreshList extends AsyncTask<Void,Void,Cursor>{

                @Override
                protected Cursor doInBackground(Void... voids) {
                    //在執行緒中，重新取得資料庫的資料，在回傳新的Cursor
                    Cursor newCursor=db.rawQuery("SELECT * FROM myTable WHERE _id",null);
                    return newCursor;
                }
                protected void onPostExecute(Cursor newCursor){
                    //更新Cursor再關，回到原來Cursor
                    adapter.changeCursor(newCursor);
                    cursor.close();
                    cursor=newCursor;
                }
            }
        });

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
    //顯示訊息
    public void prc_showmessage(String strmessage){
        Toast objtoast=Toast.makeText(this,strmessage,Toast.LENGTH_LONG);
        objtoast.show();
    }
}


