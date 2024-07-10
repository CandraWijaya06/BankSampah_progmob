package com.example.progmobbank;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class KonfirmasiPesananActivityAdmin extends AppCompatActivity {

    private static final String TAG = "KonfirmasiPesananAdmin";
    private LinearLayout containerData;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pesanan);

        containerData = findViewById(R.id.containerData);
        btnRefresh = findViewById(R.id.btnRefresh);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });

        // Fetch data on activity creation
        fetchData();
    }

    private void fetchData() {
        // Show progress indicator or other UI feedback if needed
        new FetchDataTask().execute(Db_konek.UrlGetAllHistory); // Ganti dengan URL API Anda
    }

    private class FetchDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String urlString = urls[0];
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                connection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error fetching data", e);
                return null;
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(KonfirmasiPesananActivityAdmin.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = jsonObject.getString("status");

                if (status.equals("success")) {
                    JSONArray historyArray = jsonObject.getJSONArray("history");
                    containerData.removeAllViews();  // Clear existing views

                    for (int i = 0; i < historyArray.length(); i++) {
                        try {
                            JSONObject history = historyArray.getJSONObject(i);
                            String idJual = history.getString("id_jual");
                            String kategoriSampah = history.getString("kategori_sampah");
                            String beratKg = history.getString("berat_kg");
                            String hargaTotal = history.getString("harga_total");
                            String tanggalPenyetoran = history.getString("tanggal_penyetoran");
                            String alamat = history.getString("alamat");
                            String statusPesanan = history.getString("status");

                            LinearLayout itemLayout = new LinearLayout(KonfirmasiPesananActivityAdmin.this);
                            itemLayout.setOrientation(LinearLayout.VERTICAL);
                            itemLayout.setPadding(16, 16, 16, 16);
                            itemLayout.setBackgroundResource(R.drawable.rounded_rectangle);
                            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            ));

                            // Add data to itemLayout
                            addTextView(itemLayout, "ID Jual: " + idJual);
                            addTextView(itemLayout, "Kategori: " + kategoriSampah);
                            addTextView(itemLayout, "Berat (kg): " + beratKg);
                            addTextView(itemLayout, "Total Harga: " + hargaTotal);
                            addTextView(itemLayout, "Tanggal Penyetoran: " + tanggalPenyetoran);
                            addTextView(itemLayout, "Alamat Bank Sampah: " + alamat);
                            addTextView(itemLayout, "Status: " + statusPesanan);

                            // Add Spinner for status update
                            Spinner statusSpinner = new Spinner(KonfirmasiPesananActivityAdmin.this);
                            String[] statuses = {"Pending", "Diterima", "Ditolak", "Terjual"};
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(KonfirmasiPesananActivityAdmin.this, android.R.layout.simple_spinner_item, statuses);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            statusSpinner.setAdapter(adapter);
                            itemLayout.addView(statusSpinner);

                            Button updateButton = new Button(KonfirmasiPesananActivityAdmin.this);
                            updateButton.setText("Update Status");
                            updateButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String selectedStatus = statusSpinner.getSelectedItem().toString();
                                    updateStatus(idJual, selectedStatus);
                                }
                            });
                            itemLayout.addView(updateButton);

                            containerData.addView(itemLayout);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(KonfirmasiPesananActivityAdmin.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateStatus(String idJual, String newStatus) {
        new UpdateStatusTask().execute(idJual, newStatus);
    }

    private class UpdateStatusTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String idJual = params[0];
            String newStatus = params[1];
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(Db_konek.UrlUpdateStatus); // Ganti dengan URL API Anda
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                Map<String, String> postData = new HashMap<>();
                postData.put("id_jual", idJual);
                postData.put("status", newStatus);
                writer.write(getPostDataString(postData));
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                connection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error updating status", e);
                return null;
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(KonfirmasiPesananActivityAdmin.this, "Gagal memperbarui status", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = jsonObject.getString("status");

                if (status.equals("success")) {
                    Toast.makeText(KonfirmasiPesananActivityAdmin.this, "Status berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    fetchData(); // Refresh data
                } else {
                    Toast.makeText(KonfirmasiPesananActivityAdmin.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private String getPostDataString(Map<String, String> params) {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(entry.getKey());
                result.append("=");
                result.append(entry.getValue());
            }
            return result.toString();
        }
    }

    private void addTextView(LinearLayout parent, String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(16);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        textView.setPadding(0, 4, 0, 4);
        parent.addView(textView);
    }
}
