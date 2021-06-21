package rv.adapter.layout.simple

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import rv.adapter.layout.R
import rv.adapter.layout.XRefreshView
import rv.adapter.layout.databinding.SimpleRefreshBinding

/**
 * by y on 2016/11/16
 */
class SimpleRefreshView(context: Context) : XRefreshView(context) {

    private val mRotateUpAnim: Animation = RotateAnimation(
        0.0f,
        -180.0f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = 180
        fillAfter = true
    }
    private val mRotateDownAnim: Animation = RotateAnimation(
        -180.0f,
        0.0f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = 180
        fillAfter = true
    }

    private val viewBinding by lazy {
        SimpleRefreshBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        viewBinding.root.layoutParams.height = 0
        viewBinding.show.setTextColor(Color.BLACK)
        viewBinding.show.text = context.getString(R.string.refresh_more_init)
        viewBinding.progressBar.visibility = GONE
    }

    override fun onReady() {
        viewBinding.action.startAnimation(mRotateUpAnim)
        viewBinding.progressBar.visibility = INVISIBLE
        viewBinding.action.visibility = VISIBLE
        viewBinding.show.text = context.getString(R.string.refresh_more_ready)
    }

    override fun onRefresh() {
        viewBinding.progressBar.visibility = VISIBLE
        viewBinding.action.clearAnimation()
        viewBinding.action.visibility = INVISIBLE
        viewBinding.show.text = context.getString(R.string.refresh_more_refresh)
    }

    override fun onSuccess() {
        viewBinding.progressBar.visibility = INVISIBLE
        viewBinding.action.visibility = INVISIBLE
        viewBinding.action.clearAnimation()
        viewBinding.show.text = context.getString(R.string.refresh_more_success)
    }

    override fun onError() {
        viewBinding.progressBar.visibility = INVISIBLE
        viewBinding.action.visibility = INVISIBLE
        viewBinding.action.clearAnimation()
        viewBinding.show.text = context.getString(R.string.refresh_more_error)
    }

    override fun onNormal() {
        if (isReady) {
            viewBinding.action.startAnimation(mRotateDownAnim)
        } else {
            viewBinding.action.clearAnimation()
        }
        viewBinding.action.visibility = VISIBLE
        viewBinding.progressBar.visibility = INVISIBLE
        viewBinding.show.text = context.getString(R.string.refresh_more_normal)
    }

}
