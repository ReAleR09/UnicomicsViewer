package com.example.notimportant.unicomicsviewer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.notimportant.unicomicsviewer.POJO.Series;
import com.example.notimportant.unicomicsviewer.R;
import com.example.notimportant.unicomicsviewer.adapter.SeriesAdapter;
import com.example.notimportant.unicomicsviewer.parsing.SeriesParser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private SeriesAdapter seriesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Подключаем подгрузчик картинок
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(config);

        //Настраиваем вид Главного Активити
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Настраиваем List Серий
        ArrayList<Series> seriesList = new ArrayList<Series>();
        ListView seriesListView = (ListView) findViewById(R.id.listView);


        //привязываем адаптер. Пока лист пустой, в парсере будет обновление листа при добавлении каждого елемента с уведомлением об этом адаптера
        seriesAdapter = new SeriesAdapter(this, seriesList);
        seriesListView.setAdapter(seriesAdapter);

        seriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
                //Он айтем клик, открывать окошко с описанием Серии и списком выпусков
                Series serie = seriesAdapter.getSeries(position);
                Intent intent = new Intent(getApplicationContext(), SeriesActivity.class);
                intent.putExtra("title", serie.getTitle());
                intent.putExtra("serieURL", serie.getSeriesURL());
                //запускам активити и передаем в него вышеуказанное
                startActivity(intent);
            }
        });

        //Запускаем парсер
        SeriesParser seriesParser = new SeriesParser(seriesList, seriesAdapter);
        seriesParser.run();

    }

}
