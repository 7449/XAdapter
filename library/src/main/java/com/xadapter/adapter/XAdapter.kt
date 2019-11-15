package com.xadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.XLoadMoreView
import com.xadapter.XRefreshView
import com.xadapter.holder.currentItemPosition
import com.xadapter.holder.viewHolderClick
import com.xadapter.holder.viewHolderLongClick
import com.xadapter.manager.XScrollListener
import com.xadapter.manager.XTouchListener
import com.xadapter.simple.SimpleLoadMore
import com.xadapter.simple.SimpleRefresh
import com.xadapter.vh.XViewHolder
import com.xadapter.vh.superViewHolder

/**
 * by y on 2016/11/15
 */
open class XAdapter<T> : XBaseAdapter<T>() {

    companion object {
        internal const val TYPE_ITEM = -1
        internal const val TYPE_REFRESH_HEADER = 0
        internal const val TYPE_LOAD_MORE_FOOTER = 1
    }

    internal var recyclerView: RecyclerView? = null
        set(value) {
            if (value == null) {
                return
            }
            field = value
            if (pullRefreshEnabled && refreshView == null) {
                refreshView = SimpleRefresh(value.context)
            }
            if (loadingMoreEnabled && loadMoreView == null) {
                loadMoreView = SimpleLoadMore(value.context)
            }
        }

    internal var scrollListener: XScrollListener? = null
        private set
        get() {
            if (field == null) {
                field = XScrollListener { onScrollBottom() }.apply { scrollItemCount = scrollLoadMoreItemCount }
            }
            return field
        }

    var itemLayoutId = View.NO_ID
    val adapterViewType = 100000
    val headerViewContainer = ArrayList<View>()
    val footerViewContainer = ArrayList<View>()
    val headerViewType = ArrayList<Int>()
    val footerViewType = ArrayList<Int>()

    open var dataContainer: ArrayList<T> = ArrayList()

    var touchListener: View.OnTouchListener? = null
        private set
        get() {
            refreshView?.let {
                if (field == null) {
                    field = XTouchListener(it, loadMoreView) { onRefresh() }
                }
            }
            return field
        }

    var xRefreshListener: ((adapter: XAdapter<T>) -> Unit)? = null

    var xLoadMoreListener: ((adapter: XAdapter<T>) -> Unit)? = null

    var onXFooterListener: ((view: View, adapter: XAdapter<T>) -> Unit)? = null

    var refreshView: XRefreshView? = null

    var loadMoreView: XLoadMoreView? = null

    lateinit var onXBindListener: ((holder: XViewHolder, position: Int, entity: T) -> Unit)

    var scrollLoadMoreItemCount = 1
        set(value) {
            field = value
            scrollListener?.scrollItemCount = value
        }

    var pullRefreshEnabled = false

    var loadingMoreEnabled = false

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
        if (recyclerView == null) {
            recyclerView = parent as RecyclerView
        }
        if (headerViewType.contains(viewType)) {
            return superViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (footerViewType.contains(viewType)) {
            return superViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
        }
        val xViewHolder = superViewHolder(LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)).apply { viewHolderClick(this@XAdapter).viewHolderLongClick(this@XAdapter) }
        return when (viewType) {
            TYPE_REFRESH_HEADER -> {
                refreshView?.let {
                    recyclerView?.setOnTouchListener(touchListener)
                    XViewHolder(it)
                } ?: throw NullPointerException("detect refreshView is null")
            }
            TYPE_LOAD_MORE_FOOTER -> {
                loadMoreView?.let { it ->
                    it.setOnClickListener { v -> onXFooterListener?.invoke(v, this) }
                    scrollListener?.let { recyclerView?.addOnScrollListener(it) }
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

    fun isLoadMoreType(position: Int): Boolean = loadingMoreEnabled && dataContainer.isNotEmpty() && position == itemCount - 1

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
        xLoadMoreListener?.invoke(this)
    }

    open fun onRefresh() {
        loadMoreView?.state = XLoadMoreView.NORMAL
        xRefreshListener?.invoke(this)
    }
}
