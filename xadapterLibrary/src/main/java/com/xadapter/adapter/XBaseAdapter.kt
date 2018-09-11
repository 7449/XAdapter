package com.xadapter.adapter

import android.annotation.SuppressLint
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewParent
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.*
import com.xadapter.manager.AppBarStateChangeListener
import com.xadapter.manager.XScrollListener
import com.xadapter.manager.XTouchListener
import com.xadapter.simple.SimpleLoadMore
import com.xadapter.simple.SimpleRefresh
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView

/**
 * by y on 2016/11/15
 */
abstract class XBaseAdapter<T> : RecyclerView.Adapter<XViewHolder>(), XScrollListener.XScrollBottom, XTouchListener.RefreshInterface {
    companion object {
        internal const val TYPE_ITEM = -1
        internal const val TYPE_REFRESH_HEADER = 0
        internal const val TYPE_LOAD_MORE_FOOTER = 1
    }

    var scrollLoadMoreItemCount = 1
    val mHeaderViews = ArrayList<View>()
    val mFooterViews = ArrayList<View>()
    val mHeaderViewType = ArrayList<Int>()
    val mFooterViewType = ArrayList<Int>()
    val viewType = 100000
    var mDatas: ArrayList<T> = ArrayList()
    var mEmptyView: View? = null
    lateinit var mOnXBindListener: OnXBindListener<T>
    var itemLayoutId = View.NO_ID
    var recyclerView: RecyclerView? = null
    var pullRefreshEnabled = false
        @SuppressLint("ClickableViewAccessibility")
        set(value) {
            if (recyclerView == null || !value) {
                throw NullPointerException("Detect recyclerView is null, addRecyclerView () if using pull-down refresh or pull-up load" +
                        "or setPullRefreshEnabled(true)")
            }
            field = value
            if (refreshView == null) {
                refreshView = SimpleRefresh(recyclerView!!.context)
            }
            refreshView?.state = XRefreshView.NORMAL
            touchListener = XTouchListener(refreshView!!, loadMoreView, pullRefreshEnabled, this)
            recyclerView?.setOnTouchListener(touchListener)
        }
    var refreshView: XRefreshView? = null
    var loadingMoreEnabled = false
        set(value) {
            if (recyclerView == null || !value) {
                throw NullPointerException("Detect recyclerView is null, addRecyclerView () if using pull-down refresh or pull-up load" +
                        "or setLoadingMoreEnabled(true)")
            }
            field = value
            if (loadMoreView == null) {
                loadMoreView = SimpleLoadMore(recyclerView!!.context)
            }
            loadMoreView?.state = XLoadMoreView.NORMAL
            loadMoreView?.setOnClickListener { v -> mOnFooterListener?.onXFooterClick(v) }
            recyclerView?.addOnScrollListener(XScrollListener(this).apply { scrollItemCount = scrollLoadMoreItemCount })
        }
    var loadMoreView: XLoadMoreView? = null
    var mOnItemClickListener: OnItemClickListener<T>? = null
    var mOnLongClickListener: OnItemLongClickListener<T>? = null
    var mXAdapterListener: OnXAdapterListener? = null
    var mOnFooterListener: OnFooterClickListener? = null

    private var touchListener: XTouchListener? = null

    var loadMoreState: Int
        get() = loadMoreView!!.state
        set(value) {
            loadMoreView?.state = value
        }

    var refreshState: Int
        get() = refreshView!!.state
        set(value) {
            refreshView?.refreshState(value)
        }

    open fun setOnFooterListener(onFooterListener: OnFooterClickListener) = apply { this.mOnFooterListener = onFooterListener }

    open fun setOnXAdapterListener(mLoadingListener: OnXAdapterListener) = apply { this.mXAdapterListener = mLoadingListener }

    open fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener<T>) = apply { this.mOnItemClickListener = mOnItemClickListener }

    open fun setOnLongClickListener(mOnLongClickListener: OnItemLongClickListener<T>) = apply { this.mOnLongClickListener = mOnLongClickListener }

    open fun setOnXBind(onXBindListener: OnXBindListener<T>) = apply { this.mOnXBindListener = onXBindListener }

    open fun setScrollItemCount(count: Int) = apply { this.scrollLoadMoreItemCount = count }

    open fun addHeaderView(view: View) = apply { mHeaderViews.add(view) }

    open fun addFooterView(view: View) = apply { mFooterViews.add(view) }

    open fun refresh() = apply {
        if (pullRefreshEnabled) {
            goneView(mEmptyView)
            visibleView(recyclerView)
            refreshView?.state = XRefreshView.REFRESH
            refreshView?.onMove(refreshView!!.measuredHeight.toFloat())
            mXAdapterListener?.onXRefresh()
            loadMoreView?.state = XLoadMoreView.NORMAL
            loadMoreView?.hideHeight(true)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) != TYPE_ITEM) {
                        manager.spanCount
                    } else {
                        1
                    }
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: XViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = getItemViewType(holder.layoutPosition) != TYPE_ITEM
        }
        if (recyclerView == null) {
            return
        }
        var appBarLayout: AppBarLayout? = null
        var p: ViewParent? = recyclerView!!.parent
        while (p != null) {
            if (p is CoordinatorLayout) {
                break
            }
            p = p.parent
        }
        if (p != null) {
            val coordinatorLayout = p as CoordinatorLayout?
            val childCount = coordinatorLayout!!.childCount
            for (i in childCount - 1 downTo 0) {
                val child = coordinatorLayout.getChildAt(i)
                if (child is AppBarLayout) {
                    appBarLayout = child
                    break
                }
            }
            if (appBarLayout != null && touchListener != null) {
                appBarLayout.addOnOffsetChangedListener(
                        object : AppBarStateChangeListener() {
                            public override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                                touchListener?.state = state
                            }
                        })
            }
        }
    }

    override fun onScrollBottom() {
        if (!loadingMoreEnabled) {
            return
        }
        if (refreshView != null && refreshView?.state == XRefreshView.REFRESH) {
            return
        }
        if (loadMoreView?.state == XLoadMoreView.LOAD) {
            return
        }
        recyclerView?.smoothScrollToPosition(itemCount - 1)
        loadMoreView?.state = XLoadMoreView.LOAD
        mXAdapterListener?.onXLoadMore()
    }

    override fun onRefresh() {
        if (!pullRefreshEnabled) {
            return
        }
        loadMoreView?.state = XLoadMoreView.NORMAL
        loadMoreView?.hideHeight(true)
        mXAdapterListener?.onXRefresh()
    }

    fun goneView(vararg views: View?) {
        for (view in views) {
            if (view != null && view.visibility != View.GONE)
                view.visibility = View.GONE
        }
    }

    fun visibleView(vararg views: View?) {
        for (view in views) {
            if (view != null && view.visibility != View.VISIBLE)
                view.visibility = View.VISIBLE
        }
    }

    protected fun isShowEmptyView() {
        if (recyclerView == null || mEmptyView == null) {
            return
        }
        if (mDatas.isEmpty()) {
            visibleView(mEmptyView)
            goneView(recyclerView)
        } else {
            visibleView(recyclerView)
            goneView(mEmptyView)
        }
    }
}
