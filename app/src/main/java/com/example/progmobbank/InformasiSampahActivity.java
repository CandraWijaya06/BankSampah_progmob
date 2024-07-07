package com.example.progmobbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class InformasiSampahActivity extends AppCompatActivity {

    private EditText searchEditText;
    private LinearLayout container;

    private List<SampahItem> sampahList = new ArrayList<>();
    private List<SampahItem> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_sampah);

        searchEditText = findViewById(R.id.searchEditText);
        container = findViewById(R.id.container);

        // Tambahkan data sampah
        sampahList.add(new SampahItem("Sampah Organik", SampahOrganikActivity.class, R.drawable.organik));
        sampahList.add(new SampahItem("Sampah Anorganik", SampahAnorganikActivity.class, R.drawable.anorganik));
        sampahList.add(new SampahItem("Sampah Berbahaya", SampahBerbahayaActivity.class, R.drawable.b3));

        filteredList.addAll(sampahList);
        updateUI();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(sampahList);
        } else {
            for (SampahItem item : sampahList) {
                if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        updateUI();
    }

    private void updateUI() {
        container.removeAllViews();
        for (SampahItem item : filteredList) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_sampah, container, false);
            TextView title = view.findViewById(R.id.sampahTitle);
            ImageView imageView = view.findViewById(R.id.imageView);
            Button viewMoreButton = view.findViewById(R.id.viewMoreButton);

            title.setText(item.getTitle());
            imageView.setImageResource(item.getImageResId());

            viewMoreButton.setOnClickListener(v -> {
                Intent intent = new Intent(InformasiSampahActivity.this, item.getActivityClass());
                startActivity(intent);
            });

            container.addView(view);
        }
    }

    private static class SampahItem {
        private final String title;
        private final Class<?> activityClass;
        private final int imageResId;

        public SampahItem(String title, Class<?> activityClass, int imageResId) {
            this.title = title;
            this.activityClass = activityClass;
            this.imageResId = imageResId;
        }

        public String getTitle() {
            return title;
        }

        public Class<?> getActivityClass() {
            return activityClass;
        }

        public int getImageResId() {
            return imageResId;
        }
    }
}
