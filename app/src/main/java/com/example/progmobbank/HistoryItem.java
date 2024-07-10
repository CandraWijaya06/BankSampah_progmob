package com.example.progmobbank;

public class HistoryItem {
    private String kategoriSampah;
    private int beratKg;
    private int hargaTotal;
    private String tanggalPenyetoran;
    private String alamat;
    private String status;

    public HistoryItem(String kategoriSampah, int beratKg, int hargaTotal, String tanggalPenyetoran, String alamat, String status) {
        this.kategoriSampah = kategoriSampah;
        this.beratKg = beratKg;
        this.hargaTotal = hargaTotal;
        this.tanggalPenyetoran = tanggalPenyetoran;
        this.alamat = alamat;
        this.status = status;
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
