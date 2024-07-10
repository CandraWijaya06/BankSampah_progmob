package com.example.progmobbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivityAdmin extends AppCompatActivity {

    private TextView adminWelcomeTextView;
    private Button btnEditInformasiSampah;
    private Button btnEditKategoriSampah;
    private Button btnCheckUser;
    private Button btnKonfirmasiPesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_admin);

        adminWelcomeTextView = findViewById(R.id.adminWelcomeTextView);
        btnEditInformasiSampah = findViewById(R.id.btnEditInformasiSampah);
        btnEditKategoriSampah = findViewById(R.id.btnEditKategoriSampah);
        btnCheckUser = findViewById(R.id.btnCheckUser);
        btnKonfirmasiPesanan = findViewById(R.id.btnKonfirmasiPesanan);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "Admin");

        adminWelcomeTextView.setText("Halo Admin, " + userName);

        btnEditInformasiSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivityAdmin.this, EditInformasiActivityAdmin.class));
            }
        });

        btnEditKategoriSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivityAdmin.this, EditKategoriActivityAdmin.class));
            }
        });

        btnCheckUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivityAdmin.this, CheckUserActivityAdmin.class));
            }
        });

        btnKonfirmasiPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivityAdmin.this, KonfirmasiPesananActivityAdmin.class));
            }
        });
    }
}
