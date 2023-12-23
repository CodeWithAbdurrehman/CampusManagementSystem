package com.example.campusmanagementsystemcmsfinal.StudentPortal.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.campusmanagementsystemcmsfinal.R;
import com.example.campusmanagementsystemcmsfinal.StudentPortal.MyCustomAdapter;
import com.example.campusmanagementsystemcmsfinal.databinding.FragmentHomeBinding;

public class SHomeFragment extends Fragment {

    String[] Cname = {"Computer Network","Computer Network","Computer Network","Computer Network"};

    String[] Cdesc = {"CNC601240 Credits:3.0 Active Class","CNC601240 Credits:3.0 Active Class","CNC601240 Credits:3.0 Active Class","'CNC601240 Credits:3.0 Active Class"};
    int[] image = {R.drawable.splogo,R.drawable.splogo,R.drawable.splogo,R.drawable.splogo};

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SHomeViewModel SHomeViewModel =
                new ViewModelProvider(this).get(SHomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView listView = binding.homeList;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        MyCustomAdapter adpater = new MyCustomAdapter(Cname,Cdesc,image,root.getContext());
        listView.setAdapter(adpater);
        adpater.notifyDataSetChanged();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}