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
     * 布局Id
     */
    val layoutId: Int

    /**
     * 获取HeaderView
     */
    val headerItems: ArrayList<View>

    /**
     * HeaderView ViewType
     */
    val headerTypes: ArrayList<Int>

    /**
     * 获取FooterView
     */
    val footerItems: ArrayList<View>

    /**
     * FooterView ViewType
     */
    val footerTypes: ArrayList<Int>

    /**
     * 是否开启了下拉刷新
     */
    val isPull: Boolean

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
    val isLoadMore: Boolean

    /**
     * 是否正在加载
     */
    val isLoad: Boolean

    /**
     * 当前加载状态
     */
    val loadMoreStatus: LayoutStatus

    /**
     * 默认的ViewType
     */
    val adapterViewType: Int
        get() = 100000

    /**
     * 总的Items.size
     */
    fun adapterItemCount(): Int {
        if (footerItems.isEmpty() && headerItems.isEmpty() && items.isEmpty()) {
            // 2 refresh + empty ; 1 empty
            return if (isPull) 2 else 1
        }
        return items.size + if ((isLoadMore && items.isNotEmpty()) && isPull) {
            // load and refresh
            2
        } else if ((isLoadMore && items.isNotEmpty()) || isPull) {
            // refresh
            1
        } else {
            // nothing
            0
        } + footerItems.size + headerItems.size
    }

    /**
     * 是否为ItemPosition
     * false 不是
     * true 是
     */
    fun isItem(position: Int): Boolean {
        return itemViewType(position) == ItemTypes.ITEM.type
    }

    /**
     * 获取ViewType
     */
    fun itemViewType(position: Int): Int {
        var mPos = position
        if (isRefreshHeaderType(mPos)) {
            return ItemTypes.REFRESH.type
        }
        if (isLoadMoreType(mPos)) {
            return ItemTypes.LOAD_MORE.type
        }
        if (isPull) {
            mPos -= 1
        }
        if (isHeaderType(mPos)) {
            val headerType = mPos * adapterViewType
            if (!headerTypes.contains(headerType)) {
                headerTypes.add(headerType)
            }
            return headerType
        }
        if (isFooterType(mPos)) {
            val footerType = mPos * adapterViewType
            if (!footerTypes.contains(footerType)) {
                footerTypes.add(footerType)
            }
            return footerType
        }
        if (isEmptyType(mPos)) {
            return ItemTypes.EMPTY.type
        }
        return ItemTypes.ITEM.type
    }

    /**
     * 获取当前Item的position
     */
    fun currentPosition(position: Int): Int {
        var mPos = position
        if (isPull) {
            mPos -= 1
        }
        return mPos - headerItems.size
    }

    /**
     * 是否为头部刷新ViewType
     */
    fun isRefreshHeaderType(position: Int): Boolean {
        return isPull && position == 0
    }

    /**
     * 是否为尾部刷新ViewType
     */
    fun isLoadMoreType(position: Int): Boolean {
        return isLoadMore && items.isNotEmpty() && position == adapter.itemCount - 1
    }

    /**
     * 是否为HeaderView ViewType
     */
    fun isHeaderType(position: Int): Boolean {
        return headerItems.isNotEmpty() && position < headerItems.size
    }

    /**
     * 是否为FooterView ViewType
     */
    fun isFooterType(position: Int): Boolean {
        return footerItems.isNotEmpty() && position >= items.size + headerItems.size
    }

    /**
     * 是否为EmptyView ViewType
     */
    fun isEmptyType(position: Int): Boolean {
        return items.isEmpty() && headerItems.isEmpty() && footerItems.isEmpty()
    }

    /**
     * 获取HeaderView ViewHolder
     */
    fun createHeaderViewHolder(viewType: Int): XViewHolder {
        return XViewHolder(headerItems[viewType / adapterViewType])
    }

    /**
     * 获取FooterView ViewHolder
     */
    fun createFooterViewHolder(viewType: Int): XViewHolder {
        return XViewHolder(footerItems[viewType / adapterViewType - items.size - headerItems.size])
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
    fun createLoadMoreView(parent: ViewGroup): XLoadMoreStatus {
        return SimpleLoadMoreView(parent.context)
    }

    /**
     * 获取LoadMore ViewHolder
     */
    fun createLoadViewHolder(parent: ViewGroup, status: XLoadMoreStatus): XViewHolder {
        return XViewHolder(status.xRootView)
    }

    /**
     * 获取EmptyView ViewHolder
     */
    fun createEmptyViewHolder(view: View?, parent: ViewGroup): XViewHolder {
        return XViewHolder(view ?: FrameLayout(parent.context))
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
            clickListener(this, click)
            longClickListener(this, longClick)
        }
    }

    /**
     * 点击事件
     */
    fun clickListener(
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
    fun longClickListener(
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
    @SuppressLint("ClickableViewAccessibility")
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
    @SuppressLint("ClickableViewAccessibility")
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
                    if (adapter.getItemViewType(position) != ItemTypes.ITEM.type)
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
                it.isFullSpan =
                    adapter.getItemViewType(viewHolder.layoutPosition) != ItemTypes.ITEM.type
            }
        }
    }

}