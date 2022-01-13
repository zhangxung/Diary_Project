package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //連結介面元件參數宣告
    private SQLiteDatabase db = null; //資料庫名稱
    private ListView diary_list; //列表清單
    private Button add_diary; //新增按鈕
    public Cursor cursor; //遊標
    SimpleCursorAdapter adapter; //連接後端數據和前端顯示的接口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //設定連接的介面佈局檔
        setContentView(R.layout.activity_main);
        //連結介面元件
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
        }

        //監聽列表是否有被點選
        diary_list.setOnItemClickListener(this::onItemClick);
        //長按事件設定
        diary_list.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //定義AlertDialog.Builder 物件，當長按列表項的時候彈出確認刪除對話方塊
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("確定刪除?");
                builder.setTitle("刪除本篇日記");
                //選取現在目前資料所在位置
                Integer in_id=cursor.getPosition();
                Integer idd=in_id+1;
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

             //更新cursor
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

    //新增日記按鈕設定
    public void btnAdd(View v) {
        //按下新增時，要呼叫新增的Activity，利用Intent物件。
        Intent it = new Intent(this, diary_add.class);
        startActivity(it);
        //關閉本身的Activity。
        db.close();
        this.finish();
    }

    //點擊listview的項目
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

    //離開日記按鈕設定
    public void btnBack(View v) {
        //按下新增時，要呼叫新增的Activity，利用Intent物件。
        Intent it = new Intent(this, diary_cover.class);
        startActivity(it);
        //關閉本身的Activity。
        db.close();
        this.finish();
    }
}


