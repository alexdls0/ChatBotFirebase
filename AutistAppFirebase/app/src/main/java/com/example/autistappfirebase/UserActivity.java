package com.example.autistappfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    private TextView tvSaludo;
    private FirebaseAuth firebaseAuth;
    private Button btChat, btRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();

        btChat = findViewById(R.id.btChat);
        btChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, ChatBot.class);
                startActivity(intent);
            }
        });

        btRecords = findViewById(R.id.btRecords);
        btRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, RecordsUsers.class);
                startActivity(intent);
            }
        });

        tvSaludo = findViewById(R.id.tvSaludo);
        String email = firebaseAuth.getCurrentUser().getEmail();
        email = email.substring(0, 1).toUpperCase() + email.substring(1);
        email = email.split("@")[0];
        tvSaludo.setText("Hello " + email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            /*Intent intent = new Intent(UserActivity.this, MainActivity.class);
            startActivity(intent);*/
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
