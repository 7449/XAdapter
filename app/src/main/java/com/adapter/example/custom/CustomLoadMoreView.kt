package com.adapter.example.custom

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.adapter.example.R
import com.xadapter.refresh.XLoadMoreView
import kotlinx.android.synthetic.main.layout_loadmore.view.*

/**
 * by y on 2017/6/21.
 */
class CustomLoadMoreView(context: Context) : XLoadMoreView(context) {

    init {
        addView(View.inflate(context, R.layout.layout_loadmore, null))
        tips.text = "CustomLoadMoreView"
        progressbar.visibility = View.GONE
        tips.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onLoad() {
        progressbar.visibility = View.VISIBLE
        tips.text = "正在加载..."
    }

    override fun onNoMore() {
        progressbar.visibility = View.GONE
        tips.text = "没有数据了"
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        tips.text = "加载成功"
    }

    override fun onError() {
        progressbar.visibility = View.GONE
        tips.text = "加载失败"
    }

    override fun onNormal() {
        progressbar.visibility = View.GONE
        tips.text = "CustomLoadMoreView"
    }
}
