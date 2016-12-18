package com.example.jenis.tabbedactivityapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jenis on 8/21/2016.
 */
public class AddFriendDialog  {

    private static DatabaseReference reference;
    private static FirebaseDatabase database;

    public static void showDialog(final Activity activity, final FirebaseApp app, final String Me){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Enter Friend's Username");
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        database = FirebaseDatabase.getInstance(app);
        reference = database.getReference();

        final EditText username = new EditText(activity);
        username.setInputType(InputType.TYPE_CLASS_TEXT);
        username.setHint("Friend's Username");
        linearLayout.addView(username);

        builder.setView(linearLayout);

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(checkIfUserExists(username.getText().toString())){
                            String path = "Users/" + username.getText().toString() + "/Friends/";
                            reference = database.getReference(path);
                            Friend personAdding = new Friend("\"https://firebasestorage.googleapis.com/v0/b/tabbedpractice.appspot.com/o/Jenis%2Fjenis.png?alt=media&token=27dc016b-2369-4893-8bf7-2c8603f231b0\"", 0);
                            reference.child(Me).setValue(personAdding);

                            Friend personAdded = new Friend("nothing",0);
                            reference = database.getReference("/Users/" + Me + "/Friends/");
                            reference.child(username.getText().toString()).setValue(personAdded);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.show();
    }
    public static boolean checkIfUserExists(String username){
        return true;
    }
}
