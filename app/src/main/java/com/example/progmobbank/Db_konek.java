package com.example.progmobbank;

public class Db_konek {
    public static  String ip = "172.16.153.248:80";

    public static final String urlLogin = "http://"+ip+"/banksampah/api_login.php";
    public static final String urlRegister = "http://"+ip+"/banksampah/api_register.php";
    public static final String urlJual = "http://"+ip+"/banksampah/jual_sampah.php";
    public static final String urlKategori = "http://"+ip+"/banksampah/get_kategori.php";
    public static final String urlUserProfile = "http://" + ip + "/banksampah/api_get_user_profil.php";
    public static final String urlUpdateUserProfile = "http://" + ip + "/banksampah/api_update_user_profil.php";
    public static final String urlDeleteAccount = "http://" + ip + "/banksampah/api_delete_user.php";

}
