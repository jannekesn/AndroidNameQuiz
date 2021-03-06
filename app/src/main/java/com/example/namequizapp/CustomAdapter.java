package com.example.namequizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.namequizapp.R;
import com.example.namequizapp.data.Person;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Person> {
    private Context context;
    private ArrayList<Person> db;

    public CustomAdapter(Context context, int id, ArrayList<Person> db) {
        super(context, id, db);
        this.context = context;
        this.db = db;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_view, null, true);

        }

        Person p = db.get(position);

        if (p != null) {
            TextView name = (TextView) convertView.findViewById(R.id.name);
            ImageView iv = (ImageView) convertView.findViewById(R.id.image);
            ImageButton delBtn = (ImageButton) convertView.findViewById(R.id.delBtn);

            delBtn.setTag(position);

            delBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String pos = v.getTag().toString();
                    int _position = Integer.parseInt(pos);
                    db.remove(_position);
                    notifyDataSetChanged();
                }
            });
            if(name != null)
                name.setText(p.getName());
            if(iv != null)
                iv.setImageURI(p.getImageUri());
        }
        return convertView;

    }
}