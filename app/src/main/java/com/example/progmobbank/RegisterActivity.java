package com.example.progmobbank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText namaEditText;
    private EditText emailEditText;
    private EditText kataSandiEditText;
    private EditText nomorTelpEditText;
    private CheckBox termsCheckBox;
    private Button daftarButton;
    private TextView alreadyHaveAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi view
        usernameEditText = findViewById(R.id.usernameEditText);
        namaEditText = findViewById(R.id.namaEditText);
        emailEditText = findViewById(R.id.emailEditText);
        kataSandiEditText = findViewById(R.id.passwordEditText);
        nomorTelpEditText = findViewById(R.id.noTelpEditText);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        daftarButton = findViewById(R.id.registerButton);
        alreadyHaveAccountTextView = findViewById(R.id.alreadyHaveAccountTextView);

        // Set clickable text for CheckBox
        String termsText = "Saya setuju dengan Syarat dan Ketentuan serta Kebijakan Privasi";
        SpannableString spannableString = new SpannableString(termsText);

        ClickableSpan termsClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showTermsDialog();
            }
        };

        ClickableSpan privacyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showPrivacyDialog();
            }
        };

        int termsStartIndex = termsText.indexOf("Syarat dan Ketentuan");
        int termsEndIndex = termsStartIndex + "Syarat dan Ketentuan".length();
        int privacyStartIndex = termsText.indexOf("Kebijakan Privasi");
        int privacyEndIndex = privacyStartIndex + "Kebijakan Privasi".length();

        spannableString.setSpan(termsClickableSpan, termsStartIndex, termsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(privacyClickableSpan, privacyStartIndex, privacyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsCheckBox.setText(spannableString);
        termsCheckBox.setMovementMethod(LinkMovementMethod.getInstance());
        termsCheckBox.setHighlightColor(ContextCompat.getColor(this, android.R.color.transparent));

        // Aksi tombol daftar
        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Aksi text view "Sudah punya akun? Masuk"
        alreadyHaveAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showTermsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Syarat dan Ketentuan")
                .setMessage("Syarat dan Ketentuan...")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showPrivacyDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Kebijakan Privasi")
                .setMessage("Kebijakan Privasi...")
                .setPositiveButton("OK", null)
                .show();
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString();
        String nama = namaEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = kataSandiEditText.getText().toString();
        String no_telp = nomorTelpEditText.getText().toString();
        String status = "user"; // Default status

        if (username.isEmpty() || nama.isEmpty() || email.isEmpty() || password.isEmpty() || no_telp.isEmpty() || !termsCheckBox.isChecked()) {
            Toast.makeText(this, "Silakan isi semua data dan setujui syarat & ketentuan", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlString = Db_konek.urlRegister;
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    // Kirim data ke server
                    String postData = "username=" + URLEncoder.encode(username, "UTF-8") +
                            "&nama=" + URLEncoder.encode(nama, "UTF-8") +
                            "&email=" + URLEncoder.encode(email, "UTF-8") +
                            "&password=" + URLEncoder.encode(password, "UTF-8") +
                            "&no_telp=" + URLEncoder.encode(no_telp, "UTF-8") +
                            "&status=" + URLEncoder.encode(status, "UTF-8");

                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    reader.close();
                    connection.disconnect();

                    // Parse JSON response
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleRegisterResponse(result.toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleRegisterResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String status = jsonResponse.getString("status");
            String message = jsonResponse.getString("message");

            if ("success".equals(status)) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
