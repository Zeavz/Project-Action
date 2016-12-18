package com.example.jenis.tabbedactivityapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by dan on 2016-08-30.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private DatabaseReference reference;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
        reference = FirebaseDatabase.getInstance().getReference("Users/"+ FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString()+"/");
        reference.child("Token").setValue(refreshedToken);}
    }
}
