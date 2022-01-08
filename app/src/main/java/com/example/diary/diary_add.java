package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class diary_add extends AppCompatActivity {
    private static final String LOG_TAG_d="diary_addLifeCycle";
    //宣告資料庫名稱及資料表名稱
    static final String db_name="mydiary";//資料庫名稱
    static final String tb_name="mydiary_list"; //資料表名稱
    SQLiteDatabase mydiary; //資料庫物件
    //連結介面元件參數宣告
    public TextView result_date; //顯示選擇日期
    public EditText diary_content,diary_topic; //日記的主題以及內文
    private ListView listView; //日記列表
    private Button btn_save,sel_date;  //儲存日記按鈕及設定日期按鈕
    public Intent intent_back; //建立回我的日記的頁面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG_d,"diary_add.onCreate");
        //設定連接的介面佈局檔
        setContentView(R.layout.activity_diary_add);
        //連結介面元件
        result_date=(TextView) findViewById(R.id.result_date);
        diary_content=(EditText) findViewById(R.id.diary_content);
        diary_topic=(EditText) findViewById(R.id.diary_topic);
        listView=(ListView)findViewById(R.id.diary_list);
        btn_save=(Button)findViewById(R.id.add_button);
        sel_date=(Button) findViewById(R.id.sel_date);
        //選取日期
        sel_date.setOnClickListener(sel_dateOnClick);
        //開啟或建立資料庫
        mydiary=openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);
        String createTable="CREATE TABLE myTable(_id integer PRIMARY KEY,topic text NOT NULL ,date text NOT NULL,content text NOT NULL)";
        mydiary.execSQL(createTable);
        //儲存按鈕監聽
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查詢myTable資料表
                //確認是否有填寫欄位，不為空值
                if(diary_topic.length()<1 || diary_content.length()<1)
                    Toast.makeText(diary_add.this,"請填寫主題及日記內容",Toast.LENGTH_SHORT).show();
                else {
                    try {
                        //呼叫add_diary()方法寫入資料表
                        add_diary(diary_topic.getText().toString(),result_date.getText().toString(),diary_content.getText().toString());
                        //清空輸入
                        diary_topic.setText("");
                        result_date.setText("日記時間 : ____ 年 __ 月 __ 日");
                        diary_content.setText("");
                    }catch (Exception e){
                        Toast.makeText(diary_add.this, "儲存失敗:"+e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                //回到主畫面-我的日記
                intent_back=new Intent(diary_add.this,MainActivity.class);
                startActivity(intent_back);
            }
        });
    }
    private void add_diary(String topic_diary,String date_diary,String content_diary){
        /*
        將日記加入至資料表中儲存起來
        * */
        //設定三欄位的資料表
        ContentValues cv=new ContentValues(3);
        //將資料put進去
        cv.put("Topic",topic_diary);
        cv.put("Date",date_diary);
        cv.put("Content",content_diary);
        //將資料加入到資料表
        mydiary.insert(tb_name,null,cv);
    }
    private Button.OnClickListener sel_dateOnClick=new Button.OnClickListener(){
        public void onClick(View v){
            Calendar now=Calendar.getInstance();
            DatePickerDialog date=new DatePickerDialog(diary_add.this,
                    date_set,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH));
            date.setTitle("選擇日期");
            date.setMessage("請選擇您日記記錄的時間");
            date.setCancelable(false);
            date.show();
        }
    };
    private DatePickerDialog.OnDateSetListener date_set=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
                    result_date.setText("日記時間:"+Integer.toString(year)+"年"+
                            Integer.toString(monthofyear+1)+"月"+
                            Integer.toString(dayofmonth)+"日");
                }
            };
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(LOG_TAG_d,"MainActivity.onDestroy");
        mydiary.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG_d,"MainActivity.onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG_d,"MainActivity.onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG_d,"MainActivity.onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG_d,"MainActivity.onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG_d,"MainActivity.onRestart");
    }
}
