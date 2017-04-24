package com.xadapter.adapter.multi;

import android.support.v7.widget.GridLayoutManager;

import com.xadapter.holder.XViewHolder;

/**
 * by y on 2017/4/24
 */

public interface XMultiAdapterListener<T> {
    int multiLayoutId(int viewItemType);

    int getGridLayoutManagerSpanSize(int itemViewType, GridLayoutManager gridManager, int position);

    boolean getStaggeredGridLayoutManagerFullSpan(int itemViewType);

    void onXMultiBind(XViewHolder holder, T t, int itemViewType, int position);
}
