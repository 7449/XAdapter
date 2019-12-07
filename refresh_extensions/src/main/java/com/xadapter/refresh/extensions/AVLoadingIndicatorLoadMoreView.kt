package com.xadapter.refresh.extensions

import android.content.Context
import android.graphics.Color
import com.wang.avi.Indicator
import com.xadapter.refresh.XLoadMoreView
import kotlinx.android.synthetic.main.layout_avloading_load_more.view.*

/**
 * by y on 2017/6/21.
 */
class AVLoadingIndicatorLoadMoreView(context: Context) : XLoadMoreView(context, R.layout.layout_avloading_load_more) {

    var indicator: Indicator? = null
        set(value) {
            value?.let {
                field = it
                avLoadMoreView.indicator = it
            }
        }

    var color: Int? = null
        set(value) {
            value?.let {
                field = it
                avLoadMoreView.setIndicatorColor(it)
            }
        }

    override fun initView() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, dp2px(60))
        avLoadMoreView.indicator = getIndicator(AVType.BALL_BEAT)
        avLoadMoreView.setIndicatorColor(Color.CYAN)
        avLoadMoreTips.text = context.getString(R.string.load_more_init)
    }

    override fun onStart() {
        avLoadMoreView.smoothToShow()
        avLoadMoreTips.text = context.getString(R.string.load_more_start)
    }

    override fun onLoad() {
        avLoadMoreView.smoothToShow()
        avLoadMoreTips.text = context.getString(R.string.load_more_load)
    }

    override fun onNoMore() {
        avLoadMoreView.smoothToHide()
        avLoadMoreTips.text = context.getString(R.string.load_more_no_more)
    }

    override fun onSuccess() {
        avLoadMoreView.smoothToHide()
        avLoadMoreTips.text = context.getString(R.string.load_more_success)
    }

    override fun onError() {
        avLoadMoreView.smoothToHide()
        avLoadMoreTips.text = context.getString(R.string.load_more_error)
    }

    override fun onNormal() {
        avLoadMoreView.smoothToHide()
        avLoadMoreTips.text = context.getString(R.string.load_more_normal)
    }
}