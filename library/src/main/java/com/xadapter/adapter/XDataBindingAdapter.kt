@file:Suppress("FunctionName", "ClickableViewAccessibility")

package com.xadapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import com.xadapter.currentItemPosition
import com.xadapter.holder.SuperViewHolder
import com.xadapter.holder.XDataBindingHolder
import com.xadapter.holder.XViewHolder
import com.xadapter.manager.XScrollListener
import com.xadapter.manager.XTouchListener


/**
 * @author y
 * @create 2018/12/25
 */
open class XDataBindingAdapter<T>(private val variableId: Int, private val executePendingBindings: Boolean) : XRecyclerViewAdapter<T>() {

    var mData: ObservableArrayList<T> = ObservableArrayList()

    override var dataContainer: ArrayList<T>
        get() = mData
        set(value) {
            mData.addAll(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (headerViewType.contains(viewType)) {
            return SuperViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (footerViewType.contains(viewType)) {
            return SuperViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
        }
        val viewHolder = XDataBindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), itemLayoutId, parent, false))
        viewHolder.itemView.setOnClickListener { view -> onXItemClickListener?.invoke(view, currentItemPosition(viewHolder.layoutPosition), dataContainer[currentItemPosition(viewHolder.layoutPosition)]) }
        viewHolder.itemView.setOnLongClickListener { view ->
            val invoke = onXLongClickListener?.invoke(view, currentItemPosition(viewHolder.layoutPosition), dataContainer[currentItemPosition(viewHolder.layoutPosition)]) ?: false
            invoke
        }
        if ((viewType == TYPE_REFRESH_HEADER || viewType == TYPE_LOAD_MORE_FOOTER) && recyclerView == null) {
            throw NullPointerException("detect recyclerView is null")
        }
        return when (viewType) {
            TYPE_REFRESH_HEADER -> {
                refreshView?.let {
                    touchListener = XTouchListener(it, loadMoreView) { onRefresh() }
                    recyclerView?.setOnTouchListener(touchListener)
                    XViewHolder(it)
                } ?: throw NullPointerException("detect refreshView is null")
            }
            TYPE_LOAD_MORE_FOOTER -> {
                loadMoreView?.let {
                    it.setOnClickListener { v -> onXFooterListener?.invoke(v) }
                    scrollListener = XScrollListener { onScrollBottom() }.apply {
                        scrollItemCount = scrollLoadMoreItemCount
                        recyclerView?.addOnScrollListener(this)
                    }
                    XViewHolder(it)
                } ?: throw NullPointerException("detect loadMoreView is null")
            }
            else -> viewHolder
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != TYPE_ITEM) {
            return
        }
        val pos = currentItemPosition(position)
        val t = dataContainer[pos] ?: return
        holder as XDataBindingHolder
        holder.viewDataBinding.setVariable(variableId, t)
        if (executePendingBindings) {
            holder.viewDataBinding.executePendingBindings()
        }
    }
}
