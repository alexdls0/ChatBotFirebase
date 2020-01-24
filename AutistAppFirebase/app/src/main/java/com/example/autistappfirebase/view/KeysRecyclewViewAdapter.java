package com.example.autistappfirebase.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autistappfirebase.R;
import com.example.autistappfirebase.ShowConversation;

import java.util.ArrayList;
import java.util.List;

public class KeysRecyclewViewAdapter extends RecyclerView.Adapter<KeysRecyclewViewAdapter.ItemHolder> {

    public static final String KEY = "keyDate";
    private LayoutInflater inflater;
    private List<String> keyList = new ArrayList<>();
    private Context miContexto;

    public KeysRecyclewViewAdapter(Context context, List<String> lista) {
        inflater=LayoutInflater.from(context);
        miContexto = context;
        keyList = lista;
    }

    @NonNull
    @Override
    public KeysRecyclewViewAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= inflater.inflate(R.layout.item_keys,parent,false);
        return new ItemHolder(itemView);
    }

    @Override
    public int getItemCount() {
        int elements = 0;
        if(keyList != null){
            elements = keyList.size();
        }
        return elements;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if(keyList != null){
            final String key = keyList.get(position);
            holder.tvFecha.setText(key);
            holder.btAcceder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(miContexto, ShowConversation.class);
                    intent.putExtra(KEY,key);
                    miContexto.startActivity(intent);
                }
            });
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvFecha;
        private Button btAcceder;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            btAcceder = itemView.findViewById(R.id.btAcceder);
        }
    }
}
