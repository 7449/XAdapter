@file:Suppress("MemberVisibilityCanBePrivate", "unused", "ClickableViewAccessibility")

package rv.adapter.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import rv.adapter.core.listener.XScrollListener
import rv.adapter.core.listener.XTouchListener
import rv.adapter.layout.LayoutStatus
import rv.adapter.layout.XLoadMoreStatus
import rv.adapter.layout.XRefreshStatus
import rv.adapter.layout.simple.SimpleLoadMoreView
import rv.adapter.layout.simple.SimpleRefreshView
import rv.adapter.view.holder.XViewHolder

/**
 * by y on 2016/11/15
 */
open class XAdapter<T> : RecyclerView.Adapter<XViewHolder>() {

    private val adapterViewType = 100000
    private val headerViewContainer = ArrayList<View>()
    private val footerViewContainer = ArrayList<View>()
    private val headerViewType = ArrayList<Int>()
    private val footerViewType = ArrayList<Int>()
    private val onScrollListener = XScrollListener { onScrollBottom() }
    private val onTouchListener by lazy {
        XTouchListener(requireNotNull(xRefreshStatus), appbarCallback, { isLoad }) { onRefresh() }
    }
    protected open val dataContainer: MutableList<T> = ArrayList()

    private var emptyView: View? = null
    private var itemLayoutId = View.NO_ID
    private var clickListener: ((view: View, position: Int, entity: T) -> Unit)? = null
    private var longClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null
    private var refreshListener: ((adapter: XAdapter<T>) -> Unit)? = null
    private var xRefreshStatus: XRefreshStatus? = null
    private var loadMoreListener: ((adapter: XAdapter<T>) -> Unit)? = null
    private var xLoadMoreStatus: XLoadMoreStatus? = null
    private var bindListener: ((holder: XViewHolder, position: Int, entity: T) -> Unit)? = null
    private var appbarCallback: (() -> Boolean) = { true }
    private var pullRefreshEnabled = false
    private var loadingMoreEnabled = false
    private var scrollLoadMoreItemCount = 1
        set(value) {
            field = value
            onScrollListener.scrollItemCount = value
        }

    protected val isLoad get() = xLoadMoreStatus?.isLoad ?: false
    protected val isRefresh get() = xRefreshStatus?.isRefresh ?: false
    protected val layoutId get() = itemLayoutId

    val loadMoreStatus: LayoutStatus
        get() = xLoadMoreStatus?.status ?: LayoutStatus.NORMAL

    val refreshStatus: LayoutStatus
        get() = xRefreshStatus?.status ?: LayoutStatus.NORMAL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (headerViewType.contains(viewType)) {
            return XViewHolder(headerViewContainer[viewType / adapterViewType])
        }
        if (footerViewType.contains(viewType)) {
            return XViewHolder(footerViewContainer[viewType / adapterViewType - dataContainer.size - headerViewContainer.size])
        }
        return when (viewType) {
            ItemTypes.REFRESH.type -> {
                if (xRefreshStatus == null) {
                    xRefreshStatus = SimpleRefreshView(parent.context)
                    (parent as RecyclerView).setOnTouchListener(onTouchListener)
                }
                XViewHolder(requireNotNull(xRefreshStatus).xRootView)
            }
            ItemTypes.LOAD_MORE.type -> {
                if (xLoadMoreStatus == null) {
                    xLoadMoreStatus = SimpleLoadMoreView(parent.context)
                    (parent as RecyclerView).addOnScrollListener(onScrollListener)
                }
                XViewHolder(requireNotNull(xLoadMoreStatus).xRootView)
            }
            ItemTypes.EMPTY.type -> {
                XViewHolder(emptyView ?: FrameLayout(parent.context))
            }
            else -> itemViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (getItemViewType(position) != ItemTypes.ITEM.type) {
            return
        }
        val pos = currentItemPosition(position)
        bindListener?.invoke(holder, pos, dataContainer[pos])
    }

    override fun getItemViewType(position: Int) = itemViewType(position)

    override fun getItemCount() = getAdapterItemCount()

    override fun onAttachedToRecyclerView(view: RecyclerView) = attachedToParent(view)

    override fun onViewAttachedToWindow(holder: XViewHolder) = attachedToWindow(holder)

    open fun itemViewHolder(parent: ViewGroup): XViewHolder {
        return XViewHolder(
            LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
        ).viewHolderClick().viewHolderLongClick()
    }

    open fun onScrollBottom() {
        if (dataContainer.isEmpty() || isRefresh || isLoad) {
            return
        }
        xLoadMoreStatus?.onChanged(LayoutStatus.LOAD)
        loadMoreListener?.invoke(this)
    }

    open fun onRefresh() {
        emptyView?.visibility = View.GONE
        refreshListener?.invoke(this)
    }

    fun addHeaderView(view: View) = apply { headerViewContainer.add(view) }

    fun getHeaderView(position: Int): View = headerViewContainer[position]

    fun addFooterView(view: View) = apply { footerViewContainer.add(view) }

    fun getFooterView(position: Int): View = footerViewContainer[position]

    fun setItemLayoutId(layoutId: Int) = also { itemLayoutId = layoutId }

    fun setEmptyView(emptyView: View) = also { this.emptyView = emptyView }

    fun setScrollLoadMoreItemCount(count: Int) = also { scrollLoadMoreItemCount = count }

    fun setAppbarCallback(action: () -> Boolean) = also { appbarCallback = action }

    fun openPullRefresh() = pullRefresh(true)

    fun openLoadingMore() = loadMore(true)

    fun pullRefresh(switch: Boolean) = also { pullRefreshEnabled = switch }

    fun loadMore(switch: Boolean) = also { loadingMoreEnabled = switch }

    fun setRefreshListener(action: (adapter: XAdapter<T>) -> Unit) =
        also { refreshListener = action }

    fun setRefreshStatus(status: LayoutStatus) = also {
        xRefreshStatus?.onChanged(status)
        if (dataContainer.isEmpty() && headerViewContainer.isEmpty() && footerViewContainer.isEmpty()) {
            emptyView?.visibility = View.VISIBLE
        } else {
            emptyView?.visibility = View.GONE
        }
    }

    fun setLoadMoreListener(action: (adapter: XAdapter<T>) -> Unit) =
        also { loadMoreListener = action }

    fun setLoadMoreStatus(status: LayoutStatus) =
        also { xLoadMoreStatus?.onChanged(status) }

    fun bindItem(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) =
        also { bindListener = action }

    fun setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) =
        also { clickListener = action }

    fun setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) =
        also { longClickListener = action }

    fun getItem(position: Int): T = dataContainer[position]

    fun addAll(data: List<T>, isNotify: Boolean = true) =
        also {
            dataContainer.addAll(data)
            if (isNotify) {
                notifyDataSetChanged()
            }
        }

    fun add(data: T, isNotify: Boolean = true) =
        also {
            dataContainer.add(data)
            if (isNotify) {
                notifyDataSetChanged()
            }
        }

    fun removeAll(isNotify: Boolean = true) =
        also {
            dataContainer.clear()
            if (isNotify) {
                notifyDataSetChanged()
            }
        }

    fun remove(position: Int, isNotify: Boolean = true) =
        also {
            dataContainer.removeAt(position)
            if (isNotify) {
                notifyDataSetChanged()
            }
        }

    fun removeHeader(index: Int) =
        also { headerViewContainer.removeAt(index) }.also { headerViewType.removeAt(if (index == 0) 0 else index / adapterViewType) }
            .notifyDataSetChanged()

    fun removeHeader(view: View) {
        val indexOf = headerViewContainer.indexOf(view)
        if (indexOf == -1) return
        removeHeader(indexOf)
    }

    fun removeFooter(index: Int) =
        also { footerViewContainer.removeAt(index) }.also { footerViewType.removeAt(if (index == 0) 0 else index / adapterViewType) }
            .notifyDataSetChanged()

    fun removeFooter(view: View) {
        val indexOf = footerViewContainer.indexOf(view)
        if (indexOf == -1) return
        removeFooter(indexOf)
    }

    fun removeAllNoItemViews() {
        headerViewType.clear()
        footerViewType.clear()
        headerViewContainer.clear()
        footerViewContainer.clear()
        notifyDataSetChanged()
    }

    fun refresh(view: RecyclerView) = also {
        if (!pullRefreshEnabled) {
            return@also
        }
        if (xRefreshStatus == null) {
            xRefreshStatus = SimpleRefreshView(view.context)
            view.setOnTouchListener(onTouchListener)
        }
        requireNotNull(xRefreshStatus).let {
            it.onStartRefresh()
            onRefresh()
        }
    }

    protected fun XViewHolder.viewHolderClick(): XViewHolder {
        clickListener?.let { onXItemClickListener ->
            itemView.setOnClickListener { view ->
                onXItemClickListener.invoke(
                    view,
                    currentItemPosition(bindingAdapterPosition),
                    dataContainer[currentItemPosition(bindingAdapterPosition)]
                )
            }
        }
        return this
    }

    protected fun XViewHolder.viewHolderLongClick(): XViewHolder {
        longClickListener?.let { onXItemLongClickListener ->
            itemView.setOnLongClickListener { view ->
                onXItemLongClickListener.invoke(
                    view,
                    currentItemPosition(bindingAdapterPosition),
                    dataContainer[currentItemPosition(bindingAdapterPosition)]
                )
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

    protected fun attachedToParent(recyclerView: RecyclerView) {
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    if (getItemViewType(position) != ItemTypes.ITEM.type) manager.spanCount else 1
            }
        }
    }

    protected fun attachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.layoutParams?.let {
            if (it is StaggeredGridLayoutManager.LayoutParams) {
                it.isFullSpan = getItemViewType(viewHolder.layoutPosition) != ItemTypes.ITEM.type
            }
        }
    }

    protected fun itemViewType(position: Int): Int {

        fun isRefreshHeaderType(position: Int) =
            pullRefreshEnabled && position == 0

        fun isLoadMoreType(position: Int) =
            loadingMoreEnabled && dataContainer.isNotEmpty() && position == itemCount - 1

        fun isHeaderType(position: Int) =
            headerViewContainer.isNotEmpty() && position < headerViewContainer.size

        fun isFooterType(position: Int) =
            footerViewContainer.isNotEmpty() && position >= dataContainer.size + headerViewContainer.size

        fun isEmptyType() =
            dataContainer.isEmpty() && headerViewContainer.isEmpty() && footerViewContainer.isEmpty()

        var mPos = position
        if (isRefreshHeaderType(mPos)) {
            return ItemTypes.REFRESH.type
        }
        if (isLoadMoreType(mPos)) {
            return ItemTypes.LOAD_MORE.type
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
            return ItemTypes.EMPTY.type
        }
        return ItemTypes.ITEM.type
    }

    protected fun getAdapterItemCount(): Int {
        if (footerViewContainer.isEmpty() && headerViewContainer.isEmpty() && dataContainer.isEmpty()) {
            // 2 refresh + empty ; 1 empty
            return if (pullRefreshEnabled) 2 else 1
        }
        return dataContainer.size + if ((loadingMoreEnabled && dataContainer.isNotEmpty()) && pullRefreshEnabled) {
            // load and refresh
            2
        } else if ((loadingMoreEnabled && dataContainer.isNotEmpty()) || pullRefreshEnabled) {
            // refresh
            1
        } else {
            // nothing
            0
        } + footerViewContainer.size + headerViewContainer.size
    }

}