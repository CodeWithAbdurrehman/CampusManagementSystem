package com.example.campusmanagementsystemcmsfinal.StudentPortal.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.campusmanagementsystemcmsfinal.R;
import com.example.campusmanagementsystemcmsfinal.StudentPortal.MyCustomAdapter;
import com.example.campusmanagementsystemcmsfinal.StudentPortal.Student;
import com.example.campusmanagementsystemcmsfinal.databinding.FragmentGalleryBinding;

public class SGalleryFragment extends Fragment {
    String[] Cname = {"Computer Network","Computer Network","Computer Network","Computer Network"};

    String[] Cdesc = {"CNC601240 Credits:3.0 Active Class","CNC601240 Credits:3.0 Active Class","CNC601240 Credits:3.0 Active Class","'CNC601240 Credits:3.0 Active Class"};
    int[] image = {R.drawable.splogo,R.drawable.splogo,R.drawable.splogo,R.drawable.splogo};

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SGalleryViewModel SGalleryViewModel =
                new ViewModelProvider(this).get(SGalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView listView = binding.courselist;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        MyCustomAdapter adapter = new MyCustomAdapter(Cname,Cdesc,image,root.getContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                String selectedItem = (String) adapter.getName(position);
//                Toast.makeText(requireContext(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                navigateToAttendanceFragment(selectedItem);

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void navigateToAttendanceFragment(String Name) {
        if (getActivity() instanceof Student) {
            ((Student) getActivity()).navigateToAttendanceFragment(Name);
        }
    }
}
