package com.example.notimportant.unicomicsviewer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notimportant.unicomicsviewer.POJO.Series;
import com.example.notimportant.unicomicsviewer.R;
import com.example.notimportant.unicomicsviewer.activity.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by notimportant on 01.03.16.
 */
public class SeriesAdapter extends BaseAdapter{

    private ArrayList<Series> list;
    private LayoutInflater layoutInflater;

    public SeriesAdapter(Context context, ArrayList<Series> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = layoutInflater.inflate(R.layout.serie_layout, parent, false);
        }

        final Series series = (Series) getSeries(position);

        TextView ru_title = (TextView) view.findViewById(R.id.rt_textView);
        ru_title.setText(series.getTitle());

//        TextView eng_title = (TextView) view.findViewById(R.id.et_textView);
//        eng_title.setText(series.getEngTitle());

//        TextView url_textview = (TextView) view.findViewById(R.id.url_textView);
//        url_textview.setText(series.getSeriesURL());

        //ОТОБРАЖАЕМ КАРТИНКУ? ЕЕЕЕ
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        ImageLoader.getInstance().displayImage(series.getThumbURL(), imageView);

        return view;
    }

    private Series getSeries (int position){
        return (Series) getItem(position);
    }

}
