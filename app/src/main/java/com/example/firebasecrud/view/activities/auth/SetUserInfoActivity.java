package com.example.firebasecrud.view.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebasecrud.MainActivity;
import com.example.firebasecrud.R;
import com.example.firebasecrud.databinding.ActivityPhoneLoginBinding;
import com.example.firebasecrud.databinding.ActivitySetUserInfo2Binding;
import com.example.firebasecrud.model.user.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetUserInfoActivity extends AppCompatActivity {
    private ActivitySetUserInfo2Binding binding;
    private ProgressDialog progressDialog;
    private static final String TAG = "SetUserInfoActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_user_info2);
        progressDialog = new ProgressDialog(this);

        initButtonClick();
    }

    private void initButtonClick() {
        binding.edNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, binding.edName.getText().toString());
                if (TextUtils.isEmpty(binding.edName.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please input username", Toast.LENGTH_SHORT).show();
                } else {
                    doUpdate();
                }
            }
        });
        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "this function is not ready to user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doUpdate() {
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            firebaseFirestore.collection("Users")
                    .document(user.getUid())
                    .update("userName", binding.edName.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "SetUserInfoActivityLog onSuccess");
                            Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "SetUserInfoActivityLog addOnFailureListener: " + e.getMessage());
                            progressDialog.dismiss();

                        }
                    });
        } else {
            Log.d(TAG, "SetUserInfoActivityLog error");
            Toast.makeText(getApplicationContext(), "you need to login first", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}