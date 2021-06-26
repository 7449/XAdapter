@file:Suppress("UNCHECKED_CAST")

package rv.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import rv.adapter.core.XAdapter
import rv.adapter.layout.LayoutStatus
import rv.adapter.layout.XRefreshStatus
import rv.adapter.layout.simple.SimpleRefreshView
import rv.adapter.material.AppBarStateChangeListener
import rv.adapter.view.holder.XViewHolder

fun RecyclerView.checkAdapter(): Boolean = adapter != null && adapter is XAdapter<*>

fun <T> XAdapter<T>.supportAppbar(appBarLayout: AppBarLayout) = also {
    val listener = AppBarStateChangeListener()
    appBarLayout.addOnOffsetChangedListener(listener)
    setAppbarListener { listener.currentState == AppBarStateChangeListener.EXPANDED }
}

fun RecyclerView.setItemLayoutId(layoutId: Int) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setItemLayoutId(layoutId)
    }
}

fun <T> RecyclerView.setOnBind(action: (holder: XViewHolder, position: Int, entity: T) -> Unit) =
    also {
        if (checkAdapter()) {
            xAdapter<T>().bindItem(action)
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

fun RecyclerView.setAppbarListener(action: () -> Boolean) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setAppbarListener(action)
    }
}

fun RecyclerView.setRefreshListener(action: (adapter: XAdapter<*>) -> Unit) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setRefreshListener(action)
    }
}

fun RecyclerView.setLoadMoreListener(action: (adapter: XAdapter<*>) -> Unit) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setLoadingMoreListener(action)
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

fun RecyclerView.setRefreshStatus(status: LayoutStatus) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setRefreshStatus(status)
    }
}

fun RecyclerView.setLoadMoreStatus(status: LayoutStatus) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setLoadingMoreStatus(status)
    }
}

fun RecyclerView.setScrollLoadMoreItemCount(count: Int) = also {
    if (checkAdapter()) {
        xAdapter<Any>().setScrollLoadMoreItemCount(count)
    }
}

fun RecyclerView.onRefreshSuccess() = also {
    if (checkAdapter()) {
        xAdapter<Any>().setRefreshStatus(LayoutStatus.SUCCESS)
    }
}

fun RecyclerView.onRefreshError() = also {
    if (checkAdapter()) {
        xAdapter<Any>().setRefreshStatus(LayoutStatus.ERROR)
    }
}

fun RecyclerView.onLoadingMoreSuccess() = also {
    if (checkAdapter()) {
        xAdapter<Any>().setLoadingMoreStatus(LayoutStatus.SUCCESS)
    }
}

fun RecyclerView.onLoadingMoreError() = also {
    if (checkAdapter()) {
        xAdapter<Any>().setLoadingMoreStatus(LayoutStatus.ERROR)
    }
}

fun RecyclerView.onLoadingMoreLoad() = also {
    if (checkAdapter()) {
        xAdapter<Any>().setLoadingMoreStatus(LayoutStatus.LOAD)
    }
}

fun RecyclerView.onLoadingMoreNoMore() = also {
    if (checkAdapter()) {
        xAdapter<Any>().setLoadingMoreStatus(LayoutStatus.NO_MORE)
    }
}

fun RecyclerView.addHeaderView(view: View) = also {
    if (checkAdapter()) {
        xAdapter<Any>().addHeaderView(view)
    }
}

fun RecyclerView.getHeaderView(position: Int) = xAdapter<Any>().getHeaderView(position)

fun RecyclerView.addFooterView(view: View) = also {
    if (checkAdapter()) {
        xAdapter<Any>().addFooterView(view)
    }
}

fun RecyclerView.getFooterView(position: Int) = xAdapter<Any>().getFooterView(position)

fun <T> RecyclerView.getItem(position: Int): T = xAdapter<T>().getItem(position)

fun <T> RecyclerView.getLast(): T = xAdapter<T>().getLast()

fun <T> RecyclerView.getFirst(): T = xAdapter<T>().getFirst()

fun RecyclerView.addAll(data: List<Any>, isNotify: Boolean = true) {
    if (checkAdapter()) {
        xAdapter<Any>().addAll(data, isNotify)
    }
}

fun RecyclerView.add(data: Any, isNotify: Boolean = true) {
    if (checkAdapter()) {
        xAdapter<Any>().add(data, isNotify)
    }
}

fun RecyclerView.removeAll(isNotify: Boolean = true) {
    if (checkAdapter()) {
        xAdapter<Any>().removeAll(isNotify)
    }
}

fun RecyclerView.remove(position: Int, isNotify: Boolean = true) {
    if (checkAdapter()) {
        xAdapter<Any>().remove(position, isNotify)
    }
}

fun RecyclerView.autoRefresh(
    refreshView: XRefreshStatus = SimpleRefreshView(context)
) {
    if (checkAdapter()) {
        xAdapter<Any>().autoRefresh(this, refreshView)
    }
}