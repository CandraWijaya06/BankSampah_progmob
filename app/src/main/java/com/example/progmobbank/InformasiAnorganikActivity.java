package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class InformasiAnorganikActivity extends AppCompatActivity {

    private List<String> sampahList;
    private List<String> filteredList;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_anorganik);

        EditText searchEditText = findViewById(R.id.searchEditTextAnorganik);
        container = findViewById(R.id.containerAnorganik);

        sampahList = new ArrayList<>();
        filteredList = new ArrayList<>();

        sampahList.add("Sampah Botol Plastik");
        sampahList.add("Sampah Kaleng");
        sampahList.add("Sampah Kardus");

        filteredList.addAll(sampahList);
        updateUI();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterList(String query) {
        filteredList.clear();
        for (String item : sampahList) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        updateUI();
    }

    private void updateUI() {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (String item : filteredList) {
            View view = inflater.inflate(R.layout.item_sampah_anorganik, container, false);
            TextView titleTextView = view.findViewById(R.id.titleTextView);
            Button viewMoreButton = view.findViewById(R.id.viewMoreButton);
            titleTextView.setText(item);

            viewMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    switch (item) {
                        case "Sampah Botol Plastik":
                            intent = new Intent(InformasiAnorganikActivity.this, SampahBotolPlastikActivity.class);
                            break;
                        case "Sampah Kaleng":
                            intent = new Intent(InformasiAnorganikActivity.this, SampahKalengActivity.class);
                            break;
                        case "Sampah Kardus":
                            intent = new Intent(InformasiAnorganikActivity.this, SampahKardusActivity.class);
                            break;
                        default:
                            return;
                    }
                    startActivity(intent);
                }
            });

            container.addView(view);
        }
    }
}
