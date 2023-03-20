package com.example.readitapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.readitapp.R;
import com.example.readitapp.utils.FirebaseConstants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        configureGoogleSignIn();
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseConstants.mAuth.getCurrentUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception exception = task.getException();
            if (task.isSuccessful()) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.getId());
                    // TODO: 19.03.2023 toast-uri in loc de log
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    // TODO: 19.03.2023 toast-uri in loc de log
                    Log.w("SignInActivity", "Google sign in failed", e);
                }
            } else {
                Log.w("SignInActivity", String.valueOf(exception));
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseConstants.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SignInActivity", "signInWithCredential:success");
//                        FirebaseUser user = FirebaseConstants.mAuth.getCurrentUser();
//                        FirebaseConstants.user = user;
//                        FirebaseConstants.db.collection(getString(R.string.persoana)).whereEqualTo(getString(R.string.email), user.getEmail())
//                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful() && !task.getResult().isEmpty()) {

                        // TODO: 19.03.2023 facem un call la web server ca sa verificam daca userul e admin sau nu
                        // TODO: 19.03.2023 pornesc trimit la main activity informatia de admin prin extra



                                    Intent intentMain = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intentMain);
                                    finish();
//                                } else {
////                                    addNewUser(user);
//                                    Intent intentMain = new Intent(SignInActivity.this, MainActivity.class);
//                                    SignInActivity.this.startActivity(intentMain);
//                                    SignInActivity.this.finish();
//                                }
//                            }
//                        });
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignInActivity", "signInWithCredential:failure", task.getException());
                    }
                });
    }

//    public void addNewUser(FirebaseUser user) {
//        Map<String, Object> persoana = new HashMap<>();
//        persoana.put(getString(R.string.nume), user.getDisplayName());
//        persoana.put(getString(R.string.email), user.getEmail());
//        persoana.put(getString(R.string.nickname), "");
//        persoana.put(getString(R.string.data_nasterii), new Date(System.currentTimeMillis()));
//        persoana.put(getString(R.string.greutate), 0);
//        persoana.put(getString(R.string.inaltime), 0);
//        FirebaseConstants.db.collection(getString(R.string.persoana)).add(persoana)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(SignInActivity.this, "Successful", Toast.LENGTH_LONG).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(SignInActivity.this, "Failed", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}