package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class diary_add extends AppCompatActivity {
    private TextView result_date; //顯示選擇日期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        Button sel_date=(Button) findViewById(R.id.sel_date);
        result_date=(TextView) findViewById(R.id.result_date);
        sel_date.setOnClickListener(sel_dateOnClick);
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
            result_date.setText("日記時間"+Integer.toString(year)+"年"+
                                Integer.toString(monthofyear+1)+"月"+
                                Integer.toString(dayofmonth)+"日");
        }
    };
}