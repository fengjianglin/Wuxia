package com.inkeast.wuxiaworld.ui.history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {

    private MutableLiveData<List<String>> mListData;

    public HistoryViewModel() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i + "........." + i);
        }
        mListData = new MutableLiveData<>();
        mListData.setValue(list);
    }

    public LiveData<List<String>> getListData() {
        return mListData;
    }
}