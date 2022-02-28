package com.example.firebasecrud.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebasecrud.R;
import com.example.firebasecrud.adapter.CallListAdapter;
import com.example.firebasecrud.adapter.ChatListAdapter;
import com.example.firebasecrud.model.CallList;
import com.example.firebasecrud.model.ChatList;

import java.util.ArrayList;
import java.util.List;

public class TranslateFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translate, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<CallList> lists = new ArrayList<>();
        lists.add(new CallList("11", "LyMin", "15/04/2019,9:10 pm", "https://cdn.pixabay.com/photo/2022/01/19/08/32/melons-6949139_960_720.jpg",
                "missed"));
        lists.add(new CallList("11", "LyMin", "15/04/2019,9:10 pm", "https://cdn.pixabay.com/photo/2020/11/14/09/36/cape-town-5741110_960_720.jpg",
                "income"));
        lists.add(new CallList("11", "LyMin", "15/04/2019,9:10 pm", "https://cdn.pixabay.com/photo/2022/02/14/10/35/flowers-7012876_960_720.jpg",
                "call"));
        recyclerView.setAdapter(new CallListAdapter(lists, getContext()));
        return view;
    }
}