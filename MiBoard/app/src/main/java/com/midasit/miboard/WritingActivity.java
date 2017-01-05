package com.midasit.miboard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WritingActivity extends AppCompatActivity {

    Button button_Regist;
    EditText editText_title, editText_content;
    ImageView imageView_photo;
    private static final int PICK_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        initView();
        initListener();
    }

    private void initView() {

        button_Regist = (Button) findViewById(R.id.button_default_layout1);
        editText_title = (EditText) findViewById(R.id.editText_title);
        editText_content = (EditText) findViewById(R.id.editText_content);
        imageView_photo = (ImageView) findViewById(R.id.imageView_photo);

        imageView_photo.setImageResource(R.mipmap.img_photo_default);

        button_Regist.setVisibility(View.VISIBLE);
        button_Regist.setText("등록");
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            switch (requestCode) {
                case PICK_FROM_GALLERY:
                    imageView_photo.setImageBitmap(photo);
                    imageView_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
            }
            saveImage(photo);
        }
    }

    private void saveImage(Bitmap photo) {

        final File saveDir = new File(getApplicationContext().getFilesDir(), "MiBoard");

        String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + UserInfo.getInstance().id;

        if (!saveDir.exists() && !saveDir.isDirectory()) {
            saveDir.mkdir();/* Create Directory "MIPMAP" */
        }

        File fileCacheItem = new File(saveDir + File.separator + filename + ".jpg");
        Log.e("FILE NAME : ", saveDir + File.separator + filename + ".jpg");
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
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
    }

}
