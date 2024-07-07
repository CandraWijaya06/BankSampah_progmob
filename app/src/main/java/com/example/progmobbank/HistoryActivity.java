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
            Intent intent = null;
            if (item.getItemId() == R.id.home) {
                intent = new Intent(HistoryActivity.this, BaseActivity.class);
            } else if (item.getItemId() == R.id.notifications) {
                intent = new Intent(HistoryActivity.this, NotifActivity.class);
            } else if (item.getItemId() == R.id.history) {
                return true; // Stay on this activity
            } else if (item.getItemId() == R.id.manage_accounts) {
                intent = new Intent(HistoryActivity.this, UserActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                overridePendingTransition(0, 0); // No animation for activity transition
            }
            return false;
        });

        // Setup FAB
        findViewById(R.id.fab).setOnClickListener(view -> {
            // Handle FAB click
        });
    }
}
