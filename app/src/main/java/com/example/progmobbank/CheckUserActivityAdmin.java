package com.example.progmobbank;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckUserActivityAdmin extends AppCompatActivity {

    private LinearLayout userListLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user);

        userListLayout = findViewById(R.id.userListLayout);

        fetchUsers();
    }

    private void fetchUsers() {
        String url = Db_konek.urlGetUser;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")) {
                                JSONArray usersArray = response.getJSONArray("users");
                                for (int i = 0; i < usersArray.length(); i++) {
                                    JSONObject userObject = usersArray.getJSONObject(i);
                                    addUserToLayout(userObject);
                                }
                            } else {
                                Toast.makeText(CheckUserActivityAdmin.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CheckUserActivityAdmin.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CheckUserActivityAdmin.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void addUserToLayout(JSONObject userObject) {
        // Inflate layout item_user.xml
        View userView = LayoutInflater.from(this).inflate(R.layout.item_user, userListLayout, false);

        // Initialize views
        TextView usernameTextView = userView.findViewById(R.id.textViewUsername);
        TextView namaTextView = userView.findViewById(R.id.textViewNama);
        TextView emailTextView = userView.findViewById(R.id.textViewEmail);
        TextView noTelpTextView = userView.findViewById(R.id.textViewNoTelp);

        // Set data to views
        try {
            usernameTextView.setText("Username: " + userObject.getString("username"));
            namaTextView.setText("Nama: " + userObject.getString("nama"));
            emailTextView.setText("Email: " + userObject.getString("email"));
            noTelpTextView.setText("Nomor Telpon: " + userObject.getString("no_telp"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Add userView to userListLayout
        userListLayout.addView(userView);
    }
}
