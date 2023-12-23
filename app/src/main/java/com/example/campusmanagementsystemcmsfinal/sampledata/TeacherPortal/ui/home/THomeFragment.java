package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.ui.home;

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
import com.example.campusmanagementsystemcmsfinal.databinding.FragmentHomeBinding;
import com.example.campusmanagementsystemcmsfinal.databinding.FragmentThomeBinding;
import com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.CustomAdapter;

public class THomeFragment extends Fragment {
    String[] Cname = {"Computer Network","Computer Network","Computer Network","Computer Network"};

    String[] Cdesc = {"CNC601240 Credits:3.0 Active Class","CNC601240 Credits:3.0 Active Class","CNC601240 Credits:3.0 Active Class","'CNC601240 Credits:3.0 Active Class"};
    int[] image = {R.drawable.splogo,R.drawable.splogo,R.drawable.splogo,R.drawable.splogo};

    private FragmentThomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        THomeViewModel THomeViewModel =
                new ViewModelProvider(this).get(THomeViewModel.class);

        binding = FragmentThomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView listView = binding.homeList;
        CustomAdapter adapter = new CustomAdapter(Cname,Cdesc,image,root.getContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}