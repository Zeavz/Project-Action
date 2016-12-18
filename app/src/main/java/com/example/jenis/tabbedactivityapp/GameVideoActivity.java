package com.example.jenis.tabbedactivityapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.Stack;

/**
 * Created by dan on 2016-08-23.
 */
public class GameVideoActivity extends AppCompatActivity{

    private String filePath; //path to video file
    private String gameID;
    private int newScore, randomWordNumber;
    private Context context;
    private String reciever;
    private String sender;
    private TextView textViewGuess; //where the guessed word is displayed
    private GridLayout mGridLayout; //button for letter selection go here

    private String gameWord; //current word
    private int wordLength; //num of characters in gameWord
    private String letterSelection; //selection of random letters to input guess
    private char[] currentGuessString; //current string to be displayed in guess;

    private int currentLetter; //current letter position being updated in guess
    private boolean allLetters; //true if all blanks are filled;

    private Button bBackspace; //backspace....................
    private Button bSubmit; //submit.....
    private Button bGuess; //bring up letters to make guess

    private Stack<Button> buttonStack; //buttons used to guess word go here

    private DatabaseReference reference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_video);

        textViewGuess = (TextView) findViewById(R.id.textViewGuess);
        textViewGuess.setLetterSpacing((float)0.2);
        textViewGuess.setText("");

        mGridLayout = (GridLayout) findViewById(R.id.gridView);
        gameID = getIntent().getExtras().getString("id");

        bBackspace = (Button) findViewById(R.id.btnBackspace);
        bSubmit = (Button) findViewById(R.id.btnSubmit);
        bSubmit.setAlpha(.5f);
        bGuess = (Button) findViewById(R.id.btnGuess);

        Intent i = getIntent();
        filePath = i.getExtras().getString("file");
        gameWord = i.getExtras().getString("word");
        reciever = i.getExtras().getString("Reciever");
        sender = i.getExtras().getString("Sender");
        newScore = i.getIntExtra("Score", 0);

        wordLength = gameWord.length();
        drawBlanks(wordLength);

        letterSelection = createLetterSelection().toUpperCase();
        createButtons();

        currentLetter = 0;
        allLetters = false;

        buttonStack = new Stack<Button>();

        bBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBackspace();
            }
        });
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allLetters){
                    submitGuess();
                }
            }
        });

        bGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bGuess.setVisibility(View.GONE);
                mGridLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void submitGuess(){
        String guess = textViewGuess.getText().toString();
        if(guess.toUpperCase().equals(gameWord.toUpperCase())){
            correctGuess();
        }
        else{
            wrongGuess();
        }
        removeTheGame();
    }

    public void removeTheGame(){
        reference = FirebaseDatabase.getInstance().getReference("Users/"+reciever + "/Games/recieved");
        reference.child(gameID).removeValue();
        reference = FirebaseDatabase.getInstance().getReference("Users/"+sender + "/Games/sent");
        reference.child(gameID).removeValue();

    }

    private void correctGuess(){

        Random rand = new Random();
        randomWordNumber = rand.nextInt(6)+1;
        //CreateAGame.makeANewGame(reciever,sender,this,randomWordNumber,newScore);
        AlertDialog.Builder winDialog = new AlertDialog.Builder(this);


        winDialog.setTitle("Correct!")
                .setMessage("It's your turn. Act now or later?")
                .setPositiveButton("Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startAStreakGame();
                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Record later", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .create();

        winDialog.show();
    }
    public void startAStreakGame(){
        CreateAGame.makeANewGame(reciever,sender,this,randomWordNumber,newScore + 1);
    }

    private void wrongGuess(){
        AlertDialog.Builder loseDialog = new AlertDialog.Builder(this);
        loseDialog.setTitle("Wrong!")
                .setMessage("You guess incorrectly")
                .setPositiveButton("Return to Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setCancelable(false)
                .create();

        loseDialog.show();
    }

    private void createButtons(){
        for(int i = 0; i < letterSelection.length(); i++){
            Button b = new Button(this);
            b.setText(letterSelection.charAt(i) + "");
            b.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setId(i);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!allLetters) {
                        Button btn = (Button) v;
                        String let = btn.getText().toString();
                        setLetter(let);

                        btn.setAlpha(.5f);
                        btn.setClickable(false);
                        buttonStack.push(btn);
                    }
                }
            });
            mGridLayout.addView(b);
        }
    }

    private void doBackspace(){
        if(allLetters){
            currentLetter = wordLength;
            allLetters = false;
            bSubmit.setAlpha(.5f);
            bSubmit.setClickable(false);
        }
        if(currentLetter > 0){
            currentGuessString[currentLetter-1] = '_';
            textViewGuess.setText(new String(currentGuessString));
            currentLetter--;
        }

        if(!buttonStack.empty()){
            buttonStack.peek().setAlpha(1);
            buttonStack.pop().setClickable(true);
        }

    }

    //called when letter is pressed to guess word
    private void setLetter(String letter){
        if(!allLetters){
            currentGuessString[currentLetter] = letter.charAt(0);
            textViewGuess.setText(new String(currentGuessString));
            currentLetter++;
        }
        if(currentLetter > wordLength - 1){
            currentLetter = wordLength - 1;
            allLetters = true;
            bSubmit.setAlpha(1);
            bSubmit.setClickable(true);
        }

    }

    //draw initial blanks in guess textview
    private void drawBlanks(int num){
        String mString = "";
        for(int i = 0; i < num; i++){
            mString += "_";
        }
        currentGuessString = mString.toCharArray();

        textViewGuess.setText(mString);
    }

    //random letters + word for guessing
    private String createLetterSelection(){
        int finalLen = gameWord.length() * 2;

        while((finalLen+gameWord.length()) % 7 != 0){
            finalLen--;
        }

        String finalString = gameWord;

        Random r = new Random();
        for (int i = 0; i < finalLen; i++){
            char c = (char)(r.nextInt(26) + 'a');
            finalString += "" + c;
        }

        char a[] = finalString.toCharArray();

        for(int i = 0; i < a.length-1; i++){
            int j = r.nextInt(a.length-1);
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        return new String(a);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if(hasFocus){
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            //   | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }
}