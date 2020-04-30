package com.abhishek.germanPocketDictionary.firebase;

import com.abhishek.germanPocketDictionary.utilities.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Abhishek Saxena
 * @since 18/4/20 3:43 PM
 */

public class FirebaseHandler {

    private DatabaseReference rootDatabaseReference;
    private static FirebaseHandler instance;

    public static FirebaseHandler getInstance(){
        if (instance == null)
            instance = new FirebaseHandler();
        return instance;
    }


    private FirebaseHandler() {
        rootDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private DatabaseReference getDatabaseRootReference() {
        if (rootDatabaseReference == null)
            rootDatabaseReference = FirebaseDatabase.getInstance().getReference();
        return rootDatabaseReference;
    }

    public DatabaseReference getWordsReference(){
        return getDatabaseRootReference().child(Constants.TABLES.WORDS);
    }

}
