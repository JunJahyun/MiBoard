package com.midasit.miboard;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {

    EditText editText_id, editText_password;
    Button button_login;
    int fstatus;

    StringEntity stringEntity;

    private AsyncHttpClient asyncHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initModel();
        initListener();

    }

    private void initModel() {
        asyncHttpClient = new AsyncHttpClient();
    }

    private void initView() {

        editText_id = (EditText) findViewById(R.id.editText_id);
        editText_password = (EditText) findViewById(R.id.editText_password);
        button_login = (Button) findViewById(R.id.button_login);

        editText_id.setHint("ID를 입력하세요.");
        editText_password.setHint("PASSWORD를 입력하세요.");

        editText_id.setText("a");
        editText_password.setText("a");
    }

    private void initListener() {
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo.getInstance().id = editText_id.getText().toString();
                UserInfo.getInstance().password = editText_password.getText().toString();

                JSONObject job = new JSONObject();
                try {
                    job.put("id", UserInfo.getInstance().id);
                    job.put("password", UserInfo.getInstance().password);
                    stringEntity = new StringEntity(job.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                asyncHttpClient.post(getApplicationContext(), "http://172.30.1.46:5009/login", stringEntity, "application/json", new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                        Log.e("stat", String.valueOf(statusCode));
                        switch (statusCode) {
                            case 200:
                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                                break;
                            case 404:
                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case 405:
                                Toast.makeText(getApplicationContext(), "서버와 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                        Toast.makeText(getApplicationContext(), "서버와 연결되지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }
}
