package com.example.firebasecrud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasecrud.R;
import com.example.firebasecrud.model.ChatList;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatHolder> {
    private List<ChatList> lists;
    private Context context;

    public ChatListAdapter(List<ChatList> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_chat_list, parent, false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        ChatList chat = lists.get(position);
        holder.tvDate.setText(chat.getDate());
        holder.tvDesc.setText(chat.getDescription());
        holder.tvName.setText(chat.getUserName());

        Glide.with(context)
                .load(chat.getUrlProfile())
                .into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class ChatHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvDate, tvDesc;
        private CircularImageView profile;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvName = itemView.findViewById(R.id.tvName);
            profile = itemView.findViewById(R.id.image_profile);
        }
    }
}
