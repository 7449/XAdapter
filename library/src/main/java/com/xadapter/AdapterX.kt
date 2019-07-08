@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE", "FunctionName")

package com.xadapter

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XAdapter
import com.xadapter.adapter.XDataBindingAdapter
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.XMultiCallBack

inline fun <T : XMultiCallBack> RecyclerView.multiAdapter() = adapter as XMultiAdapter<T>

inline fun <T : XMultiCallBack> RecyclerView.multiRemoveAll() = multiAdapter<T>().removeAll()

inline fun <T : XMultiCallBack> RecyclerView.multiRemove(position: Int) = multiAdapter<T>().remove(position)

inline fun <T : XMultiCallBack> RecyclerView.multiAddAll(entity: List<T>) = multiAdapter<T>().addAll(entity)

inline fun <T : XMultiCallBack> RecyclerView.multiAdd(item: T) = multiAdapter<T>().add(item)

inline fun <T : XMultiCallBack> RecyclerView.multiItem(position: Int) = multiAdapter<T>().getItem(position)

inline fun <T : XMultiCallBack> XMultiAdapter(): XMultiAdapter<T> = XMultiAdapter(ArrayList())

inline fun <T : XMultiCallBack> XMultiAdapter<T>.getItem(position: Int): T = mMultiData[position]

inline fun <T : XMultiCallBack> XMultiAdapter<T>.setItemLayoutId(noinline action: (itemViewType: Int) -> Int) = also { this.itemLayoutId = action }

inline fun <T : XMultiCallBack> XMultiAdapter<T>.setMultiBind(noinline action: (holder: XViewHolder, entity: T, itemViewType: Int, position: Int) -> Unit) = also { this.xMultiBind = action }

inline fun <T : XMultiCallBack> XMultiAdapter<T>.gridLayoutManagerSpanSize(noinline action: (itemViewType: Int, manager: GridLayoutManager, position: Int) -> Int) = also { gridLayoutManagerSpanSize = action }

inline fun <T : XMultiCallBack> XMultiAdapter<T>.staggeredGridLayoutManagerFullSpan(noinline action: (itemViewType: Int) -> Boolean) = also { staggeredGridLayoutManagerFullSpan = action }

inline fun <T : XMultiCallBack> XMultiAdapter<T>.setOnItemClickListener(noinline action: (view: View, position: Int, entity: T) -> Unit) = also { onXItemClickListener = action }

inline fun <T : XMultiCallBack> XMultiAdapter<T>.setOnItemLongClickListener(noinline action: (view: View, position: Int, entity: T) -> Boolean) = also { onXItemLongClickListener = action }

inline fun <T : XMultiCallBack> XMultiAdapter<T>.removeAll() = also { mMultiData.clear() }.notifyDataSetChanged()

inline fun <T : XMultiCallBack> XMultiAdapter<T>.remove(position: Int) = also { mMultiData.removeAt(position) }.also { notifyItemRemoved(position) }.notifyItemRangeChanged(position, itemCount)

inline fun <T : XMultiCallBack> XMultiAdapter<T>.addAll(t: List<T>) = also { mMultiData.addAll(t) }.notifyDataSetChanged()

inline fun <T : XMultiCallBack> XMultiAdapter<T>.add(t: T) = also { mMultiData.add(t) }.notifyDataSetChanged()

inline fun <T : XMultiCallBack> XMultiAdapter<T>.attach(rootView: RecyclerView) = also { rootView.adapter = it }

inline fun <T> RecyclerView.dataBindingAdapter() = adapter as XDataBindingAdapter<T>

inline fun <T> RecyclerView.oservableArrayList() = dataBindingAdapter<T>().observableArrayList()

inline fun <T> RecyclerView.dataBindingAdd(item: T) = dataBindingAdapter<T>().add(item)

inline fun <T> RecyclerView.dataBindingAddAll(data: List<T>) = dataBindingAdapter<T>().addAll(data)

inline fun <T> RecyclerView.adapter() = adapter as XAdapter<T>

inline fun <T> RecyclerView.addHeader(view: View) = adapter<T>().addHeaderView(view)

inline fun <T> RecyclerView.removeHeader(view: View) = adapter<T>().removeHeader(view)

inline fun <T> RecyclerView.removeHeader(index: Int) = adapter<T>().removeHeader(index)

inline fun <T> RecyclerView.addFooter(view: View) = adapter<T>().addFooterView(view)

inline fun <T> RecyclerView.removeFooter(view: View) = adapter<T>().removeFooter(view)

inline fun <T> RecyclerView.removeFooter(index: Int) = adapter<T>().removeFooter(index)

inline fun <T> RecyclerView.addAll(data: List<T>) = adapter<T>().addAll(data)

inline fun <T> RecyclerView.add(item: T) = adapter<T>().add(item)

inline fun <T> RecyclerView.removeAll() = adapter<T>().removeAll()

inline fun <T> RecyclerView.remove(position: Int) = adapter<T>().remove(position)

inline fun <T> RecyclerView.previousItem(position: Int) = adapter<T>().previousItem(position)

inline fun <T> RecyclerView.getItem(position: Int) = adapter<T>().getItem(position)

inline fun <T> RecyclerView.removeAllNoItemView() = adapter<T>().removeAllNoItemView()

inline fun <T> XDataBindingAdapterExecutePendingBindingsFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, true)

inline fun <T> XDataBindingAdapterFactory(variableId: Int): XDataBindingAdapter<T> = XDataBindingAdapter(variableId, false)

inline fun <T> XDataBindingAdapter<T>.observableArrayList(): ObservableArrayList<T> = dataContainer as ObservableArrayList

inline fun <T> XAdapter<T>.addHeaderView(view: View) = apply { headerViewContainer.add(view) }

inline fun <T> XAdapter<T>.addFooterView(view: View) = apply { footerViewContainer.add(view) }

inline fun <T> XAdapter<T>.setItemLayoutId(layoutId: Int) = also { this.itemLayoutId = layoutId }

inline fun <T> XAdapter<T>.customRefreshView(view: XRefreshView) = also { this.refreshView = view }

inline fun <T> XAdapter<T>.customLoadMoreView(view: XLoadMoreView) = also { this.loadMoreView = view }

inline fun <T> XAdapter<T>.setEmptyView(view: View) = also { this.emptyView = view }

inline fun <T> XAdapter<T>.setScrollLoadMoreItemCount(count: Int) = also { this.scrollLoadMoreItemCount = count }

inline fun <T> XAdapter<T>.openPullRefresh() = also { this.pullRefreshEnabled = true }

inline fun <T> XAdapter<T>.openLoadingMore() = also { this.loadingMoreEnabled = true }

inline fun <T> XAdapter<T>.setRefreshListener(noinline action: () -> Unit) = also { this.xRefreshListener = action }

inline fun <T> XAdapter<T>.setLoadMoreListener(noinline action: () -> Unit) = also { this.xLoadMoreListener = action }

inline fun <T> XAdapter<T>.setFooterListener(noinline action: (view: View) -> Unit) = also { this.onXFooterListener = action }

inline fun <T> XAdapter<T>.setOnEmptyClickListener(noinline action: (view: View) -> Unit) = also { this.onXEmptyListener = action }

inline fun <T> XAdapter<T>.setOnBind(noinline action: (holder: XViewHolder, position: Int, entity: T) -> Unit) = also { this.onXBindListener = action }

inline fun <T> XAdapter<T>.setOnItemClickListener(noinline action: (view: View, position: Int, entity: T) -> Unit) = also { onXItemClickListener = action }

inline fun <T> XAdapter<T>.setOnItemLongClickListener(noinline action: (view: View, position: Int, entity: T) -> Boolean) = also { onXItemLongClickListener = action }

inline fun <T> XAdapter<T>.attach(rootView: RecyclerView) = also { recyclerView = rootView }.also { rootView.adapter = it }

inline fun <T> XAdapter<T>.addAll(data: List<T>) {
    checkDataBinding()
    dataContainer.addAll(data)
    notifyDataSetChanged()
    isShowEmptyView()
}

inline fun <T> XAdapter<T>.add(data: T) {
    checkDataBinding()
    dataContainer.add(data)
    notifyDataSetChanged()
    isShowEmptyView()
}

inline fun <T> XAdapter<T>.checkDataBinding() = also { if (this is XDataBindingAdapter) throw IllegalAccessError() }

inline fun <T> XAdapter<T>.removeAll() = also { dataContainer.clear() }.notifyDataSetChanged()

inline fun <T> XAdapter<T>.remove(position: Int) = also { dataContainer.removeAt(position) }.notifyDataSetChanged()

inline fun <T> XAdapter<T>.previousItem(position: Int): T = if (position == 0) dataContainer[0] else dataContainer[position - 1]

inline fun <T> XAdapter<T>.removeHeader(index: Int) {
    headerViewContainer.removeAt(index)
    headerViewType.removeAt(if (index == 0) 0 else index / adapterViewType)
    notifyDataSetChanged()
}

inline fun <T> XAdapter<T>.removeHeader(view: View) {
    val indexOf = headerViewContainer.indexOf(view)
    if (indexOf == -1) return
    removeHeader(indexOf)
}

inline fun <T> XAdapter<T>.removeFooter(index: Int) {
    footerViewContainer.removeAt(index)
    footerViewType.removeAt(if (index == 0) 0 else index / adapterViewType)
    notifyDataSetChanged()
}

inline fun <T> XAdapter<T>.removeFooter(view: View) {
    val indexOf = footerViewContainer.indexOf(view)
    if (indexOf == -1) return
    notifyDataSetChanged()
}

inline fun <T> XAdapter<T>.removeAllNoItemView() {
    headerViewType.clear()
    footerViewType.clear()
    headerViewContainer.clear()
    footerViewContainer.clear()
    notifyDataSetChanged()
}

inline fun <T> XAdapter<T>.getItem(position: Int): T = dataContainer[position]

inline fun <T> XAdapter<T>.refresh() = apply {
    if (pullRefreshEnabled) {
        emptyView?.gone()
        recyclerView?.show()
        refreshView?.state = XRefreshView.REFRESH
        refreshView?.onMove(refreshView?.measuredHeight?.toFloat() ?: 0F)
        xRefreshListener?.invoke()
        loadMoreView?.state = XLoadMoreView.NORMAL
    }
}

inline fun <T> XAdapter<T>.currentItemPosition(position: Int): Int {
    var mPos = position
    if (pullRefreshEnabled) {
        mPos -= 1
    }
    return mPos - headerViewContainer.size
}

inline fun <T> XDataBindingAdapter<T>.add(data: T) {
    mData.add(data)
    notifyDataSetChanged()
    isShowEmptyView()
}

inline fun <T> XDataBindingAdapter<T>.addAll(data: List<T>) {
    mData.addAll(data)
    notifyDataSetChanged()
    isShowEmptyView()
}

inline fun <T> XAdapter<T>.isShowEmptyView() {
    if (dataContainer.isEmpty()) {
        emptyView?.show()
        recyclerView?.gone()
    } else {
        emptyView?.gone()
        recyclerView?.show()
    }
}

inline fun View.gone() = also { if (visibility == View.VISIBLE) visibility = View.GONE }

inline fun View.show() = also { if (visibility == View.GONE) visibility = View.VISIBLE }