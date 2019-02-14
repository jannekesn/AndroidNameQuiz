package com.example.namequizapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.namequizapp.R;
import com.example.namequizapp.data.Constants;
import com.example.namequizapp.data.GlideApp;
import com.example.namequizapp.data.Person;
import com.example.namequizapp.data.Uploads;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;



public class CustomAdapter extends ArrayAdapter<Uploads> {
    private Context context;
    private ArrayList<Uploads> uploadsList;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    public CustomAdapter(Context context, ArrayList<Uploads> uploadsList) {
        super(context, 0 , uploadsList);
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

        final Uploads uploads = uploadsList.get(position);

        if (uploads != null) {
            TextView name = convertView.findViewById(R.id.name);
            final ImageView iv = convertView.findViewById(R.id.image);
            ImageButton delBtn = convertView.findViewById(R.id.delBtn);

            Uri uri = Uri.parse(uploads.getUrl());

            delBtn.setTag(position);

            delBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String pos = v.getTag().toString();
                    int _position = Integer.parseInt(pos);
                    Uploads u = uploadsList.get(_position);
                    String url = u.getUrl();
                    StorageReference delRef = storageReference.child(url);
                    databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
                    databaseReference.getRoot().child(u.getName());
                    databaseReference.removeValue();
                    delRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context.getApplicationContext(), "deleted", Toast.LENGTH_SHORT);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context.getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG);
                        }
                    });

                    uploadsList.remove(_position);
                    notifyDataSetChanged();
                }
            });
            if(name != null)
                name.setText(uploads.getName());
            if(iv != null){
                GlideApp.with(context.getApplicationContext()).load(uploads.getUrl()).into(iv);
            }




        }
        return convertView;

    }
}