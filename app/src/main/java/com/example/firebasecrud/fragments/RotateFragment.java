package com.example.firebasecrud.fragments;

import android.os.Bundle;
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

    private List<ChatList> lists = new ArrayList<>();
    private List<String> allUserIds = new ArrayList<>();
    private FragmentRotateBinding binding;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private static final String TAG = "RotateFragmentLog";
    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rotate, container, false);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.lnInvite.setVisibility(View.INVISIBLE);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
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
                        binding.progressBar.setVisibility(View.GONE);
                        for (DataSnapshot sp : snapshot.getChildren()) {
                            String userID = Objects.requireNonNull(sp.child("chatid").getValue()).toString();
                            Log.d(TAG, "onDataChange: userID: " + userID);
                            getUserData(userID);
                        }
                        binding.recycleView.setAdapter(new ChatListAdapter(lists, getContext()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    void getUserData(String userID) {
        firebaseFirestore.collection("Users").document(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ChatList chatList = new ChatList(
                                documentSnapshot.getString("userID"),
                                documentSnapshot.getString("userName"),
                                "this is a description...",
                                "",
                                documentSnapshot.getString("userProfile")
                        );
                        lists.add(chatList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
    }
}