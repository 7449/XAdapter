package com.xadapter.simple

import android.view.View
import com.xadapter.adapter.XRecyclerViewAdapter
import com.xadapter.listener.OnFooterClickListener
import com.xadapter.listener.OnLoadMoreRetryListener
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView

/**
 * by y.
 *
 *
 * 二次封装Adapter
 *
 * 新增了上拉刷新状态下无法再次触发上拉刷新
 * 三个状态： 初始加载,下拉刷新,上拉加载
 * 初始可选择 StatusLayout 或者 refresh()
 * 并且为刷新footerView加了在为error的情况下才响应点击的判断
 *
 * Description:
 */
class SimpleAdapter<T> : XRecyclerViewAdapter<T>() {

    companion object {
        const val TYPE_STATUS = 0
        const val TYPE_REFRESH = 1
        const val TYPE_LOAD_MORE = 2
    }

    override fun onScrollBottom() {
        if (loadMoreState != XLoadMoreView.LOAD) {
            super.onScrollBottom()
        }
    }

    fun onComplete(type: Int) {
        if (type == TYPE_REFRESH) {
            refreshState = XRefreshView.SUCCESS
        } else {
            loadMoreState = XLoadMoreView.SUCCESS
        }
    }

    fun onError(type: Int) {
        if (type == TYPE_REFRESH) {
            refreshState = XRefreshView.ERROR
        } else {
            loadMoreState = XLoadMoreView.ERROR
        }
    }

    fun loadNoMore() {
        loadMoreState = XLoadMoreView.NOMORE
    }

    fun onLoadMoreRetry(loadMoreRetryListener: OnLoadMoreRetryListener) = apply {
        onFooterListener = object : OnFooterClickListener {
            override fun onXFooterClick(view: View) {
                if (loadMoreState == XLoadMoreView.ERROR) {
                    loadMoreState = XLoadMoreView.LOAD
                    loadMoreRetryListener.onXLoadMoreRetry()
                }
            }
        }
    }

}
