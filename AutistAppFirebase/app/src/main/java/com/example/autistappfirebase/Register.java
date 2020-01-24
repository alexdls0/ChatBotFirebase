package com.example.autistappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword, etRepeatPassword;
    private Button btRegister;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(Register.this, UserActivity.class);
            startActivity(intent);
            finish();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);

        btRegister = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.getText().toString().equalsIgnoreCase("") ||
                        etPassword.getText().toString().equalsIgnoreCase("") ||
                        etRepeatPassword.getText().toString().equalsIgnoreCase("")||
                        !etRepeatPassword.getText().toString().equalsIgnoreCase(
                                etPassword.getText().toString())||
                        !etUsername.getText().toString().contains("@")){
                            Snackbar.make(findViewById(R.id.cl), R.string.failregister,
                            Snackbar.LENGTH_LONG).show();
                        }
                else{
                    doRegister();
                }
            }
        });
    }

    private void doRegister() {
        final String username = etUsername.getText().toString();
        final String ciffPass = MainActivity.sha1(etPassword.getText().toString());
        firebaseAuth.createUserWithEmailAndPassword(username,
                ciffPass).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.v(MainActivity.TAG, "User created correctly");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Log.v(MainActivity.TAG, "UID Registro: " + uid);

                    DatabaseReference referenciaItem = database.getReference("user");
                    referenciaItem.child(uid).setValue("");

                    Intent intent = new Intent(Register.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.cl), R.string.failFirebase,
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
