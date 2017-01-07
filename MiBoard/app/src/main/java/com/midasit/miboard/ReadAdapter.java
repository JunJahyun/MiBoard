package com.midasit.miboard;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.mime.MIME;

/**
 * Created by JiyoungPark on 2017. 1. 7..
 */
public class ReadAdapter extends BaseAdapter {
    private ArrayList<ReadData> source;
    private LayoutInflater layoutInflater;

    public ReadAdapter(Context context, ArrayList<ReadData> source) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.source = source;
    }

    @Override
    public int getCount() {
        return (source != null) ? source.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return ((source != null) && (position >= 0 && position < source.size()) ? source.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReadData data = (ReadData) getItem(position);
        final ReadHolder readHolder;

        if (convertView == null) {

            readHolder = new ReadHolder();
            convertView = layoutInflater.inflate(R.layout.layout_list_item, parent, false);

            readHolder.textView_title = (TextView) convertView.findViewById(R.id.textView_title);
            readHolder.textView_id = (TextView) convertView.findViewById(R.id.textView_id);
            readHolder.imageView_thumbnail = (ImageView) convertView.findViewById(R.id.imageView_thumbnail);

            convertView.setTag(readHolder);

        } else { readHolder = (ReadHolder) convertView.getTag(); }

        readHolder.textView_title.setText(data.getTitle());
        readHolder.textView_id.setText(data.getId());

        Glide.with(parent.getContext()).load("http://172.30.1.46:5009/photos/" + data.getImageName()).into(readHolder.imageView_thumbnail);

        return convertView;
    }
}
