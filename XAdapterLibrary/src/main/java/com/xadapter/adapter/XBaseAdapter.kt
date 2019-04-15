@file:Suppress("FunctionName")

package com.xadapter.adapter

import android.view.View
import android.view.ViewParent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.XMultiCallBack
import com.xadapter.manager.AppBarStateChangeListener
import com.xadapter.manager.XTouchListener

/**
 * @author y
 * @create 2019/3/15
 */
abstract class XBaseAdapter<T> : RecyclerView.Adapter<XViewHolder>() {

    var onXItemClickListener: ((view: View, position: Int, entity: T) -> Unit)? = null

    var onXLongClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null

}

/**
 * 接管[XMultiAdapter]在[GridLayoutManager]下的显示效果
 */
internal fun <T : XMultiCallBack> XMultiAdapter<T>.internalOnAttachedToRecyclerView(recyclerView: RecyclerView) {
    val manager = recyclerView.layoutManager
    if (manager is GridLayoutManager) {
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return gridLayoutManagerSpanSize?.invoke(getItemViewType(position), manager, position)
                        ?: 0
            }
        }
    }
}

/**
 * 接管[XMultiAdapter]在[StaggeredGridLayoutManager]下的显示效果
 */
internal fun <T : XMultiCallBack> XMultiAdapter<T>.internalOnViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
    val layoutParams = viewHolder.itemView.layoutParams
    if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
        layoutParams.isFullSpan = staggeredGridLayoutManagerFullSpan?.invoke(getItemViewType(viewHolder.layoutPosition))
                ?: false
    }
}

/**
 * 接管[XRecyclerViewAdapter]在[GridLayoutManager]下的显示效果
 */
internal fun <T> XRecyclerViewAdapter<T>.internalOnAttachedToRecyclerView(recyclerView: RecyclerView) {
    val manager = recyclerView.layoutManager
    if (manager is GridLayoutManager) {
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = if (getItemViewType(position) != XRecyclerViewAdapter.TYPE_ITEM) manager.spanCount else 1
        }
    }
}

/**
 * 处理[XRecyclerViewAdapter]在[AppBarLayout]下的滑动冲突
 */
internal fun <T> XRecyclerViewAdapter<T>.internalOnViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
    val layoutParams = viewHolder.itemView.layoutParams
    if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
        layoutParams.isFullSpan = getItemViewType(viewHolder.layoutPosition) != XRecyclerViewAdapter.TYPE_ITEM
    }
    if (recyclerView == null) {
        return
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
        if (appBarLayout != null && touchListener is XTouchListener) {
            appBarLayout.addOnOffsetChangedListener(
                    object : AppBarStateChangeListener() {
                        public override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                            (touchListener as XTouchListener).state = state
                        }
                    })
        }
    }
}

/**
 * [XRecyclerViewAdapter]的 viewType
 */
internal fun <T> XRecyclerViewAdapter<T>.internalGetItemViewType(position: Int): Int {
    var mPos = position
    if (isRefreshHeaderType(mPos)) {
        return XRecyclerViewAdapter.TYPE_REFRESH_HEADER
    }
    if (isLoadMoreType(mPos)) {
        return XRecyclerViewAdapter.TYPE_LOAD_MORE_FOOTER
    }
    if (pullRefreshEnabled) {
        mPos -= 1
    }
    if (isHeaderType(mPos)) {
        headerViewType.add(mPos * adapterViewType)
        return mPos * adapterViewType
    }
    if (isFooterType(mPos)) {
        footerViewType.add(mPos * adapterViewType)
        return mPos * adapterViewType
    }
    return XRecyclerViewAdapter.TYPE_ITEM
}

/**
 * [XRecyclerViewAdapter]的 总数据个数
 */
internal fun <T> XRecyclerViewAdapter<T>.dataSize(): Int {
    return dataContainer.size + if ((loadingMoreEnabled && !dataContainer.isEmpty()) && pullRefreshEnabled) {
        2
    } else if ((loadingMoreEnabled && !dataContainer.isEmpty()) || pullRefreshEnabled) {
        1
    } else {
        0
    }
}

/**
 * 是否显示EmptyView
 */
internal fun <T> XRecyclerViewAdapter<T>.isShowEmptyView() {
    if (recyclerView == null) {
        return
    }
    if (dataContainer.isEmpty()) {
        visibleView(emptyView)
        goneView(recyclerView)
    } else {
        visibleView(recyclerView)
        goneView(emptyView)
    }
}

fun goneView(vararg views: View?) {
    for (view in views) {
        if (view != null && view.visibility != View.GONE)
            view.visibility = View.GONE
    }
}

fun visibleView(vararg views: View?) {
    for (view in views) {
        if (view != null && view.visibility != View.VISIBLE)
            view.visibility = View.VISIBLE
    }
}