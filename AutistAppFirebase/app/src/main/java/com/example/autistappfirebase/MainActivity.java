package com.example.autistappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="chatbotfirebase-:";
    public static final String USERNAME = "usernameFirebase";
    public static final String PASSWORD = "passwordFirebase";
    private Button btRegister, btLogin;
    private TextInputEditText etUsername, etPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.getText().toString().equalsIgnoreCase("")||
                    etPassword.getText().toString().equalsIgnoreCase("")){
                    Snackbar.make(findViewById(R.id.cl), R.string.faillogin,
                            Snackbar.LENGTH_LONG).show();
                }else{
                    doLogin();
                }
            }
        });

        btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void doLogin() {
        String username = etUsername.getText().toString();
        String ciffPass = sha1(etPassword.getText().toString());
        firebaseAuth.signInWithEmailAndPassword(username, ciffPass).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            Log.v(TAG, "User logged correctly");
                            Intent intent = new Intent(MainActivity.this, UserActivity.class);
                            startActivity(intent);
                        } else {
                            Snackbar.make(findViewById(R.id.cl), R.string.noLogin,
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public static String sha1(String txt) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA1");
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
