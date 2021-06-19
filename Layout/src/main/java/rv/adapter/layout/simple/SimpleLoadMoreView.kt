package rv.adapter.layout.simple

import android.content.Context
import android.graphics.Color
import android.view.View
import rv.adapter.layout.R
import rv.adapter.layout.XLoadMoreView
import rv.adapter.layout.databinding.SimpleLoadMoreBinding

/**
 * by y on 2016/9/29
 */
class SimpleLoadMoreView(context: Context) : XLoadMoreView(context) {

    private val viewBinding by lazy {
        SimpleLoadMoreBinding.bind(getChildAt(0))
    }

    init {
        addView(View.inflate(context, R.layout.simple_load_more, null))
        viewBinding.progressbar.visibility = View.GONE
        viewBinding.tvTips.setTextColor(Color.BLACK)
        viewBinding.tvTips.text = context.getString(R.string.load_more_init)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onLoad() {
        viewBinding.progressbar.visibility = View.VISIBLE
        viewBinding.tvTips.text = context.getString(R.string.load_more_load)
    }

    override fun onNoMore() {
        viewBinding.progressbar.visibility = View.GONE
        viewBinding.tvTips.text = context.getString(R.string.load_more_no_more)
    }

    override fun onSuccess() {
        viewBinding.progressbar.visibility = View.GONE
        viewBinding.tvTips.text = context.getString(R.string.load_more_success)
    }

    override fun onError() {
        viewBinding.progressbar.visibility = View.GONE
        viewBinding.tvTips.text = context.getString(R.string.load_more_error)
    }

    override fun onNormal() {
        viewBinding.progressbar.visibility = View.GONE
        viewBinding.tvTips.text = context.getString(R.string.load_more_normal)
    }
}
