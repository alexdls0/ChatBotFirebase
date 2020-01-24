package com.example.autistappfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class ShowConversation extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<Conversation> listConversations = new ArrayList<Conversation>();
    private String key = "";
    private TextView tvDate;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_conversation);
        init();
    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        key = getIntent().getStringExtra(KeysRecyclewViewAdapter.KEY);
        tvDate = findViewById(R.id.tvDate);
        tvDate.setText(buildDate());
        ll = findViewById(R.id.ll);
        toListConversations();
    }

    private String buildDate(){
        String r = key;
        r = r.substring(0,2)+ "-" + r.substring(2, 4) + "-" + r.substring(4,r.length());
        return r;
    }

    private void toListConversations() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("user/"+uid+"/"+key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listConversations.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Conversation c = objSnapshot.getValue(Conversation.class);
                    listConversations.add(c);
                }
                if(listConversations.size() != 0){
                    drawListConversation();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(MainActivity.TAG, databaseError.getMessage().toString());
            }
        });
    }

    private void drawListConversation() {
        for(Conversation c : listConversations){
            int i = 1;
            TextView tv = new TextView(this);
            tv.setId(i);
            tv.setTextSize(20);
            tv.setPadding(0,30,0,30);
            if(c.getTipo().equalsIgnoreCase("USUARIO")){
                String cadena = "User: " + c.getCadEsp();
                tv.setText(cadena);
                tv.setBackgroundColor(Color.DKGRAY);
                tv.setTextColor(Color.WHITE);
                ll.addView(tv);
            }else{
                String cadena = "Bot: " + c.getCadEsp();
                tv.setText(cadena);
                ll.addView(tv);
            }
            i++;
        }
    }
}
