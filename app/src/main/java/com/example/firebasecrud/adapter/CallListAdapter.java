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
import com.example.firebasecrud.model.CallList;
import com.example.firebasecrud.model.ChatList;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.CallHolder> {
    private List<CallList> lists;
    private Context context;

    public CallListAdapter(List<CallList> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public CallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_call_list, parent, false);
        return new CallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallHolder holder, int position) {
        CallList call = lists.get(position);
        holder.tvDate.setText(call.getDate());
        holder.tvName.setText(call.getUserName());

        if (call.getCallType().equals("missed")) {
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_arrow_downward_24));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_red_dark));
        } else if (call.getCallType().equals("income")) {
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_arrow_downward_24));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_arrow_upward_24));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_green_dark));
        }

        Glide.with(context)
                .load(call.getProfile())
                .into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public static class CallHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvDate;
        private CircularImageView profile;
        private ImageView arrow;

        public CallHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvName = itemView.findViewById(R.id.tvName);
            profile = itemView.findViewById(R.id.image_profile);
            arrow = itemView.findViewById(R.id.image_arrow);
        }
    }
}
