package com.example.campusmanagementsystemcmsfinal.StudentPortal.ui.gallery;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.campusmanagementsystemcmsfinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Attendance extends Fragment {

    TableLayout tbl;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public Attendance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String courseName = bundle.getString("Course_name", "failed");
//            Toast.makeText(getContext(), "Course Name: " + courseName, Toast.LENGTH_SHORT).show();
//            tbl = root.findViewById(R.id.AtableLayout);

            // Fetch attendance data based on user's UID and courseName
            fetchAttendanceData(courseName,root);
        } else {
            Toast.makeText(getContext(), "Failed to get course name", Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void fetchAttendanceData(String courseName,View root) {
        // Assuming the authenticated user's UID is available through mAuth
        String authenticatedUid = mAuth.getUid();

        // Reference to the "user" collection
        db.collection("user")
                .whereEqualTo("uid", authenticatedUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Document with the specified UID found
                            String uid = document.getString("uid");

                            if (uid != null && uid.equals(mAuth.getUid())) {
                                // Authenticated user's UID matches the UID in the document

                                // Now proceed to fetch attendance data for the specific user
                                String title = document.getId();
                                fetchAttendanceDataForUser(title,courseName,root);
                            } else {
                                Toast.makeText(getContext(), "User authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchAttendanceDataForUser(String title,String name,View root) {
        // Assuming you have the course name available
        String courseName = name;

        // Fetch attendance data for the authenticated user and the specified course
        db.collection("user")
                .document(title)
                .collection("attendance")  // Assuming attendance is a subcollection
                .document(courseName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        if (document.exists()) {
                            // Document exists, retrieve attendance data
                            Map<String, Object> attendanceData = document.getData();
                            Toast.makeText(root.getContext(), "hello", Toast.LENGTH_SHORT).show();

                            if (attendanceData != null) {
                                // Populate the table with attendance data
                                populateTable(attendanceData, root);
                            } else {
                                Toast.makeText(getContext(), "Attendance data is null", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch attendance data", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void populateTable(Map<String, Object> attendanceData,View root) {
        tbl = root.findViewById(R.id.AtableLayout);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        TableRow tblrow = new TableRow(getContext());
        TextView column1 = new TextView(getContext());
        column1.setText("Date");
        column1.setLayoutParams(params);
        tblrow.addView(column1);

        TextView column2 = new TextView(getContext());
        column2.setText("Status");
        column2.setLayoutParams(params);
        tblrow.addView(column2);
        tbl.addView(tblrow);
        for (Map.Entry<String, Object> entry : attendanceData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.startsWith("currentDate")) {
                String date = key.substring("currentDate".length());
                String statusKey = "present" + date;
                Object statusValue = attendanceData.get(statusKey);

                addRowToTable(date, statusValue != null && (boolean) statusValue, root);
            }
        }




        // Example: Print attendance data to Log
        for (Map.Entry<String, Object> entry : attendanceData.entrySet()) {
            Log.d(TAG, "Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
    private void addRowToTable(String date, boolean isPresent, View root) {
        TableRow tblrow = new TableRow(getContext());

        TextView column1 = new TextView(getContext());
        column1.setText(date);
        column1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        tblrow.addView(column1);

        TextView column2 = new TextView(getContext());
        column2.setText(isPresent ? "Present" : "Absent");
        column2.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        tblrow.addView(column2);

        tbl.addView(tblrow);
    }

}
