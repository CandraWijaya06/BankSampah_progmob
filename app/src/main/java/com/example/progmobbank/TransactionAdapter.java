package com.example.progmobbank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;
    private List<Transaction> transactionListFull; // List untuk menyimpan semua data

    // Constructor
    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
        this.transactionListFull = new ArrayList<>(transactionList); // Salin semua data ke transactionListFull
    }

    // Metode untuk melakukan filter berdasarkan nama produk
    public void filter(String text) {
        transactionList.clear();
        if (text.isEmpty()) {
            transactionList.addAll(transactionListFull);
        } else {
            text = text.toLowerCase();
            for (Transaction transaction : transactionListFull) {
                if (transaction.getDescription().toLowerCase().contains(text)) {
                    transactionList.add(transaction);
                }
            }
        }
        notifyDataSetChanged(); // Mengupdate RecyclerView
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.tvStatus.setText(transaction.getStatus());
        holder.tvId.setText("ID:" + transaction.getId());
        holder.ivImage.setImageResource(transaction.getImage());
        holder.tvDescription.setText(transaction.getDescription());
        holder.tvDate.setText(transaction.getDate());
        holder.tvTotalTransaction.setText(transaction.getTotalTransaction());
        holder.tvWeight.setText("Berat: " + transaction.getWeight() + "Kg");
        holder.btnDetail.setOnClickListener(view -> {
            // Handle button click
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView tvStatus, tvId, tvDescription, tvDate, tvTotalTransaction, tvWeight;
        ImageView ivImage;
        Button btnDetail;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvId = itemView.findViewById(R.id.tvId);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotalTransaction = itemView.findViewById(R.id.tvTotalTransaction);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}
