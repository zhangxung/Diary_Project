package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    //定義生命週期Tag標籤
    private static final String LOG_TAG="ActivityLifeCycle";
    private Button add_diary,exit_diary;
    //宣告前往新增日記頁面
    public Intent intent_toadd,intent_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"MainActivity.onCreate");
        setContentView(R.layout.activity_main);
        add_diary=(Button)findViewById(R.id.add_diary);
        exit_diary=(Button)findViewById(R.id.exit_button);
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
}

