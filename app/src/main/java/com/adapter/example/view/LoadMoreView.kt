package com.adapter.example.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.adapter.example.R
import com.xadapter.widget.XLoadMoreView
import kotlinx.android.synthetic.main.layout_loadmore.view.*

/**
 * by y on 2017/6/21.
 */

class LoadMoreView(context: Context) : XLoadMoreView(context, R.layout.layout_loadmore) {

    override fun initView() {
        tv_tips.text = "上拉加载"
        progressbar.visibility = View.GONE
        tv_tips.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
    }

    override fun onStart() {
        progressbar.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun onLoad() {
        progressbar.visibility = View.VISIBLE
        tv_tips.text = "正在加载...sample"
    }

    override fun onNoMore() {
        progressbar.visibility = View.GONE
        tv_tips.text = "没有数据了"
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        tv_tips.text = "加载成功"
    }

    override fun onError() {
        progressbar.visibility = View.GONE
        tv_tips.text = "加载失败"
    }

    override fun onNormal() {
        progressbar.visibility = View.GONE
        tv_tips.text = "上拉加载"
    }
}
