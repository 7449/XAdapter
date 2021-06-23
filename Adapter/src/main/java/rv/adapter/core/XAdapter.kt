@file:Suppress("MemberVisibilityCanBePrivate", "unused", "ClickableViewAccessibility")

package rv.adapter.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rv.adapter.core.listener.XScrollListener
import rv.adapter.core.listener.XTouchListener
import rv.adapter.layout.LayoutStatus
import rv.adapter.layout.XLoadMoreStatus
import rv.adapter.layout.XRefreshStatus
import rv.adapter.view.holder.XViewHolder

/**
 * by y on 2016/11/15
 */
open class XAdapter<T> : RecyclerView.Adapter<XViewHolder>(), AdapterAchieve<T> {

    protected open val dataContainer: MutableList<T> = ArrayList()
    private val headerViewContainer = arrayListOf<View>()
    private val footerViewContainer = arrayListOf<View>()
    private val headerViewType = arrayListOf<Int>()
    private val footerViewType = arrayListOf<Int>()

    override val adapter: XAdapter<T>
        get() = this

    override val items: MutableList<T>
        get() = dataContainer

    override val layoutId: Int
        get() = itemLayoutId

    override val headerItems: ArrayList<View>
        get() = headerViewContainer

    override val headerTypes: ArrayList<Int>
        get() = headerViewType

    override val footerItems: ArrayList<View>
        get() = footerViewContainer

    override val footerTypes: ArrayList<Int>
        get() = footerViewType

    override val isPull: Boolean
        get() = pullRefreshEnabled

    override val isRefresh
        get() = xRefreshStatus?.isRefresh ?: false

    override val refreshStatus: LayoutStatus
        get() = xRefreshStatus?.status ?: LayoutStatus.NORMAL

    override val isLoadMore: Boolean
        get() = loadingMoreEnabled

    override val isLoad
        get() = xLoadMoreStatus?.isLoad ?: false

    override val loadMoreStatus: LayoutStatus
        get() = xLoadMoreStatus?.status ?: LayoutStatus.NORMAL

    private val onScrollListener = XScrollListener { onLoadMore() }
    private val onTouchListener =
        XTouchListener({ xRefreshStatus }, { appbarCallback.invoke() }, { isLoad }) { onRefresh() }

    internal var itemLayoutId = View.NO_ID
    internal var emptyView: View? = null
    internal var appbarCallback: (() -> Boolean) = { true }
    internal var bindListener: ((holder: XViewHolder, position: Int, entity: T) -> Unit)? = null
    internal var pullRefreshEnabled = false
    internal var loadingMoreEnabled = false
    internal var refreshListener: ((adapter: XAdapter<T>) -> Unit)? = null
    internal var loadMoreListener: ((adapter: XAdapter<T>) -> Unit)? = null
    internal var clickListener: ((view: View, position: Int, entity: T) -> Unit)? = null
    internal var longClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null
    internal var xRefreshStatus: XRefreshStatus? = null
    internal var xLoadMoreStatus: XLoadMoreStatus? = null
    internal var scrollLoadMoreItemCount = 1
        set(value) {
            field = value
            onScrollListener.updateScrollItemCount(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
        if (headerTypes.contains(viewType)) {
            return createHeaderViewHolder(viewType)
        }
        if (footerTypes.contains(viewType)) {
            return createFooterViewHolder(viewType)
        }
        if (viewType == ItemTypes.REFRESH.type && xRefreshStatus == null) {
            xRefreshStatus = createRefreshView(parent)
        }
        if (viewType == ItemTypes.LOAD_MORE.type && xLoadMoreStatus == null) {
            xLoadMoreStatus = createLoadMoreView(parent)
        }
        return when (viewType) {
            ItemTypes.REFRESH.type -> createRefreshViewHolder(parent, checkNotNull(xRefreshStatus))
            ItemTypes.LOAD_MORE.type -> createLoadViewHolder(parent, checkNotNull(xLoadMoreStatus))
            ItemTypes.EMPTY.type -> createEmptyViewHolder(emptyView, parent)
            else -> createItemViewHolder(parent, layoutId, clickListener, longClickListener)
        }
    }

    override fun onBindViewHolder(holder: XViewHolder, position: Int) {
        if (!isItem(position)) {
            return
        }
        val pos = currentPosition(position)
        bindListener?.invoke(holder, pos, items[pos])
    }

    override fun getItemViewType(position: Int) = itemViewType(position)

    override fun getItemCount() = adapterItemCount()

    override fun onAttachedToRecyclerView(view: RecyclerView) {
        registerListener(view, onScrollListener, onTouchListener)
    }

    override fun onDetachedFromRecyclerView(view: RecyclerView) {
        unregisterListener(view, onScrollListener)
    }

    override fun onViewAttachedToWindow(holder: XViewHolder) = attachedToWindow(holder)

}