package com.midasit.miboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WritingActivity extends AppCompatActivity {

    Button button_Regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        setTitle("글 작성하기");

        initView();
    }

    private void initView() {

        button_Regist = (Button) findViewById(R.id.button_default_layout1);

        button_Regist.setVisibility(View.VISIBLE);
        button_Regist.setText("등록");
    }

    private void initListener() {

    }
}
