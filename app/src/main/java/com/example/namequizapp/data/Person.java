package com.example.namequizapp.data;

import android.net.Uri;

public class Person {
    private static long IDGEN = 0;
    private Uri imageUri;
    private String name;
    private long id;

    public Person(Uri imageUri, String name){
        this.imageUri = imageUri;
        this.name = name;
        this.id = IDGEN++;
    }

    public Uri getImageUri(){
        return imageUri;
    }

    public String getName(){
        return name;
    }

    public long getId() { return id; }

    public String toString() { return name; }
}
