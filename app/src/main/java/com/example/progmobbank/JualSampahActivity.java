package com.example.progmobbank;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class JualSampahActivity extends AppCompatActivity {

    private EditText beratKg, hargaPerKg, tanggalPenyetoran, alamat;
    private AutoCompleteTextView autoCompleteTextView;
    private TextView namaPengguna;
    private ArrayList<String> categoryNames;
    private ArrayList<Integer> categoryIds;
    private ArrayList<String> categoryPrices;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual_sampah);

        // Inisialisasi view
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        beratKg = findViewById(R.id.berat_kg);
        hargaPerKg = findViewById(R.id.harga_per_kg);
        tanggalPenyetoran = findViewById(R.id.tanggal_penyetoran);
        alamat = findViewById(R.id.alamat);
        namaPengguna = findViewById(R.id.namapengguna);

        // Mengambil data kategori dari API
        new FetchCategoriesTask().execute(Db_konek.urlKategori);

        // Mengambil data pengguna dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");  // Default ke string kosong jika tidak ada
        namaPengguna.setText(userName);

        // Setup listener untuk tanggal penyetoran
        tanggalPenyetoran.setOnClickListener(v -> showDatePickerDialog());

        // Setup listener untuk tombol Jual Sekarang
        Button jualButton = findViewById(R.id.JualButton);
        jualButton.setOnClickListener(v -> {
            String kategori = autoCompleteTextView.getText().toString();
            if (kategori.isEmpty() || !categoryNames.contains(kategori)) {
                Toast.makeText(this, "Silakan pilih kategori yang valid", Toast.LENGTH_SHORT).show();
                return;
            }

            int kategoriId = categoryIds.get(categoryNames.indexOf(kategori));
            String berat = beratKg.getText().toString();
            String harga = hargaPerKg.getText().toString();
            String tanggal = tanggalPenyetoran.getText().toString();
            String alamatSampah = alamat.getText().toString();
            String username = namaPengguna.getText().toString();  // Mengambil username dari TextView

            if (berat.isEmpty() || tanggal.isEmpty() || alamatSampah.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Silakan isi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kirim data penjualan ke API
            new Thread(() -> {
                try {
                    String urlString = Db_konek.urlJual;
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    // Kirim data ke server
                    String postData = "username=" + URLEncoder.encode(username, "UTF-8") +
                            "&kategoriId=" + URLEncoder.encode(String.valueOf(kategoriId), "UTF-8") +
                            "&beratKg=" + URLEncoder.encode(berat, "UTF-8") +
                            "&hargaPerKg=" + URLEncoder.encode(harga, "UTF-8") +
                            "&tanggalPenyetoran=" + URLEncoder.encode(tanggal, "UTF-8") +
                            "&alamat=" + URLEncoder.encode(alamatSampah, "UTF-8");

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

                    // Log respons dari server
                    String response = result.toString();
                    Log.d("JualSampahActivity", "Response: " + response);

                    // Parse JSON response
                    runOnUiThread(() -> handleJualSampahResponse(response));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        // Setup listener untuk kategori AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = (String) parent.getItemAtPosition(position);
            int selectedCategoryId = categoryIds.get(categoryNames.indexOf(selectedCategory));
            String selectedCategoryPrice = getCategoryPrice(selectedCategoryId);
            hargaPerKg.setText(selectedCategoryPrice);
        });
    }

    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                JualSampahActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year1);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; // Format tanggal yang diinginkan
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tanggalPenyetoran.setText(dateFormat.format(calendar.getTime()));
    }

    private String getCategoryPrice(int kategoriId) {
        for (int i = 0; i < categoryIds.size(); i++) {
            if (categoryIds.get(i) == kategoriId) {
                return categoryPrices.get(i); // Mengembalikan harga per kg dari kategori yang dipilih
            }
        }
        return "";
    }

    private class FetchCategoriesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                if (jsonResponse.getString("status").equals("success")) {
                    JSONArray categories = jsonResponse.getJSONArray("categories");
                    categoryNames = new ArrayList<>();
                    categoryIds = new ArrayList<>();
                    categoryPrices = new ArrayList<>();
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject category = categories.getJSONObject(i);
                        categoryNames.add(category.getString("nama_kategori"));
                        categoryIds.add(category.getInt("id_kategori"));
                        categoryPrices.add(category.getString("harga_per_kg"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JualSampahActivity.this, android.R.layout.simple_dropdown_item_1line, categoryNames);
                    autoCompleteTextView.setAdapter(adapter);

                    // Log untuk memeriksa data kategori
                    Log.d("JualSampahActivity", "Category Names: " + categoryNames.toString());
                    Log.d("JualSampahActivity", "Category IDs: " + categoryIds.toString());
                    Log.d("JualSampahActivity", "Category Prices: " + categoryPrices.toString());

                } else {
                    Toast.makeText(JualSampahActivity.this, "Gagal mengambil data kategori", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(JualSampahActivity.this, "Terjadi kesalahan dalam parsing data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(JualSampahActivity.this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleJualSampahResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String status = jsonResponse.getString("status");
            String message = jsonResponse.getString("message");

            if ("success".equals(status)) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                // Tambahkan aksi setelah berhasil, misalnya kembali ke halaman utama atau lainnya
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan dalam parsing data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
