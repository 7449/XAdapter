package rv.adapter.sample.custom

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import rv.adapter.layout.XLoadMoreView
import rv.adapter.sample.R
import rv.adapter.sample.databinding.LayoutLoadmoreBinding

/**
 * by y on 2017/6/21.
 */
class CustomLoadMoreView(context: Context) : XLoadMoreView(context) {

    private val viewBind by lazy { LayoutLoadmoreBinding.bind(this) }

    init {
        addView(View.inflate(context, R.layout.layout_loadmore, null))
        viewBind.tips.text = "CustomLoadMoreView"
        viewBind.progressbar.visibility = View.GONE
        viewBind.tips.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onLoad() {
        viewBind.progressbar.visibility = View.VISIBLE
        viewBind.tips.text = "Custom:正在加载..."
    }

    override fun onNoMore() {
        viewBind.progressbar.visibility = View.GONE
        viewBind.tips.text = "Custom:没有数据了"
    }

    override fun onSuccess() {
        viewBind.progressbar.visibility = View.GONE
        viewBind.tips.text = "Custom:加载成功"
    }

    override fun onError() {
        viewBind.progressbar.visibility = View.GONE
        viewBind.tips.text = "Custom:加载失败"
    }

    override fun onNormal() {
        viewBind.progressbar.visibility = View.GONE
        viewBind.tips.text = "CustomLoadMoreView"
    }
}
