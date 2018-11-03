package com.xadapter

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntDef

@IntDef(XLoadMoreView.LOAD, XLoadMoreView.NORMAL, XLoadMoreView.SUCCESS, XLoadMoreView.NOMORE, XLoadMoreView.ERROR)
@Retention(AnnotationRetention.SOURCE)
annotation class LoadMoreState

@IntDef(XRefreshView.NORMAL, XRefreshView.READY, XRefreshView.REFRESH, XRefreshView.SUCCESS, XRefreshView.ERROR)
@Retention(AnnotationRetention.SOURCE)
annotation class RefreshState

abstract class XRefreshView : FrameLayout {

    private lateinit var refreshView: View
    private var mMeasuredHeight: Int = 0

    var state: Int = NORMAL
        set(@RefreshState state) {
            if (state == field) {
                return
            }
            onStart()
            when (state) {
                NORMAL -> onNormal()
                READY -> onReady()
                REFRESH -> onRefresh()
                SUCCESS -> onSuccess()
                ERROR -> onError()
            }
            field = state
        }

    var visibleHeight: Int
        get() {
            return refreshView.layoutParams.height
        }
        private set(height) {
            if (height == 0) {
                state = NORMAL
            }
            val lp = refreshView.layoutParams
            lp.height = if (height < 0) 0 else height
            refreshView.layoutParams = lp
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
        refreshView = View.inflate(context, getLayoutId(), null)
        addView(refreshView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 0))
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        initView()
        measure(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        mMeasuredHeight = measuredHeight
    }

    fun refreshState(mState: Int) {
        state = mState
        postDelayed({ smoothScrollTo(0) }, 200)
    }

    fun onMove(delta: Float) {
        if (visibleHeight > 0 || delta > 0) {
            visibleHeight += delta.toInt()
            if (state < REFRESH) {
                state = if (visibleHeight > mMeasuredHeight) {
                    READY
                } else {
                    NORMAL
                }
            }
        }
    }

    fun releaseAction(): Boolean {
        var isOnRefresh = false
        if (visibleHeight > mMeasuredHeight && state < REFRESH) {
            state = REFRESH
            isOnRefresh = true
        }
        var destHeight = 0
        if (state == REFRESH) {
            destHeight = mMeasuredHeight
        }
        smoothScrollTo(destHeight)
        return isOnRefresh
    }

    private fun smoothScrollTo(destHeight: Int) {
        val animator = ValueAnimator.ofInt(visibleHeight, destHeight).setDuration(300)
        animator.addUpdateListener { animation -> visibleHeight = animation.animatedValue as Int }
        animator.start()
    }

    protected abstract fun getLayoutId(): Int
    protected abstract fun initView()
    protected abstract fun onStart()
    protected abstract fun onNormal()
    protected abstract fun onReady()
    protected abstract fun onRefresh()
    protected abstract fun onSuccess()
    protected abstract fun onError()

    companion object {
        const val NORMAL = 0 //初始状态
        const val READY = 1 //准备
        const val REFRESH = 2 //正在刷新
        const val SUCCESS = 3 // 刷新成功
        const val ERROR = 4 // 刷新失败
    }
}

abstract class XLoadMoreView : FrameLayout {
    private lateinit var loadMoreView: View

    var state: Int = NORMAL
        set(@LoadMoreState state) {
            if (state == field) {
                return
            }
            onStart()
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

    protected abstract fun getLayoutId(): Int
    protected abstract fun initView()
    protected abstract fun onStart()
    protected abstract fun onLoad()
    protected abstract fun onNoMore()
    protected abstract fun onSuccess()
    protected abstract fun onError()
    protected abstract fun onNormal()

    companion object {
        const val NORMAL = -1
        const val LOAD = 0
        const val SUCCESS = 1
        const val NOMORE = 2
        const val ERROR = 3
    }
}
