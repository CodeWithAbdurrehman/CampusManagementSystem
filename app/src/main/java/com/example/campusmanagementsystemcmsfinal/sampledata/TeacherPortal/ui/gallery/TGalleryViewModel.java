package com.example.campusmanagementsystemcmsfinal.sampledata.TeacherPortal.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TGalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TGalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}