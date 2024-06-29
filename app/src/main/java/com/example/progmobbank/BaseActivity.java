package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private Button buttonInformasiSampah;
    private Button buttonJualSampah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        buttonInformasiSampah = findViewById(R.id.buttonInformasiSampah);
        buttonJualSampah = findViewById(R.id.buttonJualSampah);

        buttonInformasiSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, InformasiSampahActivity.class);
                startActivity(intent);
            }
        });

        buttonJualSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, JualSampahActivity.class);
                startActivity(intent);
            }
        });
    }
}
