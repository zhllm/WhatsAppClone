package com.example.firebasecrud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasecrud.R;
import com.example.firebasecrud.model.chat.Chats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private final List<Chats> list;
    private final Context context;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_Right = 0;
    FirebaseUser firebaseUser;


    public ChatAdapter(List<Chats> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
        if (viewType == MSG_TYPE_Right) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (list.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_Right;
        }
        return MSG_TYPE_LEFT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.tv_text_message);
        }

        void bind(Chats chats) {
            textMessage.setText(chats.getTextMessage());
        }
    }
}
