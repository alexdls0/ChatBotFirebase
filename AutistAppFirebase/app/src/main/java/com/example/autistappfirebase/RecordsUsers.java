package com.example.autistappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.autistappfirebase.model.Conversation;
import com.example.autistappfirebase.view.KeysRecyclewViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecordsUsers extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<String> listKeys = new ArrayList<String>();
    private RecyclerView recyclerView;
    private KeysRecyclewViewAdapter recyclewViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_users);
        init();
    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        toListKeys();
    }

    private void toListKeys() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("user/"+uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listKeys.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    listKeys.add(objSnapshot.getKey());
                }
                createRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(MainActivity.TAG, databaseError.getMessage().toString());
            }
        });
    }

    private void createRecycler() {
        recyclewViewAdapter = new KeysRecyclewViewAdapter(this, listKeys);
        recyclerView = findViewById(R.id.rvKeys);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclewViewAdapter);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
