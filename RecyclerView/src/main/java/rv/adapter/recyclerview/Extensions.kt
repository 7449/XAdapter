package rv.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import rv.adapter.core.XAdapter
import rv.adapter.view.holder.XViewHolder

fun RecyclerView.fixedSize() = also { setHasFixedSize(true) }

fun <T> RecyclerView.setAdapter(dsl: AdapterExtensionsDSL<T>.() -> Unit) = also {
    adapter = AdapterExtensionsDSL<T>().also(dsl).build()
}

class AdapterExtensionsDSL<T> {

    var loadingMore = false
    var pullRefresh = false
    var itemLayoutId = View.NO_ID
    var scrollLoadMoreItemCount = 1
    val headerViews: ArrayList<View> = ArrayList()
    val footerViews: ArrayList<View> = ArrayList()

    lateinit var onBind: (holder: XViewHolder, position: Int, entity: T) -> Unit
    var onItemClickListener: ((view: View, position: Int, entity: T) -> Unit)? = null
    var onItemLongClickListener: ((view: View, position: Int, entity: T) -> Boolean)? = null
    var xRefreshListener: ((adapter: XAdapter<T>) -> Unit)? = null
    var xLoadMoreListener: ((adapter: XAdapter<T>) -> Unit)? = null

    fun addHeaderViews(vararg view: View) {
        headerViews.addAll(view)
    }

    fun addFooterViews(vararg view: View) {
        footerViews.addAll(view)
    }

    fun onBind(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) {
        this.onBind = action
    }

    fun onItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) {
        this.onItemClickListener = action
    }

    fun onItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) {
        this.onItemLongClickListener = action
    }

    fun refreshListener(action: (adapter: XAdapter<T>) -> Unit) {
        this.xRefreshListener = action
    }

    fun loadMoreListener(action: (adapter: XAdapter<T>) -> Unit) {
        this.xLoadMoreListener = action
    }

    internal fun build(): RecyclerView.Adapter<XViewHolder> {
        val xAdapter = XAdapter<T>()
        xAdapter.loadMore(loadingMore)
        xAdapter.pullRefresh(pullRefresh)
        xAdapter.setItemLayoutId(itemLayoutId)
        xAdapter.setScrollLoadMoreItemCount(scrollLoadMoreItemCount)
        headerViews.forEach { xAdapter.addHeaderView(it) }
        footerViews.forEach { xAdapter.addFooterView(it) }
        xAdapter.bindItem(onBind)
        onItemClickListener?.let { xAdapter.setOnItemClickListener(it) }
        onItemLongClickListener?.let { xAdapter.setOnItemLongClickListener(it) }
        xRefreshListener?.let { xAdapter.setRefreshListener(it) }
        xLoadMoreListener?.let { xAdapter.setLoadingMoreListener(it) }
        return xAdapter
    }
}