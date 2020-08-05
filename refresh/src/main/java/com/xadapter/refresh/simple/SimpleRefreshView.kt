package com.xadapter.refresh.simple

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.xadapter.refresh.R
import com.xadapter.refresh.XRefreshView
import kotlinx.android.synthetic.main.simple_refresh.view.*

/**
 * by y on 2016/11/16
 */
class SimpleRefreshView(context: Context) : XRefreshView(context, R.layout.simple_refresh) {

    private val mRotateUpAnim: Animation = RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    private val mRotateDownAnim: Animation = RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)

    init {
        tvTips.setTextColor(Color.BLACK)
        tvTips.text = context.getString(R.string.refresh_more_init)
        initAnimation()
    }

    private fun initAnimation() {
        mRotateUpAnim.duration = 180
        mRotateUpAnim.fillAfter = true
        mRotateDownAnim.duration = 180
        mRotateDownAnim.fillAfter = true
    }

    override fun onReady() {
        ivTips.startAnimation(mRotateUpAnim)
        progressbar.visibility = View.INVISIBLE
        ivTips.visibility = View.VISIBLE
        tvTips.text = context.getString(R.string.refresh_more_ready)
    }

    override fun onRefresh() {
        progressbar.visibility = View.VISIBLE
        ivTips.clearAnimation()
        ivTips.visibility = View.INVISIBLE
        tvTips.text = context.getString(R.string.refresh_more_refresh)
    }

    override fun onSuccess() {
        progressbar.visibility = View.INVISIBLE
        ivTips.visibility = View.INVISIBLE
        ivTips.clearAnimation()
        tvTips.text = context.getString(R.string.refresh_more_success)
    }

    override fun onError() {
        progressbar.visibility = View.INVISIBLE
        ivTips.visibility = View.INVISIBLE
        ivTips.clearAnimation()
        tvTips.text = context.getString(R.string.refresh_more_error)
    }

    override fun onNormal() {
        if (isReady) {
            ivTips.startAnimation(mRotateDownAnim)
        } else {
            ivTips.clearAnimation()
        }
        ivTips.visibility = View.VISIBLE
        progressbar.visibility = View.INVISIBLE
        tvTips.text = context.getString(R.string.refresh_more_normal)
    }
}
