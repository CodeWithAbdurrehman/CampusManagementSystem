package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.ui.slideshow;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.campusmanagementsystemcmsfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TResult extends Fragment {

    private FirebaseAuth mAuth;
    TableLayout tbl;
    EditText rollno,name,tmarks,omarks;
    FirebaseFirestore db;
    public TResult() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tresult, container, false);
        Bundle bundle = getArguments();
        String data = "";
        if (bundle != null) {
            if (bundle.containsKey("Course_name")) {
                 data = bundle.getString("Course_name");
                Toast.makeText(root.getContext(), data, Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(root.getContext(), "There was an error getting course name", Toast.LENGTH_SHORT).show();
            }
        }

        Spinner resultTypeSpinner = root.findViewById(R.id.spinnerResultType);
        List<String> resultTypes = new ArrayList<>();
        resultTypes.add("Quiz");
        resultTypes.add("Mid");
        resultTypes.add("Final");
        resultTypes.add("Assignment");
        resultTypes.add("Project");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, resultTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultTypeSpinner.setAdapter(adapter);
        Button btnsave = root.findViewById(R.id.btnsave);
        rollno = root.findViewById(R.id.editTextRollNo);
        name = root.findViewById(R.id.editTextName);
        tmarks = root.findViewById(R.id.editTextTotalMarks);
        omarks = root.findViewById(R.id.editTextObtainedMarks);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String finalData = data;
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String Name,Rollno,Tmarks,Omarks;
                Name = name.getText().toString();
                Rollno = rollno.getText().toString();
                Tmarks = tmarks.getText().toString();
                Omarks = omarks.getText().toString();
                Map<String, Object> user = new HashMap<>();
                user.put("Name"+Name, Name);
                user.put("Totalmarksin"+Name, Tmarks);
                user.put("Obtainedmarksin"+Name, Omarks);

                        db.collection("user")  // Correct collection name
                                .document(Rollno)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                // Document with entered roll number exists
                                                String teacherId = document.getString("teacher");

                                                // Check if the teacherId matches the expected teacher
                                                if (teacherId != null && teacherId.equals(mAuth.getUid().toString())) {
                                                    // Student's teacher matched, do something
                                                    Log.d(TAG, "Student's teacher matched: " + document.getId());
                                                    Toast.makeText(root.getContext(), "Yes", Toast.LENGTH_SHORT).show();

                                                    // Add a new subdocument to the specified subcollection
                                                    db.collection("user")
                                                            .document(Rollno)
                                                            .collection("results")
                                                            .document(finalData)
                                                            .set(user, SetOptions.merge())
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Log.d(TAG, "Custom document added with ID: " + "customDocumentId");
                                                                    Toast.makeText(root.getContext(), "dal giya", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.w(TAG, "Error adding custom document", e);
                                                                }
                                                            });

                                                } else {
                                                    // Student's teacher doesn't match
                                                    Log.d(TAG, "Student's teacher does not match: " + document.getId());
                                                    Toast.makeText(root.getContext(), "Teacher does not math", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                // Document with entered roll number does not exist
                                                Toast.makeText(root.getContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "Document does not exist for roll number: BCSM-F21-190");
                                            }
                                        } else {
                                            Toast.makeText(root.getContext(), "No", Toast.LENGTH_SHORT).show();
                                            Log.w(TAG, "Error getting document.", task.getException());
                                        }
                                    }
                                });
                    }
                });


        return root;
    }

    }
