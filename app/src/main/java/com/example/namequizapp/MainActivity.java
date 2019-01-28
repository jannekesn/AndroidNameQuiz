package com.example.namequizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToDatabase(View view){
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

    public void startQuiz(View view){
        Intent intent = new Intent(this, startQuizActivity.class);
        startActivity(intent);
    }

    public void addToDatabase(View view){
        Intent intent = new Intent(this, AddToDatabaseActivity.class);
        startActivity(intent);
    }
}
