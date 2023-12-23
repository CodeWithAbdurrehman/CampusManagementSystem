package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.ui.gallery;

import static android.content.ContentValues.TAG;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.campusmanagementsystemcmsfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class TAttendance extends Fragment {


    public TAttendance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tattendance, container, false);

        Bundle bundle = getArguments();
        String course = bundle.getString("Course_name");
        ListView listview = root.findViewById(R.id.studentListView);
        Button btnsave = root.findViewById(R.id.btnsave);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Reference to the "user" collection
        CollectionReference userCollection = db.collection("user");

// Query to filter documents where the "role" field is equal to "student"
        Query studentQuery = userCollection.whereEqualTo("role", "student");
        List<String> rollno= new ArrayList<String>();
        List<String> name= new ArrayList<String>();

        // Execute the query
        studentQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Retrieve the document ID (title) for each matching document
                                String documentId = document.getId();
                                rollno.add(documentId);

                                // You can also access other fields using document.getData()
                                Map<String, Object> data = document.getData();
                                name.add(data.get("Name").toString());
                                // Access other fields as needed
                            }
//                            Toast.makeText(root.getContext(), rollno.get(0), Toast.LENGTH_SHORT).show();
                            String[] namelist = name.toArray(new String[0]);
                            String[] Rollno = rollno.toArray(new String[0]);
                            CustomAdapterforAttendance adapter = new CustomAdapterforAttendance(namelist, Rollno, root.getContext());
                            listview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();








                        } else {
                            Log.w(TAG, "Error getting documents", task.getException());
                        }
                    }
                });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iterate through the list view to get attendance status for each student
                for (int i = 0; i < listview.getCount(); i++) {
                    View listItem = listview.getChildAt(i);
                    CheckBox checkBox = listItem.findViewById(R.id.checkBoxAttendance);

                    // Get student details
                    String studentName = name.get(i).toString();;
                    String studentRollNo = rollno.get(i);;
                    boolean isPresent = checkBox.isChecked();

                    // Create attendance document and subdocument for each student
                    saveAttendance(studentName, studentRollNo, isPresent,course);
//                    Toast.makeText(root.getContext(), "name"+studentName, Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(root.getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return root;

    }

    private void saveAttendance(String studentName, String studentRollNo, boolean isPresent,String coursename) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Replace "your_course_name" with the actual course name
        String courseName = coursename;

        // Reference to the "user" collection
        CollectionReference userCollection = db.collection("user");

        // Reference to the specific student's attendance document
        DocumentReference attendanceDocRef = userCollection.document(studentRollNo)
                .collection("attendance")
                .document(courseName);

        // Create data to be saved in the subdocument
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        Map<String, Object> attendanceData = new HashMap<>();
        attendanceData.put("currentDate"+currentDate, currentDate);
        attendanceData.put("present"+currentDate, isPresent);

        // Set data in the subdocument
        attendanceDocRef.set(attendanceData, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Attendance saved for " + studentName);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error saving attendance", e);
                    }
                });
    }


}