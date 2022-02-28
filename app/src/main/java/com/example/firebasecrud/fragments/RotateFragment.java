package com.example.firebasecrud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasecrud.R;
import com.example.firebasecrud.adapter.ChatListAdapter;
import com.example.firebasecrud.model.ChatList;

import java.util.ArrayList;
import java.util.List;

public class RotateFragment extends Fragment {

    private List<ChatList> lists = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rotate, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getLists();
        return view;
    }

    private void getLists() {
        lists.add(new ChatList("11", "LyMin", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2020/11/14/09/36/cape-town-5741110_960_720.jpg"));
        lists.add(new ChatList("12", "wulx", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2022/02/07/15/47/cliffs-6999727_960_720.jpg"));
        lists.add(new ChatList("13", "lxd", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2022/01/19/08/32/melons-6949139_960_720.jpg"));
        lists.add(new ChatList("13", "lxd", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2022/02/10/08/14/heart-7004820_960_720.jpg"));
        lists.add(new ChatList("13", "lxd", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2022/02/12/21/22/toast-7009956_960_720.jpg"));
        lists.add(new ChatList("13", "lxd", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2021/05/26/10/49/woman-6284845_960_720.jpg"));
        lists.add(new ChatList("13", "lxd", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2020/11/14/09/36/cape-town-5741110_960_720.jpg"));
        lists.add(new ChatList("13", "lxd", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2020/11/14/09/36/cape-town-5741110_960_720.jpg"));
        lists.add(new ChatList("13", "lxd", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2022/01/19/08/32/melons-6949139_960_720.jpg"));
        lists.add(new ChatList("13", "lxd", "hello world", "12/04/2021",
                "https://cdn.pixabay.com/photo/2022/02/10/08/14/heart-7004820_960_720.jpg"));


        recyclerView.setAdapter(new ChatListAdapter(lists, getContext()));
    }
}