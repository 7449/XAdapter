package rv.adapter.layout.simple

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import rv.adapter.layout.R
import rv.adapter.layout.XLoadMoreView
import rv.adapter.layout.databinding.SimpleLoadMoreBinding

/**
 * by y on 2016/9/29
 */
class SimpleLoadMoreView(context: Context) : XLoadMoreView(context) {

    private val binding by lazy {
        SimpleLoadMoreBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    init {
        binding.progressBar.visibility = GONE
        binding.show.setTextColor(Color.BLACK)
        binding.show.text = context.getString(R.string.load_more_init)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onLoad() {
        binding.progressBar.visibility = VISIBLE
        binding.show.text = context.getString(R.string.load_more_load)
    }

    override fun onNoMore() {
        binding.progressBar.visibility = GONE
        binding.show.text = context.getString(R.string.load_more_no_more)
    }

    override fun onSuccess() {
        binding.progressBar.visibility = GONE
        binding.show.text = context.getString(R.string.load_more_success)
    }

    override fun onError() {
        binding.progressBar.visibility = GONE
        binding.show.text = context.getString(R.string.load_more_error)
    }

    override fun onNormal() {
        binding.progressBar.visibility = GONE
        binding.show.text = context.getString(R.string.load_more_normal)
    }

}
