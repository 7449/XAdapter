package com.xadapter.widget

import android.content.Context
import android.support.annotation.IntDef
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * by y on 2017/6/19.
 */

abstract class XLoadMoreView : FrameLayout {
    protected lateinit var loadMoreView: View

    var state: Int = NORMAL
        set(@XLoadMoreView.LoadMoreState state) {
            if (state == field) {
                return
            }
            onStart()
            hideHeight(false)
            when (state) {
                LOAD -> onLoad()
                NOMORE -> onNoMore()
                SUCCESS -> onSuccess()
                ERROR -> onError()
                NORMAL -> onNormal()
            }
            field = state
        }

    constructor(context: Context) : super(context) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        loadMoreView = View.inflate(context, getLayoutId(), null)
        addView(loadMoreView)
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        initView()
    }

    fun hideHeight(hide: Boolean) {
        val layoutParams = layoutParams
        layoutParams.height = if (hide) 1 else FrameLayout.LayoutParams.WRAP_CONTENT
        layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        setLayoutParams(layoutParams)
        visibility = if (hide) View.GONE else View.VISIBLE
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected abstract fun onStart()

    protected abstract fun onLoad()

    protected abstract fun onNoMore()

    protected abstract fun onSuccess()

    protected abstract fun onError()

    protected abstract fun onNormal()

    @IntDef(LOAD, NORMAL, SUCCESS, NOMORE, ERROR)
    @Retention(AnnotationRetention.SOURCE)
    annotation class LoadMoreState

    companion object {
        const val NORMAL = -1
        const val LOAD = 0
        const val SUCCESS = 1
        const val NOMORE = 2
        const val ERROR = 3
    }

}
