package com.example.notimportant.unicomicsviewer.activity;

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
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.notimportant.unicomicsviewer.POJO.Series;
import com.example.notimportant.unicomicsviewer.R;
import com.example.notimportant.unicomicsviewer.adapter.SeriesAdapter;

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

    private static ArrayList<Series> seriesList;

    private ListView seriesListView;

    private static SeriesAdapter seriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        seriesList = new ArrayList<Series>();
        seriesListView = (ListView) findViewById(R.id.listView);

        new Parsing().execute();

    }

    private class Parsing extends AsyncTask<String, Void, String> {
        String PAGE_URL = "http://unicomics.ru/comics/series";

        @Override
        protected String doInBackground(String... arg) {
            Document doc;
            try {
                doc = Jsoup.connect(PAGE_URL).get();
                //получаем строчку с вызовом скрипта генерации пагинации
                Element pag = doc.getElementsByClass("pagination").first().getElementsByTag("script").first();
                String pagination = pag.data();
                //Log.d("TEST", pagination);
                //получаем количество страниц
                Pattern tp = Pattern.compile("^.*?', (.*?), .*$");
                Matcher tm = tp.matcher(pagination);
                tm.find();
                int page_count = Integer.valueOf(tm.group(1));

                //проходимся по всем страницам
                for(int i = 1; i <= page_count; i++){
                    Document series_page;
                    try {
                        series_page = Jsoup.connect(PAGE_URL + "/page/" + i).get();

                        Elements series_element = series_page.getElementsByClass("list_series");
                        seriesList = new ArrayList<>();
                        for(Element serie_element : series_element){
                            //получаем тайтл, ссылки на серию и превью обложки
                            String thumbURL = serie_element.getElementsByTag("img").first().attr("src");
                            String title = serie_element.getElementsByClass("list_h").first().text();
                            String seriesURL = serie_element.getElementsByClass("list_h").first().attr("href");
                            Log.d("TEST", thumbURL);

                            Series serie = new Series(title, "blah-blah", thumbURL, seriesURL);

                            //засовываем в лист
                            seriesList.add(serie);
                            //Log.d("TEST", "____________________");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //привязать адаптер к листу
            Log.d("TEST", "Сбор окончен");
            seriesAdapter = new SeriesAdapter(getApplicationContext(), seriesList);
            seriesListView.setAdapter(seriesAdapter);
        }
    }

    public static void refresh(){
        seriesAdapter.notifyDataSetChanged();
    }
}
