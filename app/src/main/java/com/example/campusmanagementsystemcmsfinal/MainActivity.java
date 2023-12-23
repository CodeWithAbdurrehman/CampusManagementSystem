package com.example.campusmanagementsystemcmsfinal;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campusmanagementsystemcmsfinal.StudentPortal.Student;
import com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.TeacherPortal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    private FirebaseAuth mAuth;
    Button login;
    FirebaseFirestore fsstore;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
                mAuth.signOut();
//            Intent i = new Intent(MainActivity.this,MainActivity.class);
//            startActivity(i);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.btnlogin);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        mAuth = FirebaseAuth.getInstance();
        fsstore = FirebaseFirestore.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, Password;
                email = String.valueOf(username.getText());
                Password = String.valueOf(password.getText());
                if (email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an Email", Toast.LENGTH_SHORT).show();
                }
                if (Password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Password", Toast.LENGTH_SHORT).show();
                }

                mAuth.signInWithEmailAndPassword(email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Search for the document containing the specified UID
                                    fsstore.collection("user")
                                            .whereEqualTo("uid", mAuth.getUid())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            // Document with the specified UID found
                                                            String role = document.getString("role");

                                                            if (role != null) {
                                                                if (role.equals("teacher")) {
                                                                    // Navigate to TeacherPortal
                                                                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(MainActivity.this, TeacherPortal.class);
                                                                    startActivity(i);
                                                                } else if (role.equals("student")) {
                                                                    // Navigate to Student
                                                                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                                                    Intent i = new Intent(MainActivity.this, Student.class);
                                                                    startActivity(i);
                                                                } else {
                                                                    Toast.makeText(MainActivity.this, "Invalid role", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(MainActivity.this, "Role not found", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } else {
                                                        Log.w(TAG, "Error getting documents.", task.getException());
                                                        Toast.makeText(MainActivity.this, "Error getting documents", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}