package com.example.accessibility.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accessibility.R;

import java.util.List;

public class Chat_title_Adapter extends RecyclerView.Adapter<Chat_title_Adapter.ChatTitleViewHolder> {

    View view;
    Context context;
    List<String> chat_titles;
    ChatTitleClickListener chatTitleClickListener;
    public Chat_title_Adapter(Context context, List<String> chat_titles, ChatTitleClickListener chatTitleClickListener){
        this.context=context;
        this.chat_titles=chat_titles;
        this.chatTitleClickListener=chatTitleClickListener;
    }
    @NonNull

    @Override
    public ChatTitleViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.chat_title_card,parent,false);
        return new ChatTitleViewHolder(view,chatTitleClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  Chat_title_Adapter.ChatTitleViewHolder holder, int position) {
        String chat_string=chat_titles.get(position);
        holder.chat_title.setText(chat_string);
    }

    @Override
    public int getItemCount() {
        return chat_titles.size();
    }

    class ChatTitleViewHolder extends RecyclerView.ViewHolder {
        ChatTitleClickListener chatTitleClickListener;
        TextView chat_title;
        public ChatTitleViewHolder(@NonNull  View itemView,ChatTitleClickListener chatTitleClickListener) {
            super(itemView);

            chat_title=itemView.findViewById(R.id.chat);


            this.chatTitleClickListener=chatTitleClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatTitleClickListener.onChatClick(chat_titles.get(getAdapterPosition()));
                }
            });
        }
    }
    public interface ChatTitleClickListener{
        void onChatClick(String chat_title);
    }
}
