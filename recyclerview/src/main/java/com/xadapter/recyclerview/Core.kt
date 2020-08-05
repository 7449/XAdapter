@file:Suppress("UNCHECKED_CAST")

package com.xadapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XAdapter
import com.xadapter.multi.XMultiCallBack
import com.xadapter.refresh.XLoadMoreCallback
import com.xadapter.refresh.XRefreshCallback
import com.xadapter.vh.XViewHolder

fun RecyclerView.addHeaderView(view: View) = also {
    if (checkAdapter()) {
        adapter<Any>().addHeaderView(view)
    }
}

fun RecyclerView.addFooterView(view: View) = also {
    if (checkAdapter()) {
        adapter<Any>().addFooterView(view)
    }
}

fun RecyclerView.setItemLayoutId(layoutId: Int) = also {
    if (checkAdapter()) {
        adapter<Any>().setItemLayoutId(layoutId)
    }
}

fun RecyclerView.setEmptyView(view: View) = also {
    if (checkAdapter()) {
        adapter<Any>().setEmptyView(view)
    }
}

fun RecyclerView.customScrollListener(onScrollListener: RecyclerView.OnScrollListener) = also {
    if (checkAdapter()) {
        adapter<Any>().customScrollListener(onScrollListener)
    }
}

fun RecyclerView.customRefreshCallback(callback: XRefreshCallback) = also {
    if (checkAdapter()) {
        adapter<Any>().customRefreshCallback(callback)
    }
}

fun RecyclerView.customLoadMoreCallback(callback: XLoadMoreCallback) = also {
    if (checkAdapter()) {
        adapter<Any>().customLoadMoreCallback(callback)
    }
}

fun RecyclerView.setScrollLoadMoreItemCount(count: Int) = also {
    if (checkAdapter()) {
        adapter<Any>().setScrollLoadMoreItemCount(count)
    }
}

fun RecyclerView.openPullRefresh() = also {
    if (checkAdapter()) {
        adapter<Any>().openPullRefresh()
    }
}

fun RecyclerView.openLoadingMore() = also {
    if (checkAdapter()) {
        adapter<Any>().openLoadingMore()
    }
}

fun RecyclerView.setRefreshListener(action: (adapter: XAdapter<*>) -> Unit) = also {
    if (checkAdapter()) {
        adapter<Any>().setRefreshListener(action)
    }
}

fun RecyclerView.setRefreshState(status: Int) = also {
    if (checkAdapter()) {
        adapter<Any>().setRefreshState(status)
    }
}

fun RecyclerView.setLoadMoreListener(action: (adapter: XAdapter<*>) -> Unit) = also {
    if (checkAdapter()) {
        adapter<Any>().setLoadMoreListener(action)
    }
}

fun RecyclerView.setLoadMoreState(status: Int) = also {
    if (checkAdapter()) {
        adapter<Any>().setLoadMoreState(status)
    }
}

fun <T> RecyclerView.setOnBind(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) = also {
    if (checkAdapter()) {
        adapter<T>().setOnBind(action)
    }
}

fun <T> RecyclerView.setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) = also {
    if (checkAdapter()) {
        adapter<T>().setOnItemClickListener(action)
    }
}

fun <T> RecyclerView.setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) = also {
    if (checkAdapter()) {
        adapter<T>().setOnItemLongClickListener(action)
    }
}

fun <T> RecyclerView.getItem(position: Int): T = adapter<T>().getItem(position)

fun RecyclerView.getHeaderView(position: Int) = adapter<Any>().getHeaderView(position)

fun RecyclerView.getFooterView(position: Int) = adapter<Any>().getFooterView(position)

fun RecyclerView.addAll(data: List<Any>) {
    if (checkAdapter()) {
        adapter<Any>().addAll(data)
    }
}

fun RecyclerView.add(data: Any) {
    if (checkAdapter()) {
        adapter<Any>().add(data)
    }
}

fun RecyclerView.removeAll() {
    if (checkAdapter()) {
        adapter<Any>().removeAll()
    }
}

fun RecyclerView.remove(position: Int) {
    if (checkAdapter()) {
        adapter<Any>().remove(position)
    }
}

fun RecyclerView.removeHeader(index: Int) {
    if (checkAdapter()) {
        adapter<Any>().removeHeader(index)
    }
}

fun RecyclerView.removeHeader(view: View) {
    if (checkAdapter()) {
        adapter<Any>().removeHeader(view)
    }
}

fun RecyclerView.removeFooter(index: Int) {
    if (checkAdapter()) {
        adapter<Any>().removeFooter(index)
    }
}

fun RecyclerView.removeFooter(view: View) {
    if (checkAdapter()) {
        adapter<Any>().removeFooter(view)
    }
}

fun RecyclerView.removeAllNotItemViews() {
    if (checkAdapter()) {
        adapter<Any>().removeAllNotItemViews()
    }
}

fun RecyclerView.refresh() {
    if (checkAdapter()) {
        adapter<Any>().refresh(this)
    }
}

fun <T : XMultiCallBack> RecyclerView.getMultiItem(position: Int): T = multiAdapter<T>().getItem(position)

fun RecyclerView.multiSetItemLayoutId(action: (itemViewType: Int) -> Int) = also {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().setItemLayoutId(action)
    }
}

fun <T : XMultiCallBack> RecyclerView.multiSetBind(action: (holder: XViewHolder, entity: T, itemViewType: Int, position: Int) -> Unit) = also {
    if (checkMultiAdapter()) {
        multiAdapter<T>().setMultiBind(action)
    }
}

fun RecyclerView.multiGridLayoutManagerSpanSize(action: (itemViewType: Int, manager: GridLayoutManager, position: Int) -> Int) = also {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().gridLayoutManagerSpanSize(action)
    }
}

fun RecyclerView.multiStaggeredGridLayoutManagerFullSpan(action: (itemViewType: Int) -> Boolean) = also {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().staggeredGridLayoutManagerFullSpan(action)
    }
}

fun <T : XMultiCallBack> RecyclerView.multiSetOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) = also {
    if (checkMultiAdapter()) {
        multiAdapter<T>().setOnItemClickListener(action)
    }
}

fun <T : XMultiCallBack> RecyclerView.multiSetOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) = also {
    if (checkMultiAdapter()) {
        multiAdapter<T>().setOnItemLongClickListener(action)
    }
}

fun RecyclerView.multiRemoveAll() {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().removeAll()
    }
}

fun RecyclerView.multiRemove(position: Int) {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().remove(position)
    }
}

fun RecyclerView.multiAddAll(data: List<XMultiCallBack>) {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().addAll(data)
    }
}

fun RecyclerView.multiAdd(data: XMultiCallBack) {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().add(data)
    }
}