package com.example.firebasecrud.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasecrud.R;
import com.example.firebasecrud.adapter.ChatListAdapter;
import com.example.firebasecrud.databinding.FragmentRotateBinding;
import com.example.firebasecrud.model.ChatList;
import com.example.firebasecrud.model.chat.Chats;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RotateFragment extends Fragment {

    private final List<ChatList> lists = new ArrayList<>();
    private final List<String> allUserIds = new ArrayList<>();
    private FragmentRotateBinding binding;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private static final String TAG = "RotateFragmentLog";
    private FirebaseFirestore firebaseFirestore;
    private final Handler handler = new Handler();
    private ChatListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rotate, container, false);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.lnInvite.setVisibility(View.INVISIBLE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        adapter = new ChatListAdapter(lists, getContext());
        binding.recycleView.setAdapter(adapter);
        Log.d(TAG, "onCreateView: ");
        getLists();

        return binding.getRoot();
    }

    private void getLists() {
        binding.progressBar.setVisibility(View.VISIBLE);
        reference.child("ChatList")
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lists.clear();
                        allUserIds.clear();
                        binding.progressBar.setVisibility(View.GONE);
                        for (DataSnapshot sp : snapshot.getChildren()) {
                            String userID = Objects.requireNonNull(sp.child("chatid").getValue()).toString();
                            allUserIds.add(userID);
                        }
                        getUserInfo();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    void getUserInfo() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (String userID : allUserIds) {
                    firebaseFirestore.collection("Users")
                            .document(userID)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    try {
                                        ChatList chatList = new ChatList(
                                                documentSnapshot.getString("userID"),
                                                documentSnapshot.getString("userName"),
                                                "this is a description...",
                                                "",
                                                documentSnapshot.getString("userProfile")
                                        );
                                        lists.add(chatList);
                                    } catch (Exception e) {
                                        Log.d(TAG, "onSuccess: parse json error: " + e.getMessage());
                                    }
                                    if (adapter != null) {
                                        adapter.notifyItemChanged(0);
                                        adapter.notifyDataSetChanged();
                                        Log.d(TAG, "onSuccess: adapter count" + adapter.getItemCount());
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }
        });

    }
}