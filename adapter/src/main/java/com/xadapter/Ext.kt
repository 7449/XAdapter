package com.xadapter

import android.view.ViewParent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.xadapter.adapter.XAdapter
import com.xadapter.manager.AppBarStateChangeListener
import com.xadapter.manager.XTouchListener
import com.xadapter.vh.XViewHolder

fun <T> XViewHolder.viewHolderClick(adapter: XAdapter<T>): XViewHolder {
    itemView.setOnClickListener { view ->
        adapter.onXItemClickListener?.invoke(view,
                adapter.currentItemPosition(layoutPosition),
                adapter.dataContainer[adapter.currentItemPosition(layoutPosition)])
    }
    return this
}

fun <T> XViewHolder.viewHolderLongClick(adapter: XAdapter<T>) {
    itemView.setOnLongClickListener { view ->
        val invoke = adapter.onXItemLongClickListener?.invoke(view,
                adapter.currentItemPosition(layoutPosition),
                adapter.dataContainer[adapter.currentItemPosition(layoutPosition)]) ?: false
        invoke
    }
}

fun <T> XAdapter<T>.currentItemPosition(position: Int): Int {
    var mPos = position
    if (pullRefreshEnabled) {
        mPos -= 1
    }
    return mPos - headerViewContainer.size
}

/**
 * 接管[XAdapter]在[GridLayoutManager]下的显示效果
 */
internal fun <T> XAdapter<T>.internalOnAttachedToRecyclerView(recyclerView: RecyclerView) {
    val manager = recyclerView.layoutManager
    if (manager is GridLayoutManager) {
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = if (getItemViewType(position) != XAdapter.TYPE_ITEM) manager.spanCount else 1
        }
    }
}

/**
 * 处理[XAdapter]在[AppBarLayout]下的滑动冲突
 */
internal fun <T> XAdapter<T>.internalOnViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
    val layoutParams = viewHolder.itemView.layoutParams
    if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
        layoutParams.isFullSpan = getItemViewType(viewHolder.layoutPosition) != XAdapter.TYPE_ITEM
    }
    var appBarLayout: AppBarLayout? = null
    var p: ViewParent? = recyclerView?.parent
    while (p != null) {
        if (p is CoordinatorLayout) {
            break
        }
        p = p.parent
    }
    if (p != null) {
        val coordinatorLayout = p as CoordinatorLayout?
        val childCount = coordinatorLayout?.childCount ?: 0
        for (i in childCount - 1 downTo 0) {
            val child = coordinatorLayout?.getChildAt(i)
            if (child is AppBarLayout) {
                appBarLayout = child
                break
            }
        }
        if (touchListener is XTouchListener) {
            appBarLayout?.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                public override fun onStateChanged(appBarLayout: AppBarLayout, state: Int) {
                    (touchListener as XTouchListener).state = state
                }
            })
        }
    }
}

/**
 * [XAdapter]的 viewType
 */
internal fun <T> XAdapter<T>.internalGetItemViewType(position: Int): Int {
    var mPos = position
    if (isRefreshHeaderType(mPos)) {
        return XAdapter.TYPE_REFRESH_HEADER
    }
    if (isLoadMoreType(mPos)) {
        return XAdapter.TYPE_LOAD_MORE_FOOTER
    }
    if (pullRefreshEnabled) {
        mPos -= 1
    }
    if (isHeaderType(mPos)) {
        val headerType = mPos * adapterViewType
        if (!headerViewType.contains(headerType)) {
            headerViewType.add(headerType)
        }
        return mPos * adapterViewType
    }
    if (isFooterType(mPos)) {
        val footerType = mPos * adapterViewType
        if (!footerViewType.contains(footerType)) {
            footerViewType.add(footerType)
        }
        return mPos * adapterViewType
    }
    return XAdapter.TYPE_ITEM
}

/**
 * [XAdapter]的 总数据个数
 */
internal fun <T> XAdapter<T>.dataSize(): Int {
    return dataContainer.size + if ((loadingMoreEnabled && dataContainer.isNotEmpty()) && pullRefreshEnabled) {
        2
    } else if ((loadingMoreEnabled && dataContainer.isNotEmpty()) || pullRefreshEnabled) {
        1
    } else {
        0
    }
}