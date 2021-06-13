package com.example.accessibility.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accessibility.R;

import java.util.List;

public class TextsScreenAdapter extends RecyclerView.Adapter<TextsScreenAdapter.TextHolder>{

    Context context;
    View view;
    List<String> texts;


    public TextsScreenAdapter(Context context,List<String> texts) {
        this.context=context;
        this.texts=texts;
    }
    @NonNull
    @Override
    public TextHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.text_card,parent,false);
        return new TextHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  TextsScreenAdapter.TextHolder holder, int position) {
        String text_string=texts.get(position);

        holder.text.setText(text_string);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    class TextHolder extends RecyclerView.ViewHolder {
        TextView text;
        public TextHolder(@NonNull  View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
        }
    }
}
