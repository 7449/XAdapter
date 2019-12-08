package com.adapter.example.custom

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import com.adapter.example.R
import com.xadapter.refresh.XRefreshView
import kotlinx.android.synthetic.main.layout_refresh.view.*

/**
 * by y on 2017/6/21.
 */
class CustomRefreshView(context: Context) : XRefreshView(context, R.layout.layout_refresh) {

    private lateinit var mRotateUpAnim: Animation
    private lateinit var mRotateDownAnim: Animation

    override fun initView() {
        tips.text = "CustomRefreshView"
        tips.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
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
        ivTips.clearAnimation()
        ivTips.visibility = View.VISIBLE
        progressbar.visibility = View.INVISIBLE
    }

    override fun onNormal() {
        if (state == READY) {
            ivTips.startAnimation(mRotateDownAnim)
        } else {
            ivTips.clearAnimation()
        }
        ivTips.visibility = View.VISIBLE
        progressbar.visibility = View.INVISIBLE
        tips.text = "CustomRefreshView"
    }

    override fun onReady() {
        ivTips.startAnimation(mRotateUpAnim)
        progressbar.visibility = View.INVISIBLE
        ivTips.visibility = View.VISIBLE
        tips.text = "释放立即刷新"
    }

    override fun onRefresh() {
        progressbar.visibility = View.VISIBLE
        ivTips.visibility = View.INVISIBLE
        tips.text = "正在刷新..."
    }

    override fun onSuccess() {
        progressbar.visibility = View.INVISIBLE
        ivTips.visibility = View.INVISIBLE
        ivTips.clearAnimation()
        tips.text = "刷新成功"
    }

    override fun onError() {
        progressbar.visibility = View.INVISIBLE
        ivTips.visibility = View.INVISIBLE
        ivTips.clearAnimation()
        tips.text = "刷新失败"
    }
}
