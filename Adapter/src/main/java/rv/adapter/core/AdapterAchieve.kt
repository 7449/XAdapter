package rv.adapter.core

import android.view.View
import rv.adapter.layout.LayoutStatus
import rv.adapter.layout.XLoadMoreStatus
import rv.adapter.layout.XRefreshStatus
import rv.adapter.view.holder.XViewHolder

interface AdapterAchieve<T> : AdapterApi<T> {

    override fun setItemLayoutId(layoutId: Int): XAdapter<T> {
        return adapter.apply {
            itemLayoutId = layoutId
        }
    }

    override fun setEmptyView(view: View): XAdapter<T> {
        return adapter.apply {
            emptyView = view
        }
    }

    override fun setScrollLoadMoreItemCount(count: Int): XAdapter<T> {
        return adapter.apply {
            scrollLoadMoreItemCount = count
        }
    }

    override fun setAppbarCallback(action: () -> Boolean): XAdapter<T> {
        return adapter.apply {
            appbarCallback = action
        }
    }

    override fun bindItem(action: (holder: XViewHolder, position: Int, entity: T) -> Unit): XAdapter<T> {
        return adapter.apply {
            bindListener = action
        }
    }

    override fun pullRefresh(switch: Boolean): XAdapter<T> {
        return adapter.apply {
            pullRefreshEnabled = switch
        }
    }

    override fun loadMore(switch: Boolean): XAdapter<T> {
        return adapter.apply {
            loadingMoreEnabled = switch
        }
    }

    override fun setRefreshListener(action: (adapter: XAdapter<T>) -> Unit): XAdapter<T> {
        return adapter.apply {
            refreshListener = action
        }
    }

    override fun setLoadMoreListener(action: (adapter: XAdapter<T>) -> Unit): XAdapter<T> {
        return adapter.apply {
            loadMoreListener = action
        }
    }

    override fun setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit): XAdapter<T> {
        return adapter.apply {
            clickListener = action
        }
    }

    override fun setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean): XAdapter<T> {
        return adapter.apply {
            longClickListener = action
        }
    }

    override fun setRefreshView(view: XRefreshStatus): XAdapter<T> {
        return adapter.apply {
            xRefreshStatus = view
        }
    }

    override fun setLoadMoreView(view: XLoadMoreStatus): XAdapter<T> {
        return adapter.apply {
            xLoadMoreStatus = view
        }
    }

    override fun setRefreshStatus(status: LayoutStatus): XAdapter<T> {
        return adapter.apply {
            xRefreshStatus?.onChanged(status)
            if (xRefreshStatus?.isDone == true && loadingMoreEnabled) {
                xLoadMoreStatus?.xRootView?.getChildAt(0)?.visibility = View.VISIBLE
                xLoadMoreStatus?.onChanged(LayoutStatus.NORMAL)
            }
            if (items.isEmpty() && headerItems.isEmpty() && footerItems.isEmpty()) {
                emptyView?.visibility = View.VISIBLE
            } else {
                emptyView?.visibility = View.GONE
            }
        }
    }

    override fun setLoadMoreStatus(status: LayoutStatus): XAdapter<T> {
        return adapter.apply {
            xLoadMoreStatus?.onChanged(status)
        }
    }

    override fun onRefresh() {
        adapter.emptyView?.visibility = View.GONE
        if (isPull) {
            adapter.xLoadMoreStatus?.xRootView?.getChildAt(0)?.visibility = View.GONE
        }
        adapter.refreshListener?.invoke(adapter)
    }

    override fun onLoadMore() {
        if (items.isEmpty() || isRefresh || isLoad) {
            return
        }
        adapter.xLoadMoreStatus?.onChanged(LayoutStatus.LOAD)
        adapter.loadMoreListener?.invoke(adapter)
    }

}