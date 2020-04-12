package com.inkeast.wuxiaworld.ui.bookmark;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inkeast.wuxiaworld.R;
import com.inkeast.wuxiaworld.database.Bookmark;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {


    private Context mContext;
    private List<Bookmark> mData;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public BookmarkAdapter(Context context, List<Bookmark> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.bookmark_item, parent, false);
        return new BookmarkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, final int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder {

        public BookmarkViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(final Bookmark bookmark) {
            ((TextView) itemView.findViewById(android.R.id.title)).setText(bookmark.title);
            ((TextView) itemView.findViewById(android.R.id.content)).setText(bookmark.url);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(bookmark);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(mOnItemLongClickListener != null) {
                        return mOnItemLongClickListener.onItemLongClick(bookmark);
                    }
                    return false;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Bookmark bookmark);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(Bookmark bookmark);
    }
}
