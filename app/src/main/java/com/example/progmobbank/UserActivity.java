package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.manage_accounts); // Set the default selected item

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(UserActivity.this, BaseActivity.class));
                return true;
            } else if (item.getItemId() == R.id.notifications) {
                startActivity(new Intent(UserActivity.this, NotifActivity.class));
                return true;
            } else if (item.getItemId() == R.id.history) {
                startActivity(new Intent(UserActivity.this, HistoryActivity.class));
                return true;
            } else if (item.getItemId() == R.id.manage_accounts) {
                return true; // Stay on this activity
            }
            return false;
        });

        // Setup FAB
        findViewById(R.id.fab).setOnClickListener(view -> {
            // Handle FAB click
        });
    }
}
