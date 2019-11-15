package com.xadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.holder.*
import com.xadapter.vh.XDataBindingHolder
import com.xadapter.vh.XViewHolder
import com.xadapter.vh.superViewHolder

/**
 * @author y
 * @create 2018/12/25
 */
open class XDataBindingAdapter<T>(private val variableId: Int, private val executePendingBindings: Boolean) : XAdapter<T>() {

    var mData: ObservableArrayList<T> = ObservableArrayList()

    override var dataContainer: ArrayList<T>
        get() = mData
        set(value) {
            mData.addAll(value)
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (recyclerView == null) {
            recyclerView = parent as RecyclerView
        }
        if (headerViewType.contains(viewType)) {
            return superViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (footerViewType.contains(viewType)) {
            return superViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
        }
        val viewHolder = XDataBindingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), itemLayoutId, parent, false)).apply { viewHolderClick(this@XDataBindingAdapter).viewHolderLongClick(this@XDataBindingAdapter) }
        return when (viewType) {
            TYPE_REFRESH_HEADER -> {
                refreshView?.let {
                    recyclerView?.setOnTouchListener(touchListener)
                    XViewHolder(it)
                } ?: throw NullPointerException("detect refreshView is null")
            }
            TYPE_LOAD_MORE_FOOTER -> {
                loadMoreView?.let { it ->
                    it.setOnClickListener { v -> onXFooterListener?.invoke(v, this) }
                    scrollListener?.let { recyclerView?.addOnScrollListener(it) }
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
