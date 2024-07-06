package com.example.progmobbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private Button logoutButton;
    private TextView userNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        logoutButton = findViewById(R.id.logoutButton);
        userNameText = findViewById(R.id.userNameText);

        // Ambil nama pengguna dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");  // Default ke string kosong jika tidak ada

        // Atur teks TextView dengan nama pengguna
        userNameText.setText(userName);

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
        editor.remove("userName");
        editor.apply();

        // Arahkan ke LoginActivity setelah logout
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
