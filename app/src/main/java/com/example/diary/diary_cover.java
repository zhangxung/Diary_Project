package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class diary_cover extends AppCompatActivity {
    private static final String LOG_TAG_c="diary_addLifeCycle";
    private Button btn_enter, btn_back;
    public Intent intent_enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG_c,"diary_cover.onCreate");
        //設定連接的介面佈局檔
        setContentView(R.layout.activity_diary_cover);
        //連結介面元件
        btn_enter=(Button) findViewById(R.id.enter_diary);

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_enter=new Intent(diary_cover.this,MainActivity.class);
                startActivity(intent_enter);
            }
        });
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