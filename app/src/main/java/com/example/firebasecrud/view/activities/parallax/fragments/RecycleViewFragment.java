package com.example.firebasecrud.view.activities.parallax.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasecrud.R;
import com.example.firebasecrud.view.activities.parallax.fragments.recycleview.CustomAdapter;

public class RecycleViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycle_view, container, false);
        recyclerView.setAdapter(new CustomAdapter(getContext(), null));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return recyclerView;
    }
}