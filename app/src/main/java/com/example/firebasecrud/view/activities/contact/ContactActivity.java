package com.example.firebasecrud.view.activities.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.firebasecrud.R;
import com.example.firebasecrud.adapter.ContactAdapter;
import com.example.firebasecrud.databinding.ActivityContactBinding;
import com.example.firebasecrud.model.user.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private ActivityContactBinding binding;
    private final List<Users> list = new ArrayList<>();
    private ContactAdapter adapter;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    static final String TAG = "ContactActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact);
        binding.contactRecycle.setLayoutManager(new LinearLayoutManager(this));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (firebaseUser != null) {
            getContactList();
        }
    }

    private void getContactList() {
        firebaseFirestore.collection("Users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d(TAG, "get users success");
                        for (QueryDocumentSnapshot querySnapshot : queryDocumentSnapshots) {
                            String userID = querySnapshot.getString("userID");
                            if (userID != null && !userID.equals(firebaseUser.getUid())) {
                                String username = querySnapshot.getString("userName");
                                String imageProfile = querySnapshot.getString("userProfile");
                                String desc = querySnapshot.getString("bio");
                                Users users = new Users();
                                users.setUserID(userID);
                                users.setBio(desc);
                                users.setUserName(username);
                                users.setUserProfile(imageProfile);
                                list.add(users);
                            }
                        }
                        adapter = new ContactAdapter(ContactActivity.this, list);
                        binding.contactRecycle.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}