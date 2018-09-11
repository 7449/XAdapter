package com.xadapter.simple

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.listener.OnFooterClickListener
import com.xadapter.listener.OnLoadMoreRetryListener
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
            if (mXAdapterListener != null && loadMoreState != XLoadMoreView.LOAD) {
                loadMoreView?.state = XLoadMoreView.NORMAL
                loadMoreView?.hideHeight(true)
                mXAdapterListener?.onXRefresh()
            }
        }
    }

    override fun refresh() = apply {
        goneView(mEmptyView)
        visibleView(recyclerView)
        mXAdapterListener?.onXRefresh()
        swipeRefreshLayout.isRefreshing = true
        loadMoreView?.state = XLoadMoreView.NORMAL
        loadMoreView?.hideHeight(true)
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
        mXAdapterListener?.onXLoadMore()
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

    fun setOnLoadMoreRetry(loadMoreRetryListener: OnLoadMoreRetryListener) = apply {
        setOnFooterListener(object : OnFooterClickListener {
            override fun onXFooterClick(view: View) {
                if (loadMoreState == XLoadMoreView.ERROR) {
                    loadMoreState = XLoadMoreView.LOAD
                    loadMoreRetryListener.onXLoadMoreRetry()
                }
            }
        })
    }

}