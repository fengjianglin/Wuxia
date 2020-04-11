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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.inkeast.wuxiaworld.AppDatabase;
import com.inkeast.wuxiaworld.R;

import java.util.List;

public class BookmarkFragment extends Fragment {

    private BookmarkViewModel bookmarkViewModel;
    private RecyclerView mRecyclerView;

    private AppDatabase mAppDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bookmark, container, false);
        mRecyclerView = root.findViewById(R.id.bookmark_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        bookmarkViewModel.getListData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> list) {
//                mRecyclerView.setAdapter(new BookmarkAdapter(BookmarkFragment.this.getContext(), list));
            }
        });

        mAppDatabase = Room.databaseBuilder(this.getContext(), AppDatabase.class, "app_database")
                .allowMainThreadQueries() //数据库中的操作一般不在主线程 这里强行在主线层中进行操作
                .build();

        List<Bookmark> bookmarks = mAppDatabase.getBookmarkDao().loadAllBookmarks();
        mRecyclerView.setAdapter(new BookmarkAdapter(BookmarkFragment.this.getContext(), bookmarks));

        return root;
    }
}
