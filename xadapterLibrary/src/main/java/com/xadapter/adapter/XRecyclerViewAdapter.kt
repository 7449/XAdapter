package com.xadapter.adapter

import android.annotation.SuppressLint
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
@SuppressLint("ClickableViewAccessibility")
open class XRecyclerViewAdapter<T> : RecyclerView.Adapter<XViewHolder>(), XScrollListener.XScrollBottom, XTouchListener.RefreshInterface {

    companion object {
        internal const val TYPE_ITEM = -1
        internal const val TYPE_REFRESH_HEADER = 0
        internal const val TYPE_LOAD_MORE_FOOTER = 1
    }

    val headerViewContainer = ArrayList<View>()
    val footerViewContainer = ArrayList<View>()
    private val mHeaderViewType = ArrayList<Int>()
    private val mFooterViewType = ArrayList<Int>()
    private val adapterViewType = 100000

    private var touchListener: XTouchListener? = null
    open var dataContainer: ArrayList<T> = ArrayList()

    open var scrollListener: RecyclerView.OnScrollListener? = null
    open var recyclerView: RecyclerView? = null
    open var refreshView: XRefreshView? = null
    open var loadMoreView: XLoadMoreView? = null

    open var scrollLoadMoreItemCount = 1
        set(value) {
            field = value
            if (scrollListener != null && scrollListener is XScrollListener) {
                (scrollListener as XScrollListener).scrollItemCount = value
            }
        }

    open var emptyView: View? = null
        set(value) {
            field = value
            field?.setOnClickListener { view -> onXEmptyListener?.onXEmptyClick(view) }
        }

    open var onXEmptyListener: OnXEmptyListener? = null

    open lateinit var onXBindListener: OnXBindListener<T>

    open var itemLayoutId = View.NO_ID

    open var pullRefreshEnabled = false
        set(value) {
            field = value
            if (refreshView == null) {
                refreshView = SimpleRefresh(recyclerView!!.context)
            }
        }

    open var loadingMoreEnabled = false
        set(value) {
            field = value
            if (loadMoreView == null) {
                loadMoreView = SimpleLoadMore(recyclerView!!.context)
            }
        }

    open var onItemClickListener: OnItemClickListener<T>? = null

    open var onLongClickListener: OnItemLongClickListener<T>? = null

    open var xAdapterListener: OnXAdapterListener? = null

    open var onFooterListener: OnFooterClickListener? = null

    open var loadMoreState: Int
        get() = loadMoreView!!.state
        set(value) {
            loadMoreView?.state = value
        }

    open var refreshState: Int
        get() = refreshView!!.state
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
            refreshView?.onMove(refreshView!!.measuredHeight.toFloat())
            xAdapterListener?.onXRefresh()
            loadMoreView?.state = XLoadMoreView.NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (mHeaderViewType.contains(viewType)) {
            return XViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (mFooterViewType.contains(viewType)) {
            return XViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
        }
        val xViewHolder = XViewHolder(LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false))
        xViewHolder.itemView.setOnClickListener { view ->
            onItemClickListener?.onItemClick(view,
                    getItemPosition(xViewHolder.layoutPosition),
                    dataContainer[getItemPosition(xViewHolder.layoutPosition)])
        }
        xViewHolder.itemView.setOnLongClickListener { view ->
            onLongClickListener?.onLongClick(view,
                    getItemPosition(xViewHolder.layoutPosition),
                    dataContainer[getItemPosition(xViewHolder.layoutPosition)])
            true
        }
        if ((viewType == TYPE_REFRESH_HEADER || viewType == TYPE_LOAD_MORE_FOOTER) && recyclerView == null) {
            throw NullPointerException("detect recyclerView is null")
        }
        return when (viewType) {
            XRecyclerViewAdapter.TYPE_REFRESH_HEADER -> {
                touchListener = XTouchListener(refreshView!!, loadMoreView, this)
                recyclerView?.setOnTouchListener(touchListener)
                XViewHolder(refreshView!!)
            }
            XRecyclerViewAdapter.TYPE_LOAD_MORE_FOOTER -> {
                loadMoreView?.setOnClickListener { v -> onFooterListener?.onXFooterClick(v) }
                if (scrollListener == null){
                    scrollListener = XScrollListener(this).apply { scrollItemCount = scrollLoadMoreItemCount }
                }
                recyclerView?.addOnScrollListener(scrollListener)
                XViewHolder(loadMoreView!!)
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
            mHeaderViewType.add(mPos * adapterViewType)
            return mPos * adapterViewType
        }
        if (isFooterType(mPos)) {
            mFooterViewType.add(mPos * adapterViewType)
            return mPos * adapterViewType
        }
        return XRecyclerViewAdapter.TYPE_ITEM
    }

    override fun getItemCount(): Int = dataSize + footerViewContainer.size + headerViewContainer.size
    private fun isRefreshHeaderType(position: Int): Boolean = pullRefreshEnabled && position == 0
    private fun isHeaderType(position: Int): Boolean = headerViewContainer.size != 0 && position < headerViewContainer.size
    private fun isFooterType(position: Int): Boolean = footerViewContainer.size != 0 && position >= dataContainer.size + headerViewContainer.size
    private fun isLoadMoreType(position: Int): Boolean = loadingMoreEnabled && !dataContainer.isEmpty() && position == itemCount - 1
    private fun getItemPosition(position: Int): Int {
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
        if (refreshView != null && refreshView?.state == XRefreshView.REFRESH) {
            return
        }
        if (loadMoreView?.state == XLoadMoreView.LOAD) {
            return
        }
        loadMoreView?.state = XLoadMoreView.LOAD
        xAdapterListener?.onXLoadMore()
    }

    override fun onRefresh() {
        loadMoreView?.state = XLoadMoreView.NORMAL
        xAdapterListener?.onXRefresh()
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
        if (recyclerView == null || emptyView == null) {
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
