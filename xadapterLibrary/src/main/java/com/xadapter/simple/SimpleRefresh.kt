package com.xadapter.simple

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

import com.xadapter.R
import com.xadapter.widget.XRefreshView

/**
 * by y on 2016/11/16
 */

class SimpleRefresh : XRefreshView {

    private lateinit var progressBar: AppCompatImageView
    private lateinit var mText: AppCompatTextView
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var mTipsIv: AppCompatImageView

    private lateinit var mRotateUpAnim: Animation
    private lateinit var mRotateDownAnim: Animation

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    public override fun initView() {
        progressBar = findViewById(R.id.progressbar)
        mText = findViewById(R.id.tv_tips)
        mTipsIv = findViewById(R.id.iv_tips)
        animationDrawable = progressBar.background as AnimationDrawable
        mText.text = "下拉刷新"
        mText.setTextColor(Color.BLACK)
        initAnimation()
    }

    private fun initAnimation() {
        mRotateUpAnim = RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mRotateUpAnim.duration = 180
        mRotateUpAnim.fillAfter = true
        mRotateDownAnim = RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mRotateDownAnim.duration = 180
        mRotateDownAnim.fillAfter = true
    }

    override fun getLayoutId(): Int {
        return R.layout.simple_refresh
    }

    override fun onStart() {
        mTipsIv.clearAnimation()
        mTipsIv.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    override fun onNormal() {
        if (state == READY) {
            mTipsIv.startAnimation(mRotateDownAnim)
        } else {
            mTipsIv.clearAnimation()
        }
        mTipsIv.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
        animationDrawable.stop()
        mText.text = "下拉刷新"
    }

    override fun onReady() {
        mTipsIv.startAnimation(mRotateUpAnim)
        progressBar.visibility = View.INVISIBLE
        mTipsIv.visibility = View.VISIBLE
        animationDrawable.stop()
        mText.text = "释放立即刷新"
    }

    override fun onRefresh() {
        progressBar.visibility = View.VISIBLE
        mTipsIv.visibility = View.INVISIBLE
        animationDrawable.start()
        mText.text = "正在刷新..."
    }

    override fun onSuccess() {
        progressBar.visibility = View.INVISIBLE
        mTipsIv.visibility = View.INVISIBLE
        animationDrawable.stop()
        mTipsIv.clearAnimation()
        mText.text = "刷新成功"
    }

    override fun onError() {
        progressBar.visibility = View.INVISIBLE
        mTipsIv.visibility = View.INVISIBLE
        animationDrawable.stop()
        mTipsIv.clearAnimation()
        mText.text = "刷新失败"
    }

}
