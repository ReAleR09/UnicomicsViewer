package com.example.notimportant.unicomicsviewer.parsing;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notimportant.unicomicsviewer.POJO.Comics;
import com.example.notimportant.unicomicsviewer.POJO.Series;
import com.example.notimportant.unicomicsviewer.R;
import com.example.notimportant.unicomicsviewer.adapter.ComicsAdapter;
import com.example.notimportant.unicomicsviewer.adapter.SeriesAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class OneSerieParser {

    private String comicsUrl;
    private ArrayList<Comics> comicsList;
    private ComicsAdapter comicsAdapter;
    //TODO
    //ЧЕТА НЕБЕЗОПАСНА ЭТО ВОТ ВСЕ
    Activity activity;

    public OneSerieParser(String comicsUrl, ArrayList<Comics> comicsList, ComicsAdapter comicsAdapter, Activity activity) {
        this.comicsUrl = comicsUrl;
        this.comicsList = comicsList;
        this.comicsAdapter = comicsAdapter;
        this.activity = activity;
    }

    public void run(){
        new Parsing().execute();
    }

    private class Parsing extends AsyncTask<String, Comics, String> {
        String PAGE_URL = comicsUrl;
        String ru_title;
        String eng_title;
        String description;
        String year;
        String publisher;
        String picURL;

        int count;
        int i;

        @Override
        protected String doInBackground(String... arg) {
            Document doc;

            comicsList.clear();

            try {
                doc = Jsoup.connect(PAGE_URL).get();
                //Получаем инфо о Серии(Тайтлы, описание, главная обложка)

                ru_title = doc.getElementsByTag("h1").get(0).text();
                eng_title = doc.getElementsByTag("h2").get(0).text();
                Element middle_cont = doc.getElementById("middle_cont");

                picURL =
                        middle_cont.getElementsByClass("big_img").get(0)
                                .getElementsByTag("img").get(0)
                                .attr("src");


                description = middle_cont.getElementsByTag("p").get(1).text();
                Elements tds = middle_cont.getElementsByTag("td");
                year = tds.get(2).text();
                publisher = tds.get(4).text();

                //получаем список выпусков
                Elements numbersEl = doc.getElementsByClass("list_comics");
                count = numbersEl.size();
                i = 0;
                publishProgress();
                for(Element el : numbersEl){
                    String thumbUrl =
                            el.getElementsByClass("l_com").get(0)
                            .getElementsByTag("img").get(0).attr("src");
                    String numberNameRu =
                            el.getElementsByClass("list_h").get(0)
                            .text();
                    Log.d("TEST", numberNameRu);
                    String comicsUrl =
                            el.getElementsByClass("button").get(1)
                            .getElementsByTag("a").get(0)
                            .attr("href");

                    Comics comics = new Comics(numberNameRu, thumbUrl, comicsUrl);
                    i++;
                    publishProgress(comics);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Comics... values) {
            super.onProgressUpdate(values);
            //засовываем в лист
            if(values.length != 0){
                comicsList.add(values[0]);
                //сообщаем адаптеру, чтобы он обновил список
                comicsAdapter.notifyDataSetChanged();
            }

            TextView textView = (TextView)activity.findViewById(R.id.serieTitleRus);
            textView.setText("ЗАГРУЗКА " + i + "/" + count);

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("TEST", "Сбор инфы о серии окончен");
            ImageView imageView = (ImageView) activity.findViewById(R.id.comics_cover);
            ImageLoader.getInstance().displayImage(picURL, imageView);

            TextView serieTitleRus = (TextView) activity.findViewById(R.id.serieTitleRus);
            serieTitleRus.setText(ru_title);

            TextView serieTitleEng = (TextView) activity.findViewById(R.id.serieTitleEng);
            serieTitleEng.setText(eng_title);

            TextView serieDesc = (TextView) activity.findViewById(R.id.serieDesc);
            serieDesc.setText(description);

            TextView serieYear = (TextView) activity.findViewById(R.id.serieYear);
            serieYear.setText(year);

            TextView seriePublisher = (TextView) activity.findViewById(R.id.seriePublisher);
            seriePublisher.setText(publisher);


        }
    }
}
