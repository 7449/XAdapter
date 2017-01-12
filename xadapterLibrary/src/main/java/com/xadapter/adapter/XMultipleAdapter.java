package com.xadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xadapter.holder.XViewHolder;

/**
 * by y on 2017/1/12.
 */

public abstract class XMultipleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new XViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemLayout(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    protected abstract int getItemLayout(int viewType);



}
