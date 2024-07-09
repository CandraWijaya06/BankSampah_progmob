package com.example.progmobbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HistoryItem> historyList;

    public HistoryAdapter(Context context, ArrayList<HistoryItem> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        }

        HistoryItem currentItem = (HistoryItem) getItem(position);

        TextView textViewKategoriSampah = convertView.findViewById(R.id.textViewKategoriSampah);
        TextView textViewBeratKg = convertView.findViewById(R.id.textViewBeratKg);
        TextView textViewHargaTotal = convertView.findViewById(R.id.textViewHargaTotal);
        TextView textViewTanggalPenyetoran = convertView.findViewById(R.id.textViewTanggalPenyetoran);
        TextView textViewAlamat = convertView.findViewById(R.id.textViewAlamat);
        TextView textViewStatus = convertView.findViewById(R.id.textViewStatus);

        textViewKategoriSampah.setText(currentItem.getKategoriSampah());
        textViewBeratKg.setText(String.valueOf(currentItem.getBeratKg()));
        textViewHargaTotal.setText(String.valueOf(currentItem.getHargaTotal()));
        textViewTanggalPenyetoran.setText(currentItem.getTanggalPenyetoran());
        textViewAlamat.setText(currentItem.getAlamat());
        textViewStatus.setText(currentItem.getStatus());

        return convertView;
    }
}
