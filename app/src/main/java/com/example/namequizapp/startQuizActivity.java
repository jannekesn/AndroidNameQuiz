package com.example.namequizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namequizapp.data.Constants;
import com.example.namequizapp.data.Person;
import com.example.namequizapp.data.PersonDB;
import com.example.namequizapp.data.Uploads;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Random;

public class startQuizActivity extends AppCompatActivity {

    private Uploads uploads;
    private ArrayList<Uploads> guessed;
    private ArrayList<Uploads> cl;
    private Integer score = 0;
    private Integer attempts = 0;
    private Uploads currentPerson;
    private ImageView imageView;
    private TextView scoreCountView;
    private TextView attemptsView;
    private EditText guessText;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        startNewGame();
    }

    public Uploads giveRandom;{
        Uploads uploads1;
        int s = 0;
        s = cl.size();
        Random r = new Random();
        int l = 0;
        int res = r.nextInt(s-l) + l;
        return uploads1 = cl.get(res);
    }

    private void startNewGame(){


        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Uploads uploads = snapshot.getValue(Uploads.class);
                    cl.add(uploads);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        currentPerson = giveRandom;
        score = 0;
        scoreCountView = findViewById(R.id.textView_score);
        scoreCountView.setText(score.toString());

        attempts = 0;
        attemptsView = findViewById(R.id.textView_attempts);
        attemptsView.setText(attempts.toString());

        imageView = findViewById(R.id.imageView);
        Uri uri = Uri.parse(currentPerson.getUrl());
        imageView.setImageURI(uri);
        guessed = new ArrayList<>();
        guessed.add(currentPerson);
    }

    public void guess(View view){
        guessText = findViewById(R.id.editText_guess);
        String guess = guessText.getText().toString();

        attempts++;

        if(guess.toLowerCase().equals(currentPerson.getName().toLowerCase())){ //if guessed correct
            score++;
            Toast.makeText(getApplicationContext(), R.string.correct, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong, that was " + currentPerson.getName() + "!", Toast.LENGTH_LONG).show();
        }

        if(guessed.size() >= uploads.count()){ //if game is over
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Game Over")
                    .setMessage("You got " + score + "/" + uploads.count() + " points.")
                    .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startNewGame();
                        }
                    })
                    .setNegativeButton("Return to menu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            while(guessed.contains(currentPerson)){
                currentPerson = giveRandom;
            }

            //update
            guessed.add(currentPerson);
            Uri uri = Uri.parse(currentPerson.getUrl());
            imageView.setImageURI(uri);
            scoreCountView.setText(score.toString());
            attemptsView.setText(attempts.toString());
            guessText.setText("");

        }
    }
}
