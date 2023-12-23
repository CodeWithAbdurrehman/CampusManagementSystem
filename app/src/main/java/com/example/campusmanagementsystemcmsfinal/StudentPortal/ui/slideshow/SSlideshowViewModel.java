package com.example.campusmanagementsystemcmsfinal.StudentPortal.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SSlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SSlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}