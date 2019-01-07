package com.xadapter.simple

import androidx.recyclerview.widget.GridLayoutManager
import com.xadapter.OnXMultiAdapterListener

/**
 * @author y
 * @create 2019/1/7
 */
abstract class SimpleOnXMultiAdapterListener<T> : OnXMultiAdapterListener<T> {
    override fun getGridLayoutManagerSpanSize(itemViewType: Int, gridManager: GridLayoutManager, position: Int): Int {
        return 0
    }

    override fun getStaggeredGridLayoutManagerFullSpan(itemViewType: Int): Boolean {
        return false
    }
}
