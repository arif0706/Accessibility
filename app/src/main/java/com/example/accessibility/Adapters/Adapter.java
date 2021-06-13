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

public class Adapter extends RecyclerView.Adapter<Adapter.AppNameHolder> {

    View view;
    Context context;

    List<String> app_names;
    AppTitleClickListener appTitleClickListener;

    public Adapter(Context context, List<String> app_names,AppTitleClickListener appTitleClickListener){
        this.context=context;
        this.app_names=app_names;
        this.appTitleClickListener=appTitleClickListener;
    }

    @NonNull
    @Override
    public AppNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.app_name_card,parent,false);
        return new AppNameHolder(view,appTitleClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.AppNameHolder holder, int position) {

        String app_title_string=app_names.get(position);

        holder.app_title.setText(app_title_string);
    }

    @Override
    public int getItemCount() {
        return app_names.size();
    }

    class AppNameHolder extends RecyclerView.ViewHolder {
        TextView app_title;

        AppTitleClickListener appTitleClickListener;
        public AppNameHolder(@NonNull  View itemView,AppTitleClickListener appTitleClickListener) {
            super(itemView);

            app_title=itemView.findViewById(R.id.app_title);
            this.appTitleClickListener=appTitleClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appTitleClickListener.onAppTitleClick(app_names.get(getAdapterPosition()));
                }
            });
        }
    }
    public interface AppTitleClickListener{
        void onAppTitleClick(String app_name);
    }
}
