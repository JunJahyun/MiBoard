package com.midasit.miboard;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class WritingActivity extends AppCompatActivity {

    Button button_Regist;
    EditText editText_title, editText_content;
    ImageView imageView_photo;
    MiboardData miboardData;
    String dir, filename;
    File file;
    private AsyncHttpClient asyncHttpClient;
    private static final int PICK_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        initView();
        initModel();
        initListener();
    }

    private void initView() {

        button_Regist = (Button) findViewById(R.id.button_default_layout1);
        editText_title = (EditText) findViewById(R.id.editText_title);
        editText_content = (EditText) findViewById(R.id.editText_content);
        imageView_photo = (ImageView) findViewById(R.id.imageView_photo);

        imageView_photo.setImageResource(R.mipmap.img_photo_default);

        editText_title.setText("170101");
        editText_content.setText("오늘은 데일리를 3카드로 뽑았다.");

        button_Regist.setVisibility(View.VISIBLE);
        button_Regist.setText("등록");
    }

    private void initModel() {
        miboardData = new MiboardData();
        asyncHttpClient = new AsyncHttpClient();
    }

    private void initListener() {

        imageView_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);

                intent.putExtra("crop", "true");
                intent.putExtra("outputX", 400);
                intent.putExtra("outputY", 250);

                try {
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });

        button_Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miboardData.setTitle(editText_title.getText().toString());
                miboardData.setContent(editText_content.getText().toString());
                miboardData.setImageName(filename);

                RequestParams params = new RequestParams();
                params.setContentEncoding("utf-8");
                params.add("id", miboardData.getId());
                params.add("title", miboardData.getTitle());
                params.add("content", miboardData.getContent());
                params.add("imageName", miboardData.getImageName());
                try {
                    params.put("file", file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                asyncHttpClient.post(getApplicationContext(), getString(R.string.default_url) + "write", params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Toast.makeText(getApplicationContext(), "Daily 저장에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        }
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "서버와의 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras;
        try {
            extras = data.getExtras();
            Bitmap photo = extras.getParcelable("data");
            switch (requestCode) {
                case PICK_FROM_GALLERY:
                    imageView_photo.setImageBitmap(photo);
                    imageView_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
            }
            dir = saveImage(photo);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String saveImage(Bitmap photo) {

        final File saveDir = new File(getApplicationContext().getFilesDir(), "MiBoard");

        filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + UserInfo.getInstance().id;

        if (!saveDir.exists() && !saveDir.isDirectory()) {
            saveDir.mkdir();/* Create Directory "MIPMAP" */
        }

        file = new File(saveDir + File.separator + filename + ".jpg");
        Log.e("dir : ", saveDir + File.separator + filename + ".jpg");
        OutputStream out = null;

        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveDir + File.separator + filename;
    }
}
