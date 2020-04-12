package com.inkeast.wuxiaworld.ui.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inkeast.wuxiaworld.MainActivity;
import com.inkeast.wuxiaworld.MainApplication;
import com.inkeast.wuxiaworld.R;
import com.inkeast.wuxiaworld.database.Bookmark;

import java.util.List;

public class BookmarkFragment extends Fragment implements BookmarkAdapter.OnItemClickListener, BookmarkAdapter.OnItemLongClickListener {

    private RecyclerView mRecyclerView;
    private BookmarkAdapter mBookmarkAdapter;
    private List<Bookmark> mBookmarks;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_bookmark, container, false);
        mRecyclerView = root.findViewById(R.id.bookmark_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        mBookmarks = MainApplication.getDatabase().getBookmarkDao().loadAllBookmarks();

        mBookmarkAdapter = new BookmarkAdapter(BookmarkFragment.this.getContext(), mBookmarks);
        mBookmarkAdapter.setOnItemClickListener(this);
        mBookmarkAdapter.setOnItemLongClickListener(this);
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
        MainApplication.getDatabase().getBookmarkDao().deleteAll();
        mBookmarks.clear();
        mBookmarkAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Bookmark bookmark) {
        FragmentActivity activity = getActivity();
        if(activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            mainActivity.navigateToHome(bookmark.url);
        }
    }

    @Override
    public boolean onItemLongClick(Bookmark bookmark) {
        Toast.makeText(this.getContext(), bookmark + " onItemLongClick", Toast.LENGTH_SHORT).show();
        return true;
    }
}
