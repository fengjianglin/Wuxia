package com.inkeast.wuxiaworld.ui.bookmark;

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

import java.util.List;

public class BookmarkFragment extends Fragment {

    private AppDatabase mAppDatabase;
    private RecyclerView mRecyclerView;
    private BookmarkAdapter mBookmarkAdapter;
    private List<Bookmark> mBookmarks;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_bookmark, container, false);
        mRecyclerView = root.findViewById(R.id.bookmark_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        mAppDatabase = Room.databaseBuilder(this.getContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries() // 数据库中的操作一般不在主线程,这里允许在主线层中进行操作
                .build();
        mBookmarks = mAppDatabase.getBookmarkDao().loadAllBookmarks();

        mBookmarkAdapter = new BookmarkAdapter(BookmarkFragment.this.getContext(), mBookmarks);
        mBookmarkAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                View emptyView = root.findViewById(android.R.id.empty);
                if (emptyView != null) {
                    if (mBookmarkAdapter.getItemCount() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mBookmarkAdapter);
        mBookmarkAdapter.notifyDataSetChanged();

        return root;
    }

    public void removeAllBookmarks() {
        mAppDatabase.getBookmarkDao().deleteAll();
        mBookmarks.clear();
        mBookmarkAdapter.notifyDataSetChanged();
    }
}
