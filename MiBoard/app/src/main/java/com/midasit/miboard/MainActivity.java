package com.midasit.miboard;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;
    Button button_Search, button_Write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("글 목록");

        initView();
        initModel();
        initListener();
    }

    private void initModel() {
        backPressCloseHandler = new BackPressCloseHandler(this);
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

    @Override
    public void onBackPressed() {
            backPressCloseHandler.onBackPressed();
    }
}
