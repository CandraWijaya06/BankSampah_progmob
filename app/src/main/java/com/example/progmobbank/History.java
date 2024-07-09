package com.example.progmobbank;

public class History {
    private String kategoriSampah;
    private double beratKg;
    private double hargaTotal;
    private String tanggalPenyetoran;
    private String alamat;
    private String status;

    public History(String kategoriSampah, double beratKg, double hargaTotal, String tanggalPenyetoran, String alamat, String status) {
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

    public double getBeratKg() {
        return beratKg;
    }

    public double getHargaTotal() {
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
