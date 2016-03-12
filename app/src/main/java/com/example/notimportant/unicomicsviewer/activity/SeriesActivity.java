package com.example.notimportant.unicomicsviewer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notimportant.unicomicsviewer.POJO.Comics;
import com.example.notimportant.unicomicsviewer.R;
import com.example.notimportant.unicomicsviewer.adapter.ComicsAdapter;
import com.example.notimportant.unicomicsviewer.parsing.OneSerieParser;

import java.util.ArrayList;

public class SeriesActivity extends AppCompatActivity {

    private ComicsAdapter comicsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        //получаем intent, чтобы узнать, какой комикс мы открываем
        Intent intent = getIntent();

        //for testing purpose ))
        this.setTitle(intent.getStringExtra("title"));
        TextView description = (TextView)findViewById(R.id.serieDesc);
        String serieURL = intent.getStringExtra("serieURL");
        description.setText(serieURL);

        ArrayList<Comics> comicsList = new ArrayList<Comics>();
        ListView comicsListView = (ListView) findViewById(R.id.comicsListView);

        comicsAdapter = new ComicsAdapter(this, comicsList);
        comicsListView.setAdapter(comicsAdapter);

        comicsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
            }
        });
        OneSerieParser oneSerieParser = new OneSerieParser(serieURL, comicsList, comicsAdapter, this);
        oneSerieParser.run();
    }
}
