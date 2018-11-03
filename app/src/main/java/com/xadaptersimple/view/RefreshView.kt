package com.xadaptersimple.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.xadapter.XRefreshView
import com.xadaptersimple.R

/**
 * by y on 2017/6/21.
 */

class RefreshView : XRefreshView {
    private lateinit var progressBar: ProgressBar
    private lateinit var mText: AppCompatTextView
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
        mText.text = "下拉刷新"
        mText.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
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
        return R.layout.layout_refresh
    }

    override fun onStart() {
        mTipsIv.clearAnimation()
        mTipsIv.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    override fun onNormal() {
        if (state == XRefreshView.READY) {
            mTipsIv.startAnimation(mRotateDownAnim)
        } else {
            mTipsIv.clearAnimation()
        }
        mTipsIv.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
        mText.text = "下拉刷新"
    }

    override fun onReady() {
        mTipsIv.startAnimation(mRotateUpAnim)
        progressBar.visibility = View.INVISIBLE
        mTipsIv.visibility = View.VISIBLE
        mText.text = "释放立即刷新"
    }

    override fun onRefresh() {
        progressBar.visibility = View.VISIBLE
        mTipsIv.visibility = View.INVISIBLE
        mText.text = "正在刷新..."
    }

    override fun onSuccess() {
        progressBar.visibility = View.INVISIBLE
        mTipsIv.visibility = View.INVISIBLE
        mTipsIv.clearAnimation()
        mText.text = "刷新成功"
    }

    override fun onError() {
        progressBar.visibility = View.INVISIBLE
        mTipsIv.visibility = View.INVISIBLE
        mTipsIv.clearAnimation()
        mText.text = "刷新失败"
    }


}
