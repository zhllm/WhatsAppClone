package com.example.firebasecrud.view.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.firebasecrud.MainActivity;
import com.example.firebasecrud.R;
import com.example.firebasecrud.databinding.ActivityPhoneLoginBinding;
import com.example.firebasecrud.model.user.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityPhoneLoginBinding binding;
    private FirebaseAuth mAuth;
    private String mVerificationID;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    static String[] countries = {"India", "USA", "China", "Japan", "Other"};
    private static final String TAG = "PhoneLoginActivityLog";
    private ProgressDialog progressDialog;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_login);
        binding.spinnerCountry.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        binding.spinnerCountry.setAdapter(aa);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("hello");


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked");
                if (binding.btnNext.getText().toString().equals("Confirm")) {
                    Log.d(TAG, "Confirm");
                    if (binding.edCode.getText().toString().isEmpty()) {
                        Log.d(TAG, "edCode isEmpty");
                        Toast.makeText(PhoneLoginActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressDialog.setMessage("Please wait verifyPhoneNumberWithCode");
                    progressDialog.show();
                    verifyPhoneNumberWithCode(mVerificationID, binding.edCode.getText().toString());
                    return;
                }
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                startPhoneNumberVerification("+8618380481994");
                // startPhoneNumberVerification("+" + binding.edCode.getText().toString() + binding.edPhone.getText().toString()); // binding.edPhone.getText().toString()
            }
        });

    }

    private void startPhoneNumberVerification(String phoneNumber) {
        Log.d(TAG, "startPhoneNumberVerification: ");
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Log.d(TAG, "onComplete: ");
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Log.d(TAG, "onVerificationFailed: error " + e.getMessage());
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                Log.d(TAG, "onCodeSent:" + verificationId);

                                mVerificationID = verificationId;
                                mResendToken = token;
                                binding.btnNext.setText("Confirm");
                                progressDialog.dismiss();
                            }
                        }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            progressDialog.dismiss();
                            FirebaseUser user = task.getResult().getUser();
                            Log.d(TAG, user.getUid() + user.getPhoneNumber() + user.toString());
                            String userID = user.getUid();
                            Users users = new Users(userID,
                                    "",
                                    user.getPhoneNumber(),
                                    "",
                                    "", "", "", "", "", "");
                            fireStore.collection("Users")
                                    .document(userID)
                                    .set(users)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "add:user success");
                                            startActivity(new Intent(PhoneLoginActivity.this, SetUserInfoActivity.class));
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "add:user fail" + e.getMessage());
                                        }
                                    });
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}