package rv.adapter.layout

import android.view.ViewGroup

interface XLoadMoreStatus {

    val xRootView: ViewGroup

    var status: LayoutStatus

    val isNormal: Boolean
        get() = status == LayoutStatus.NORMAL

    val isLoad: Boolean
        get() = status == LayoutStatus.LOAD

    val isSuccess: Boolean
        get() = status == LayoutStatus.SUCCESS

    val isError: Boolean
        get() = status == LayoutStatus.ERROR

    val isNoMore: Boolean
        get() = status == LayoutStatus.NO_MORE

    fun onChanged(status: LayoutStatus) {
        if (this.status == status) return
        when (status) {
            LayoutStatus.NORMAL -> onNormal()
            LayoutStatus.LOAD -> onLoad()
            LayoutStatus.SUCCESS -> onSuccess()
            LayoutStatus.ERROR -> onError()
            LayoutStatus.NO_MORE -> onNoMore()
            else -> throw RuntimeException("check status:$status")
        }
        this.status = status
    }

    fun onNormal()

    fun onLoad()

    fun onSuccess()

    fun onError()

    fun onNoMore()

}