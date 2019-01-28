package com.example.namequizapp.data;

import android.net.Uri;

import java.util.ArrayList;

import com.example.namequizapp.R;

public class PersonDB {

    private static PersonDB instance = null;
    private ArrayList<Person> list = new ArrayList<>();

    public PersonDB(){
        list.add(new Person(Uri.parse("android.resource://com.example.namequizapp/" + R.drawable.img1), "Per"));
        list.add(new Person(Uri.parse("android.resource://com.example.namequizapp/" + R.drawable.img2), "Bob"));
        list.add(new Person(Uri.parse("android.resource://com.example.namequizapp/" + R.drawable.img3), "Jens"));
        list.add(new Person(Uri.parse("android.resource://com.example.namequizapp/" + R.drawable.img3), "Snoppi"));
    }


    public static PersonDB getInstance(){
        if(instance == null) {
            instance = new PersonDB();
        }
        return instance;
    }

    public int count(){
        return list.size();
    }

    public Person getPersonByIndex(int i){
        return list.get(i);
    }

    public long getIdByIndex(int i) { return list.get(i).getId(); }

    public ArrayList<Person> getAll(){
        return list;
    }

    public void addPerson(Person p){
        list.add(p);
    }

    public Person getPersonById(long id) {
        for (Person p : list) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    public Person getRandom(){
        return list.get((int)(Math.random() * list.size()));
    }

}
