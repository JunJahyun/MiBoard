package com.midasit.miboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ReadingActivity extends AppCompatActivity {

    Button button_Modify, button_Delete;
    EditText editText_title, editText_content;
    ImageView imageView_photo;
    ReadData rd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        initModel();
        initView();
        initListener();
        setTitle("글 읽기");
    }

    private void initModel() {
        rd = new ReadData();

        rd.setId(getIntent().getStringExtra("id"));
        rd.setTitle(getIntent().getStringExtra("title"));
        rd.setContent(getIntent().getStringExtra("content"));
        rd.setImageName(getIntent().getStringExtra("imageName"));
    }

    private void initView() {

        button_Modify = (Button) findViewById(R.id.button_default_layout1);
        button_Delete = (Button) findViewById(R.id.button_default_layout2);
        editText_title = (EditText) findViewById(R.id.editText_title);
        editText_content = (EditText) findViewById(R.id.editText_content);
        imageView_photo = (ImageView) findViewById(R.id.imageView_photo);

        button_Modify.setVisibility(View.VISIBLE);
        button_Delete.setVisibility(View.VISIBLE);
        button_Modify.setText("수정");
        button_Delete.setText("삭제");

        editText_title.setClickable(false);
        editText_title.setFocusable(false);
        editText_content.setClickable(false);
        editText_content.setFocusable(false);


        editText_title.setText(rd.getTitle());
        editText_content.setText(rd.getContent());

        Glide.with(getApplicationContext()).load("http://172.30.1.46:5009/photos/" + rd.getImageName()).into(imageView_photo);
    }

    private void initListener() {

    }
}
