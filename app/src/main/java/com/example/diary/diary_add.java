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

    //宣告物件
    private SQLiteDatabase db = null;
    //連結介面元件參數宣告
    private EditText topic; //日記的主題
    private TextView date; //顯示選擇日期
    private EditText content; //日記的內文
    public TextView result_date; //顯示選擇日期
    private Button sel_date;  //設定日期按鈕
    static final String tb_name="mydiary_list"; //資料表名稱


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //設定連接的介面佈局檔
        setContentView(R.layout.activity_diary_add);
        //連結介面元件
        date = (TextView) findViewById(R.id.result_date);
        content = (EditText) findViewById(R.id.diary_content);
        topic = (EditText) findViewById(R.id.diary_topic);
        sel_date = (Button) findViewById(R.id.sel_date);
        //選取日期
        sel_date.setOnClickListener(sel_dateOnClick);
        //開啟或建立資料庫
        db = openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);
        //建立資料表的SQL語法
        String strcreatedb = " CREATE TABLE IF NOT EXISTS " +
                "myTable (_id INTEGER PRIMARY KEY, topic text NOT NULL " +
                ",date text NOT NULL, content text NOT NULL)";
        db.execSQL(strcreatedb);
    }
        //新增日記
        public void btnAdd (View v)
        {
            String strinsertsql = "INSERT INTO mytable (topic,date,content) values " +
                    "('" + topic.getText().toString() + "'," +
                    "'" + date.getText().toString() + "'," +
                    "'" + content.getText().toString() + "')";
            db.execSQL(strinsertsql);
            //完成新增後，要呼叫列表的Activity，利用Intent物件。
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            //關閉本身的Activity。
            db.close();
            this.finish();
        }
        //取消新增
        public void btnCancel (View v)
        {
            //按下「取消」，就呼叫列表的Activity，利用Intent物件。
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            //關閉本身的Activity。
            db.close();
            this.finish();
        }
    //設定日期
    private Button.OnClickListener sel_dateOnClick = new Button.OnClickListener() {
        public void onClick(View v) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog date = new DatePickerDialog(diary_add.this,
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
    //讀取時間
    private DatePickerDialog.OnDateSetListener date_set =
            new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthofyear, int dayofmonth) {
            date.setText(Integer.toString(year) + " 年 " +
                    Integer.toString(monthofyear + 1) + " 月 " +
                    Integer.toString(dayofmonth) + " 日 ");
        }
    };
}
