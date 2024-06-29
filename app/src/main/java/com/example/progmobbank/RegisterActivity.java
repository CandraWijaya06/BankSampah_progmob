package com.example.progmobbank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RegisterActivity extends AppCompatActivity {

    private EditText namaEditText;
    private EditText emailEditText;
    private EditText kataSandiEditText;
    private EditText nomorTelpEditText;
    private CheckBox termsCheckBox;
    private Button daftarButton;
    private TextView alreadyHaveAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi view
        namaEditText = findViewById(R.id.namaEditText);
        emailEditText = findViewById(R.id.emailEditText);
        kataSandiEditText = findViewById(R.id.kataSandiEditText);
        nomorTelpEditText = findViewById(R.id.nomorTeleponEditText);
        termsCheckBox = findViewById(R.id.termsCheckBox);
        daftarButton = findViewById(R.id.registerButton);
        alreadyHaveAccountTextView = findViewById(R.id.alreadyHaveAccountTextView);

        // Set clickable text for CheckBox
        String termsText = "Saya setuju dengan Syarat dan Ketentuan serta Kebijakan Privasi";
        SpannableString spannableString = new SpannableString(termsText);

        ClickableSpan termsClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showTermsDialog();
            }
        };

        ClickableSpan privacyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showPrivacyDialog();
            }
        };

        int termsStartIndex = termsText.indexOf("Syarat dan Ketentuan");
        int termsEndIndex = termsStartIndex + "Syarat dan Ketentuan".length();
        int privacyStartIndex = termsText.indexOf("Kebijakan Privasi");
        int privacyEndIndex = privacyStartIndex + "Kebijakan Privasi".length();

        spannableString.setSpan(termsClickableSpan, termsStartIndex, termsEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(privacyClickableSpan, privacyStartIndex, privacyEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsCheckBox.setText(spannableString);
        termsCheckBox.setMovementMethod(LinkMovementMethod.getInstance());
        termsCheckBox.setHighlightColor(ContextCompat.getColor(this, android.R.color.transparent));

        // Set listener untuk tombol daftar
        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil nilai dari input
                String nama = namaEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String kataSandi = kataSandiEditText.getText().toString();
                String nomorTelp = nomorTelpEditText.getText().toString();

                // Validasi input menggunakan metode yang sudah Anda definisikan
                if (!isValidNama(nama)) {
                    namaEditText.setError("Nama harus menggunakan huruf");
                    return;
                }

                if (!isValidEmail(email)) {
                    emailEditText.setError("Email harus menggunakan format yang benar (contoh: example@gmail.com)");
                    return;
                }

                if (!isValidKataSandi(kataSandi)) {
                    kataSandiEditText.setError("Kata sandi harus menggunakan huruf dan angka minimal 8 karakter");
                    return;
                }

                if (!isValidNomorTelp(nomorTelp)) {
                    nomorTelpEditText.setError("Nomor telepon harus menggunakan 12 angka");
                    return;
                }

                if (!termsCheckBox.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Harap setujui Syarat & Ketentuan", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Jika semua input valid, maka lanjutkan ke proses registrasi
                // Misalnya, Anda bisa menambahkan logika untuk menyimpan data ke database atau melakukan proses registrasi lainnya
                // Contoh sederhana menggunakan Toast
                Toast.makeText(RegisterActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();

                // Setelah registrasi berhasil, pindah ke halaman lain atau lakukan tindakan lanjutan
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Menutup activity saat ini agar tidak bisa kembali lagi dengan tombol back
            }
        });

        // Set listener untuk teks "Sudah memiliki akun?"
        alreadyHaveAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke halaman login
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Metode validasi
    private boolean isValidNama(String nama) {
        return nama.matches("[a-zA-Z]+");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@gmail.com$");
    }

    private boolean isValidKataSandi(String kataSandi) {
        return kataSandi.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$");
    }

    private boolean isValidNomorTelp(String nomorTelp) {
        return nomorTelp.matches("^[0-9]{12}$");
    }

    // Method untuk menampilkan dialog syarat dan ketentuan
    private void showTermsDialog() {
        // Buat dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Syarat & Ketentuan");
        builder.setMessage("Syarat dan Ketentuan Pembuatan Akun\n" +
                "1. Umur: Anda harus berusia minimal 18 tahun untuk membuat akun di platform kami.\n" +
                "2. Kebenaran Informasi: Anda harus memberikan informasi yang akurat dan benar saat mendaftar, termasuk nama, alamat email, dan informasi lainnya yang diminta.\n" +
                "3. Kepemilikan Akun: Anda tidak boleh membuat akun atas nama orang lain atau menggunakan informasi palsu untuk membuat akun.\n" +
                "4. Kata Sandi: Anda bertanggung jawab untuk menjaga kerahasiaan kata sandi akun Anda dan tidak boleh membagikannya kepada siapa pun.\n" +
                "5. Penggunaan Akun: Anda hanya boleh menggunakan akun Anda untuk tujuan yang sah dan tidak boleh menggunakan akun Anda untuk melakukan kegiatan yang melanggar hukum atau mengganggu orang lain.\n" +
                "6. Hak Kepemilikan Konten: Anda memahami bahwa konten yang Anda unggah atau bagikan di platform kami menjadi milik kami dan dapat digunakan untuk keperluan komersial atau non-komersial.\n" +
                "7. Larangan Aktivitas: Anda tidak boleh melakukan aktivitas yang dapat merugikan platform kami, termasuk spamming, phishing, atau mengirimkan virus atau malware.\n" +
                "8. Penggunaan Cookie: Kami menggunakan cookie untuk meningkatkan pengalaman pengguna dan Anda memahami bahwa cookie tersebut dapat digunakan untuk mengumpulkan informasi tentang Anda.\n" +
                "9. Perubahan Syarat dan Ketentuan: Kami berhak untuk mengubah syarat dan ketentuan ini sewaktu-waktu tanpa pemberitahuan sebelumnya dan Anda diharapkan untuk memeriksa syarat dan ketentuan ini secara teratur.\n" +
                "10. Hukum yang Berlaku: Syarat dan ketentuan ini diatur dan diinterpretasikan berdasarkan hukum yang berlaku di [nama negara atau wilayah] dan Anda memahami bahwa Anda tunduk pada yurisdiksi hukum tersebut.\n" +
                "Dengan membuat akun, Anda menyatakan bahwa Anda telah membaca, memahami, dan menyetujui syarat dan ketentuan ini.");

        // Tambahkan tombol OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Tindakan setelah tombol OK ditekan (jika perlu)
            }
        });

        // Tampilkan dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method untuk menampilkan dialog kebijakan privasi
    private void showPrivacyDialog() {
        // Buat dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kebijakan Privasi");
        builder.setMessage("1. Pengumpulan Informasi: Kami mengumpulkan informasi pribadi Anda, seperti nama, alamat email, dan informasi lainnya yang diminta saat mendaftar, untuk tujuan membuat akun dan menyediakan layanan kepada Anda.\n" +
                "2. Tujuan Penggunaan Informasi: Kami menggunakan informasi pribadi Anda untuk tujuan seperti memverifikasi identitas, mengirimkan notifikasi, dan meningkatkan pengalaman pengguna.\n" +
                "3. Penggunaan Cookie: Kami menggunakan cookie untuk mengumpulkan informasi tentang Anda, seperti preferensi dan kebiasaan browsing, untuk meningkatkan pengalaman pengguna dan menyajikan konten yang relevan.\n" +
                "4. Penggunaan Informasi Pribadi oleh Pihak Ketiga: Kami dapat membagikan informasi pribadi Anda kepada pihak ketiga yang terpercaya untuk tujuan seperti pengiriman produk atau layanan, namun kami akan memastikan bahwa pihak ketiga tersebut mematuhi kebijakan privasi yang sama dengan kami.\n" +
                "5. Keamanan Informasi: Kami menggunakan teknologi keamanan yang sesuai untuk melindungi informasi pribadi Anda dari akses yang tidak sah, penggunaan yang tidak sah, atau pengungkapan yang tidak sah.\n" +
                "6. Akses dan Perbaikan Informasi: Anda memiliki hak untuk mengakses dan memperbaiki informasi pribadi Anda yang tersimpan di dalam sistem kami.\n" +
                "7. Penghapusan Informasi: Kami akan menghapus informasi pribadi Anda jika Anda meminta penghapusan akun atau jika informasi tersebut tidak lagi diperlukan untuk tujuan yang sah.\n" +
                "8. Penggunaan Informasi untuk Tujuan Komersial: Kami dapat menggunakan informasi pribadi Anda untuk tujuan komersial, seperti mengirimkan promosi atau penawaran, namun Anda dapat memilih untuk tidak menerima komunikasi komersial dari kami.\n" +
                "9. Perubahan Kebijakan Privasi: Kami berhak untuk mengubah kebijakan privasi ini sewaktu-waktu tanpa pemberitahuan sebelumnya dan Anda diharapkan untuk memeriksa kebijakan privasi ini secara teratur.\n" +
                "10. Hak dan Kewajiban: Anda memahami bahwa Anda memiliki hak untuk melindungi informasi pribadi Anda dan kami memiliki kewajiban untuk melindungi informasi pribadi Anda sesuai dengan kebijakan privasi ini.\n" +
                "Dengan membuat akun, Anda menyatakan bahwa Anda telah membaca, memahami, dan menyetujui kebijakan privasi ini.");

        // Tambahkan tombol OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Tindakan setelah tombol OK ditekan (jika perlu)
            }
        });

        // Tampilkan dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
