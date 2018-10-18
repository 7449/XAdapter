package com.xadaptersimple.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.xadapter.widget.XLoadMoreView
import com.xadaptersimple.R

/**
 * by y on 2017/6/21.
 */

class LoadMoreView : XLoadMoreView {

    private lateinit var progressBar: ProgressBar
    private lateinit var mText: AppCompatTextView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun initView() {
        progressBar = findViewById(R.id.progressbar)
        mText = findViewById(R.id.tv_tips)
        mText.text = "上拉加载"
        progressBar.visibility = View.GONE
        mText.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_loadmore
    }

    override fun onStart() {
        progressBar.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun onLoad() {
        progressBar.visibility = View.VISIBLE
        mText.text = "正在加载...sample"
    }

    override fun onNoMore() {
        progressBar.visibility = View.GONE
        mText.text = "没有数据了"
    }

    override fun onSuccess() {
        progressBar.visibility = View.GONE
        mText.text = "加载成功"
    }

    override fun onError() {
        progressBar.visibility = View.GONE
        mText.text = "加载失败"
    }

    override fun onNormal() {
        progressBar.visibility = View.GONE
        mText.text = "上拉加载"
    }
}
