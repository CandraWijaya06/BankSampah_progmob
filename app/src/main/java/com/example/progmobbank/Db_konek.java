package com.example.progmobbank;

public class Db_konek {
    public static  String ip = "172.23.48.1:8080";

    public static final String urlLogin = "http://"+ip+"/banksampah/api_login.php";
    public static final String urlRegister = "http://"+ip+"/banksampah/api_register.php";
    public static final String urlJual = "http://"+ip+"/banksampah/jual_sampah.php";
    public static final String urlKategori = "http://"+ip+"/banksampah/get_kategori.php";
}
