package com.Demo.vjrutnat.quanlyschitieu.QuanLyChiTieu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Demo.vjrutnat.quanlyschitieu.R;

public class IntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce);
        Button btnClose = (Button)findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClose = new Intent(IntroduceActivity.this , MainActivity.class);
                startActivity(intentClose);
             }
        });
    }
}
