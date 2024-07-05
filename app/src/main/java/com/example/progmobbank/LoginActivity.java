package com.example.progmobbank;

import android.content.Intent;
import android.content.SharedPreferences;  // Import SharedPreferences
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private TextView createAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        createAccountTextView = findViewById(R.id.createAccountTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Log.d("LoginActivity", "Username: " + username);
                Log.d("LoginActivity", "Password: " + password);

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                } else {
                    new LoginTask().execute(username, password);
                }
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Lupa Kata Sandi diklik", Toast.LENGTH_SHORT).show();
            }
        });

        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String response = "";

            try {
                String apiUrl = Db_konek.urlLogin;
                // Menyandikan parameter query untuk menghindari karakter yang tidak diinginkan
                String queryParams = "?username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
                URL url = new URL(apiUrl + queryParams);

                Log.d("LoginTask", "URL: " + url.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                response = sb.toString();
                Log.d("LoginTask", "Server Response: " + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            handleLoginResponse(result);
        }
    }

    private void handleLoginResponse(String response) {
        try {
            Log.d("LoginActivity", "Response: " + response);
            JSONObject jsonResponse = new JSONObject(response);
            String status = jsonResponse.getString("status");

            if (status.equals("success")) {
                // Menyimpan ID pengguna ke dalam SharedPreferences
                String userId = jsonResponse.getJSONObject("user").getString("userId");

                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", userId);  // Simpan userId ke dalam session
                editor.apply();

                Toast.makeText(LoginActivity.this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
        }
    }
}
