@file:Suppress("UNCHECKED_CAST")

package com.xadapter.holder

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XAdapter

/**
 * BaseViewHolder
 * [RecyclerView.ViewHolder]
 */
open class XViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class XDataBindingHolder(var viewDataBinding: ViewDataBinding) : XViewHolder(viewDataBinding.root)

internal fun superViewHolder(parent: ViewGroup, layoutId: Int) = XViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

internal fun superViewHolder(view: View) = XViewHolder(view)

internal fun <T : View> XViewHolder.getView(id: Int): T {
    var viewSparseArray: SparseArray<View>? = itemView.tag as SparseArray<View>?
    if (null == viewSparseArray) {
        viewSparseArray = SparseArray()
        itemView.tag = viewSparseArray
    }
    var childView: View? = viewSparseArray.get(id)
    if (null == childView) {
        childView = itemView.findViewById(id)
        viewSparseArray.put(id, childView)
    }
    return childView as T
}

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