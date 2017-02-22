package com.xadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xadapter.holder.XViewHolder;

import java.util.List;

/**
 * by y on 2017/1/12.
 */

public abstract class XMultipleAdapter<T> extends RecyclerView.Adapter<XViewHolder> {
    protected List<T> mDatas = null;
    protected OnItemClickListener<T> mOnItemClickListener;
    protected OnItemLongClickListener<T> mOnLongClickListener;

    public XMultipleAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new XViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemLayout(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(XViewHolder holder, int position) {
        onBindHolder(holder, position, getItemViewType(position));
    }

    protected abstract void onBindHolder(XViewHolder holder, int position, int itemViewType);

    protected abstract int getItemLayout(int viewType);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface OnItemClickListener<T> {

        void onItemClick(View view, int position, T info);

    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T info);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
    }
}
