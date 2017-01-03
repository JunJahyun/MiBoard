package com.midasit.miboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button_Search, button_Write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("글 목록");

        initView();
        initListener();
    }

    private void initModel() {

    }

    private void initView() {
        button_Search = (Button) findViewById(R.id.button_Search);
        button_Write = (Button) findViewById(R.id.button_Write);
    }

    private void initListener() {
        button_Write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WritingActivity.class));
            }
        });
    }
}
