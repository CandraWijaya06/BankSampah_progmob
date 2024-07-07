package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.notifications); // Set the default selected item

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(NotifActivity.this, BaseActivity.class));
                return true;
            } else if (item.getItemId() == R.id.notifications) {
                return true; // Stay on this activity
            } else if (item.getItemId() == R.id.history) {
                startActivity(new Intent(NotifActivity.this, HistoryActivity.class));
                return true;
            } else if (item.getItemId() == R.id.manage_accounts) {
                startActivity(new Intent(NotifActivity.this, UserActivity.class));
                return true;
            }
            return false;
        });

        // Setup FAB
        findViewById(R.id.fab).setOnClickListener(view -> {
            // Handle FAB click
        });
    }
}
