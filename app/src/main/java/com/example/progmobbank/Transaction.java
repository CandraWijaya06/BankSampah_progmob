package com.example.progmobbank;

public class Transaction {
    private int idJual;
    private String kategoriSampah;
    private int beratKg;
    private int hargaTotal;
    private String tanggalPenyetoran; // Menggunakan String untuk representasi tanggal
    private String alamat;
    private String status;

    public Transaction(int idJual, String kategoriSampah, int beratKg, int hargaTotal,
                       String tanggalPenyetoran, String alamat, String status) {
        this.idJual = idJual;
        this.kategoriSampah = kategoriSampah;
        this.beratKg = beratKg;
        this.hargaTotal = hargaTotal;
        this.tanggalPenyetoran = tanggalPenyetoran;
        this.alamat = alamat;
        this.status = status;
    }

    // Getter untuk semua atribut
    public int getIdJual() {
        return idJual;
    }

    public String getKategoriSampah() {
        return kategoriSampah;
    }

    public int getBeratKg() {
        return beratKg;
    }

    public int getHargaTotal() {
        return hargaTotal;
    }

    public String getTanggalPenyetoran() {
        return tanggalPenyetoran;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getStatus() {
        return status;
    }
}
