package com.example.namequizapp;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.namequizapp.data.Person;
import com.example.namequizapp.data.PersonDB;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddToDatabaseActivity extends AppCompatActivity {

    static final int REQUEST_CAMERA = 1;
    static final int REQUEST_GALLERY = 2;

    private PersonDB db = PersonDB.getInstance();
    private ImageView iv;
    private Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_database);
    }

    public void selectImage(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*"); //to specify type of data to return
        startActivityForResult(Intent.createChooser(intent, "Select file"), REQUEST_GALLERY);
    }

    public void captureImage(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            file = Uri.fromFile(createImageFile());
        } catch (Exception ex){
            //
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        iv = (ImageView) findViewById(R.id.addedImageView);
        if(data != null && resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_GALLERY){
                file = data.getData();
                iv.setImageURI(file);
            } else if(requestCode == REQUEST_CAMERA) {
                iv.setImageURI(file);
            }
        }
    }

    public File createImageFile() throws IOException{
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    public void addPerson(View view){
        EditText inputName = (EditText) findViewById(R.id.inputName);
        String name = inputName.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Name can't be empty!", Toast.LENGTH_SHORT).show();
        }else if(file == null){
            Toast.makeText(getApplicationContext(), "Please select an image.", Toast.LENGTH_SHORT).show();
        }else{
            Person p = new Person(file, name);
            db.addPerson(p);
            Toast.makeText(getApplicationContext(), "Person added.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}
