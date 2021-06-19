package rv.adapter.layout.simple

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import rv.adapter.layout.R
import rv.adapter.layout.XRefreshView
import rv.adapter.layout.databinding.SimpleRefreshBinding

/**
 * by y on 2016/11/16
 */
class SimpleRefreshView(context: Context) : XRefreshView(context, R.layout.simple_refresh) {

    private val mRotateUpAnim: Animation = RotateAnimation(
        0.0f,
        -180.0f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    private val mRotateDownAnim: Animation = RotateAnimation(
        -180.0f,
        0.0f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )

    private val viewBinding by lazy { SimpleRefreshBinding.bind(getChildAt(0)) }

    init {
        viewBinding.tvTips.setTextColor(Color.BLACK)
        viewBinding.tvTips.text = context.getString(R.string.refresh_more_init)
        initAnimation()
    }

    private fun initAnimation() {
        mRotateUpAnim.duration = 180
        mRotateUpAnim.fillAfter = true
        mRotateDownAnim.duration = 180
        mRotateDownAnim.fillAfter = true
    }

    override fun onReady() {
        viewBinding.ivTips.startAnimation(mRotateUpAnim)
        viewBinding.progressbar.visibility = View.INVISIBLE
        viewBinding.ivTips.visibility = View.VISIBLE
        viewBinding.tvTips.text = context.getString(R.string.refresh_more_ready)
    }

    override fun onRefresh() {
        viewBinding.progressbar.visibility = View.VISIBLE
        viewBinding.ivTips.clearAnimation()
        viewBinding.ivTips.visibility = View.INVISIBLE
        viewBinding.tvTips.text = context.getString(R.string.refresh_more_refresh)
    }

    override fun onSuccess() {
        viewBinding.progressbar.visibility = View.INVISIBLE
        viewBinding.ivTips.visibility = View.INVISIBLE
        viewBinding.ivTips.clearAnimation()
        viewBinding.tvTips.text = context.getString(R.string.refresh_more_success)
    }

    override fun onError() {
        viewBinding.progressbar.visibility = View.INVISIBLE
        viewBinding.ivTips.visibility = View.INVISIBLE
        viewBinding.ivTips.clearAnimation()
        viewBinding.tvTips.text = context.getString(R.string.refresh_more_error)
    }

    override fun onNormal() {
        if (isReady) {
            viewBinding.ivTips.startAnimation(mRotateDownAnim)
        } else {
            viewBinding.ivTips.clearAnimation()
        }
        viewBinding.ivTips.visibility = View.VISIBLE
        viewBinding.progressbar.visibility = View.INVISIBLE
        viewBinding.tvTips.text = context.getString(R.string.refresh_more_normal)
    }
}
