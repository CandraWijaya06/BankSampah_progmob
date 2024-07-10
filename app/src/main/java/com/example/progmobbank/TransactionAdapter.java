package com.example.progmobbank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private Context context;
    private List<Transaction> transactionList;

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.textIdJual.setText("ID Jual: " + transaction.getIdJual());
        holder.textKategoriSampah.setText("Kategori Sampah: " + transaction.getKategoriSampah());
        holder.textBeratKg.setText("Berat (kg): " + transaction.getBeratKg());
        holder.textHargaTotal.setText("Harga Total: $" + transaction.getHargaTotal());
        holder.textTanggalPenyetoran.setText("Tanggal Penyetoran: " + transaction.getTanggalPenyetoran());
        holder.textAlamat.setText("Alamat: " + transaction.getAlamat());
        holder.textStatus.setText("Status: " + transaction.getStatus());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView textIdJual, textKategoriSampah, textBeratKg, textHargaTotal,
                textTanggalPenyetoran, textAlamat, textStatus;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            textIdJual = itemView.findViewById(R.id.text_id_jual);
            textKategoriSampah = itemView.findViewById(R.id.text_kategori_sampah);
            textBeratKg = itemView.findViewById(R.id.text_berat_kg);
            textHargaTotal = itemView.findViewById(R.id.text_harga_total);
            textTanggalPenyetoran = itemView.findViewById(R.id.text_tanggal_penyetoran);
            textAlamat = itemView.findViewById(R.id.text_alamat);
            textStatus = itemView.findViewById(R.id.text_status);
        }
    }
}
