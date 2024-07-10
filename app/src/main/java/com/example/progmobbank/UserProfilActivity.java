package com.example.progmobbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserProfilActivity extends AppCompatActivity {

    private EditText editTextNama, editTextEmail, editTextNoTelp;
    private Button buttonSimpan, buttonHapusAkun;
    private static final String TAG = "UserProfilActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);

        editTextNama = findViewById(R.id.editTextNama);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNoTelp = findViewById(R.id.editTextNoTelp);
        buttonSimpan = findViewById(R.id.buttonSimpan);
        buttonHapusAkun = findViewById(R.id.buttonHapusAkun);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", "");


        new GetUserDataTask().execute(username);

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanPerubahan();
            }
        });

        buttonHapusAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void simpanPerubahan() {
        String nama = editTextNama.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String noTelp = editTextNoTelp.getText().toString().trim();

        if (nama.isEmpty() || email.isEmpty() || noTelp.isEmpty()) {
            Toast.makeText(UserProfilActivity.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", "");

        new UpdateUserDataTask().execute(username, nama, email, noTelp);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi Hapus Akun")
                .setMessage("Apakah Anda yakin ingin menghapus akun?")
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusAkun();
                    }
                })
                .setNegativeButton("Kembali", null)
                .show();
    }

    private void hapusAkun() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", "");

        new DeleteAccountTask().execute(username);
    }

    private class GetUserDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String urlString = Db_konek.urlUserProfile + "?username=" + username;
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                } else {
                    Log.e(TAG, "Server returned non-OK status: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error during API request: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    Log.d(TAG, "API Response: " + result);
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equals("success")) {
                        JSONObject userObject = jsonObject.getJSONObject("user");
                        editTextNama.setText(userObject.getString("nama"));
                        editTextEmail.setText(userObject.getString("email"));
                        editTextNoTelp.setText(userObject.getString("no_telp"));
                    } else {
                        Log.e(TAG, "Error: " + jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "No response from API");
            }
        }
    }

    private class UpdateUserDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String nama = params[1];
            String email = params[2];
            String noTelp = params[3];
            String urlString = Db_konek.urlUpdateUserProfile;
            try {
                // Format data untuk POST request
                String data = "username=" + URLEncoder.encode(username, "UTF-8") +
                        "&nama=" + URLEncoder.encode(nama, "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8") +
                        "&no_telp=" + URLEncoder.encode(noTelp, "UTF-8");

                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(data.getBytes());

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                } else {
                    Log.e(TAG, "Server returned non-OK status: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error during API request: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    Log.d(TAG, "API Response: " + result);
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("status").equals("success")) {
                        // Tampilkan notifikasi bahwa profil berhasil diupdate
                        Toast.makeText(UserProfilActivity.this, "Profil berhasil diupdate", Toast.LENGTH_SHORT).show();
                        // Kembali ke activity_user setelah update
                        finish();
                    } else {
                        Log.e(TAG, "Error: " + jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "No response from API");
            }
        }
    }

    private class DeleteAccountTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String urlString = Db_konek.urlDeleteAccount + "?username=" + username;
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                } else {
                    Log.e(TAG, "Server returned non-OK status: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error during API request: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    Log.d(TAG, "API Response: " + result);
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("success")) {
                        // Hapus session dari SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("userId");
                        editor.remove("userName");
                        editor.apply();

                        // Tampilkan notifikasi bahwa akun berhasil dihapus
                        Toast.makeText(UserProfilActivity.this, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();

                        // Arahkan ke LoginActivity setelah menghapus akun
                        Intent intent = new Intent(UserProfilActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (status.equals("error")) {
                        // Tampilkan pesan error jika username tidak valid
                        Toast.makeText(UserProfilActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "Unknown status: " + status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "No response from API");
                // Tampilkan pesan error jika tidak ada respons dari API
                Toast.makeText(UserProfilActivity.this, "Tidak ada respons dari server", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
