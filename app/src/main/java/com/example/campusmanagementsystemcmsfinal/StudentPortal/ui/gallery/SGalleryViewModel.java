package com.example.campusmanagementsystemcmsfinal.StudentPortal.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SGalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SGalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}