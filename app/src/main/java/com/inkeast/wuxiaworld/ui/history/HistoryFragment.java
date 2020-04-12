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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.inkeast.wuxiaworld.AppDatabase;
import com.inkeast.wuxiaworld.R;
import com.inkeast.wuxiaworld.ui.bookmark.Bookmark;
import com.inkeast.wuxiaworld.ui.bookmark.BookmarkAdapter;
import com.inkeast.wuxiaworld.ui.bookmark.BookmarkFragment;

import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private RecyclerView mRecyclerView;

    private AppDatabase mAppDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = root.findViewById(R.id.history_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        historyViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> list) {
//                mRecyclerView.setAdapter(new HistoryAdapter(HistoryFragment.this.getContext(), list));
            }
        });

        mAppDatabase = Room.databaseBuilder(this.getContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries() //数据库中的操作一般不在主线程 这里强行在主线层中进行操作
                .build();
        List<History> histories = mAppDatabase.getHistoryDao().loadAllHistories();

        mRecyclerView.setAdapter(new HistoryAdapter(HistoryFragment.this.getContext(), histories));

        return root;
    }
}
