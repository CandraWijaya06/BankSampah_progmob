package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history); // Set the default selected item

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(HistoryActivity.this, BaseActivity.class));
                return true;
            } else if (item.getItemId() == R.id.notifications) {
                startActivity(new Intent(HistoryActivity.this, NotifActivity.class));
                return true;
            } else if (item.getItemId() == R.id.history) {
                return true; // Stay on this activity
            } else if (item.getItemId() == R.id.manage_accounts) {
                startActivity(new Intent(HistoryActivity.this, UserActivity.class));
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
