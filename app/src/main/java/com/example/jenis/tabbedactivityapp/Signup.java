package com.example.jenis.tabbedactivityapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;

    private Button mSignup;
    private EditText mUsername, mEmail, mPassword, mRetypePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mSignup = (Button)findViewById(R.id.buttonsignup);
        mUsername = (EditText)findViewById(R.id.username);
        mEmail = (EditText)findViewById(R.id.email);
        mPassword = (EditText)findViewById(R.id.password);
        mRetypePassword = (EditText)findViewById(R.id.password2);

        auth = FirebaseAuth.getInstance();

    }

    public void goToApp(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createUserStuff(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(mUsername.getText().toString())
                .setPhotoUri(Uri.parse("gs://tabbedpractice.appspot.com/Profile Pictures/"))
                .build();
        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(Signup.this, "Creating Account Failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    reference = FirebaseDatabase.getInstance().getReference("Users/"+mUsername.getText().toString()+"/Profile");
                    UserInfo newUser = new UserInfo();
                    newUser.setUserName(mUsername.getText().toString());
                    reference.setValue(newUser);
                }
            }
        });
    }

    public void CreateAccount(String email, String password){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(Signup.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    createUserStuff();
                    goToApp();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });
    }
}
