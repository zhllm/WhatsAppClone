package com.example.firebasecrud.view.activities.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.firebasecrud.R;
import com.example.firebasecrud.adapter.ChatAdapter;
import com.example.firebasecrud.databinding.ActivityChatBinding;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String receiveID;
    private final static String TAG = "ChatActivityLog";
    private ChatAdapter chatAdapter;
    private List<Chats> list;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String username = intent.getStringExtra("userName");
        String profile = intent.getStringExtra("userProfile");
        receiveID = intent.getStringExtra("userID");
        if (receiveID != null) {
            binding.tvUsername.setText(username);
            if (profile != null) {
                if (profile.equals("")) {
                    binding.imageProfile.setImageDrawable(getDrawable(R.drawable.mlale_plc));
                } else {
                    Glide.with(this)
                            .load(profile)
                            .into(binding.imageProfile);
                }
            }
        }

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.edMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(binding.edMessage.getText().toString())) {
                    binding.btnSend.setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_voice_24));
                } else {
                    binding.btnSend.setImageDrawable(getDrawable(R.drawable.ic_baseline_send_24));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        initBtnClick();
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setStackFromEnd(true);
        binding.recycleView.setLayoutManager(manager);
        readChats();
    }

    private void readChats() {
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Chats")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            list.clear();
                            for (DataSnapshot sp : snapshot.getChildren()) {
                                Chats chats = sp.getValue(Chats.class);
                                if (chats != null
                                        && (
                                        chats.getSender().equals(firebaseUser.getUid()) && chats.getReceiver().equals(receiveID)
                                                || chats.getReceiver().equals(firebaseUser.getUid()) && chats.getSender().equals(receiveID))
                                ) {
                                    // chat 过滤
                                    list.add(chats);
                                }
                            }
                            if (chatAdapter != null) {
                                chatAdapter.notifyDataSetChanged();
                            } else {
                                chatAdapter = new ChatAdapter(list, ChatActivity.this);
                                binding.recycleView.setAdapter(chatAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, "readChats: error: " + e.getMessage());
        }
    }

    private void initBtnClick() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(binding.edMessage.getText().toString())) {
                    sendTextMessage(binding.edMessage.getText().toString());
                    binding.edMessage.setText("");
                }
            }
        });
    }

    private void sendTextMessage(String text) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String today = formatter.format(date);

        Calendar currentDateTime = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        String currentTime = df.format(currentDateTime.getTime());
        Chats chats = new Chats(
                today + ", " + currentTime,
                text,
                "TEXT",
                firebaseUser.getUid(),
                receiveID
        );

        reference.child("Chats").push().setValue(chats)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "setValue(chats): success: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "setValue(chats): fail: " + e.getMessage());
                    }
                });
        DatabaseReference chatRef1 = FirebaseDatabase.getInstance()
                .getReference("ChatList")
                .child(firebaseUser.getUid())
                .child(receiveID);
        chatRef1.child("chatid").setValue(receiveID);

        DatabaseReference chatRef2 = FirebaseDatabase.getInstance()
                .getReference("ChatList")
                .child(receiveID)
                .child(firebaseUser.getUid());
        chatRef2.child("chatid").setValue(firebaseUser.getUid());


    }
}