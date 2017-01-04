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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText editText_id, editText_password;
    Button button_login;
    int fstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();
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

                attemptLogin attemptLogin = new attemptLogin();
                attemptLogin.execute();
            }
        });
    }

    private class attemptLogin extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            URL url = null;
            HttpURLConnection conn = null;
            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;
            fstatus = 0;

            try {
                url = new URL("http://192.168.43.106:5009/login");
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(10000);
                    conn.setRequestMethod("POST");
                    // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
                    conn.setRequestProperty("Cache-Control", "no-cache");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
                    conn.setDoInput(true);

                    JSONObject job = new JSONObject();
                    job.put("id", UserInfo.getInstance().id);
                    job.put("password", UserInfo.getInstance().password);
                    Log.e("id", UserInfo.getInstance().id);
                    Log.e("password", UserInfo.getInstance().password);

                    os = conn.getOutputStream();
                    os.write(job.toString().getBytes());
                    os.flush();

                    String response;

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        byte[] byteBuffer = new byte[1024];
                        byte[] byteData = null;
                        int nLength = 0;
                        while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                            baos.write(byteBuffer, 0, nLength);
                        }
                        byteData = baos.toByteArray();

                        response = new String(byteData);

                        Log.e("response", response);

                        JSONObject responseJSON = new JSONObject(response);
                        Integer result = responseJSON.getInt("result_code");

                        switch (result) {
                            case 200:
                                fstatus = 200;
                                break;
                            case 404:
                                fstatus = 404;
                                break;
                            case 405:
                                fstatus = 405;
                                break;
                        }
                        Log.e("DATA response = ", response);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPreExecute();
            switch (fstatus) {
                case 200:
                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다다.", Toast.LENGTH_SHORT).show();
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
    }
}
