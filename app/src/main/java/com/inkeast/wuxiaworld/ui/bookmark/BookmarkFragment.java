package com.inkeast.wuxiaworld.ui.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inkeast.wuxiaworld.R;
import com.inkeast.wuxiaworld.ui.history.HistoryAdapter;
import com.inkeast.wuxiaworld.ui.history.HistoryFragment;
import com.inkeast.wuxiaworld.ui.history.HistoryViewModel;

import java.util.List;

public class BookmarkFragment extends Fragment {

    private BookmarkViewModel bookmarkViewModel;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bookmark, container, false);
        mRecyclerView = root.findViewById(R.id.bookmark_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        bookmarkViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> list) {
                mRecyclerView.setAdapter(new HistoryAdapter(BookmarkFragment.this.getContext(), list));
            }
        });
        return root;
    }
}
