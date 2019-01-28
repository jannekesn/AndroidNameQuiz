package com.example.namequizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.namequizapp.data.PersonDB;

import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    private PersonDB db = PersonDB.getInstance();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        ListView lv = (ListView) findViewById(R.id.list_view);
        adapter = new CustomAdapter(this, R.layout.list_item_view, db.getAll());
        lv.setAdapter(adapter);

    }

    public void addPerson(View view){
        Intent intent = new Intent(this, AddToDatabaseActivity.class);
        startActivity(intent);
    }
}
