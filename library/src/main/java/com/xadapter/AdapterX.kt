package com.xadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XAdapter
import com.xadapter.holder.XViewHolder

@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.adapter() = adapter as XAdapter<T>

fun <T> RecyclerView.attachAdapter(adapter: XAdapter<T>) = also { setAdapter(adapter) }.adapter<T>()

//fun <T> RecyclerView.addHeader(view: View) = adapter<T>().addHeaderView(view)
//
//fun <T> RecyclerView.removeHeader(view: View) = adapter<T>().removeHeader(view)
//
//fun <T> RecyclerView.removeHeader(index: Int) = adapter<T>().removeHeader(index)
//
//fun <T> RecyclerView.addFooter(view: View) = adapter<T>().addFooterView(view)
//
//fun <T> RecyclerView.removeFooter(view: View) = adapter<T>().removeFooter(view)
//
//fun <T> RecyclerView.removeFooter(index: Int) = adapter<T>().removeFooter(index)
//
//fun <T> RecyclerView.addAll(data: List<T>) = adapter<T>().addAll(data)
//
//fun <T> RecyclerView.add(item: T) = adapter<T>().add(item)
//
//fun <T> RecyclerView.removeAll() = adapter<T>().removeAll()
//
//fun <T> RecyclerView.remove(position: Int) = adapter<T>().remove(position)
//
//fun <T> RecyclerView.previousItem(position: Int) = adapter<T>().previousItem(position)
//
//fun <T> RecyclerView.getItem(position: Int) = adapter<T>().getItem(position)
//
//fun <T> RecyclerView.removeAllNoItemView() = adapter<T>().removeAllNoItemView()

fun <T> XAdapter<T>.addHeaderView(view: View) = apply { headerViewContainer.add(view) }

fun <T> XAdapter<T>.addFooterView(view: View) = apply { footerViewContainer.add(view) }

fun <T> XAdapter<T>.setItemLayoutId(layoutId: Int) = also { this.itemLayoutId = layoutId }

fun <T> XAdapter<T>.customRefreshView(view: XRefreshView) = also { this.refreshView = view }

fun <T> XAdapter<T>.customLoadMoreView(view: XLoadMoreView) = also { this.loadMoreView = view }

fun <T> XAdapter<T>.setScrollLoadMoreItemCount(count: Int) = also { this.scrollLoadMoreItemCount = count }

fun <T> XAdapter<T>.openPullRefresh() = also { this.pullRefreshEnabled = true }

fun <T> XAdapter<T>.openLoadingMore() = also { this.loadingMoreEnabled = true }

fun <T> XAdapter<T>.setRefreshListener(action: () -> Unit) = also { this.xRefreshListener = action }

fun <T> XAdapter<T>.setRefreshState(status: Int) = also { refreshState = status }

fun <T> XAdapter<T>.setLoadMoreListener(action: () -> Unit) = also { this.xLoadMoreListener = action }

fun <T> XAdapter<T>.setLoadMoreState(status: Int) = also { loadMoreState = status }

fun <T> XAdapter<T>.setFooterListener(action: (view: View, adapter: XAdapter<T>) -> Unit) = also { this.onXFooterListener = action }

fun <T> XAdapter<T>.setOnBind(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) = also { this.onXBindListener = action }

fun <T> XAdapter<T>.setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) = also { onXItemClickListener = action }

fun <T> XAdapter<T>.setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) = also { onXItemLongClickListener = action }

fun <T> XAdapter<T>.getItem(position: Int): T = dataContainer[position]

fun <T> XAdapter<T>.addAll(data: List<T>) = also { dataContainer.addAll(data) }.notifyDataSetChanged()

fun <T> XAdapter<T>.add(data: T) = also { dataContainer.add(data) }.notifyDataSetChanged()

fun <T> XAdapter<T>.removeAll() = also { dataContainer.clear() }.notifyDataSetChanged()

fun <T> XAdapter<T>.remove(position: Int) = also { dataContainer.removeAt(position) }.notifyDataSetChanged()

fun <T> XAdapter<T>.previousItem(position: Int): T = if (position == 0) dataContainer[0] else dataContainer[position - 1]

fun <T> XAdapter<T>.removeHeader(index: Int) = also { headerViewContainer.removeAt(index) }.also { headerViewType.removeAt(if (index == 0) 0 else index / adapterViewType) }.notifyDataSetChanged()

fun <T> XAdapter<T>.removeHeader(view: View) {
    val indexOf = headerViewContainer.indexOf(view)
    if (indexOf == -1) return
    removeHeader(indexOf)
}

fun <T> XAdapter<T>.removeFooter(index: Int) = also { footerViewContainer.removeAt(index) }.also { footerViewType.removeAt(if (index == 0) 0 else index / adapterViewType) }.notifyDataSetChanged()

fun <T> XAdapter<T>.removeFooter(view: View) {
    val indexOf = footerViewContainer.indexOf(view)
    if (indexOf == -1) return
    notifyDataSetChanged()
}

fun <T> XAdapter<T>.removeAllNotItemView() {
    headerViewType.clear()
    footerViewType.clear()
    headerViewContainer.clear()
    footerViewContainer.clear()
    notifyDataSetChanged()
}

fun <T> XAdapter<T>.refresh(view: RecyclerView) = apply {
    recyclerView = view
    if (pullRefreshEnabled) {
        refreshView?.state = XRefreshView.REFRESH
        refreshView?.onMove(refreshView?.measuredHeight?.toFloat() ?: 0F)
        xRefreshListener?.invoke()
        loadMoreView?.state = XLoadMoreView.NORMAL
    }
}