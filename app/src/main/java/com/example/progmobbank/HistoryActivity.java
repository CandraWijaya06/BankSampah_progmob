package com.example.progmobbank;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistory;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryItem> historyList;
    private static final String TAG = "HistoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistory = findViewById(R.id.listViewHistory);
        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(this, historyList);
        listViewHistory.setAdapter(historyAdapter);

        // Ambil username dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", "");

        // Ambil data history dari API
        new GetHistoryTask().execute(username);
    }

    private class GetHistoryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String urlString = Db_konek.urlGetHistory + "?username=" + username;
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
                        JSONArray historyArray = jsonObject.getJSONArray("history");
                        for (int i = 0; i < historyArray.length(); i++) {
                            JSONObject historyObject = historyArray.getJSONObject(i);
                            String kategoriSampah = historyObject.getString("kategori_sampah");
                            int beratKg = historyObject.getInt("berat_kg");
                            int hargaTotal = historyObject.getInt ("harga_total");
                            String tanggalPenyetoran = historyObject.getString("tanggal_penyetoran");
                            String alamat = historyObject.getString("alamat");
                            String status = historyObject.getString("status");

                            HistoryItem historyItem = new HistoryItem(kategoriSampah, beratKg, hargaTotal, tanggalPenyetoran, alamat, status);
                            historyList.add(historyItem);
                        }
                        historyAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "Error: " + jsonObject.getString("message"));
                        Toast.makeText(HistoryActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
}
