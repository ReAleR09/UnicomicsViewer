package com.example.notimportant.unicomicsviewer.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notimportant.unicomicsviewer.POJO.Comics;
import com.example.notimportant.unicomicsviewer.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ComicsAdapter extends BaseAdapter{

    private ArrayList<Comics> list;
    private LayoutInflater layoutInflater;

    public ComicsAdapter(Context context, ArrayList<Comics> list) {
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
            view = layoutInflater.inflate(R.layout.comic_layout, parent, false);
        }

        final Comics comics = getComics(position);
        TextView name = (TextView) view.findViewById(R.id.number_name);
        name.setText(comics.getTitle());

        ImageView imageView = (ImageView) view.findViewById(R.id.number_thumb);
        ImageLoader.getInstance().displayImage(comics.getThumbUrl(), imageView);


        return view;
    }

    public Comics getComics (int position){
        return (Comics) getItem(position);
    }
}
