@file:Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate", "unused", "FunctionName")

package com.xadapter.holder

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.currentItemPosition
import com.xadapter.listener.XMultiCallBack

class XDataBindingHolder(var viewDataBinding: ViewDataBinding) : XViewHolder(viewDataBinding.root)

open class XViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

internal fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.SuperViewHolder(parent: ViewGroup, layoutId: Int) = XViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

internal fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.SuperViewHolder(view: View) = XViewHolder(view)

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

internal fun <T : XMultiCallBack> XViewHolder.MultiViewHolderClick(xMultiAdapter: XMultiAdapter<T>): XViewHolder {
    itemView.setOnClickListener {
        if (xMultiAdapter.mMultiData[layoutPosition].position == XMultiCallBack.NO_CLICK_POSITION) return@setOnClickListener
        xMultiAdapter.onXItemClickListener?.onXItemClick(it, layoutPosition, xMultiAdapter.mMultiData[layoutPosition])
    }
    return this
}

internal fun <T : XMultiCallBack> XViewHolder.MultiViewHolderLongClick(xMultiAdapter: XMultiAdapter<T>) {
    itemView.setOnLongClickListener {
        if (xMultiAdapter.mMultiData[layoutPosition].position == XMultiCallBack.NO_CLICK_POSITION) return@setOnLongClickListener false
        return@setOnLongClickListener xMultiAdapter.onXLongClickListener?.onXItemLongClick(itemView, layoutPosition, xMultiAdapter.mMultiData[layoutPosition])
                ?: false
    }
}

internal fun <T> XViewHolder.XViewHolderClick(adapter: XRecyclerViewAdapter<T>): XViewHolder {
    itemView.setOnClickListener { view ->
        adapter.onXItemClickListener?.onXItemClick(view,
                adapter.currentItemPosition(layoutPosition),
                adapter.dataContainer[adapter.currentItemPosition(layoutPosition)])
    }
    return this
}

internal fun <T> XViewHolder.XViewHolderLongClick(adapter: XRecyclerViewAdapter<T>) {
    itemView.setOnLongClickListener { view ->
        adapter.onXLongClickListener?.onXItemLongClick(view,
                adapter.currentItemPosition(layoutPosition),
                adapter.dataContainer[adapter.currentItemPosition(layoutPosition)])
        true
    }
}