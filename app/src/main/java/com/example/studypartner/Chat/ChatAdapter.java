package com.example.studypartner.Chat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studypartner.R;


import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<ChatObject> chatList;
    private Context context;

    public ChatAdapter(List<ChatObject> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolder rov = new ChatViewHolder(layoutView);
        return rov;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.mMessage.setText(chatList.get(position).getMessage());

        if (chatList.get(position).getCurrentUser()) {
            holder.mMessage.setGravity(Gravity.END);
            holder.mMessage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#404040"));
            ((LinearLayout.LayoutParams) holder.mMessage.getLayoutParams()).gravity = Gravity.END;
        } else {
            holder.mMessage.setGravity(Gravity.START);
            holder.mMessage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
            ((LinearLayout.LayoutParams) holder.mMessage.getLayoutParams()).gravity = Gravity.START;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}