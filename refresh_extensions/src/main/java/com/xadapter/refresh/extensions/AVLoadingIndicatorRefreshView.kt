package com.xadapter.refresh.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.wang.avi.Indicator
import com.xadapter.refresh.Callback.Companion.READY
import com.xadapter.refresh.XRefreshView
import kotlinx.android.synthetic.main.layout_avloading_refresh.view.*
import java.util.*
import kotlin.math.max

/**
 * by y on 2017/6/21.
 */
class AVLoadingIndicatorRefreshView(context: Context) : XRefreshView(context, R.layout.layout_avloading_refresh) {

    var indicator: Indicator? = null
        set(value) {
            value?.let {
                field = it
                avLoadingView.indicator = it
            }
        }

    var color: Int? = null
        set(value) {
            value?.let {
                field = it
                avLoadingView.setIndicatorColor(it)
            }
        }

    private lateinit var mRotateUpAnim: Animation
    private lateinit var mRotateDownAnim: Animation

    override fun initView() {
        avLoadingView.indicator = getIndicator(AVType.BALL_SPIN_FADE_LOADER)
        avLoadingView.setIndicatorColor(Color.GRAY)
        avLoadingView.hide()
        avTips.setTextColor(Color.BLACK)
        avTips.text = context.getString(R.string.refresh_more_init)
        mRotateUpAnim = RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mRotateUpAnim.duration = 180
        mRotateUpAnim.fillAfter = true
        mRotateDownAnim = RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        mRotateDownAnim.duration = 180
        mRotateDownAnim.fillAfter = true
        avRefreshTime.text = String.format(context.getString(R.string.refresh_more_time), resetTime())
    }

    override fun onStart() {
        avIvIcon.clearAnimation()
        avIvIcon.visibility = View.VISIBLE
        avLoadingView.hide()
        avTips.text = context.getString(R.string.refresh_more_start)
        avRefreshTime.text = String.format(context.getString(R.string.refresh_more_time), resetTime())
    }

    override fun onReady() {
        avIvIcon.startAnimation(mRotateUpAnim)
        avLoadingView.hide()
        avIvIcon.visibility = View.VISIBLE
        avTips.text = context.getString(R.string.refresh_more_ready)
        avRefreshTime.text = String.format(context.getString(R.string.refresh_more_time), resetTime())
    }

    override fun onRefresh() {
        avLoadingView.smoothToShow()
        avIvIcon.visibility = View.INVISIBLE
        avTips.text = context.getString(R.string.refresh_more_refresh)
        avRefreshTime.text = String.format(context.getString(R.string.refresh_more_time), resetTime())
    }

    override fun onSuccess() {
        avLoadingView.hide()
        avIvIcon.visibility = View.INVISIBLE
        avIvIcon.clearAnimation()
        avTips.text = context.getString(R.string.refresh_more_success)
        saveLastRefreshTime()
        avRefreshTime.text = String.format(context.getString(R.string.refresh_more_time), resetTime())
    }

    override fun onError() {
        avLoadingView.hide()
        avIvIcon.visibility = View.INVISIBLE
        avIvIcon.clearAnimation()
        avTips.text = context.getString(R.string.refresh_more_error)
        saveLastRefreshTime()
        avRefreshTime.text = String.format(context.getString(R.string.refresh_more_time), resetTime())
    }

    override fun onNormal() {
        if (state == READY) {
            avIvIcon.startAnimation(mRotateDownAnim)
        } else {
            avIvIcon.clearAnimation()
        }
        avIvIcon.visibility = View.VISIBLE
        avLoadingView.hide()
        avTips.text = context.getString(R.string.refresh_more_normal)
        avRefreshTime.text = String.format(context.getString(R.string.refresh_more_time), resetTime())
    }

    private fun getLastRefreshTime(): Long {
        return context.getSharedPreferences("avi_refresh_time", Context.MODE_PRIVATE).getLong("avi_refresh_time", Date().time)
    }

    private fun saveLastRefreshTime() {
        context.getSharedPreferences("avi_refresh_time", Context.MODE_PRIVATE).edit().putLong("avi_refresh_time", System.currentTimeMillis()).apply()
    }

    private fun resetTime(): String {
        val ct = ((System.currentTimeMillis() - getLastRefreshTime()) / 1000).toInt()
        if (ct == 0) {
            return "刚刚"
        }
        if (ct in 1..59) {
            return ct.toString() + "秒前"
        }
        if (ct in 60..3599) {
            return max(ct / 60, 1).toString() + "分钟前"
        }
        if (ct in 3600..86399)
            return (ct / 3600).toString() + "小时前"
        if (ct in 86400..2591999) {
            val day = ct / 86400
            return day.toString() + "天前"
        }
        return if (ct in 2592000..31103999) {
            (ct / 2592000).toString() + "月前"
        } else (ct / 31104000).toString() + "年前"
    }
}
