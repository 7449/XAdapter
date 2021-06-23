@file:Suppress("UNCHECKED_CAST")

package rv.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rv.adapter.core.XAdapter
import rv.adapter.layout.LayoutStatus
import rv.adapter.multiple.XMultiCallBack
import rv.adapter.view.holder.XViewHolder

fun RecyclerView.addHeaderView(view: View) = also {
    if (checkAdapter()) {
        xAdapter<Any>().addHeaderView(view)
    }
}

fun RecyclerView.addFooterView(view: View) = also {
    if (checkAdapter()) {
        xAdapter<Any>().addFooterView(view)
    }
}

fun RecyclerView.setItemLayoutId(layoutId: Int) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setItemLayoutId(layoutId)
    }
}

fun RecyclerView.setEmptyView(view: View) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setEmptyView(view)
    }
}

fun RecyclerView.setScrollLoadMoreItemCount(count: Int) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setScrollLoadMoreItemCount(count)
    }
}

fun RecyclerView.openPullRefresh() = also {
    if (checkAdapter()) {
        xAdapter<Any>().openPullRefresh()
    }
}

fun RecyclerView.openLoadingMore() = also {
    if (checkAdapter()) {
        xAdapter<Any>().openLoadingMore()
    }
}

fun RecyclerView.setRefreshListener(action: (adapter: XAdapter<*>) -> Unit) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setRefreshListener(action)
    }
}

fun RecyclerView.setRefreshStatus(status: LayoutStatus) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setRefreshStatus(status)
    }
}

fun RecyclerView.setLoadMoreListener(action: (adapter: XAdapter<*>) -> Unit) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setLoadMoreListener(action)
    }
}

fun RecyclerView.setLoadMoreStatus(status: LayoutStatus) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setLoadMoreStatus(status)
    }
}

fun <T> RecyclerView.setOnBind(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) =
    also {
        if (checkAdapter()) {
            xAdapter<T>().bindItem(action)
        }
    }

fun <T> RecyclerView.setOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) =
    also {
        if (checkAdapter()) {
            xAdapter<T>().setOnItemClickListener(action)
        }
    }

fun <T> RecyclerView.setOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) =
    also {
        if (checkAdapter()) {
            xAdapter<T>().setOnItemLongClickListener(action)
        }
    }

fun <T> RecyclerView.getItem(position: Int): T = xAdapter<T>().getItem(position)

fun RecyclerView.getHeaderView(position: Int) = xAdapter<Any>().getHeaderView(position)

fun RecyclerView.getFooterView(position: Int) = xAdapter<Any>().getFooterView(position)

fun RecyclerView.addAll(data: List<Any>) {
    if (checkAdapter()) {
        xAdapter<Any>().addAll(data)
    }
}

fun RecyclerView.add(data: Any) {
    if (checkAdapter()) {
        xAdapter<Any>().add(data)
    }
}

fun RecyclerView.removeAll() {
    if (checkAdapter()) {
        xAdapter<Any>().removeAll()
    }
}

fun RecyclerView.remove(position: Int) {
    if (checkAdapter()) {
        xAdapter<Any>().remove(position)
    }
}

fun RecyclerView.removeHeader(index: Int) {
    if (checkAdapter()) {
        xAdapter<Any>().removeHeader(index)
    }
}

fun RecyclerView.removeHeader(view: View) {
    if (checkAdapter()) {
        xAdapter<Any>().removeHeader(view)
    }
}

fun RecyclerView.removeFooter(index: Int) {
    if (checkAdapter()) {
        xAdapter<Any>().removeFooter(index)
    }
}

fun RecyclerView.removeFooter(view: View) {
    if (checkAdapter()) {
        xAdapter<Any>().removeFooter(view)
    }
}

fun RecyclerView.removeAllNoItemViews() {
    if (checkAdapter()) {
        xAdapter<Any>().removeAllNoItemViews()
    }
}

fun RecyclerView.refresh(view: RecyclerView) {
    if (checkAdapter()) {
        xAdapter<Any>().autoRefresh(view)
    }
}

fun <T : XMultiCallBack> RecyclerView.getMultiItem(position: Int): T =
    multiAdapter<T>().getItem(position)

fun RecyclerView.multiSetItemLayoutId(action: (itemViewType: Int) -> Int) = also {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().setItemLayoutId(action)
    }
}

fun <T : XMultiCallBack> RecyclerView.multiSetBind(action: (holder: XViewHolder, entity: T, itemViewType: Int, position: Int) -> Unit) =
    also {
        if (checkMultiAdapter()) {
            multiAdapter<T>().setMultiBind(action)
        }
    }

fun RecyclerView.multiGridLayoutManagerSpanSize(action: (itemViewType: Int, manager: GridLayoutManager, position: Int) -> Int) =
    also {
        if (checkMultiAdapter()) {
            multiAdapter<XMultiCallBack>().gridLayoutManagerSpanSize(action)
        }
    }

fun RecyclerView.multiStaggeredGridLayoutManagerFullSpan(action: (itemViewType: Int) -> Boolean) =
    also {
        if (checkMultiAdapter()) {
            multiAdapter<XMultiCallBack>().staggeredGridLayoutManagerFullSpan(action)
        }
    }

fun <T : XMultiCallBack> RecyclerView.multiSetOnItemClickListener(action: (view: View, position: Int, entity: T) -> Unit) =
    also {
        if (checkMultiAdapter()) {
            multiAdapter<T>().setOnItemClickListener(action)
        }
    }

fun <T : XMultiCallBack> RecyclerView.multiSetOnItemLongClickListener(action: (view: View, position: Int, entity: T) -> Boolean) =
    also {
        if (checkMultiAdapter()) {
            multiAdapter<T>().setOnItemLongClickListener(action)
        }
    }

fun RecyclerView.multiRemoveAll() {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().removeAll()
    }
}

fun RecyclerView.multiRemove(position: Int) {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().remove(position)
    }
}

fun RecyclerView.multiAddAll(data: List<XMultiCallBack>) {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().addAll(data)
    }
}

fun RecyclerView.multiAdd(data: XMultiCallBack) {
    if (checkMultiAdapter()) {
        multiAdapter<XMultiCallBack>().add(data)
    }
}