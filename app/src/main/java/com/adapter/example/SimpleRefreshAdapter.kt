package com.adapter.example

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.adapter.goneView
import com.xadapter.adapter.visibleView
import com.xadapter.widget.XLoadMoreView

/**
 * @author y
 */
class SimpleRefreshAdapter<T>(private val swipeRefreshLayout: SwipeRefreshLayout) : XRecyclerViewAdapter<T>() {

    companion object {
        const val TYPE_STATUS = 0
        const val TYPE_REFRESH = 1
        const val TYPE_LOAD_MORE = 2
    }

    init {
        swipeRefreshLayout.setOnRefreshListener {
            if (loadMoreState != XLoadMoreView.LOAD) {
                loadMoreView?.state = XLoadMoreView.NORMAL
                xRefreshListener?.invoke()
            }
        }
    }

    fun refresh() = apply {
        goneView(emptyView)
        visibleView(recyclerView)
        xRefreshListener?.invoke()
        swipeRefreshLayout.isRefreshing = true
        loadMoreView?.state = XLoadMoreView.NORMAL
    }

    override fun onScrollBottom() {
        if (!loadingMoreEnabled) {
            return
        }
        if (swipeRefreshLayout.isRefreshing) {
            return
        }
        if (loadMoreView?.state == XLoadMoreView.LOAD) {
            return
        }
        loadMoreView?.state = XLoadMoreView.LOAD
        xLoadMoreListener?.invoke()
    }

    fun onComplete(type: Int) {
        if (type == TYPE_REFRESH) {
            swipeRefreshLayout.isRefreshing = false
            loadMoreState = XLoadMoreView.NOMORE
        } else {
            loadMoreState = XLoadMoreView.SUCCESS
        }
    }

    fun onError(type: Int) {
        if (type == TYPE_REFRESH) {
            swipeRefreshLayout.isRefreshing = false
        } else {
            loadMoreState = XLoadMoreView.ERROR
        }
    }

    fun loadNoMore() {
        loadMoreState = XLoadMoreView.NOMORE
    }

    fun setOnLoadMoreRetry(loadMoreRetryListener: () -> Unit) = apply {
        onXFooterListener = {
            if (loadMoreState == XLoadMoreView.ERROR) {
                loadMoreState = XLoadMoreView.LOAD
                loadMoreRetryListener()
            }
        }
    }

}
