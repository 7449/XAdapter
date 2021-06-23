package rv.adapter.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import rv.adapter.layout.LayoutStatus
import rv.adapter.layout.XLoadMoreStatus
import rv.adapter.layout.XRefreshStatus
import rv.adapter.layout.simple.SimpleRefreshView
import rv.adapter.view.holder.XViewHolder

interface AdapterApi<T> : Adapter<T> {

    fun setItemLayoutId(layoutId: Int): XAdapter<T>
    fun setEmptyView(view: View): XAdapter<T>
    fun setAppbarCallback(action: () -> Boolean): XAdapter<T>
    fun bindItem(action: (holder: XViewHolder, position: Int, entity: T) -> Unit): XAdapter<T>
    fun pullRefresh(switch: Boolean): XAdapter<T>
    fun loadMore(switch: Boolean): XAdapter<T>
    fun setRefreshListener(action: (adapter: XAdapter<T>) -> Unit): XAdapter<T>
    fun setLoadMoreListener(action: (adapter: XAdapter<T>) -> Unit): XAdapter<T>
    fun setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit): XAdapter<T>
    fun setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean): XAdapter<T>
    fun setRefreshStatus(status: LayoutStatus): XAdapter<T>
    fun setRefreshView(view: XRefreshStatus): XAdapter<T>
    fun setLoadMoreStatus(status: LayoutStatus): XAdapter<T>
    fun setLoadMoreView(view: XLoadMoreStatus): XAdapter<T>
    fun setScrollLoadMoreItemCount(count: Int): XAdapter<T>

    fun onRefresh()
    fun onLoadMore()

    fun openPullRefresh() = pullRefresh(true)
    fun openLoadingMore() = loadMore(true)
    fun addHeaderView(view: View) = apply { headerItems.add(view) }
    fun getHeaderView(position: Int): View = headerItems[position]
    fun addFooterView(view: View) = apply { footerItems.add(view) }
    fun getFooterView(position: Int): View = footerItems[position]
    fun getItem(position: Int): T = items[position]

    fun addAll(data: List<T>, isNotify: Boolean = true) = also {
        items.addAll(data)
        if (isNotify) {
            adapter.notifyDataSetChanged()
        }
    }

    fun add(data: T, isNotify: Boolean = true) = also {
        items.add(data)
        if (isNotify) {
            adapter.notifyDataSetChanged()
        }
    }

    fun removeAll(isNotify: Boolean = true) = also {
        items.clear()
        if (isNotify) {
            adapter.notifyDataSetChanged()
        }
    }

    fun remove(position: Int, isNotify: Boolean = true) = also {
        items.removeAt(position)
        if (isNotify) {
            adapter.notifyDataSetChanged()
        }
    }

    fun removeHeader(index: Int) = also {
        headerItems.removeAt(index)
        headerTypes.removeAt(if (index == 0) 0 else index / adapterViewType)
        adapter.notifyDataSetChanged()
    }

    fun removeHeader(view: View) = also {
        val indexOf = headerItems.indexOf(view)
        if (indexOf == -1) return@also
        removeHeader(indexOf)
    }

    fun removeFooter(index: Int) = also {
        footerItems.removeAt(index)
        footerTypes.removeAt(if (index == 0) 0 else index / adapterViewType)
        adapter.notifyDataSetChanged()
    }

    fun removeFooter(view: View) = also {
        val indexOf = footerItems.indexOf(view)
        if (indexOf == -1) return@also
        removeFooter(indexOf)
    }

    fun removeAllNoItemViews() {
        headerTypes.clear()
        footerTypes.clear()
        headerItems.clear()
        footerItems.clear()
        adapter.notifyDataSetChanged()
    }

    fun autoRefresh(
        view: RecyclerView,
        refreshView: XRefreshStatus = SimpleRefreshView(view.context)
    ) = also {
        openPullRefresh()
        setRefreshView(refreshView)
        refreshView.let {
            it.onStartRefresh()
            onRefresh()
        }
    }

}