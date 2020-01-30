package com.xadapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.adapter.XAdapter
import com.xadapter.vh.XViewHolder

fun RecyclerView.fixedSize() = also { setHasFixedSize(true) }

fun <T> RecyclerView.setAdapter(dsl: AdapterExtensionsDSL<T>.() -> Unit) = also {
    adapter = AdapterExtensionsDSL<T>().also(dsl).build()
}

class AdapterExtensionsDSL<T> {

    var loadingMore = false
    var pullRefresh = false
    var itemLayoutId = View.NO_ID
    var scrollLoadMoreItemCount = 1
    var headerViews: ArrayList<View> = ArrayList()
    var footerViews: ArrayList<View> = ArrayList()

    lateinit var onBind: (holder: XViewHolder, position: Int, entity: T) -> Unit
    var onItemClickListener: ((view: View, position: Int, entity: T) -> Unit)? = null
    var onItemLongClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null
    var xRefreshListener: ((adapter: XAdapter<T>) -> Unit)? = null
    var xLoadMoreListener: ((adapter: XAdapter<T>) -> Unit)? = null

    fun addHeaderViews(vararg view: View) {
        headerViews.addAll(view)
    }

    fun addFooterViews(vararg view: View) {
        footerViews.addAll(view)
    }

    fun onBind(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) {
        this.onBind = action
    }

    fun onItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) {
        this.onItemClickListener = action
    }

    fun onItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) {
        this.onItemLongClickListener = action
    }

    fun refreshListener(action: (adapter: XAdapter<T>) -> Unit) {
        this.xRefreshListener = action
    }

    fun loadMoreListener(action: (adapter: XAdapter<T>) -> Unit) {
        this.xLoadMoreListener = action
    }

    internal fun build(): RecyclerView.Adapter<XViewHolder> {
        val xAdapter = XAdapter<T>()
        xAdapter.loadingMoreEnabled = loadingMore
        xAdapter.pullRefreshEnabled = pullRefresh
        xAdapter.itemLayoutId = itemLayoutId
        xAdapter.scrollLoadMoreItemCount = scrollLoadMoreItemCount
        headerViews.forEach { xAdapter.addHeaderView(it) }
        footerViews.forEach { xAdapter.addFooterView(it) }
        xAdapter.onXBindListener = onBind
        xAdapter.onXItemClickListener = onItemClickListener
        xAdapter.onXItemLongClickListener = onItemLongClickListener
        xAdapter.xRefreshListener = xRefreshListener
        xAdapter.xLoadMoreListener = xLoadMoreListener
        return xAdapter
    }
}