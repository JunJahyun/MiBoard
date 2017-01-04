package com.midasit.miboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReadingActivity extends AppCompatActivity {

    Button button_Modify, button_Delete, button_SendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        initView();
        initListener();
        setTitle("글 읽기");
    }

    private void initView() {

        button_Modify = (Button) findViewById(R.id.button_default_layout3);
        button_Delete = (Button) findViewById(R.id.button_default_layout2);
        button_SendEmail = (Button) findViewById(R.id.button_default_layout1);

        button_Modify.setVisibility(View.VISIBLE);
        button_Delete.setVisibility(View.VISIBLE);
        button_SendEmail.setVisibility(View.VISIBLE);

        button_Modify.setText("수정");
        button_Delete.setText("삭제");
        button_SendEmail.setText("발송");
    }

    private void initListener() {

    }
}
