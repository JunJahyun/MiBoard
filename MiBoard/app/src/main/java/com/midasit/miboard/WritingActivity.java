package com.midasit.miboard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WritingActivity extends AppCompatActivity {

    Button button_Regist;
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
        imageView_photo = (ImageView) findViewById(R.id.imageView_photo);

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
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);

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

        if(data != null) {
            Bundle extras = data.getExtras();

            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                switch (requestCode) {

                    case PICK_FROM_GALLERY:
                        imageView_photo.setImageBitmap(photo);
                        break;
                }
            }
        }
    }
}
