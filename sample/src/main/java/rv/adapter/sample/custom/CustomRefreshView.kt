package rv.adapter.sample.custom

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import rv.adapter.layout.XRefreshView
import rv.adapter.sample.R
import rv.adapter.sample.databinding.LayoutRefreshBinding

/**
 * by y on 2017/6/21.
 */
class CustomRefreshView(context: Context) : XRefreshView(context, R.layout.layout_refresh) {

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

    private val viewBind by lazy { LayoutRefreshBinding.bind(this) }

    init {
        viewBind.tips.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        viewBind.tips.text = "下拉立即刷新"
        initAnimation()
    }

    private fun initAnimation() {
        mRotateUpAnim.duration = 180
        mRotateUpAnim.fillAfter = true
        mRotateDownAnim.duration = 180
        mRotateDownAnim.fillAfter = true
    }

    override fun onReady() {
        viewBind.ivTips.startAnimation(mRotateUpAnim)
        viewBind.progressbar.visibility = View.INVISIBLE
        viewBind.ivTips.visibility = View.VISIBLE
        viewBind.tips.text = "释放立即刷新"
    }

    override fun onRefresh() {
        viewBind.progressbar.visibility = View.VISIBLE
        viewBind.ivTips.clearAnimation()
        viewBind.ivTips.visibility = View.INVISIBLE
        viewBind.tips.text = "正在刷新..."
    }

    override fun onSuccess() {
        viewBind.progressbar.visibility = View.INVISIBLE
        viewBind.ivTips.visibility = View.INVISIBLE
        viewBind.ivTips.clearAnimation()
        viewBind.tips.text = "刷新成功"
    }

    override fun onError() {
        viewBind.progressbar.visibility = View.INVISIBLE
        viewBind.ivTips.visibility = View.INVISIBLE
        viewBind.ivTips.clearAnimation()
        viewBind.tips.text = "刷新失败"
    }

    override fun onNormal() {
        if (isReady) {
            viewBind.ivTips.startAnimation(mRotateDownAnim)
        } else {
            viewBind.ivTips.clearAnimation()
        }
        viewBind.ivTips.visibility = View.VISIBLE
        viewBind.progressbar.visibility = View.INVISIBLE
        viewBind.tips.text = "下拉立即刷新"
    }
}
