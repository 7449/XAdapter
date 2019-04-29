package com.adapter.example.view

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import com.adapter.example.R
import com.xadapter.widget.XRefreshView
import kotlinx.android.synthetic.main.layout_refresh.view.*

/**
 * by y on 2017/6/21.
 */

class RefreshView(context: Context) : XRefreshView(context, R.layout.layout_refresh) {

    private lateinit var mRotateUpAnim: Animation
    private lateinit var mRotateDownAnim: Animation

    public override fun initView() {
        tv_tips.text = "下拉刷新"
        tv_tips.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
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

    override fun onStart() {
        iv_tips.clearAnimation()
        iv_tips.visibility = View.VISIBLE
        progressbar.visibility = View.INVISIBLE
    }

    override fun onNormal() {
        if (state == XRefreshView.READY) {
            iv_tips.startAnimation(mRotateDownAnim)
        } else {
            iv_tips.clearAnimation()
        }
        iv_tips.visibility = View.VISIBLE
        progressbar.visibility = View.INVISIBLE
        tv_tips.text = "下拉刷新"
    }

    override fun onReady() {
        iv_tips.startAnimation(mRotateUpAnim)
        progressbar.visibility = View.INVISIBLE
        iv_tips.visibility = View.VISIBLE
        tv_tips.text = "释放立即刷新"
    }

    override fun onRefresh() {
        progressbar.visibility = View.VISIBLE
        iv_tips.visibility = View.INVISIBLE
        tv_tips.text = "正在刷新..."
    }

    override fun onSuccess() {
        progressbar.visibility = View.INVISIBLE
        iv_tips.visibility = View.INVISIBLE
        iv_tips.clearAnimation()
        tv_tips.text = "刷新成功"
    }

    override fun onError() {
        progressbar.visibility = View.INVISIBLE
        iv_tips.visibility = View.INVISIBLE
        iv_tips.clearAnimation()
        tv_tips.text = "刷新失败"
    }


}
