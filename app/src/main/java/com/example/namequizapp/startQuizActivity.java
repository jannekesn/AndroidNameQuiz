package com.example.namequizapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namequizapp.data.Person;
import com.example.namequizapp.data.PersonDB;

import java.util.ArrayList;

public class startQuizActivity extends AppCompatActivity {

    private PersonDB db = PersonDB.getInstance();
    private ArrayList<Person> guessed;
    private Integer score = 0;
    private Person currentPerson;
    private ImageView imageView;
    private TextView scoreCountView;
    private EditText guessText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        startNewGame();
    }

    private void startNewGame(){
        currentPerson = db.getRandom();
        score = 0;
        scoreCountView = findViewById(R.id.textView_score);
        scoreCountView.setText(score.toString());
        imageView = findViewById(R.id.imageView);
        imageView.setImageURI(currentPerson.getImageUri());
        guessed = new ArrayList<>();
        guessed.add(currentPerson);
    }

    public void guess(View view){
        guessText = findViewById(R.id.editText_guess);
        String guess = guessText.getText().toString();

        if(guess.toLowerCase().equals(currentPerson.getName().toLowerCase())){
            score++;
            Toast.makeText(getApplicationContext(), R.string.correct, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong, that was " + currentPerson.getName() + "!", Toast.LENGTH_LONG).show();
        }

        if(guessed.size() >= db.count()){ //if game is over
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Game Over")
                    .setMessage("You got " + score + "/" + db.count() + " points.")
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
                currentPerson = db.getRandom();
            }

            //update
            guessed.add(currentPerson);
            imageView.setImageURI(currentPerson.getImageUri());
            scoreCountView.setText(score.toString());
            guessText.setText("");
        }
    }
}
