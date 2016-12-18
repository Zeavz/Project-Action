package com.example.jenis.tabbedactivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private FirebaseApp app;
    private FirebaseAuth auth;
    public String GlobalUsername;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = FirebaseApp.getInstance();
        auth = FirebaseAuth.getInstance(app);
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    goToLogin();
                }
                else{
                    GlobalUsername = auth.getCurrentUser().getDisplayName();
                    FirebaseMessaging.getInstance().subscribeToTopic("test");
                    onTokenRefresh();
                }
            }
        });
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FirebaseInstanceId.getInstance().getToken();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void goToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void removeTheGame(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child("Zeavz").child("Games").child("recieved").child("TESTERDATA").removeValue();
    }

    public void StartAGame(){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public String getGlobalUsername(){
        return GlobalUsername;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewPager.setCurrentItem(2);
        if (auth.getCurrentUser() == null){
            goToLogin();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_signOut) {
            signout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signout(){
        auth.signOut();
    }

    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        reference = FirebaseDatabase.getInstance().getReference("Users/"+auth.getCurrentUser().getDisplayName()+"/");
        reference.child("Token").setValue(refreshedToken);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return PlaceholderFragment.newInstance(position + 1);
                case 1:
                    return Friends.newInstance();
                case 2:
                    return Feed.newInstance();
                case 3:
                    return Profile.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Store";
                case 1:
                    return "Friends";
                case 2:
                    return "Feed";
                case 3:
                    return "Profile";
            }
            return null;
        }
    }
}
