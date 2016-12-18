package com.example.jenis.tabbedactivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseApp app;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private EditText mUser;
    private EditText mPass;
    private Button mLogin;
    private Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUser = (EditText)findViewById(R.id.editTextuser);
        mPass = (EditText)findViewById(R.id.editTextpass);
        mLogin = (Button)findViewById(R.id.buttonlogin);
        mSignUp = (Button)findViewById(R.id.buttonsignup);


        app = FirebaseApp.getInstance();
        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    goToMain();
                }
            }
        };
    }
    public void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logIn(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                }
                else{
                    loggedin();
                }
            }
        });
    }

    public void loggedin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void goToSignup(){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn(mUser.getText().toString(), mPass.getText().toString());
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goToSignup();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }
}
