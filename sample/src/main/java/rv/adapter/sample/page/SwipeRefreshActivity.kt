package rv.adapter.sample.page

import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import rv.adapter.layout.Callback
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivitySwipeRefreshBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity

class SwipeRefreshActivity : BaseActivity<ActivitySwipeRefreshBinding>(
    R.layout.activity_swipe_refresh,
    "SwipeRefreshSample"
),
    SwipeRefreshLayout.OnRefreshListener {

    override fun onCreateViewBinding(rootView: View): ActivitySwipeRefreshBinding {
        return ActivitySwipeRefreshBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.swipeRefresh.setOnRefreshListener(this)
        viewBinding.include.recyclerView
            .linearLayoutManager()
            .attachXAdapter<SampleEntity>()
            .setItemLayoutId(R.layout.layout_json_item)
            .openLoadingMore()
            .setOnBind<SampleEntity> { holder, _, entity ->
                Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
            }
            .setLoadMoreListener {
                this@SwipeRefreshActivity.viewBinding.include.recyclerView.postDelayed({
                    it.setLoadMoreState(Callback.ERROR)
                }, 1500)
            }
        viewBinding.swipeRefresh.post { onRefresh() }
        // 模拟一下
        viewBinding.swipeRefresh.postDelayed({
            viewBinding.include.recyclerView.addAll(JsonUtils.jsonList)
        }, 1500)
    }

    override fun onRefresh() {
        if (viewBinding.include.recyclerView.xAdapter<SampleEntity>().loadMoreState == Callback.LOAD) {
            return
        }
        viewBinding.swipeRefresh.isRefreshing = true
        viewBinding.include.recyclerView.postDelayed({
            viewBinding.swipeRefresh.isRefreshing = false
        }, 1500)
    }
}