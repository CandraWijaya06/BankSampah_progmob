package com.example.progmobbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home); // Set the default selected item

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                return true; // Stay on this activity
            } else if (item.getItemId() == R.id.notifications) {
                startActivity(new Intent(BaseActivity.this, NotifActivity.class));
                return true;
            } else if (item.getItemId() == R.id.history) {
                startActivity(new Intent(BaseActivity.this, HistoryActivity.class));
                return true;
            } else if (item.getItemId() == R.id.manage_accounts) {
                startActivity(new Intent(BaseActivity.this, UserActivity.class));
                return true;
            }
            return false;
        });

// Setup FAB
        findViewById(R.id.fab).setOnClickListener(view -> {
            // Handle FAB click
        });

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
