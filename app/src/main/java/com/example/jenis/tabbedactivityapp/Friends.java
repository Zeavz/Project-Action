package com.example.jenis.tabbedactivityapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jenis on 8/8/2016.
 */
public class Friends extends Fragment {

    private View rootView;

    private RecyclerView recyclerFriends;
    private Button addFriend;
    private Button deleteFriend;
    private FriendAdapter friendAdapter;
    private String user;

    private FirebaseApp app;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    private ChildEventListener childEventListener;

    public List<Friend> FriendList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.friendsfrag, container, false);
            recyclerFriends = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            addFriend = (Button) rootView.findViewById(R.id.buttonAddFriend);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerFriends.setLayoutManager(layoutManager);
            recyclerFriends.setHasFixedSize(false);
            resetrecycler();

            friendAdapter = new FriendAdapter(FriendList);

            recyclerFriends.setAdapter(friendAdapter);
            friendAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    recyclerFriends.smoothScrollToPosition(friendAdapter.getItemCount());
                }
            });

            app = FirebaseApp.getInstance();
            database = FirebaseDatabase.getInstance(app);
            auth = FirebaseAuth.getInstance(app);
            //databaseReference = database.getReference();
            user = ((MainActivity)getActivity()).getGlobalUsername();

            databaseReference = database.getReference("Users/"+user+"/Friends/");
            //Query orderFriendsByInteractionPoints = databaseReference.orderByChild("points");
            childEventListener = databaseReference.orderByChild("points").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Friend info = dataSnapshot.getValue(Friend.class);
                    info.setName(dataSnapshot.getKey());
                    friendAdapter.addMessage(info);
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

    public static Friends newInstance(){
        Friends friendss = new Friends();
        return friendss;
    }
    public Friends(){
    }

    public void resetrecycler(){
        int size = FriendList.size();
        if(size>0){
            for (int i=0; i<size;i++){
                this.FriendList.remove(0);
                friendAdapter.notifyItemRemoved(0);
                friendAdapter.notifyItemRangeChanged(0,FriendList.size());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        databaseReference.removeEventListener(childEventListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFriendDialog.showDialog(getActivity(), app, ((MainActivity)getActivity()).getGlobalUsername());
            }
        });
    }

}
