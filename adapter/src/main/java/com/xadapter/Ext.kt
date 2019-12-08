package com.xadapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xadapter.adapter.XAdapter
import com.xadapter.vh.XViewHolder

fun <T> XViewHolder.viewHolderClick(adapter: XAdapter<T>): XViewHolder {
    adapter.onXItemClickListener?.let { onXItemClickListener ->
        itemView.setOnClickListener { view ->
            onXItemClickListener.invoke(view, adapter.currentItemPosition(layoutPosition), adapter.dataContainer[adapter.currentItemPosition(layoutPosition)])
        }
    }
    return this
}

fun <T> XViewHolder.viewHolderLongClick(adapter: XAdapter<T>) {
    adapter.onXItemLongClickListener?.let { onXItemLongClickListener ->
        itemView.setOnLongClickListener { view ->
            onXItemLongClickListener.invoke(view, adapter.currentItemPosition(layoutPosition), adapter.dataContainer[adapter.currentItemPosition(layoutPosition)])
        }
    }
}

fun <T> XAdapter<T>.currentItemPosition(position: Int): Int {
    var mPos = position
    if (pullRefreshEnabled) {
        mPos -= 1
    }
    return mPos - headerViewContainer.size
}

internal fun <T> XAdapter<T>.internalOnAttachedToRecyclerView(recyclerView: RecyclerView) {
    val manager = recyclerView.layoutManager
    if (manager is GridLayoutManager) {
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = if (getItemViewType(position) != XAdapter.TYPE_ITEM) manager.spanCount else 1
        }
    }
}

internal fun <T> XAdapter<T>.internalOnViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
    viewHolder.itemView.layoutParams?.let {
        if (it is StaggeredGridLayoutManager.LayoutParams) {
            it.isFullSpan = getItemViewType(viewHolder.layoutPosition) != XAdapter.TYPE_ITEM
        }
    }
}

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
    if (dataContainer.isEmpty() && headerViewContainer.isEmpty() && footerViewContainer.isEmpty()) {
        return XAdapter.TYPE_EMPTY
    }
    return XAdapter.TYPE_ITEM
}

internal fun <T> XAdapter<T>.getAdapterItemCount(): Int {
    if (footerViewContainer.isEmpty() && headerViewContainer.isEmpty() && dataContainer.isEmpty()) {
        return if (pullRefreshEnabled) 2 else 1
    }
    return dataSize() + footerViewContainer.size + headerViewContainer.size
}

internal fun <T> XAdapter<T>.dataSize(): Int {
    return dataContainer.size + if ((loadingMoreEnabled && dataContainer.isNotEmpty()) && pullRefreshEnabled) {
        2
    } else if ((loadingMoreEnabled && dataContainer.isNotEmpty()) || pullRefreshEnabled) {
        1
    } else {
        0
    }
}