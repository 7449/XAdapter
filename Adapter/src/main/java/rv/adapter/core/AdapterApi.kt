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
    fun bindItem(action: (holder: XViewHolder, position: Int, entity: T) -> Unit): XAdapter<T>
    fun pullRefresh(switch: Boolean): XAdapter<T>
    fun loadMore(switch: Boolean): XAdapter<T>
    fun setAppbarListener(action: () -> Boolean): XAdapter<T>
    fun setRefreshListener(action: (adapter: XAdapter<T>) -> Unit): XAdapter<T>
    fun setLoadingMoreListener(action: (adapter: XAdapter<T>) -> Unit): XAdapter<T>
    fun setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit): XAdapter<T>
    fun setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean): XAdapter<T>
    fun setRefreshStatus(status: LayoutStatus): XAdapter<T>
    fun setRefreshView(view: XRefreshStatus): XAdapter<T>
    fun setLoadingMoreStatus(status: LayoutStatus): XAdapter<T>
    fun setLoadingMoreView(view: XLoadMoreStatus): XAdapter<T>
    fun setScrollLoadMoreItemCount(count: Int): XAdapter<T>

    fun onRefresh()
    fun onLoadingMore()

    fun onRefreshSuccess() {
        setRefreshStatus(LayoutStatus.SUCCESS)
    }

    fun onRefreshError() {
        setRefreshStatus(LayoutStatus.ERROR)
    }

    fun onLoadingMoreSuccess() {
        setLoadingMoreStatus(LayoutStatus.SUCCESS)
    }

    fun onLoadingMoreError() {
        setLoadingMoreStatus(LayoutStatus.ERROR)
    }

    fun onLoadingMoreLoad() {
        setLoadingMoreStatus(LayoutStatus.LOAD)
    }

    fun onLoadingMoreNoMore() {
        setLoadingMoreStatus(LayoutStatus.NO_MORE)
    }

    fun openPullRefresh() = pullRefresh(true)
    fun openLoadingMore() = loadMore(true)
    fun addHeaderView(view: View) = apply { headerViews.add(view) }
    fun getHeaderView(position: Int): View = headerViews[position]
    fun addFooterView(view: View) = apply { footerViews.add(view) }
    fun getFooterView(position: Int): View = footerViews[position]
    fun getItem(position: Int): T = items[position]
    fun getLast(): T = items.last()
    fun getFirst(): T = items.first()

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
        headerViews.removeAt(index)
        headerTypes.removeAt(if (index == 0) 0 else index / adapterViewType)
        adapter.notifyDataSetChanged()
    }

    fun removeHeader(view: View) = also {
        val indexOf = headerViews.indexOf(view)
        if (indexOf == -1) return@also
        removeHeader(indexOf)
    }

    fun removeFooter(index: Int) = also {
        footerViews.removeAt(index)
        footerTypes.removeAt(if (index == 0) 0 else index / adapterViewType)
        adapter.notifyDataSetChanged()
    }

    fun removeFooter(view: View) = also {
        val indexOf = footerViews.indexOf(view)
        if (indexOf == -1) return@also
        removeFooter(indexOf)
    }

    fun removeAllNoItemViews() {
        headerTypes.clear()
        footerTypes.clear()
        headerViews.clear()
        footerViews.clear()
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