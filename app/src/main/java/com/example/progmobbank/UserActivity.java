package com.example.progmobbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {

    private TextView userName;
    private Button logoutButton;
    private Button editProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userName = findViewById(R.id.userName);
        logoutButton = findViewById(R.id.logoutButton);
        editProfileButton = findViewById(R.id.button_edit_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userNameString = sharedPreferences.getString("userName", "");  // Default ke string kosong jika tidak ada

        userName.setText(userNameString);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.manage_accounts); // Set the default selected item

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;
            if (item.getItemId() == R.id.home) {
                intent = new Intent(UserActivity.this, BaseActivity.class);
            } else if (item.getItemId() == R.id.notifications) {
                intent = new Intent(UserActivity.this, NotifActivity.class);
            } else if (item.getItemId() == R.id.history) {
                intent = new Intent(UserActivity.this, HistoryActivity.class);
            } else if (item.getItemId() == R.id.manage_accounts) {
                return true; // Stay on this activity
            }

            if (intent != null) {
                startActivity(intent);
                overridePendingTransition(0, 0); // No animation for activity transition
            }
            return false;
        });

        // Setup Logout Button
        logoutButton.setOnClickListener(v -> showLogoutConfirmationDialog());

        // Setup Edit Profile Button
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, UserProfilActivity.class);
            startActivity(intent);
        });
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya", (dialog, which) -> logoutUser())
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void logoutUser() {
        // Hapus session dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");
        editor.remove("userName");
        editor.apply();

        // Arahkan ke LoginActivity setelah logout
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
