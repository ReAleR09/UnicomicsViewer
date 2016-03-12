package com.example.notimportant.unicomicsviewer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notimportant.unicomicsviewer.POJO.Comics;
import com.example.notimportant.unicomicsviewer.R;

import java.util.ArrayList;

public class SeriesActivity extends AppCompatActivity {

    private ArrayList<Comics> comicsList;
    private ListView comicsListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        //получаем intent, чтобы узнать, какой комикс мы открываем
        Intent intent = getIntent();
        comicsListView = (ListView)findViewById(R.id.comicsListView);
        //for testing purpose ))
        this.setTitle(intent.getStringExtra("title"));
        TextView description = (TextView)findViewById(R.id.serieDesc);
        description.setText(intent.getStringExtra("serieURL"));
        
    }
}
