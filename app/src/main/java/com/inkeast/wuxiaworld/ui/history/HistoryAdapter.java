package com.inkeast.wuxiaworld.ui.history;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inkeast.wuxiaworld.R;
import com.inkeast.wuxiaworld.database.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {


    private Context mContext;
    private List<History> mData;

    public HistoryAdapter(Context context, List<History> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, final int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(final History history) {
            ((TextView) itemView.findViewById(android.R.id.title)).setText(history.url);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, history + " onClick", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(mContext, history + " onLongClick", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}
