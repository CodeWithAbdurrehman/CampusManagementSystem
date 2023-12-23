package com.example.campusmanagementsystemcmsfinal.StudentPortal.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SHomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}