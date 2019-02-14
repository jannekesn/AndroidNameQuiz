package com.example.namequizapp;

import android.content.Context;
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

        Button button = (Button) findViewById(R.id.shrdPrfBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SharPref.class);
                startActivity(intent);
            }
        });
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

