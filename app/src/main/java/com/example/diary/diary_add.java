package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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

    public ArrayAdapter<String> adapter;
    private ArrayList<String> diary_items=new ArrayList<>();
    //連結介面元件參數宣告
    private TextView result_date; //顯示選擇日期
    private EditText diary_content,diary_topic;
    private SQLiteDatabase mydiary;
    private ListView listView;
    private Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        Button sel_date=(Button) findViewById(R.id.sel_date);
        result_date=(TextView) findViewById(R.id.result_date);
        sel_date.setOnClickListener(sel_dateOnClick);

        //連結介面元件
        diary_content=(EditText) findViewById(R.id.diary_content);
        diary_topic=(EditText) findViewById(R.id.diary_topic);
        listView=(ListView)findViewById(R.id.diary_list);
        btn_save=(Button)findViewById(R.id.add_button);
        //宣告Adapter
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,diary_items);
        listView.setAdapter(adapter);
        //取得資料庫實體
        mydiary=new MyDiary_SQL(this).getWritableDatabase();

        //儲存按鈕
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查詢myTable資料表

                if(diary_topic.length()<1 || diary_content.length()<1)
                    Toast.makeText(diary_add.this,"請填寫主題及日記內容",Toast.LENGTH_SHORT).show();
                else {
                    try {
                        mydiary.execSQL("INSERT INTO myTable(diary_topic,result_diary,diary_content)VALUES(?,?,?)",
                                new Object[]{diary_topic.getText().toString(),
                                result_date.getText().toString(),
                                diary_content.getText().toString()});
                        Toast.makeText(diary_add.this,"新增日記"+diary_topic.getText().toString()+
                                "日記日期:"+result_date.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        //清空輸入
                        diary_topic.setText("");
                        result_date.setText("日記時間:____年__月__日");
                        diary_content.setText("");
                    }catch (Exception e){
                        Toast.makeText(diary_add.this, "儲存失敗:"+e.toString(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

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
        mydiary.close();
    }

}

