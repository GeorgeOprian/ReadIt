package com.example.readitapp.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public final class FirebaseConstants {

    public static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseUser user = mAuth.getCurrentUser();
    public static final FirebaseFirestore db = FirebaseFirestore.getInstance();
}
