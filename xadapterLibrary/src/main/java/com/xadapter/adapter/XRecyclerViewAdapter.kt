package com.xadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.appbar.AppBarLayout
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
@SuppressLint("ClickableViewAccessibility")
open class XRecyclerViewAdapter<T> : RecyclerView.Adapter<XViewHolder>(), XScrollListener.XScrollBottom, XTouchListener.RefreshInterface {

    companion object {
        const val TYPE_ITEM = -1
        const val TYPE_REFRESH_HEADER = 0
        const val TYPE_LOAD_MORE_FOOTER = 1
    }

    val headerViewContainer = ArrayList<View>()
    val footerViewContainer = ArrayList<View>()

    val headerViewType = ArrayList<Int>()
    val footerViewType = ArrayList<Int>()
    val adapterViewType = 100000

    var touchListener: View.OnTouchListener? = null
    open var dataContainer: ArrayList<T> = ArrayList()

    open var scrollListener: RecyclerView.OnScrollListener? = null
    open var recyclerView: RecyclerView? = null

    open var refreshView: XRefreshView? = null
    open var loadMoreView: XLoadMoreView? = null

    open lateinit var onXBindListener: OnXBindListener<T>

    open var scrollLoadMoreItemCount = 1
        set(value) {
            field = value
            if (scrollListener is XScrollListener) {
                (scrollListener as XScrollListener).scrollItemCount = value
            }
        }

    open var emptyView: View? = null
        set(value) {
            field = value
            field?.setOnClickListener { view -> onXEmptyListener?.onXEmptyClick(view) }
        }

    open var onXEmptyListener: OnXEmptyListener? = null

    open var itemLayoutId = View.NO_ID

    open var pullRefreshEnabled = false
        set(value) {
            field = value
            if (refreshView == null) {
                recyclerView?.let {
                    refreshView = SimpleRefresh(it.context)
                }
            }
        }

    open var loadingMoreEnabled = false
        set(value) {
            field = value
            if (loadMoreView == null) {
                recyclerView?.let {
                    loadMoreView = SimpleLoadMore(it.context)
                }
            }
        }

    open var onXItemClickListener: OnXItemClickListener<T>? = null

    open var onXLongClickListener: OnXItemLongClickListener<T>? = null

    open lateinit var xAdapterListener: OnXAdapterListener

    open var onXFooterListener: OnXFooterClickListener? = null

    open var loadMoreState: Int
        get() = loadMoreView?.state ?: XLoadMoreView.NORMAL
        set(value) {
            loadMoreView?.state = value
        }

    open var refreshState: Int
        get() = refreshView?.state ?: XRefreshView.NORMAL
        set(value) {
            refreshView?.refreshState(value)
        }

    open fun addHeaderView(view: View) = apply { headerViewContainer.add(view) }

    open fun addFooterView(view: View) = apply { footerViewContainer.add(view) }

    open fun refresh() = apply {
        if (pullRefreshEnabled) {
            goneView(emptyView)
            visibleView(recyclerView)
            refreshView?.state = XRefreshView.REFRESH
            refreshView?.onMove(refreshView?.measuredHeight?.toFloat() ?: 0F)
            xAdapterListener.onXRefresh()
            loadMoreView?.state = XLoadMoreView.NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (headerViewType.contains(viewType)) {
            return XViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (footerViewType.contains(viewType)) {
            return XViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
        }
        val xViewHolder = XViewHolder(LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false))
        xViewHolder.itemView.setOnClickListener { view ->
            onXItemClickListener?.onXItemClick(view,
                    getItemPosition(xViewHolder.layoutPosition),
                    dataContainer[getItemPosition(xViewHolder.layoutPosition)])
        }
        xViewHolder.itemView.setOnLongClickListener { view ->
            onXLongClickListener?.onXItemLongClick(view,
                    getItemPosition(xViewHolder.layoutPosition),
                    dataContainer[getItemPosition(xViewHolder.layoutPosition)])
            true
        }
        if ((viewType == TYPE_REFRESH_HEADER || viewType == TYPE_LOAD_MORE_FOOTER) && recyclerView == null) {
            throw NullPointerException("detect recyclerView is null")
        }
        return when (viewType) {
            XRecyclerViewAdapter.TYPE_REFRESH_HEADER -> {
                refreshView?.let {
                    touchListener = XTouchListener(it, loadMoreView, this)
                    recyclerView?.setOnTouchListener(touchListener)
                    XViewHolder(it)
                } ?: throw NullPointerException("detect refreshView is null")
            }
            XRecyclerViewAdapter.TYPE_LOAD_MORE_FOOTER -> {
                loadMoreView?.let { it ->
                    loadMoreView?.setOnClickListener { v -> onXFooterListener?.onXFooterClick(v) }
                    scrollListener = XScrollListener(this).apply { scrollItemCount = scrollLoadMoreItemCount }
                    scrollListener?.let { recyclerView?.addOnScrollListener(it) }
                    XViewHolder(it)
                } ?: throw NullPointerException("detect loadMoreView is null")
            }
            else -> xViewHolder
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != XRecyclerViewAdapter.TYPE_ITEM) {
            return
        }
        val pos = getItemPosition(position)
        val t = dataContainer[pos] ?: return
        onXBindListener.onXBind(holder, pos, t)
    }

    open fun addAll(data: List<T>) {
        dataContainer.addAll(data)
        isShowEmptyView()
        notifyDataSetChanged()
    }

    open fun add(data: T) {
        dataContainer.add(data)
        notifyDataSetChanged()
    }

    open fun removeAll() {
        dataContainer.clear()
        notifyDataSetChanged()
    }

    open fun remove(position: Int) {
        dataContainer.removeAt(position)
        notifyDataSetChanged()
    }

    open fun previousItem(position: Int): T {
        return if (position == 0) {
            dataContainer[0]
        } else dataContainer[position - 1]
    }

    open fun removeHeader(index: Int) {
        headerViewContainer.removeAt(index)
        notifyDataSetChanged()
    }

    open fun removeHeader(view: View) {
        headerViewContainer.remove(view)
        notifyDataSetChanged()
    }

    open fun removeFooter(index: Int) {
        footerViewContainer.removeAt(index)
        notifyDataSetChanged()
    }

    open fun removeFooter(view: View) {
        footerViewContainer.remove(view)
        notifyDataSetChanged()
    }

    open fun removeAllNoItemView() {
        headerViewContainer.clear()
        footerViewContainer.clear()
        notifyDataSetChanged()
    }

    open fun getData(position: Int): T = dataContainer[position]

    override fun getItemViewType(position: Int): Int {
        var mPos = position
        if (isRefreshHeaderType(mPos)) {
            return XRecyclerViewAdapter.TYPE_REFRESH_HEADER
        }
        if (isLoadMoreType(mPos)) {
            return XRecyclerViewAdapter.TYPE_LOAD_MORE_FOOTER
        }
        if (pullRefreshEnabled) {
            mPos -= 1
        }
        if (isHeaderType(mPos)) {
            headerViewType.add(mPos * adapterViewType)
            return mPos * adapterViewType
        }
        if (isFooterType(mPos)) {
            footerViewType.add(mPos * adapterViewType)
            return mPos * adapterViewType
        }
        return XRecyclerViewAdapter.TYPE_ITEM
    }

    override fun getItemCount(): Int = dataSize + footerViewContainer.size + headerViewContainer.size
    fun isRefreshHeaderType(position: Int): Boolean = pullRefreshEnabled && position == 0
    fun isHeaderType(position: Int): Boolean = headerViewContainer.size != 0 && position < headerViewContainer.size
    fun isFooterType(position: Int): Boolean = footerViewContainer.size != 0 && position >= dataContainer.size + headerViewContainer.size
    fun isLoadMoreType(position: Int): Boolean = loadingMoreEnabled && !dataContainer.isEmpty() && position == itemCount - 1
    fun getItemPosition(position: Int): Int {
        var mPos = position
        if (pullRefreshEnabled) {
            mPos -= 1
        }
        return mPos - headerViewContainer.size
    }

    private val dataSize: Int
        get() {
            return dataContainer.size + if ((loadingMoreEnabled && !dataContainer.isEmpty()) && pullRefreshEnabled) {
                2
            } else if ((loadingMoreEnabled && !dataContainer.isEmpty()) || pullRefreshEnabled) {
                1
            } else {
                0
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
        var p: ViewParent? = recyclerView?.parent
        while (p != null) {
            if (p is CoordinatorLayout) {
                break
            }
            p = p.parent
        }
        if (p != null) {
            val coordinatorLayout = p as CoordinatorLayout?
            val childCount = coordinatorLayout?.childCount ?: 0
            for (i in childCount - 1 downTo 0) {
                val child = coordinatorLayout?.getChildAt(i)
                if (child is AppBarLayout) {
                    appBarLayout = child
                    break
                }
            }
            if (appBarLayout != null && touchListener is XTouchListener) {
                appBarLayout.addOnOffsetChangedListener(
                        object : AppBarStateChangeListener() {
                            public override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarStateChangeListener.State) {
                                (touchListener as XTouchListener).state = state
                            }
                        })
            }
        }
    }

    override fun onScrollBottom() {
        if (recyclerView != null && refreshView?.state == XRefreshView.REFRESH) {
            return
        }
        if (loadMoreView?.state == XLoadMoreView.LOAD) {
            return
        }
        loadMoreView?.state = XLoadMoreView.LOAD
        xAdapterListener.onXLoadMore()
    }

    override fun onRefresh() {
        loadMoreView?.state = XLoadMoreView.NORMAL
        xAdapterListener.onXRefresh()
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

    private fun isShowEmptyView() {
        if (recyclerView == null) {
            return
        }
        if (dataContainer.isEmpty()) {
            visibleView(emptyView)
            goneView(recyclerView)
        } else {
            visibleView(recyclerView)
            goneView(emptyView)
        }
    }
}
