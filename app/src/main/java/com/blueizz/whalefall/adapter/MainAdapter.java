package com.blueizz.whalefall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blueizz.whalefall.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<String> mData;
    private OnItemClickListener mOnItemClickListener;

    public MainAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> list) {
        mData = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.activityName.setText(mData.get(position));
        //将数据保存在itemView的Tag中，以便点击时获取
        holder.itemView.setTag(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick((String) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(String activityName);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityName;

        public ViewHolder(View itemView) {
            super(itemView);

            activityName = itemView.findViewById(R.id.tv_activity_name);
        }
    }
}
