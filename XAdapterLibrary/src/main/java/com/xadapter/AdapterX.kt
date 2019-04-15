package com.xadapter

import android.view.View
import androidx.databinding.ObservableArrayList
import com.xadapter.adapter.*
import com.xadapter.listener.XMultiCallBack
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView

/**
 * @author y
 * @create 2019/3/12
 */
fun <T> XDataBindingAdapterExecutePendingBindingsFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, true)

fun <T> XDataBindingAdapterFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, false)

fun <T> XDataBindingAdapter<T>.observableArrayList(): ObservableArrayList<T> = dataContainer as ObservableArrayList

fun <T : XMultiCallBack> XMultiAdapter(): XMultiAdapter<T> = com.xadapter.adapter.XMultiAdapter(ArrayList())

fun <T : XMultiCallBack> XMultiAdapter<T>.removeAll() {
    mMultiData.clear()
    notifyDataSetChanged()
}

fun <T : XMultiCallBack> XMultiAdapter<T>.remove(position: Int) {
    mMultiData.removeAt(position)
    notifyItemRemoved(position)
    notifyItemRangeChanged(position, itemCount)
}

fun <T : XMultiCallBack> XMultiAdapter<T>.addAll(t: List<T>) {
    mMultiData.addAll(t)
    notifyDataSetChanged()
}

fun <T : XMultiCallBack> XMultiAdapter<T>.add(t: T) {
    mMultiData.add(t)
    notifyDataSetChanged()
}

fun <T : XMultiCallBack> XMultiAdapter<T>.getItem(position: Int): T = mMultiData[position]

fun <T> XRecyclerViewAdapter<T>.addHeaderView(view: View) = apply { headerViewContainer.add(view) }

fun <T> XRecyclerViewAdapter<T>.addFooterView(view: View) = apply { footerViewContainer.add(view) }

fun <T> XRecyclerViewAdapter<T>.addAll(data: List<T>) {
    if (this is XDataBindingAdapter) {
        throw IllegalAccessError()
    }
    dataContainer.addAll(data)
    isShowEmptyView()
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.add(data: T) {
    if (this is XDataBindingAdapter) {
        throw IllegalAccessError()
    }
    dataContainer.add(data)
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.removeAll() {
    dataContainer.clear()
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.remove(position: Int) {
    dataContainer.removeAt(position)
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.previousItem(position: Int): T {
    return if (position == 0) {
        dataContainer[0]
    } else dataContainer[position - 1]
}

fun <T> XRecyclerViewAdapter<T>.removeHeader(index: Int) {
    headerViewContainer.removeAt(index)
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.removeHeader(view: View) {
    headerViewContainer.remove(view)
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.removeFooter(index: Int) {
    footerViewContainer.removeAt(index)
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.removeFooter(view: View) {
    footerViewContainer.remove(view)
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.removeAllNoItemView() {
    headerViewContainer.clear()
    footerViewContainer.clear()
    notifyDataSetChanged()
}

fun <T> XRecyclerViewAdapter<T>.getItem(position: Int): T = dataContainer[position]

fun <T> XRecyclerViewAdapter<T>.refresh() = apply {
    if (pullRefreshEnabled) {
        goneView(emptyView)
        visibleView(recyclerView)
        refreshView?.state = XRefreshView.REFRESH
        refreshView?.onMove(refreshView?.measuredHeight?.toFloat() ?: 0F)
        xRefreshListener?.invoke()
        loadMoreView?.state = XLoadMoreView.NORMAL
    }
}

fun <T> XRecyclerViewAdapter<T>.currentItemPosition(position: Int): Int {
    var mPos = position
    if (pullRefreshEnabled) {
        mPos -= 1
    }
    return mPos - headerViewContainer.size
}

fun <T> XDataBindingAdapter<T>.add(data: T) {
    mData.add(data)
    notifyDataSetChanged()
}

fun <T> XDataBindingAdapter<T>.addAll(data: List<T>) {
    mData.addAll(data)
    notifyDataSetChanged()
}