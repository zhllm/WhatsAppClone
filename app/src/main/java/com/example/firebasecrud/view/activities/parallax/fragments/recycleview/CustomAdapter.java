package com.example.firebasecrud.view.activities.parallax.fragments.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasecrud.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomHolder> {
    private Context context;
    private List<String> list = new ArrayList<>();

    public CustomAdapter(Context context, List<String> list) {
        this.context = context;
        this.list.add("1");
        this.list.add("1");
        this.list.add("1");
        this.list.add("1");
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parallax_recycle_cell, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class CustomHolder extends RecyclerView.ViewHolder {

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
