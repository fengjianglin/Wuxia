package com.inkeast.wuxiaworld.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inkeast.wuxiaworld.R;

import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = root.findViewById(R.id.history_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        historyViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> list) {
                mRecyclerView.setAdapter(new HistoryAdapter(HistoryFragment.this.getContext(), list));
            }
        });
        return root;
    }
}
