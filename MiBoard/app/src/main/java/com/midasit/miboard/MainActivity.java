package com.midasit.miboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;
    Button button_search, button_write;
    ListView listView_daily;
    private AsyncHttpClient asyncHttpClient, asyncPhotoHttpClient;
    ReadAdapter readAdapter;
    FileOutputStream outputStream;
    File file;
    ReadData data;
    ArrayList<ReadData> source;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initModel();
        initController();
        initListener();

        readDaily();
    }

    private void initModel() {
        backPressCloseHandler = new BackPressCloseHandler(this);
        asyncHttpClient = new AsyncHttpClient();
        asyncPhotoHttpClient = new AsyncHttpClient();
        source = new ArrayList<>();
        file = new File(getApplicationContext().getFilesDir(), "MiBoard");
    }

    private void initView() {
        button_search = (Button) findViewById(R.id.button_search);
        button_write = (Button) findViewById(R.id.button_write);
        listView_daily = (ListView) findViewById(R.id.listView_daily);
    }

    private void initController() {

        readAdapter = new ReadAdapter(getApplicationContext(), source);
        listView_daily.setAdapter(readAdapter);
    }

    private void initListener() {
        button_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WritingActivity.class));
            }
        });

        listView_daily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReadData rd = (ReadData) parent.getItemAtPosition(position);
                Bundle extras = new Bundle();
                extras.putString("id", rd.getImageName());
                extras.putString("title", rd.getTitle());
                extras.putString("content", rd.getContent());
                extras.putString("imageName", rd.getImageName());

                Intent intent = new Intent(getApplicationContext(), ReadingActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void readDaily() {
        asyncHttpClient.get(getString(R.string.default_url) + "read", new BaseJsonHttpResponseHandler<JSONArray>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {

            }

            @Override
            protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                JSONArray jsonArray;

                jsonArray = new JSONArray(rawJsonData);
                Log.e("jsonarray:", String.valueOf(jsonArray.getJSONObject(1).get("id")));
                Log.e("json", rawJsonData);


                for (int i = 0; jsonArray.getJSONObject(i) != null; i++) {
                    data = new ReadData();
                    data.setId(String.valueOf(jsonArray.getJSONObject(i).get("id")));
                    data.setTitle(String.valueOf(jsonArray.getJSONObject(i).get("title")));
                    data.setContent(String.valueOf(jsonArray.getJSONObject(i).get("content")));
                    data.setImageName(String.valueOf(jsonArray.getJSONObject(i).get("imageName")));

                    source.add(i, data);
                }
                return jsonArray;
            }
        });

        readAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
            backPressCloseHandler.onBackPressed();
    }
}
