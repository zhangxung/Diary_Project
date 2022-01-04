package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import com.example.diary.ui.adapter.*;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import com.example.diary.model.DiaryDb;
import com.example.diary.model.Diary;
import java.util.ArrayList;

public class DiaryListActivity extends AppCompatActivity {
    private ListView diaryListView;
    private Button addDiaryButton;
    private DiaryDb diaryDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list_activity);
        setLayout();
        findView();
        setView();
        setListener();
    }
    private void setLayout(){
        setContentView(R.layout.diary_list_activity);
    }
    private void findView(){
        diaryListView = findViewById(R.id.diary_list);
        addDiaryButton = findViewById(R.id.add_diary_button);
    }
    private void setView(){
        if(diaryDb == null) {
            diaryDb = new DiaryDb(this);
        }
        ArrayList<Diary> diaries = diaryDb.getAllDiaries();
        DiaryAdapter diaryAdapter = new DiaryAdapter(this, diaries);
        diaryListView.setAdapter(diaryAdapter);
    }
    private void setListener(){
        addDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryListActivity.this, DiaryEditActivity.class);
                startActivity(intent);
            }
        });
        diaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diary diary = (Diary)diaryListView.getItemAtPosition(position);
                Intent intent = DiaryReadActivity.createIntent(DiaryListActivity.this, diary);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setView();
            }
        }, 500);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        diaryDb.close();
    }
}