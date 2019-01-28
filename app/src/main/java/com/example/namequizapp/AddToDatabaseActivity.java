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
    private String imagePath;
    private Uri uri;

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
        if(intent.resolveActivity(getPackageManager()) != null){
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException ex){
                ex.printStackTrace();
            }

            if(imageFile != null){
                uri = FileProvider.getUriForFile(this, "com.example.namequizapp.fileprovider", imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data != null && resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_GALLERY){
                uri = data.getData();
            }
            ImageView iv = (ImageView) findViewById(R.id.addedImageView);
            iv.setImageURI(uri);
        }
    }

    public File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);

        imagePath = image.getAbsolutePath();
        return image;
    }

    public void addPerson(View view){
        EditText inputName = (EditText) findViewById(R.id.inputName);
        String name = inputName.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(getApplicationContext(), "Name can't be empty!", Toast.LENGTH_SHORT).show();
        }else if(uri == null){
            Toast.makeText(getApplicationContext(), "Please select an image.", Toast.LENGTH_SHORT).show();
        }else{
            Person p = new Person(uri, name);
            db.addPerson(p);
            Toast.makeText(getApplicationContext(), "Person added.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}
