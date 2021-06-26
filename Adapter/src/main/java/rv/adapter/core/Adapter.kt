package rv.adapter.core

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import rv.adapter.layout.LayoutStatus
import rv.adapter.layout.XLoadMoreStatus
import rv.adapter.layout.XRefreshStatus
import rv.adapter.layout.simple.SimpleLoadMoreView
import rv.adapter.layout.simple.SimpleRefreshView
import rv.adapter.view.holder.XViewHolder

@SuppressLint("ClickableViewAccessibility")
interface Adapter<T> {

    /**
     * [XAdapter]
     */
    val adapter: XAdapter<T>

    /**
     * 获取目前数据
     */
    val items: MutableList<T>

    /**
     * 获取HeaderView
     */
    val headerViews: ArrayList<View>

    /**
     * HeaderView ViewType
     */
    val headerTypes: ArrayList<Int>

    /**
     * 获取FooterView
     */
    val footerViews: ArrayList<View>

    /**
     * FooterView ViewType
     */
    val footerTypes: ArrayList<Int>

    /**
     * 是否开启了下拉刷新
     */
    val isPullRefresh: Boolean

    /**
     * 是否正在刷新
     */
    val isRefresh: Boolean

    /**
     * 当前刷新状态
     */
    val refreshStatus: LayoutStatus

    /**
     * 是否开启了上拉加载
     */
    val isLoadingMore: Boolean

    /**
     * 是否正在加载
     */
    val isLoading: Boolean

    /**
     * 当前加载状态
     */
    val loadingMoreStatus: LayoutStatus

    /**
     * 默认的ViewType
     */
    val adapterViewType: Int
        get() = 100000

    /**
     * 总的Items.size
     */
    fun finalItemCount(): Int {
        if (footerViews.isEmpty() && headerViews.isEmpty() && items.isEmpty()) {
            // 2 refresh + empty ; 1 empty
            return if (isPullRefresh) 2 else 1
        }
        return items.size + if ((isLoadingMore && items.isNotEmpty()) && isPullRefresh) {
            // load and refresh
            2
        } else if ((isLoadingMore && items.isNotEmpty()) || isPullRefresh) {
            // refresh
            1
        } else {
            // nothing
            0
        } + footerViews.size + headerViews.size
    }

    /**
     * 是否为ItemPosition
     * false 不是
     * true 是
     */
    fun isItemViewType(position: Int): Boolean {
        return finalItemViewType(position) == ItemTypes.ITEM.type
    }

    /**
     * 获取ViewType
     */
    fun finalItemViewType(position: Int): Int {
        var mPos = position
        if (isRefreshViewType(mPos)) {
            return ItemTypes.REFRESH.type
        }
        if (isLoadingMoreViewType(mPos)) {
            return ItemTypes.LOAD_MORE.type
        }
        if (isPullRefresh) {
            mPos -= 1
        }
        if (isHeaderViewType(mPos)) {
            val headerType = mPos * adapterViewType
            if (!headerTypes.contains(headerType)) {
                headerTypes.add(headerType)
            }
            return headerType
        }
        if (isFooterViewType(mPos)) {
            val footerType = mPos * adapterViewType
            if (!footerTypes.contains(footerType)) {
                footerTypes.add(footerType)
            }
            return footerType
        }
        if (isEmptyViewType(mPos)) {
            return ItemTypes.EMPTY.type
        }
        return ItemTypes.ITEM.type
    }

    /**
     * 获取当前Item的position
     */
    fun currentPosition(position: Int): Int {
        var mPos = position
        if (isPullRefresh) {
            mPos -= 1
        }
        return mPos - headerViews.size
    }

    /**
     * 是否为头部刷新ViewType
     */
    fun isRefreshViewType(position: Int): Boolean {
        return isPullRefresh && position == 0
    }

    /**
     * 是否为尾部刷新ViewType
     */
    fun isLoadingMoreViewType(position: Int): Boolean {
        return isLoadingMore && items.isNotEmpty() && position == adapter.itemCount - 1
    }

    /**
     * 是否为HeaderView ViewType
     */
    fun isHeaderViewType(position: Int): Boolean {
        return headerViews.isNotEmpty() && position < headerViews.size
    }

    /**
     * 是否为FooterView ViewType
     */
    fun isFooterViewType(position: Int): Boolean {
        return footerViews.isNotEmpty() && position >= items.size + headerViews.size
    }

    /**
     * 是否为EmptyView ViewType
     */
    fun isEmptyViewType(position: Int): Boolean {
        return items.isEmpty() && headerViews.isEmpty() && footerViews.isEmpty()
    }

    /**
     * 获取HeaderView ViewHolder
     */
    fun createHeaderViewHolder(viewType: Int): XViewHolder {
        return XViewHolder(headerViews[viewType / adapterViewType])
    }

    /**
     * 获取FooterView ViewHolder
     */
    fun createFooterViewHolder(viewType: Int): XViewHolder {
        return XViewHolder(footerViews[viewType / adapterViewType - items.size - headerViews.size])
    }

    /**
     * 获取Refresh ViewHolder
     */
    fun createRefreshViewHolder(parent: ViewGroup, status: XRefreshStatus): XViewHolder {
        return XViewHolder(status.xRootView)
    }

    /**
     * 获取RefreshView
     */
    fun createRefreshView(parent: ViewGroup): XRefreshStatus {
        return SimpleRefreshView(parent.context)
    }

    /**
     * 获取LoadMoreView
     */
    fun createLoadingMoreView(parent: ViewGroup): XLoadMoreStatus {
        return SimpleLoadMoreView(parent.context)
    }

    /**
     * 获取LoadMore ViewHolder
     */
    fun createLoadingViewHolder(parent: ViewGroup, status: XLoadMoreStatus): XViewHolder {
        return XViewHolder(status.xRootView)
    }

    /**
     * 获取EmptyView ViewHolder
     */
    fun createEmptyViewHolder(view: View?, parent: ViewGroup): XViewHolder {
        return XViewHolder((view ?: FrameLayout(parent.context)))
    }

    /**
     * 获取ViewHolder
     */
    fun createItemViewHolder(
        parent: ViewGroup,
        layoutId: Int,
        click: ((view: View, position: Int, entity: T) -> Unit)?,
        longClick: ((view: View, position: Int, entity: T) -> Boolean)?
    ): XViewHolder {
        return XViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        ).apply {
            setOnClickListener(this, click)
            setOnLongClickListener(this, longClick)
        }
    }

    /**
     * 点击事件
     */
    fun setOnClickListener(
        holder: XViewHolder,
        action: ((view: View, position: Int, entity: T) -> Unit)?
    ): XViewHolder {
        action?.let { listener ->
            holder.itemView.setOnClickListener { view ->
                listener.invoke(
                    view,
                    currentPosition(holder.bindingAdapterPosition),
                    items[currentPosition(holder.bindingAdapterPosition)]
                )
            }
        }
        return holder
    }

    /**
     * 长按事件
     */
    fun setOnLongClickListener(
        holder: XViewHolder,
        action: ((view: View, position: Int, entity: T) -> Boolean)?
    ): XViewHolder {
        action?.let { listener ->
            holder.itemView.setOnLongClickListener { view ->
                listener.invoke(
                    view,
                    currentPosition(holder.bindingAdapterPosition),
                    items[currentPosition(holder.bindingAdapterPosition)]
                )
            }
        }
        return holder
    }

    /**
     * 注册滑动和触摸
     */
    fun registerListener(
        view: RecyclerView,
        onScrollListener: RecyclerView.OnScrollListener,
        onTouchListener: View.OnTouchListener
    ) {
        view.addOnScrollListener(onScrollListener)
        view.setOnTouchListener(onTouchListener)
        attachedToParent(view)
    }

    /**
     * 取消注册滑动和触摸
     */
    fun unregisterListener(
        view: RecyclerView,
        onScrollListener: RecyclerView.OnScrollListener
    ) {
        view.removeOnScrollListener(onScrollListener)
        view.setOnTouchListener(null)
    }

    /**
     * 适配[GridLayoutManager]
     */
    fun attachedToParent(view: RecyclerView) {
        val manager = view.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    if (!isItemViewType(position))
                        manager.spanCount
                    else
                        1
            }
        }
    }

    /**
     * 适配[StaggeredGridLayoutManager]
     */
    fun attachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.layoutParams?.let {
            if (it is StaggeredGridLayoutManager.LayoutParams) {
                it.isFullSpan = !isItemViewType(viewHolder.bindingAdapterPosition)
            }
        }
    }

}