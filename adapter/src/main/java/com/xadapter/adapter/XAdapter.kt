@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.xadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xadapter.listener.XScrollListener
import com.xadapter.listener.XTouchListener
import com.xadapter.refresh.Callback
import com.xadapter.refresh.XLoadMoreCallback
import com.xadapter.refresh.XRefreshCallback
import com.xadapter.refresh.simple.SimpleLoadMoreView
import com.xadapter.refresh.simple.SimpleRefreshView
import com.xadapter.vh.XViewHolder

/**
 * by y on 2016/11/15
 */
open class XAdapter<T> : RecyclerView.Adapter<XViewHolder>() {

    companion object {
        const val TYPE_ITEM = -1
        const val TYPE_REFRESH = -2
        const val TYPE_LOAD_MORE = -3
        const val TYPE_EMPTY = -4
    }

    private var onXItemClickListener: ((view: View, position: Int, entity: T) -> Unit)? = null
    private var onXItemLongClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null

    private val adapterViewType = 100000
    private val headerViewContainer = ArrayList<View>()
    private val footerViewContainer = ArrayList<View>()
    private val headerViewType = ArrayList<Int>()
    private val footerViewType = ArrayList<Int>()
    private var emptyView: View? = null

    open var dataContainer: MutableList<T> = ArrayList()
    var itemLayoutId = View.NO_ID
    var xAppbarCallback: (() -> Boolean)? = null

    private var xRefreshListener: ((adapter: XAdapter<T>) -> Unit)? = null
    private var xLoadMoreListener: ((adapter: XAdapter<T>) -> Unit)? = null
    private lateinit var onXBindListener: ((holder: XViewHolder, position: Int, entity: T) -> Unit)

    private var pullRefreshEnabled = false
    private var loadingMoreEnabled = false

    private var xRefreshCallback: XRefreshCallback? = null
    private var xLoadMoreCallback: XLoadMoreCallback? = null

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
                        ?: { true }, {
                    xLoadMoreCallback?.isLoading ?: false
                }, xRefreshCallback.requireAny()) { onRefresh() }
            }
            return field
        }

    var recyclerView: RecyclerView? = null
        @SuppressLint("ClickableViewAccessibility")
        set(value) {
            if (value == null || recyclerView != null) {
                return
            }
            field = value
            if (pullRefreshEnabled) {
                xRefreshCallback = SimpleRefreshView(value.context)
                field?.setOnTouchListener(onTouchListener)
            }
            if (loadingMoreEnabled) {
                xLoadMoreCallback = SimpleLoadMoreView(value.context)
                field?.addOnScrollListener(onScrollListener.requireAny())
            }
        }

    val loadMoreState: Int
        get() = xLoadMoreCallback?.currentState ?: Callback.NULL

    val refreshState: Int
        get() = xRefreshCallback?.currentState ?: Callback.NULL

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
            TYPE_REFRESH -> XViewHolder(xRefreshCallback.requireAny<XRefreshCallback>().xRootView)
            TYPE_LOAD_MORE -> XViewHolder(xLoadMoreCallback.requireAny<XLoadMoreCallback>().xRootView)
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

    override fun getItemViewType(position: Int) = itemViewType(position)

    override fun getItemCount() = getAdapterItemCount()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) = attachedToRecyclerView(recyclerView)

    override fun onViewAttachedToWindow(holder: XViewHolder) = viewAttachedToWindow(holder)

    open fun defaultViewHolder(parent: ViewGroup): XViewHolder {
        return XViewHolder(LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)).viewHolderClick().viewHolderLongClick()
    }

    open fun onScrollBottom() {
        if (dataContainer.isEmpty() || xRefreshCallback?.isRefresh == true || xLoadMoreCallback?.isLoading == true) {
            return
        }
        xLoadMoreCallback?.onChange(Callback.LOAD)
        xLoadMoreListener?.invoke(this)
    }

    open fun onRefresh() {
        emptyView?.visibility = View.GONE
        xRefreshListener?.invoke(this)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> Any?.requireAny(): T = this as T

    fun addHeaderView(view: View) = apply { headerViewContainer.add(view) }

    fun getHeaderView(position: Int): View? = headerViewContainer[position]

    fun requireHeaderView(position: Int): View = getHeaderView(position).requireAny()

    fun addFooterView(view: View) = apply { footerViewContainer.add(view) }

    fun getFooterView(position: Int): View? = footerViewContainer[position]

    fun requireFooterView(position: Int): View = getFooterView(position).requireAny()

    fun setItemLayoutId(layoutId: Int) = also { this.itemLayoutId = layoutId }

    fun setEmptyView(emptyView: View) = also { this.emptyView = emptyView }

    fun customRefreshCallback(callback: XRefreshCallback) = also { this.xRefreshCallback = callback }

    fun customLoadMoreCallback(callback: XLoadMoreCallback) = also { this.xLoadMoreCallback = callback }

    fun customScrollListener(onScrollListener: RecyclerView.OnScrollListener) = also { this.onScrollListener = onScrollListener }

    fun setScrollLoadMoreItemCount(count: Int) = also { this.scrollLoadMoreItemCount = count }

    fun openPullRefresh() = pullRefresh(true)

    fun openLoadingMore() = loadMore(true)

    fun pullRefresh(switch: Boolean) = also { this.pullRefreshEnabled = switch }

    fun loadMore(switch: Boolean) = also { this.loadingMoreEnabled = switch }

    fun setRefreshListener(action: (adapter: XAdapter<T>) -> Unit) = also { this.xRefreshListener = action }

    fun setRefreshState(state: Int) = also {
        xRefreshCallback?.onChange(state)
        if (dataContainer.isEmpty() && headerViewContainer.isEmpty() && footerViewContainer.isEmpty()) {
            emptyView?.visibility = View.VISIBLE
        } else {
            emptyView?.visibility = View.GONE
        }
    }

    fun setLoadMoreListener(action: (adapter: XAdapter<T>) -> Unit) = also { this.xLoadMoreListener = action }

    fun setLoadMoreState(state: Int) = also { xLoadMoreCallback?.onChange(state) }

    fun setOnBind(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) = also { this.onXBindListener = action }

    fun setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) = also { onXItemClickListener = action }

    fun setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) = also { onXItemLongClickListener = action }

    fun getItem(position: Int): T = dataContainer[position]

    fun addAll(data: List<T>) = also { dataContainer.addAll(data) }.notifyDataSetChanged()

    fun add(data: T) = also { dataContainer.add(data) }.notifyDataSetChanged()

    fun removeAll() = also { dataContainer.clear() }.notifyDataSetChanged()

    fun remove(position: Int) = also { dataContainer.removeAt(position) }.notifyDataSetChanged()

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

    fun removeAllNotItemViews() {
        headerViewType.clear()
        footerViewType.clear()
        headerViewContainer.clear()
        footerViewContainer.clear()
        notifyDataSetChanged()
    }

    fun refresh(view: RecyclerView) = also {
        openPullRefresh()
        recyclerView = view
        xRefreshCallback?.let {
            it.onChange(Callback.REFRESH)
            it.onChangeMoveHeight(it.xRootView.measuredHeight)
            xRefreshListener?.invoke(this)
        }
        xLoadMoreCallback?.onChange(Callback.NORMAL)
    }

    protected fun XViewHolder.viewHolderClick(): XViewHolder {
        onXItemClickListener?.let { onXItemClickListener ->
            itemView.setOnClickListener { view ->
                onXItemClickListener.invoke(view, currentItemPosition(layoutPosition), dataContainer[currentItemPosition(layoutPosition)])
            }
        }
        return this
    }

    protected fun XViewHolder.viewHolderLongClick(): XViewHolder {
        onXItemLongClickListener?.let { onXItemLongClickListener ->
            itemView.setOnLongClickListener { view ->
                onXItemLongClickListener.invoke(view, currentItemPosition(layoutPosition), dataContainer[currentItemPosition(layoutPosition)])
            }
        }
        return this
    }

    protected fun currentItemPosition(position: Int): Int {
        var mPos = position
        if (pullRefreshEnabled) {
            mPos -= 1
        }
        return mPos - headerViewContainer.size
    }

    protected fun attachedToRecyclerView(recyclerView: RecyclerView) {
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (getItemViewType(position) != TYPE_ITEM) manager.spanCount else 1
            }
        }
    }

    protected fun viewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.layoutParams?.let {
            if (it is StaggeredGridLayoutManager.LayoutParams) {
                it.isFullSpan = getItemViewType(viewHolder.layoutPosition) != TYPE_ITEM
            }
        }
    }

    protected fun itemViewType(position: Int): Int {

        fun isRefreshHeaderType(position: Int) = pullRefreshEnabled && position == 0
        fun isLoadMoreType(position: Int) = loadingMoreEnabled && dataContainer.isNotEmpty() && position == itemCount - 1
        fun isHeaderType(position: Int) = headerViewContainer.isNotEmpty() && position < headerViewContainer.size
        fun isFooterType(position: Int) = footerViewContainer.isNotEmpty() && position >= dataContainer.size + headerViewContainer.size
        fun isEmptyType() = dataContainer.isEmpty() && headerViewContainer.isEmpty() && footerViewContainer.isEmpty()

        var mPos = position
        if (isRefreshHeaderType(mPos)) {
            return TYPE_REFRESH
        }
        if (isLoadMoreType(mPos)) {
            return TYPE_LOAD_MORE
        }
        if (pullRefreshEnabled) {
            mPos -= 1
        }
        if (isHeaderType(mPos)) {
            val headerType = mPos * adapterViewType
            if (!headerViewType.contains(headerType)) {
                headerViewType.add(headerType)
            }
            return headerType
        }
        if (isFooterType(mPos)) {
            val footerType = mPos * adapterViewType
            if (!footerViewType.contains(footerType)) {
                footerViewType.add(footerType)
            }
            return footerType
        }
        if (isEmptyType()) {
            return TYPE_EMPTY
        }
        return TYPE_ITEM
    }

    protected fun getAdapterItemCount(): Int {
        if (footerViewContainer.isEmpty() && headerViewContainer.isEmpty() && dataContainer.isEmpty()) {
            return if (pullRefreshEnabled) 2 else 1
        }
        return dataContainer.size + if ((loadingMoreEnabled && dataContainer.isNotEmpty()) && pullRefreshEnabled) {
            2
        } else if ((loadingMoreEnabled && dataContainer.isNotEmpty()) || pullRefreshEnabled) {
            1
        } else {
            0
        } + footerViewContainer.size + headerViewContainer.size
    }
}
