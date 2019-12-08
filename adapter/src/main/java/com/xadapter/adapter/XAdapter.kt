package com.xadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.*
import com.xadapter.listener.XScrollListener
import com.xadapter.listener.XTouchListener
import com.xadapter.refresh.XLoadMoreView
import com.xadapter.refresh.XRefreshView
import com.xadapter.refresh.simple.SimpleLoadMoreView
import com.xadapter.refresh.simple.SimpleRefreshView
import com.xadapter.vh.XViewHolder
import com.xadapter.vh.superViewHolder

/**
 * by y on 2016/11/15
 */
open class XAdapter<T> : RecyclerView.Adapter<XViewHolder>() {

    companion object {
        const val TYPE_ITEM = -1
        const val TYPE_REFRESH_HEADER = 0
        const val TYPE_LOAD_MORE_FOOTER = 1
        const val TYPE_EMPTY = 2
    }

    var onXItemClickListener: ((view: View, position: Int, entity: T) -> Unit)? = null

    var onXItemLongClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null

    var itemLayoutId = View.NO_ID
    val adapterViewType = 100000
    val headerViewContainer = ArrayList<View>()
    val footerViewContainer = ArrayList<View>()
    val headerViewType = ArrayList<Int>()
    val footerViewType = ArrayList<Int>()
    var emptyView: View? = null
        set(value) {
            value?.let {
                field = it
                it.setOnClickListener { view -> onEmptyViewClickListener?.invoke(view) }
            }
        }

    open var dataContainer: ArrayList<T> = ArrayList()

    var onEmptyViewClickListener: ((view: View) -> Unit)? = null

    var xAppbarCallback: (() -> Boolean)? = null

    var xRefreshListener: ((adapter: XAdapter<T>) -> Unit)? = null

    var xLoadMoreListener: ((adapter: XAdapter<T>) -> Unit)? = null

    var xFooterListener: ((view: View, adapter: XAdapter<T>) -> Unit)? = null

    lateinit var onXBindListener: ((holder: XViewHolder, position: Int, entity: T) -> Unit)

    var pullRefreshEnabled = false

    var loadingMoreEnabled = false

    lateinit var refreshView: XRefreshView

    lateinit var loadMoreView: XLoadMoreView

    var scrollLoadMoreItemCount = 1
        set(value) {
            field = value
            if (onScrollListener is XScrollListener) {
                (onScrollListener as XScrollListener).scrollItemCount = value
            }
        }

    var onScrollListener: RecyclerView.OnScrollListener? = null
        get() {
            if (field == null) {
                field = XScrollListener { onScrollBottom() }.apply { scrollItemCount = scrollLoadMoreItemCount }
            }
            return field
        }

    private var onTouchListener: View.OnTouchListener? = null
        get() {
            if (field == null) {
                field = XTouchListener(xAppbarCallback
                        ?: { true }, { if (isLoadMoreViewInit()) loadMoreView.state == XLoadMoreView.LOAD else false }, refreshView) { onRefresh() }
            }
            return field
        }

    var recyclerView: RecyclerView? = null
        @SuppressLint("ClickableViewAccessibility")
        set(value) {
            if (value == null) {
                return
            }
            field = value
            if (pullRefreshEnabled) {
                if (!isRefreshViewInit()) {
                    refreshView = SimpleRefreshView(value.context)
                }
                field?.setOnTouchListener(onTouchListener)
            }
            if (loadingMoreEnabled) {
                if (!isLoadMoreViewInit()) {
                    loadMoreView = SimpleLoadMoreView(value.context)
                    loadMoreView.setOnClickListener { xFooterListener?.invoke(it, this) }
                }
                field?.addOnScrollListener(onScrollListener
                        ?: throw KotlinNullPointerException("scrollListener == null"))
            }
        }

    var loadMoreState: Int
        get() = loadMoreView.state
        set(value) {
            loadMoreView.state = value
        }

    var refreshState: Int
        get() = refreshView.state
        set(value) {
            refreshView.refreshState(value)
            if (dataContainer.isEmpty() && headerViewContainer.isEmpty() && footerViewContainer.isEmpty()) {
                emptyView?.visibility = View.VISIBLE
            } else {
                emptyView?.visibility = View.GONE
            }
        }

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
        return when (viewType) {
            TYPE_REFRESH_HEADER -> XViewHolder(refreshView)
            TYPE_LOAD_MORE_FOOTER -> XViewHolder(loadMoreView)
            TYPE_EMPTY -> XViewHolder(emptyView ?: FrameLayout(parent.context))
            else -> defaultViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != TYPE_ITEM) {
            return
        }
        val pos = currentItemPosition(position)
        onXBindListener(holder, pos, dataContainer[pos])
    }

    override fun getItemViewType(position: Int) = internalGetItemViewType(position)

    override fun getItemCount() = getAdapterItemCount()

    fun isRefreshHeaderType(position: Int) = pullRefreshEnabled && position == 0

    fun isHeaderType(position: Int) = headerViewContainer.size != 0 && position < headerViewContainer.size

    fun isFooterType(position: Int) = footerViewContainer.size != 0 && position >= dataContainer.size + headerViewContainer.size

    fun isLoadMoreType(position: Int) = loadingMoreEnabled && dataContainer.isNotEmpty() && position == itemCount - 1

    fun isRefreshViewInit() = ::refreshView.isInitialized

    fun isLoadMoreViewInit() = ::loadMoreView.isInitialized

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) = internalOnAttachedToRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: XViewHolder) = internalOnViewAttachedToWindow(holder)

    open fun defaultViewHolder(parent: ViewGroup): XViewHolder {
        return XViewHolder(LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)).apply { viewHolderClick(this@XAdapter).viewHolderLongClick(this@XAdapter) }
    }

    open fun onScrollBottom() {
        if (dataContainer.isEmpty()
                || (isRefreshViewInit() && refreshView.state == XRefreshView.REFRESH)
                || loadMoreView.state == XLoadMoreView.LOAD) {
            return
        }
        loadMoreView.state = XLoadMoreView.LOAD
        xLoadMoreListener?.invoke(this)
    }

    open fun onRefresh() {
        emptyView?.visibility = View.GONE
        xRefreshListener?.invoke(this)
    }
}
