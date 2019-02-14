package com.example.namequizapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SharPref extends AppCompatActivity {

    public static final String myPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";

    private TextView textView;
    private Button b1, b2;

    SharedPreferences sharedPrefrecences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shar_pref);

        textView = (TextView) findViewById(R.id.nameSPTxt);
        b1 = (Button) findViewById(R.id.addSPBtn);
        b2 = (Button) findViewById(R.id.clearSPBtn);

        sharedPrefrecences = getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = textView.getText().toString().trim();

                SharedPreferences.Editor editor = sharedPrefrecences.edit();

                editor.putString(Name , n);
                editor.commit();

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPrefrecences.edit();

                editor.clear();

            }
        });

    }
}
