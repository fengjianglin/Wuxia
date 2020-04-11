package com.inkeast.wuxiaworld.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarkViewModel extends ViewModel {

    private MutableLiveData<List<String>> mListData;

    public BookmarkViewModel() {
        List<String> list = new ArrayList<>();
        for (int i = 50; i < 100; i++) {
            list.add(i + "........." + i);
        }
        mListData = new MutableLiveData<>();
        mListData.setValue(list);
    }

    public LiveData<List<String>> getListData() {
        return mListData;
    }
}