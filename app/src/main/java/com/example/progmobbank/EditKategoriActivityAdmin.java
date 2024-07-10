package com.example.progmobbank;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditKategoriActivityAdmin extends AppCompatActivity {

    private ListView listViewKategori;
    private EditText etNamaKategori, etHargaPerKg;
    private Button btnUpdateKategori;
    private int selectedKategoriId;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> kategoriList;
    private ArrayList<JSONObject> kategoriJsonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kategori_admin);

        listViewKategori = findViewById(R.id.listViewKategori);
        etNamaKategori = findViewById(R.id.etNamaKategori);
        etHargaPerKg = findViewById(R.id.etHargaPerKg);
        btnUpdateKategori = findViewById(R.id.btnUpdateKategori);

        kategoriList = new ArrayList<>();
        kategoriJsonList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kategoriList);
        listViewKategori.setAdapter(adapter);

        fetchKategori();

        listViewKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject selectedKategori = kategoriJsonList.get(position);
                    selectedKategoriId = selectedKategori.getInt("id_kategori");
                    etNamaKategori.setText(selectedKategori.getString("nama_kategori"));
                    etHargaPerKg.setText(String.valueOf(selectedKategori.getInt("harga_per_kg")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnUpdateKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKategori();
            }
        });
    }

    private void fetchKategori() {
        String url = Db_konek.urlKategori;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                JSONArray categories = response.getJSONArray("categories");
                                for (int i = 0; i < categories.length(); i++) {
                                    JSONObject category = categories.getJSONObject(i);
                                    kategoriList.add(category.getString("nama_kategori") + " - " + category.getInt("harga_per_kg") + " per kg");
                                    kategoriJsonList.add(category);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(EditKategoriActivityAdmin.this, "No categories found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditKategoriActivityAdmin.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EditKategoriActivityAdmin.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void updateKategori() {
        final String namaKategori = etNamaKategori.getText().toString().trim();
        final String hargaPerKg = etHargaPerKg.getText().toString().trim();

        if (namaKategori.isEmpty() || hargaPerKg.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Db_konek.urlEditKategori;
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if (status.equals("success")) {
                                Toast.makeText(EditKategoriActivityAdmin.this, "Kategori updated successfully", Toast.LENGTH_SHORT).show();
                                fetchKategori(); // Refresh the list
                            } else {
                                Toast.makeText(EditKategoriActivityAdmin.this, "Failed to update kategori: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditKategoriActivityAdmin.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(EditKategoriActivityAdmin.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_kategori", String.valueOf(selectedKategoriId));
                params.put("nama_kategori", namaKategori);
                params.put("harga_per_kg", hargaPerKg);
                return params;
            }
        };

        queue.add(postRequest);
    }
}
