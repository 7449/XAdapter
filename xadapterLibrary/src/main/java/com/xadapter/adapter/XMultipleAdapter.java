package com.xadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xadapter.holder.XViewHolder;

/**
 * by y on 2017/1/12.
 */

public abstract class XMultipleAdapter extends RecyclerView.Adapter<XViewHolder> {


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


}
