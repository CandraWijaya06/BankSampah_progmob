package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SampahBotolPlastikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampah_botol_plastik);

        Button buttonJualSampah = findViewById(R.id.buttonJualSampah);
        buttonJualSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampahBotolPlastikActivity.this, JualSampahActivity.class);
                startActivity(intent);
            }
        });
    }
}
