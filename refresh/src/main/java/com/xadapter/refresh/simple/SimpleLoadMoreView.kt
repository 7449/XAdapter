package com.xadapter.refresh.simple

import android.content.Context
import android.graphics.Color
import android.view.View
import com.xadapter.refresh.R
import com.xadapter.refresh.XLoadMoreView
import kotlinx.android.synthetic.main.simple_load_more.view.*

/**
 * by y on 2016/9/29
 */
class SimpleLoadMoreView(context: Context) : XLoadMoreView(context, R.layout.simple_load_more) {

    override fun initView() {
        tvTips.setTextColor(Color.BLACK)
        progressbar.visibility = View.GONE
        tvTips.text = context.getString(R.string.load_more_init)
    }

    override fun onStart() {
        progressbar.visibility = View.GONE
        tvTips.text = context.getString(R.string.load_more_start)
    }

    override fun onLoad() {
        progressbar.visibility = View.VISIBLE
        tvTips.text = context.getString(R.string.load_more_load)
    }

    override fun onNoMore() {
        progressbar.visibility = View.GONE
        tvTips.text = context.getString(R.string.load_more_no_more)
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        tvTips.text = context.getString(R.string.load_more_success)
    }

    override fun onError() {
        progressbar.visibility = View.GONE
        tvTips.text = context.getString(R.string.load_more_error)
    }

    override fun onNormal() {
        progressbar.visibility = View.GONE
        tvTips.text = context.getString(R.string.load_more_normal)
    }
}
