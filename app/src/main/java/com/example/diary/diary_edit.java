package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class diary_edit extends AppCompatActivity {
    //定義生命週期Tag標籤
    private static final String LOG_TAG_e="diary_editLifeCycle";
    private EditText diary_content,diary_topic;
    private TextView result_date;
    private TextView lbl_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG_e,"diary_cover.onCreate");
        setContentView(R.layout.activity_diary_edit);
        int int_id = 0;
        String str_topic = "";
        String str_data = "";
        String str_content = "";
        result_date=(TextView) findViewById(R.id.result_date);
        diary_content=(EditText) findViewById(R.id.diary_content);
        diary_topic=(EditText) findViewById(R.id.diary_topic);
        lbl_id=(TextView) findViewById(R.id.lbl_id);

        Intent it2 = getIntent();
        String strcustid = it2.getStringExtra("pid");

        Cursor cursor = db.rawQuery("SELECT * FROM myTable where _id=" + strcustid ,null);

        if (cursor != null && cursor.getCount() >=0)
        {
            while (cursor.moveToNext()) {
                int_id = cursor.getInt(0);
                str_topic = cursor.getString(1);
                str_data = cursor.getString(2);
                str_content = cursor.getString(3);
            }
        }

        diary_topic.setText(str_topic);
        result_date.setText(str_data);
        diary_content.setText(str_content);
        lbl_id.setText(String.valueOf(int_id));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}