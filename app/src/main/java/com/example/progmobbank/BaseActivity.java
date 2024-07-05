package com.example.progmobbank;

import android.content.Intent;
import android.content.SharedPreferences;  // Import SharedPreferences
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private Button logoutButton;  // Tambahkan tombol logout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);  // Ganti dengan layout yang sesuai

        logoutButton = findViewById(R.id.logoutButton);  // Inisialisasi tombol logout

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        // Hapus session dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");
        editor.apply();

        // Arahkan ke LoginActivity setelah logout
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
