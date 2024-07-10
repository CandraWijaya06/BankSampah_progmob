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
            Intent intent = null;
            if (item.getItemId() == R.id.home) {
                intent = new Intent(NotifActivity.this, BaseActivity.class);
            } else if (item.getItemId() == R.id.notifications) {
                return true; // Stay on this activity
            } else if (item.getItemId() == R.id.history) {
                intent = new Intent(NotifActivity.this, HistoryActivity.class);
            } else if (item.getItemId() == R.id.manage_accounts) {
                intent = new Intent(NotifActivity.this, UserActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                overridePendingTransition(0, 0); // No animation for activity transition
            }
            return false;
        });

    }
}
