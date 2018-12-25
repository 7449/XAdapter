package com.xadapter.simple

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.view.View
import com.xadapter.R
import com.xadapter.XLoadMoreView
import kotlinx.android.synthetic.main.simple_load_more.view.*

/**
 * by y on 2016/9/29
 */

class SimpleLoadMore(context: Context) : XLoadMoreView(context, R.layout.simple_load_more) {

    private lateinit var animationDrawable: AnimationDrawable

    public override fun initView() {
        animationDrawable = progressbar.background as AnimationDrawable
        tv_tips.setTextColor(Color.BLACK)
        progressbar.visibility = View.GONE
        tv_tips.text = "上拉加载"
    }

    override fun onStart() {
        progressbar.visibility = View.GONE
        animationDrawable.stop()
    }

    override fun onLoad() {
        progressbar.visibility = View.VISIBLE
        animationDrawable.start()
        tv_tips.text = "正在加载..."
    }

    override fun onNoMore() {
        progressbar.visibility = View.GONE
        animationDrawable.stop()
        tv_tips.text = "没有数据了"
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        animationDrawable.stop()
        tv_tips.text = "加载成功"
    }

    override fun onError() {
        progressbar.visibility = View.GONE
        animationDrawable.stop()
        tv_tips.text = "加载失败"
    }

    override fun onNormal() {
        progressbar.visibility = View.GONE
        animationDrawable.stop()
        tv_tips.text = "上拉加载"
    }
}
