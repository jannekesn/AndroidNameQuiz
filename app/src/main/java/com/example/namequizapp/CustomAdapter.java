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
import com.example.namequizapp.data.Uploads;

import java.util.ArrayList;
import java.util.List;

import com.bumtech.glide.Glide

public class CustomAdapter extends ArrayAdapter<Person> {
    private Context context;
    private List<Uploads> uploadsList;

    public CustomAdapter(Context context, int id, List<Uploads> uploadsList) {
        super(context, id, uploadsList);
        this.context = context;
        this.uploadsList = uploadsList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_view, null, true);

        }

        Uploads uploads = uploadsList.get(position);

        if (uploads != null) {
            TextView name = (TextView) convertView.findViewById(R.id.name);
            ImageView iv = (ImageView) convertView.findViewById(R.id.image);
            ImageButton delBtn = (ImageButton) convertView.findViewById(R.id.delBtn);

            delBtn.setTag(position);

            delBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String pos = v.getTag().toString();
                    int _position = Integer.parseInt(pos);
                    uploadsList.remove(_position);
                    notifyDataSetChanged();
                }
            });
            if(name != null)
                name.setText(uploads.getName());
            if(iv != null)
                Glide.with(context).load(uploads.getUrl()).into(iv);
        }
        return convertView;

    }
}