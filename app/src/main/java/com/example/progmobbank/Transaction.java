package com.example.progmobbank;

public class Transaction {

    private String status;
    private String id;
    private int image;
    private String description;
    private String date;
    private String totalTransaction;
    private int weight;

    public Transaction(String status, String id, int image, String description, String date, String totalTransaction, int weight) {
        this.status = status;
        this.id = id;
        this.image = image;
        this.description = description;
        this.date = date;
        this.totalTransaction = totalTransaction;
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTotalTransaction() {
        return totalTransaction;
    }

    public int getWeight() {
        return weight;
    }
}
