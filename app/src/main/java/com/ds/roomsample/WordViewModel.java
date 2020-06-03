package com.ds.roomsample;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private LiveData<List<Name>> mAllNames;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllNames = mRepository.getNames();
    }

    LiveData<List<Name>> getAllWords() {
        return mAllNames;
    }

    public void insert(Name name) {
        mRepository.insert(name);
    }
}