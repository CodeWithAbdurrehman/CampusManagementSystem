package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TSlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TSlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}