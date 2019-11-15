@file:Suppress("UNCHECKED_CAST")

package com.xadapter.holder

import com.xadapter.adapter.XAdapter
import com.xadapter.vh.XViewHolder

internal fun <T> XViewHolder.viewHolderClick(adapter: XAdapter<T>): XViewHolder {
    itemView.setOnClickListener { view ->
        adapter.onXItemClickListener?.invoke(view,
                adapter.currentItemPosition(layoutPosition),
                adapter.dataContainer[adapter.currentItemPosition(layoutPosition)])
    }
    return this
}

internal fun <T> XViewHolder.viewHolderLongClick(adapter: XAdapter<T>) {
    itemView.setOnLongClickListener { view ->
        val invoke = adapter.onXItemLongClickListener?.invoke(view,
                adapter.currentItemPosition(layoutPosition),
                adapter.dataContainer[adapter.currentItemPosition(layoutPosition)]) ?: false
        invoke
    }
}

internal fun <T> XAdapter<T>.currentItemPosition(position: Int): Int {
    var mPos = position
    if (pullRefreshEnabled) {
        mPos -= 1
    }
    return mPos - headerViewContainer.size
}