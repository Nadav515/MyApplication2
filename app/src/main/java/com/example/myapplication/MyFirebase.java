package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyFirebase {
    private static FirebaseUser currentuser;
    private static FirebaseAuth fAuth;
    private static FirebaseDatabase db;

    private static DatabaseReference ref;


public MyFirebase()
{
    fAuth = FirebaseAuth.getInstance();
    currentuser= fAuth.getCurrentUser();
    db= FirebaseDatabase.getInstance("https://water-app-62925-default-rtdb.europe-west1.firebasedatabase.app/");
}
public DatabaseReference getUserRef()
{
    currentuser=fAuth.getCurrentUser();
    ref = db.getReference("user/"+currentuser.getUid());
    return ref;
}
public void addUser(User u)
{
    currentuser = fAuth.getCurrentUser();
    ref = db.getReference("user/"+currentuser.getUid()); ;
    ref.setValue(u);
}
}

