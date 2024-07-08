package com.example.progmobbank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivityAdmin extends AppCompatActivity {

    private TextView adminWelcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_admin);

        adminWelcomeTextView = findViewById(R.id.adminWelcomeTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "Admin");

        adminWelcomeTextView.setText("Halo Admin, " + userName);
    }
}
