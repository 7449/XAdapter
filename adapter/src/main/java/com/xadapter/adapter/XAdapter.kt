@file:Suppress("MemberVisibilityCanBePrivate", "unused")

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
import com.xadapter.refresh.Callback
import com.xadapter.refresh.XLoadMoreView
import com.xadapter.refresh.XRefreshView
import com.xadapter.refresh.simple.SimpleLoadMoreView
import com.xadapter.refresh.simple.SimpleRefreshView
import com.xadapter.vh.XViewHolder

/**
 * by y on 2016/11/15
 */
open class XAdapter<T> : RecyclerView.Adapter<XViewHolder>() {

    companion object {
        const val TYPE_ITEM = -1
        const val TYPE_REFRESH_HEADER = -2
        const val TYPE_LOAD_MORE_FOOTER = -3
        const val TYPE_EMPTY = -4
    }

    var onXItemClickListener: ((view: View, position: Int, entity: T) -> Unit)? = null

    var onXItemLongClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null

    var onEmptyViewClickListener: ((view: View) -> Unit)? = null

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
                        ?: { true }, { if (isLoadMoreViewInit()) loadMoreView.isLoading else false }, refreshView) { onRefresh() }
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
        get() = loadMoreView.currentState
        set(value) {
            loadMoreView.onChange(value)
        }

    var refreshState: Int
        get() = refreshView.currentState
        set(value) {
            refreshView.onChange(value)
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
            return XViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (footerViewType.contains(viewType)) {
            return XViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
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
                || (isRefreshViewInit() && refreshView.isRefresh)
                || loadMoreView.isLoading) {
            return
        }
        loadMoreView.onChange(Callback.LOAD)
        xLoadMoreListener?.invoke(this)
    }

    open fun onRefresh() {
        emptyView?.visibility = View.GONE
        xRefreshListener?.invoke(this)
    }

    fun addHeaderView(view: View) = apply { headerViewContainer.add(view) }

    fun getHeaderView(position: Int): View? = headerViewContainer[position]

    fun addFooterView(view: View) = apply { footerViewContainer.add(view) }

    fun getFooterView(position: Int): View? = footerViewContainer[position]

    fun setItemLayoutId(layoutId: Int) = also { this.itemLayoutId = layoutId }

    fun setEmptyView(emptyView: View) = also { this.emptyView = emptyView }

    fun customRefreshView(view: XRefreshView) = also { this.refreshView = view }

    fun customLoadMoreView(view: XLoadMoreView) = also { this.loadMoreView = view }

    fun customScrollListener(onScrollListener: RecyclerView.OnScrollListener) = also { this.onScrollListener = onScrollListener }

    fun setScrollLoadMoreItemCount(count: Int) = also { this.scrollLoadMoreItemCount = count }

    fun openPullRefresh() = also { this.pullRefreshEnabled = true }

    fun openLoadingMore() = also { this.loadingMoreEnabled = true }

    fun setRefreshListener(action: (adapter: XAdapter<T>) -> Unit) = also { this.xRefreshListener = action }

    fun setRefreshState(status: Int) = also { refreshState = status }

    fun setLoadMoreListener(action: (adapter: XAdapter<T>) -> Unit) = also { this.xLoadMoreListener = action }

    fun setLoadMoreState(status: Int) = also { loadMoreState = status }

    fun setFooterListener(action: (view: View, adapter: XAdapter<T>) -> Unit) = also { this.xFooterListener = action }

    fun setOnBind(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) = also { this.onXBindListener = action }

    fun setOnEmptyViewClickListener(action: (view: View) -> Unit) = also { this.onEmptyViewClickListener = action }

    fun setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) = also { onXItemClickListener = action }

    fun setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) = also { onXItemLongClickListener = action }

    fun getItem(position: Int): T = dataContainer[position]

    fun addAll(data: List<T>) = also { dataContainer.addAll(data) }.notifyDataSetChanged()

    fun add(data: T) = also { dataContainer.add(data) }.notifyDataSetChanged()

    fun removeAll() = also { dataContainer.clear() }.notifyDataSetChanged()

    fun remove(position: Int) = also { dataContainer.removeAt(position) }.notifyDataSetChanged()

    fun previousItem(position: Int): T = if (position == 0) dataContainer[0] else dataContainer[position - 1]

    fun removeHeader(index: Int) = also { headerViewContainer.removeAt(index) }.also { headerViewType.removeAt(if (index == 0) 0 else index / adapterViewType) }.notifyDataSetChanged()

    fun removeHeader(view: View) {
        val indexOf = headerViewContainer.indexOf(view)
        if (indexOf == -1) return
        removeHeader(indexOf)
    }

    fun removeFooter(index: Int) = also { footerViewContainer.removeAt(index) }.also { footerViewType.removeAt(if (index == 0) 0 else index / adapterViewType) }.notifyDataSetChanged()

    fun removeFooter(view: View) {
        val indexOf = footerViewContainer.indexOf(view)
        if (indexOf == -1) return
        removeFooter(indexOf)
    }

    fun removeAllNotItemView() {
        headerViewType.clear()
        footerViewType.clear()
        headerViewContainer.clear()
        footerViewContainer.clear()
        notifyDataSetChanged()
    }

    fun refresh(view: RecyclerView) = also {
        recyclerView = view
        if (pullRefreshEnabled) {
            refreshView.onChange(Callback.REFRESH)
            refreshView.onChangeMoveHeight(refreshView.measuredHeight)
            xRefreshListener?.invoke(this)
            loadMoreView.onChange(Callback.NORMAL)
        }
    }
}
