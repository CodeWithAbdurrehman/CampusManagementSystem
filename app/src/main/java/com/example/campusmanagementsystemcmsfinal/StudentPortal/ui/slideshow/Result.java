package com.example.campusmanagementsystemcmsfinal.StudentPortal.ui.slideshow;



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

import java.util.Map;


public class Result extends Fragment {

    TableLayout tbl;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public Result() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_result, container, false);
        Bundle bundle = getArguments();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if(bundle!=null) {
//            textview = root.findViewById(R.id.textview);
            String data = bundle.getString("Course_name", "failed");
            fetchresultDataData(data,root);



        }else{
            Toast.makeText(getContext(),"failed",Toast.LENGTH_SHORT).show();
//
        }
        return root;
    }

    private void fetchresultDataData(String courseName,View root) {
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
                                fetchresultDataForUser(title,courseName,root);
//                                Toast.makeText(root.getContext(), "hrllo", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "User authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchresultDataForUser(String title,String name,View root) {
        // Assuming you have the course name available
        String courseName = name;

        // Fetch attendance data for the authenticated user and the specified course
        db.collection("user")
                .document(title)
                .collection("results")  // Assuming attendance is a subcollection
                .document(courseName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        if (document.exists()) {
                            // Document exists, retrieve attendance data
                            Map<String, Object> attendanceData = document.getData();

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

    private void populateTable(Map<String, Object> resultData, View root) {
        tbl = root.findViewById(R.id.tableLayout);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );

        TableRow tblrow = new TableRow(getContext());
        TextView column1 = new TextView(getContext());
        column1.setText("Name");
        column1.setLayoutParams(params);
        tblrow.addView(column1);

        TextView column2 = new TextView(getContext());
        column2.setText("Total Marks");
        column2.setLayoutParams(params);
        tblrow.addView(column2);

        TextView column3 = new TextView(getContext());
        column3.setText("Obtained Marks");
        column3.setLayoutParams(params);
        tblrow.addView(column3);
        tbl.addView(tblrow);
        Toast.makeText(root.getContext(), "help", Toast.LENGTH_SHORT).show();
        // Iterate through the resultData entries and populate the table
        for (Map.Entry<String, Object> entry : resultData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.startsWith("Name")) {
                String name = value != null ? value.toString() : "";
                String totalMarks = getStringValue("Totalmarks", resultData);
                String obtainedMarks = getStringValue("Obtainedmarks", resultData);

                addRowToTable(name, totalMarks, obtainedMarks, root);
            }

    }}
    private String getStringValue(String prefix, Map<String, Object> resultData) {
        for (Map.Entry<String, Object> entry : resultData.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.startsWith(prefix)) {
                return value != null ? value.toString() : "";
            }
        }
        return "";
    }

    private void addRowToTable(String name, String totalMarks, String obtainedMarks, View root) {
        TableRow tblrow = new TableRow(getContext());

        TextView column1 = new TextView(getContext());
        column1.setText(name);
        column1.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        tblrow.addView(column1);

        TextView column2 = new TextView(getContext());
        column2.setText(totalMarks);
        column2.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        tblrow.addView(column2);

        TextView column3 = new TextView(getContext());
        column3.setText(obtainedMarks);
        column3.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));
        tblrow.addView(column3);

        tbl.addView(tblrow);
    }

}
