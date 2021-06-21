package rv.adapter.layout

import android.view.View
import android.view.ViewParent

interface XRefreshStatus {

    val xRootView: View

    var status: LayoutStatus

    val visibleHeight: Int

    val isReleaseAction: Boolean

    val refreshParent: ViewParent? get() = xRootView.parent

    val isNormal: Boolean
        get() = status == LayoutStatus.NORMAL

    val isReady: Boolean
        get() = status == LayoutStatus.READY

    val isRefresh: Boolean
        get() = status == LayoutStatus.REFRESH

    val isSuccess: Boolean
        get() = status == LayoutStatus.SUCCESS

    val isError: Boolean
        get() = status == LayoutStatus.ERROR

    val isDone: Boolean
        get() = isSuccess || isError

    val isBegin: Boolean
        get() = isNormal || isReady

    fun onChanged(status: LayoutStatus) {
        if (this.status == status) return
        when (status) {
            LayoutStatus.NORMAL -> onNormal()
            LayoutStatus.READY -> onReady()
            LayoutStatus.REFRESH -> onRefresh()
            LayoutStatus.SUCCESS -> onSuccess()
            LayoutStatus.ERROR -> onError()
            else -> throw RuntimeException("check status:$status")
        }
        this.status = status
    }

    fun onChangedHeight(height: Int)

    fun onNormal()

    fun onReady()

    fun onRefresh()

    fun onSuccess()

    fun onError()

}