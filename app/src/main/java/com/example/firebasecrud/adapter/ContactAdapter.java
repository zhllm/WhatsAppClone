package com.example.firebasecrud.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasecrud.R;
import com.example.firebasecrud.model.user.Users;
import com.example.firebasecrud.view.activities.chat.ChatActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> {
    private Context context;
    private List<Users> list;

    public ContactAdapter(Context context, List<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_contact_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Users users = list.get(position);
        holder.tvUsername.setText(users.getUserName());
        holder.tvDesc.setText(users.getBio());
        if (users.getUserProfile() != null) {
            Glide.with(context)
                    .load(Uri.parse(users.getUserProfile()))
                    .into(holder.imageProfile);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(
                        new Intent(context, ChatActivity.class)
                                .putExtra("userID", users.getUserID())
                                .putExtra("userName", users.getUserName())
                                .putExtra("userProfile", users.getUserProfile())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvDesc;
        CircularImageView imageProfile;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvUsername = itemView.findViewById(R.id.tv_username);
            imageProfile = itemView.findViewById(R.id.image_profile);
        }
    }
}
