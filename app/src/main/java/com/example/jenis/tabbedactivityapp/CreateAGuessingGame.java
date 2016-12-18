package com.example.jenis.tabbedactivityapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Jenis on 8/27/2016.
 */
public class CreateAGuessingGame {

    private static DatabaseReference reference;
    private static StorageReference storageReference;
    private static String word;
    private static String reciever;
    private static String sender;
    private static String gamesID;
    private static int score;

    public static void CreateGame(final String gameID, final Context context){
        gamesID = gameID;
        reference = FirebaseDatabase.getInstance().getReference("Games");
        reference.child(gameID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Game game = dataSnapshot.getValue(Game.class);
                word = game.getWord();
                reciever = game.getReciever();
                sender = game.getSender();
                score = game.getScore();
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

        storageReference = FirebaseStorage.getInstance().getReference("Games");
        storageReference.child(gameID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Intent intent = new Intent(context, GameVideoActivity.class);
                intent.putExtra("file",uri.toString());
                intent.putExtra("id", gamesID);
                intent.putExtra("Reciever", reciever);
                intent.putExtra("Sender", sender);
                intent.putExtra("Score", score);
                intent.putExtra("word", word);
                context.startActivity(intent);
            }
        });
    }
}
