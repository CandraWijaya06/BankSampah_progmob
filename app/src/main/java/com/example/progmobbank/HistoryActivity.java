package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private TransactionAdapter adapter;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactions = new ArrayList<>();
        transactions.add(new Transaction("Status: Menunggu Konfirmasi", "123456789", R.drawable.box, "Kardus Bekas", "23 Januari - 14.00 WITA", "Rp 10.000", 2));
        transactions.add(new Transaction("Status: Menunggu Pembeli", "123456789", R.drawable.aluminium, "Aluminium Bekas", "23 Januari - 14.00 WITA", "Rp 50.000", 10));
        transactions.add(new Transaction("Status: Terjual", "123456789", R.drawable.box, "Kertas Koran Bekas", "31 Juli - 20.00 WITA", "Rp 25.000", 4));
        transactions.add(new Transaction("Status: Ditolak", "123456789", R.drawable.box, "Kardus Bekas", "31 Juli - 20.00 WITA", "Rp 25.000", 4));

        adapter = new TransactionAdapter(transactions);
        recyclerView.setAdapter(adapter);

        // Setup SearchView
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText); // Panggil metode filter pada adapter saat teks pencarian berubah
                return true;
            }
        });

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history); // Set item terpilih secara default

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;
            if (item.getItemId() == R.id.home) {
                intent = new Intent(HistoryActivity.this, BaseActivity.class);
            } else if (item.getItemId() == R.id.notifications) {
                intent = new Intent(HistoryActivity.this, NotifActivity.class);
            } else if (item.getItemId() == R.id.history) {
                return true; // Tetap di activity ini
            } else if (item.getItemId() == R.id.manage_accounts) {
                intent = new Intent(HistoryActivity.this, UserActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                overridePendingTransition(0, 0); // Tanpa animasi saat transisi activity
            }
            return false;
        });

    }
}
