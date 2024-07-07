package com.example.progmobbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private TextView userNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        userNameText = findViewById(R.id.userNameText);
        LinearLayout informasiSampahButton = findViewById(R.id.informasiSampahButton);

        // Ambil nama pengguna dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");  // Default ke string kosong jika tidak ada

        // Atur teks TextView dengan nama pengguna
        userNameText.setText(userName);
    }

    public void onInformasiSampahClick(View view) {
        Intent intent = new Intent(BaseActivity.this, InformasiSampahActivity.class);
        startActivity(intent);
    }

    public void onJualSampahClick(View view) {
        Intent intent = new Intent(BaseActivity.this, JualSampahActivity.class);
        startActivity(intent);
    }

    public void onLogoutClick(View view) {
        logoutUser();
    }

    private void logoutUser() {
        // Hapus session dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");
        editor.remove("userName");
        editor.apply();

        // Arahkan ke LoginActivity setelah logout
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
