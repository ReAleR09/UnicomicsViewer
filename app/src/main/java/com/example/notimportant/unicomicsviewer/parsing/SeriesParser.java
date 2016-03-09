package com.example.notimportant.unicomicsviewer.parsing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.notimportant.unicomicsviewer.POJO.Series;
import com.example.notimportant.unicomicsviewer.adapter.SeriesAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by notimportant on 09.03.16.
 */
public class SeriesParser{

    private ArrayList seriesList;
    private SeriesAdapter seriesAdapter;
    private ListView seriesListView;
    private Context context;

    public SeriesParser(ArrayList seriesList, SeriesAdapter seriesAdapter, ListView seriesListView, Context context) {
        this.seriesList = seriesList;
        this.seriesAdapter = seriesAdapter;
        this.seriesListView = seriesListView;
        this.context = context;

    }

    public void run(){
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

                seriesList.clear();

                //проходимся по всем страницам
                for(int i = 1; i <= page_count; i++){
                    Document series_page;
                    try {
                        series_page = Jsoup.connect(PAGE_URL + "/page/" + i).get();
                        Elements series_element = series_page.getElementsByClass("list_series");

                        for(Element serie_element : series_element){
                            //получаем тайтл, ссылки на серию и превью обложки
                            String thumbURL = serie_element.getElementsByTag("img").first().attr("src");
                            String title = serie_element.getElementsByClass("list_h").first().text();
                            //TODO
                            //гетать eng-title
                            String seriesURL = serie_element.getElementsByClass("list_h").first().attr("href");
                            Log.d("TEST", thumbURL);

                            Series serie = new Series(title, " ", thumbURL, seriesURL);

                            //засовываем в лист
                            seriesList.add(serie);
                            //TODO
                            //попробвать апдейтить список в прямом эфире seriesAdapter.notifyDataSetChanged();
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
            seriesAdapter = new SeriesAdapter(context, seriesList);
            seriesListView.setAdapter(seriesAdapter);
        }
    }

}