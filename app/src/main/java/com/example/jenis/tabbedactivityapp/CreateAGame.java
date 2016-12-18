package com.example.jenis.tabbedactivityapp;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Jenis on 8/23/2016.
 */
public class CreateAGame {

    private static DatabaseReference reference;

    public static void makeANewGame(final String sender, final String reciever, final Context context, int RandomNumber, final int score){

        reference = FirebaseDatabase.getInstance().getReference("Words");
        reference.child(String.valueOf(RandomNumber)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String gameWord = dataSnapshot.getValue(String.class);
                startNewIntent(gameWord, sender, reciever, context, score);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static void startNewIntent(String gameWord, String sender, String reciever, Context context, int score){
        Intent intent = new Intent(context, CameraActivity.class);
        intent.putExtra("Word", gameWord);
        intent.putExtra("Sender", sender);
        intent.putExtra("Reciever", reciever);
        intent.putExtra("Score",score);
        context.startActivity(intent);
    }

}
