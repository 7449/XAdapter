package com.xadapter.simple

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.View

import com.xadapter.R
import com.xadapter.widget.XLoadMoreView

/**
 * by y on 2016/9/29
 */

class SimpleLoadMore : XLoadMoreView {

    private lateinit var progressBar: AppCompatImageView
    private lateinit var mText: AppCompatTextView
    private lateinit var animationDrawable: AnimationDrawable

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun getLayoutId(): Int {
        return R.layout.simple_load_more
    }

    public override fun initView() {
        progressBar = findViewById(R.id.progressbar)
        mText = findViewById(R.id.tv_tips)
        animationDrawable = progressBar.background as AnimationDrawable
        mText.setTextColor(Color.BLACK)
        progressBar.visibility = View.GONE
        mText.text = "上拉加载"
    }

    override fun onStart() {
        progressBar.visibility = View.GONE
        animationDrawable.stop()
    }

    override fun onLoad() {
        progressBar.visibility = View.VISIBLE
        animationDrawable.start()
        mText.text = "正在加载..."
    }

    override fun onNoMore() {
        progressBar.visibility = View.GONE
        animationDrawable.stop()
        mText.text = "没有数据了"
    }

    override fun onSuccess() {
        progressBar.visibility = View.GONE
        animationDrawable.stop()
        mText.text = "加载成功"
    }

    override fun onError() {
        progressBar.visibility = View.GONE
        animationDrawable.stop()
        mText.text = "加载失败"
    }

    override fun onNormal() {
        progressBar.visibility = View.GONE
        animationDrawable.stop()
        mText.text = "上拉加载"
    }
}
