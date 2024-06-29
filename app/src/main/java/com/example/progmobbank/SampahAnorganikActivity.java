package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SampahAnorganikActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampah_anorganik);

        Button viewMoreButton = findViewById(R.id.viewMoreButton);
        viewMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampahAnorganikActivity.this, InformasiAnorganikActivity.class);
                startActivity(intent);
            }
        });
    }
}
