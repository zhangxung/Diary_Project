package com.example.diary.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.TextView;
import com.example.diary.model.Diary;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DiaryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Diary> diaries;
    private SimpleDateFormat dateFormat;
    public DiaryAdapter(Context context, ArrayList<Diary> diaries) {
        this.context = context;
        this.diaries = diaries;

        this.dateFormat = new SimpleDateFormat("yyyy, MM/dd");
    }
    @Override
    public int getCount() {
        return diaries.size();
    }
    @Override
    public Diary getItem(int position) {
        return diaries.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.diary_item, null);
        }
        ImageView photoImage = (ImageView) convertView.findViewById(R.id.photo_image);
        TextView timeText = (TextView) convertView.findViewById(R.id.time_text);
        TextView noteText = (TextView) convertView.findViewById(R.id.note_text);
        if (!TextUtils.isEmpty(getItem(position).previewPhotoUri)) {
            photoImage.setImageURI(Uri.parse(getItem(position).previewPhotoUri));
        }
        timeText.setText(dateFormat.format(new Date(getItem(position).time)));
        noteText.setText(getItem(position).previewNote);
        return convertView;
    }
}