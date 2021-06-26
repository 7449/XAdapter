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

    override fun setAppbarListener(action: () -> Boolean): XAdapter<T> {
        return adapter.apply {
            appBarListener = action
        }
    }

    override fun setRefreshListener(action: (adapter: XAdapter<T>) -> Unit): XAdapter<T> {
        return adapter.apply {
            refreshListener = action
        }
    }

    override fun setLoadingMoreListener(action: (adapter: XAdapter<T>) -> Unit): XAdapter<T> {
        return adapter.apply {
            loadingMoreListener = action
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

    override fun setLoadingMoreView(view: XLoadMoreStatus): XAdapter<T> {
        return adapter.apply {
            xLoadMoreStatus = view
        }
    }

    override fun setScrollLoadMoreItemCount(count: Int): XAdapter<T> {
        return adapter.apply {
            scrollLoadMoreItemCount = count
        }
    }

    override fun onRefresh() {
        adapter.xLoadMoreStatus?.xRootView?.visibility = View.GONE
        adapter.refreshListener?.invoke(adapter)
    }

    override fun onLoadingMore() {
        if (items.isEmpty() || isRefresh || isLoading || !isLoadingMore) {
            return
        }
        setLoadingMoreStatus(LayoutStatus.LOAD)
        adapter.loadingMoreListener?.invoke(adapter)
    }

    override fun setRefreshStatus(status: LayoutStatus): XAdapter<T> {
        return adapter.apply {
            xRefreshStatus?.onChanged(status)
            if (xRefreshStatus?.isDone == true && loadingMoreEnabled) {
                xLoadMoreStatus?.xRootView?.visibility = View.VISIBLE
                setLoadingMoreStatus(LayoutStatus.NORMAL)
            }
        }
    }

    override fun setLoadingMoreStatus(status: LayoutStatus): XAdapter<T> {
        return adapter.apply {
            xLoadMoreStatus?.onChanged(status)
        }
    }

}