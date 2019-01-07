@file:Suppress("FunctionName")

package com.xadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import com.xadapter.holder.XDataBindingHolder
import com.xadapter.holder.XViewHolder
import com.xadapter.manager.XScrollListener
import com.xadapter.manager.XTouchListener

fun <T> XDataBindingAdapterExecutePendingBindingsFactory(variableId: Int): XDataBindingAdapter<T> {
    return XDataBindingAdapter(variableId, true)
}

fun <T> XDataBindingAdapterFactory(variableId: Int): XDataBindingAdapter<T> {
    return XDataBindingAdapter(variableId, false)
}

/**
 * @author y
 * @create 2018/12/25
 */
@SuppressLint("ClickableViewAccessibility")
open class XDataBindingAdapter<T>(private val variableId: Int, private val executePendingBindings: Boolean) : XRecyclerViewAdapter<T>() {

    private var mData: ObservableArrayList<T> = ObservableArrayList()

    override var dataContainer: ArrayList<T>
        get() = super.dataContainer
        set(value) {
            mData.addAll(value)
        }

    fun observableArrayList(): ObservableArrayList<T> {
        return dataContainer as ObservableArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (headerViewType.contains(viewType)) {
            return XViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (footerViewType.contains(viewType)) {
            return XViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
        }
        val viewHolder = XDataBindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), itemLayoutId, parent, false))
        viewHolder.itemView.setOnClickListener { view ->
            onItemClickListener?.onItemClick(view,
                    getItemPosition(viewHolder.layoutPosition),
                    dataContainer[getItemPosition(viewHolder.layoutPosition)])
        }
        viewHolder.itemView.setOnLongClickListener { view ->
            onLongClickListener?.onLongClick(view,
                    getItemPosition(viewHolder.layoutPosition),
                    dataContainer[getItemPosition(viewHolder.layoutPosition)])
            true
        }
        if ((viewType == TYPE_REFRESH_HEADER || viewType == TYPE_LOAD_MORE_FOOTER) && recyclerView == null) {
            throw NullPointerException("detect recyclerView is null")
        }
        return when (viewType) {
            XRecyclerViewAdapter.TYPE_REFRESH_HEADER -> {
                refreshView?.let {
                    touchListener = XTouchListener(it, loadMoreView, this)
                    recyclerView?.setOnTouchListener(touchListener)
                    XViewHolder(it)
                } ?: throw NullPointerException("detect refreshView is null")
            }
            XRecyclerViewAdapter.TYPE_LOAD_MORE_FOOTER -> {
                loadMoreView?.let { it ->
                    loadMoreView?.setOnClickListener { v -> onFooterListener?.onXFooterClick(v) }
                    scrollListener = XScrollListener(this).apply { scrollItemCount = scrollLoadMoreItemCount }
                    scrollListener?.let { recyclerView?.addOnScrollListener(it) }
                    XViewHolder(it)
                } ?: throw NullPointerException("detect loadMoreView is null")
            }
            else -> viewHolder
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != XRecyclerViewAdapter.TYPE_ITEM) {
            return
        }
        val pos = getItemPosition(position)
        val t = dataContainer[pos] ?: return
        holder as XDataBindingHolder
        holder.viewDataBinding.setVariable(variableId, t)
        if (executePendingBindings) {
            holder.viewDataBinding.executePendingBindings()
        }
    }
}
