package com.example.jenis.tabbedactivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
 * Created by Jenis on 7/7/2016.
 */
public class Feed extends Fragment {

    private Button mPlay;

    private RecyclerView mRecycle;
    private RecyclerView mRecyclesent;
    private RecyclerView mRecyclePending;

    private SingleFeedAdapter mAdapter;
    private SingleFeedAdapter mAdapter2;
    private SingleFeedAdapter mAdapter3;
    private TextView currentUser;
    private String whoIsLoggedInRightNow;

    public FirebaseApp app;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private DatabaseReference databaseReference3;
    private FirebaseAuth auth;

    private ChildEventListener childEventListener;
    private ChildEventListener childEventListener2;
    private ChildEventListener childEventListener3;

    public List<singleNameFeedClass> FeedList = new ArrayList<>();
    public List<singleNameFeedClass> FeedList2 = new ArrayList<>();
    private List<singleNameFeedClass> FeedList3 = new ArrayList<>();

    public List<String> recievedGames = new ArrayList<>();
    public List<String> sentGames = new ArrayList<>();
    public List<String> pendingGames = new ArrayList<>();

    public static Feed newInstance(){
        Feed feed = new Feed();
        return feed;
    }
    public Feed(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feedfrag, container, false);

        mPlay = (Button)rootView.findViewById(R.id.buttonPlay);
        currentUser = (TextView)rootView.findViewById(R.id.textViewuser);
        mPlay = (Button)rootView.findViewById(R.id.buttonPlay);
        whoIsLoggedInRightNow = ((MainActivity)getActivity()).getGlobalUsername();

        mRecycle = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);
        mRecyclesent = (RecyclerView)rootView.findViewById(R.id.my_recycler_view2);
        mRecyclePending = (RecyclerView)rootView.findViewById(R.id.my_recycler_view3);

        resetrecycler(FeedList, mAdapter);
        resetrecycler(FeedList2, mAdapter2);
        resetrecycler(FeedList3, mAdapter3);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity());

        mRecycle.setLayoutManager(layoutManager);
        mRecyclesent.setLayoutManager(layoutManager2);
        mRecyclePending.setLayoutManager(layoutManager3);

        mRecycle.setHasFixedSize(true);
        mRecyclesent.setHasFixedSize(true);
        mRecyclePending.setHasFixedSize(true);

        mAdapter = new SingleFeedAdapter(FeedList);
        mAdapter2 = new SingleFeedAdapter(FeedList2);
        mAdapter3 = new SingleFeedAdapter(FeedList3);


        mRecycle.setAdapter(mAdapter);
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecycle.smoothScrollToPosition(mAdapter.getItemCount());
            }
        });

        mRecyclesent.setAdapter(mAdapter2);
        mAdapter2.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclesent.smoothScrollToPosition(mAdapter2.getItemCount());
            }
        });

        mRecyclePending.setAdapter(mAdapter3);
        mAdapter3.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mRecyclePending.smoothScrollToPosition(mAdapter3.getItemCount());
            }
        });

        app = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(app);
        auth = FirebaseAuth.getInstance(app);

        databaseReference = database.getReference();
        databaseReference2 = database.getReference();
        databaseReference3 = database.getReference();

        currentUser.setText(whoIsLoggedInRightNow);


        databaseReference = database.getReference("Users/"+whoIsLoggedInRightNow+"/Games/recieved");
        //databaseReference = database.getReference("Users/Zeavz/Games/recieved");
        childEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                singleNameFeedClass info = new singleNameFeedClass();
                info.setName(dataSnapshot.getValue(String.class));
                info.setGameId(dataSnapshot.getKey());
                info.setSentOrRecieved("recieved");
                mAdapter.addMessage(info);
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
        databaseReference2 = database.getReference("Users/"+whoIsLoggedInRightNow+"/Games/sent");
        childEventListener2 = databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                singleNameFeedClass info2 = new singleNameFeedClass();
                info2.setName(dataSnapshot.getValue(String.class));
                info2.setGameId(dataSnapshot.getKey());
                info2.setSentOrRecieved("sent");
                mAdapter2.addMessage(info2);
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
        databaseReference3 = database.getReference("Users/"+whoIsLoggedInRightNow+"/Games/pending");
        childEventListener3 = databaseReference3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                singleNameFeedClass info3 = new singleNameFeedClass();
                info3.setName(dataSnapshot.getValue(String.class));
                info3.setGameId(dataSnapshot.getKey());
                info3.setSentOrRecieved("pending");
                mAdapter2.addMessage(info3);
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

    @Override
    public void onPause() {
        super.onPause();
        databaseReference.removeEventListener(childEventListener);
        databaseReference2.removeEventListener(childEventListener2);
    }

    public void startCameraStuff(){
        Intent intent = new Intent(getActivity(),CameraActivity.class);
        startActivity(intent);
    }

    public void resetrecycler(List<singleNameFeedClass> x, SingleFeedAdapter y){
        int size = x.size();
        if(size>0){
            for (int i=0; i<size;i++){
                x.remove(0);
                y.notifyItemRemoved(0);
                y.notifyItemRangeChanged(0,x.size());
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        //mPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startCameraStuff();
//            }
//        });
    }
}
