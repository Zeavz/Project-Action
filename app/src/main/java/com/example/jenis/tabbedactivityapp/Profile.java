package com.example.jenis.tabbedactivityapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jenis on 8/22/2016.
 */
public class Profile extends Fragment{

    private ImageView BigProfilePicture;
    private TextView TextForName, TextForFirstName, TextForLastName, TextForBirthday, TextForPhone;
    private DatabaseReference databaseReference;
    private String username;
    private Button message, delete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profilefrag, container, false);
        BigProfilePicture = (ImageView)rootView.findViewById(R.id.imageViewBigProfilePicture);
        TextForName = (TextView)rootView.findViewById(R.id.textViewFullName);
        TextForFirstName = (TextView)rootView.findViewById(R.id.textViewFirstName);
        TextForLastName = (TextView)rootView.findViewById(R.id.textViewLastName);
        TextForBirthday = (TextView)rootView.findViewById(R.id.textViewBirthday);
        TextForPhone = (TextView)rootView.findViewById(R.id.textViewPhoneNumber);
        message = (Button)rootView.findViewById(R.id.buttonMessageFromProfile);
        delete = (Button)rootView.findViewById(R.id.buttonDelete);

        message.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        username = ((MainActivity)getActivity()).getGlobalUsername();
        TextForName.setText(username);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users/"+((MainActivity)getActivity()).getGlobalUsername()+"/Profile");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                UserInfo info = dataSnapshot.getValue(UserInfo.class);
//                GliderStuff.profileImage(info.getLinkToProfile(),BigProfilePicture);
//
//                TextForFirstName.setText(info.getfirstName());
//                TextForLastName.setText(info.getlastName());
//                TextForBirthday.setText(info.getBirthday());
//                TextForPhone.setText(info.getPhone());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    public static Profile newInstance(){
        Profile profile = new Profile();
        return profile;
    }
    public Profile(){}

}
