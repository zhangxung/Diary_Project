package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class diary_cover extends AppCompatActivity {
    //連結介面元件參數宣告
    private Button btn_enter; //進入按鈕
    public Intent intent_enter; //意圖進入
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}

