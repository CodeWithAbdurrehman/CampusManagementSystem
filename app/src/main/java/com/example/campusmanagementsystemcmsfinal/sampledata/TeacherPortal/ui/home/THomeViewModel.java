package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class THomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public THomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}