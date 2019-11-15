@file:Suppress("UNCHECKED_CAST")

package com.xadapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xadapter.*
import com.xadapter.adapter.XAdapter
import com.xadapter.adapter.XDataBindingAdapter
import com.xadapter.multi.*
import com.xadapter.vh.XViewHolder

fun <T> RecyclerView.adapter() = adapter as XAdapter<T>

fun <T> RecyclerView.dataBindingAdapter() = adapter as XDataBindingAdapter<T>

fun <T : XMultiCallBack> RecyclerView.multiAdapter() = adapter as XMultiAdapter<T>

fun <T> RecyclerView.attachAdapter(adapter: XAdapter<T>) = also { setAdapter(adapter) }

fun <T> RecyclerView.attachAdapter() = attachAdapter(XAdapter<T>())

fun <T> RecyclerView.attachDataBindingAdapter(adapter: XDataBindingAdapter<T>) = also { setAdapter(adapter) }

fun <T> RecyclerView.attachDataBindingAdapter(variableId: Int) = attachDataBindingAdapter(XDataBindingAdapterExecutePendingBindingsFactory<T>(variableId))

fun <T : XMultiCallBack> RecyclerView.attachMultiAdapter(adapter: XMultiAdapter<T>) = also { setAdapter(adapter) }

fun <T : XMultiCallBack> RecyclerView.attachMultiAdapter(array: ArrayList<T> = ArrayList()) = attachMultiAdapter(XMultiAdapter(array))


fun RecyclerView.linearLayoutManager() = also { layoutManager = LinearLayoutManager(this.context) }

fun RecyclerView.gridLayoutManager(spanCount: Int = 2) = also { layoutManager = GridLayoutManager(this.context, spanCount) }

fun RecyclerView.staggeredGridLayoutManager(spanCount: Int, orientation: Int) = also { layoutManager = StaggeredGridLayoutManager(spanCount, orientation) }

fun RecyclerView.horizontalStaggeredGridLayoutManager(spanCount: Int) = also { layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL) }

fun RecyclerView.orientationStaggeredGridLayoutManager(spanCount: Int) = also { layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL) }

fun RecyclerView.checkAdapter(): Boolean = adapter != null && adapter is XAdapter<*>

fun RecyclerView.checkMultiAdapter(): Boolean = adapter != null && adapter is XMultiAdapter<*>

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

fun RecyclerView.customRefreshView(view: XRefreshView) = also {
    if (checkAdapter()) {
        adapter<Any>().customRefreshView(view)
    }
}

fun RecyclerView.customLoadMoreView(view: XLoadMoreView) = also {
    if (checkAdapter()) {
        adapter<Any>().customLoadMoreView(view)
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

fun RecyclerView.setFooterListener(action: (view: View, adapter: XAdapter<*>) -> Unit) = also {
    if (checkAdapter()) {
        adapter<Any>().setFooterListener(action)
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

fun <T> RecyclerView.previousItem(position: Int): T = adapter<T>().previousItem(position)

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

fun RecyclerView.removeAllNotItemView() {
    if (checkAdapter()) {
        adapter<Any>().removeAllNotItemView()
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