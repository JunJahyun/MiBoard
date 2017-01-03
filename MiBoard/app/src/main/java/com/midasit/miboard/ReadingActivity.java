package com.midasit.miboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReadingActivity extends AppCompatActivity {

    Button button_Regist, button_Modify, button_Delete, button_SendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        initView();
        initListener();
        setTitle("글 읽기");
    }

    private void initView() {
        button_Regist = (Button) findViewById(R.id.button_Regist);
        button_Modify = (Button) findViewById(R.id.button_Modify);
        button_Delete = (Button) findViewById(R.id.button_Delete);
        button_SendEmail = (Button) findViewById(R.id.button_SendEmail);

        button_Regist.setVisibility(View.INVISIBLE);
    }

    private void initListener() {

    }
}
