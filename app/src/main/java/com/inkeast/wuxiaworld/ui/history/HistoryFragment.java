package com.inkeast.wuxiaworld.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inkeast.wuxiaworld.MainActivity;
import com.inkeast.wuxiaworld.MainApplication;
import com.inkeast.wuxiaworld.R;
import com.inkeast.wuxiaworld.database.History;

import java.util.List;

public class HistoryFragment extends Fragment implements HistoryAdapter.OnItemClickListener, HistoryAdapter.OnItemLongClickListener {

    private RecyclerView mRecyclerView;
    private HistoryAdapter mHistoryAdapter;
    private List<History> mHistories;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = root.findViewById(R.id.history_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        mHistories = MainApplication.getDatabase().getHistoryDao().loadAllHistories();
        mHistoryAdapter = new HistoryAdapter(HistoryFragment.this.getContext(), mHistories);
        mHistoryAdapter.setOnItemClickListener(this);
        mHistoryAdapter.setOnItemLongClickListener(this);
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
        MainApplication.getDatabase().getHistoryDao().deleteAll();
        mHistories.clear();
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(History history) {
        FragmentActivity activity = getActivity();
        if(activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.navigateToHome(history.url);
        }
    }

    @Override
    public boolean onItemLongClick(History history) {
        return true;
    }
}
