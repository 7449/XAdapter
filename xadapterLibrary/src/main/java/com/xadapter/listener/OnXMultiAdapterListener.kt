package com.xadapter.listener

import android.support.v7.widget.GridLayoutManager

import com.xadapter.holder.XViewHolder

/**
 * by y on 2017/4/24
 */

interface OnXMultiAdapterListener<T> {
    fun multiLayoutId(viewItemType: Int): Int

    fun getGridLayoutManagerSpanSize(itemViewType: Int, gridManager: GridLayoutManager, position: Int): Int

    fun getStaggeredGridLayoutManagerFullSpan(itemViewType: Int): Boolean

    fun onXMultiBind(holder: XViewHolder, entity: T, itemViewType: Int, position: Int)
}
