package com.xadapter.listener

import androidx.recyclerview.widget.GridLayoutManager
import com.xadapter.holder.XViewHolder

/**
 * @author y
 * @create 2019/3/12
 */
interface OnXMultiAdapterListener<T> {
    fun multiLayoutId(viewItemType: Int): Int

    fun getGridLayoutManagerSpanSize(itemViewType: Int, gridManager: GridLayoutManager, position: Int): Int

    fun getStaggeredGridLayoutManagerFullSpan(itemViewType: Int): Boolean

    fun onXMultiBind(holder: XViewHolder, entity: T, itemViewType: Int, position: Int)
}