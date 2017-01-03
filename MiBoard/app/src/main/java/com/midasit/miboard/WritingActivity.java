package com.midasit.miboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WritingActivity extends AppCompatActivity {

    Button button_Regist, button_Modify, button_Delete, button_SendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        setTitle("글 작성하기");

        initView();
    }

    private void initView() {

        button_Regist = (Button) findViewById(R.id.button_Regist);
        button_Modify = (Button) findViewById(R.id.button_Modify);
        button_Delete = (Button) findViewById(R.id.button_Delete);
        button_SendEmail = (Button) findViewById(R.id.button_SendEmail);

        button_Modify.setVisibility(View.INVISIBLE);
        button_Delete.setVisibility(View.INVISIBLE);
        button_SendEmail.setVisibility(View.INVISIBLE);
    }

    private void initListener() {

    }
}
