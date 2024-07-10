package com.example.progmobbank;

public class Db_konek {
    public static  String ip = "172.23.48.1:8080";

    public static final String urlLogin = "http://"+ip+"/banksampah/api_login.php";
    public static final String urlRegister = "http://"+ip+"/banksampah/api_register.php";
    public static final String urlJual = "http://"+ip+"/banksampah/jual_sampah.php";
    public static final String urlKategori = "http://"+ip+"/banksampah/get_kategori.php";
    public static final String urlUserProfile = "http://" + ip + "/banksampah/api_get_user_profil.php";
    public static final String urlUpdateUserProfile = "http://" + ip + "/banksampah/api_update_user_profil.php";
    public static final String urlDeleteAccount = "http://" + ip + "/banksampah/api_delete_user.php";
    public static final String urlGetHistory = "http://" + ip + "/banksampah/history.php";
    public static final String urlGetUser = "http://" + ip + "/banksampah/get_user.php";
    public static final String urlEditKategori = "http://" + ip + "/banksampah/edit_kategori.php";
    public static final String UrlGetKategori = "http://" + ip + "/banksampah/kategori.php";


}
