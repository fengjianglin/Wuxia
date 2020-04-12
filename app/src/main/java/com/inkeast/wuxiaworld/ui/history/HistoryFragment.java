package com.inkeast.wuxiaworld.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.inkeast.wuxiaworld.AppDatabase;
import com.inkeast.wuxiaworld.R;

import java.util.List;

public class HistoryFragment extends Fragment {

    private AppDatabase mAppDatabase;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mHistoryAdapter;
    private List<History> mHistories;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = root.findViewById(R.id.history_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        mAppDatabase = Room.databaseBuilder(this.getContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries() //数据库中的操作一般不在主线程 这里允许在主线层中进行操作
                .build();
        mHistories = mAppDatabase.getHistoryDao().loadAllHistories();
        mHistoryAdapter = new HistoryAdapter(HistoryFragment.this.getContext(), mHistories);
        mHistoryAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                View emptyView = root.findViewById(android.R.id.empty);
                if (emptyView != null) {
                    if (mHistoryAdapter.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mHistoryAdapter);
        mHistoryAdapter.notifyDataSetChanged();

        return root;
    }

    public void removeAllHistories() {
        mAppDatabase.getHistoryDao().deleteAll();
        mHistories.clear();
        mHistoryAdapter.notifyDataSetChanged();
    }
}
