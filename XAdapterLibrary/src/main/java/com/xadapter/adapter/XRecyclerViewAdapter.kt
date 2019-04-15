package com.xadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.currentItemPosition
import com.xadapter.holder.SuperViewHolder
import com.xadapter.holder.XViewHolder
import com.xadapter.holder.XViewHolderClick
import com.xadapter.holder.XViewHolderLongClick
import com.xadapter.manager.XScrollListener
import com.xadapter.manager.XTouchListener
import com.xadapter.simple.SimpleLoadMore
import com.xadapter.simple.SimpleRefresh
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView

/**
 * by y on 2016/11/15
 */
open class XRecyclerViewAdapter<T> : XBaseAdapter<T>() {

    companion object {
        const val TYPE_ITEM = -1
        const val TYPE_REFRESH_HEADER = 0
        const val TYPE_LOAD_MORE_FOOTER = 1
    }

    var itemLayoutId = View.NO_ID

    val headerViewContainer = ArrayList<View>()
    val footerViewContainer = ArrayList<View>()
    open var dataContainer: ArrayList<T> = ArrayList()

    val headerViewType = ArrayList<Int>()
    val footerViewType = ArrayList<Int>()
    val adapterViewType = 100000

    var touchListener: View.OnTouchListener? = null

    var xRefreshListener: (() -> Unit)? = null

    var xLoadMoreListener: (() -> Unit)? = null

    var onXFooterListener: ((view: View) -> Unit)? = null

    var scrollListener: RecyclerView.OnScrollListener? = null

    var recyclerView: RecyclerView? = null

    var refreshView: XRefreshView? = null

    var loadMoreView: XLoadMoreView? = null

    var onXEmptyListener: ((view: View) -> Unit)? = null

    lateinit var onXBindListener: ((holder: XViewHolder, position: Int, entity: T) -> Unit)

    var scrollLoadMoreItemCount = 1
        set(value) {
            field = value
            if (scrollListener is XScrollListener) {
                (scrollListener as XScrollListener).scrollItemCount = value
            }
        }

    var emptyView: View? = null
        set(value) {
            field = value
            field?.setOnClickListener { view -> onXEmptyListener?.invoke(view) }
        }

    var pullRefreshEnabled = false
        set(value) {
            field = value
            if (refreshView == null) {
                recyclerView?.let {
                    refreshView = SimpleRefresh(it.context)
                }
            }
        }

    var loadingMoreEnabled = false
        set(value) {
            field = value
            if (loadMoreView == null) {
                recyclerView?.let {
                    loadMoreView = SimpleLoadMore(it.context)
                }
            }
        }

    var loadMoreState: Int
        get() = loadMoreView?.state ?: XLoadMoreView.NORMAL
        set(value) {
            loadMoreView?.state = value
        }

    var refreshState: Int
        get() = refreshView?.state ?: XRefreshView.NORMAL
        set(value) {
            refreshView?.refreshState(value)
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (headerViewType.contains(viewType)) {
            return SuperViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (footerViewType.contains(viewType)) {
            return SuperViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
        }

        val xViewHolder = SuperViewHolder(LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)).apply { XViewHolderClick(this@XRecyclerViewAdapter).apply { XViewHolderLongClick(this@XRecyclerViewAdapter) } }

        if ((viewType == TYPE_REFRESH_HEADER || viewType == TYPE_LOAD_MORE_FOOTER) && recyclerView == null) {
            throw NullPointerException("detect recyclerView is null")
        }

        return when (viewType) {
            TYPE_REFRESH_HEADER -> {
                refreshView?.let {
                    touchListener = XTouchListener(it, loadMoreView) { onRefresh() }
                    recyclerView?.setOnTouchListener(touchListener)
                    XViewHolder(it)
                } ?: throw NullPointerException("detect refreshView is null")
            }
            TYPE_LOAD_MORE_FOOTER -> {
                loadMoreView?.let {
                    it.setOnClickListener { v -> onXFooterListener?.invoke(v) }
                    scrollListener = XScrollListener() { onScrollBottom() }.apply {
                        scrollItemCount = scrollLoadMoreItemCount
                        recyclerView?.addOnScrollListener(this)
                    }
                    XViewHolder(it)
                } ?: throw NullPointerException("detect loadMoreView is null")
            }
            else -> xViewHolder
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != TYPE_ITEM) {
            return
        }
        val pos = currentItemPosition(position)
        val t = dataContainer[pos] ?: return
        onXBindListener(holder, pos, t)
    }

    override fun getItemViewType(position: Int): Int = internalGetItemViewType(position)

    override fun getItemCount(): Int = dataSize() + footerViewContainer.size + headerViewContainer.size

    fun isRefreshHeaderType(position: Int): Boolean = pullRefreshEnabled && position == 0

    fun isHeaderType(position: Int): Boolean = headerViewContainer.size != 0 && position < headerViewContainer.size

    fun isFooterType(position: Int): Boolean = footerViewContainer.size != 0 && position >= dataContainer.size + headerViewContainer.size

    fun isLoadMoreType(position: Int): Boolean = loadingMoreEnabled && !dataContainer.isEmpty() && position == itemCount - 1

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) = internalOnAttachedToRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: XViewHolder) = internalOnViewAttachedToWindow(holder)

    open fun onScrollBottom() {
        if (recyclerView != null && refreshView?.state == XRefreshView.REFRESH) {
            return
        }
        if (loadMoreView?.state == XLoadMoreView.LOAD) {
            return
        }
        loadMoreView?.state = XLoadMoreView.LOAD
        xLoadMoreListener?.invoke()
    }

    open fun onRefresh() {
        loadMoreView?.state = XLoadMoreView.NORMAL
        xRefreshListener?.invoke()
    }
}
