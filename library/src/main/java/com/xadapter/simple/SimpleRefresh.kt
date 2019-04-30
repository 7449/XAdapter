package com.xadapter.simple

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.xadapter.R
import com.xadapter.widget.XRefreshView
import kotlinx.android.synthetic.main.simple_refresh.view.*

/**
 * by y on 2016/11/16
 */

class SimpleRefresh(context: Context) : XRefreshView(context, R.layout.simple_refresh) {

    private lateinit var animationDrawable: AnimationDrawable

    private lateinit var mRotateUpAnim: Animation
    private lateinit var mRotateDownAnim: Animation

    public override fun initView() {
        animationDrawable = progressbar.background as AnimationDrawable
        tv_tips.setTextColor(Color.BLACK)
        tv_tips.text = context.getString(R.string.refresh_more_init)
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
        tv_tips.text = context.getString(R.string.refresh_more_start)
    }

    override fun onReady() {
        iv_tips.startAnimation(mRotateUpAnim)
        progressbar.visibility = View.INVISIBLE
        iv_tips.visibility = View.VISIBLE
        animationDrawable.stop()
        tv_tips.text = context.getString(R.string.refresh_more_ready)
    }

    override fun onRefresh() {
        progressbar.visibility = View.VISIBLE
        iv_tips.visibility = View.INVISIBLE
        animationDrawable.start()
        tv_tips.text = context.getString(R.string.refresh_more_refresh)
    }

    override fun onSuccess() {
        progressbar.visibility = View.INVISIBLE
        iv_tips.visibility = View.INVISIBLE
        animationDrawable.stop()
        iv_tips.clearAnimation()
        tv_tips.text = context.getString(R.string.refresh_more_success)
    }

    override fun onError() {
        progressbar.visibility = View.INVISIBLE
        iv_tips.visibility = View.INVISIBLE
        animationDrawable.stop()
        iv_tips.clearAnimation()
        tv_tips.text = context.getString(R.string.refresh_more_error)
    }

    override fun onNormal() {
        if (state == READY) {
            iv_tips.startAnimation(mRotateDownAnim)
        } else {
            iv_tips.clearAnimation()
        }
        iv_tips.visibility = View.VISIBLE
        progressbar.visibility = View.INVISIBLE
        animationDrawable.stop()
        tv_tips.text = context.getString(R.string.refresh_more_normal)
    }

}
