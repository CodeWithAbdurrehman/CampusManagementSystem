package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.ui.slideshow;

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
import com.example.campusmanagementsystemcmsfinal.databinding.FragmentSlideshowBinding;
import com.example.campusmanagementsystemcmsfinal.databinding.FragmentTslideshowBinding;
import com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.CustomAdapter;
import com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.TeacherPortal;

public class TSlideshowFragment extends Fragment {

    private FragmentTslideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TSlideshowViewModel TSlideshowViewModel =
                new ViewModelProvider(this).get(TSlideshowViewModel.class);

        binding = FragmentTslideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        String[] Cname = {"Computer Network","Mobile Application","Web Application","Technical Buisness"};

        String[] Cdesc = {"CNC601240 Credits:3.0 Active Class","CNC601240 Credits:3.0 Active Class","CNC601240 Credits:3.0 Active Class","'CNC601240 Credits:3.0 Active Class"};
        int[] image = {R.drawable.splogo,R.drawable.splogo,R.drawable.splogo,R.drawable.splogo};
        final ListView listView = binding.resultlist;
        CustomAdapter adapter = new CustomAdapter(Cname,Cdesc,image,root.getContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                String selectedItem = (String) adapter.getName(position);
//                Toast.makeText(requireContext(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

                navigateToResultFragment(selectedItem);

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void navigateToResultFragment(String Name) {
        if (getActivity() instanceof TeacherPortal) {
            ((TeacherPortal) getActivity()).navigateToResultFragment(Name);
        }
    }
}