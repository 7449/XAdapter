package rv.adapter.sample.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import rv.adapter.layout.Callback
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityCollapsingToolbarBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity
import rv.adapter.sample.supportAppbar

class CollapsingToolbarActivity :
    BaseActivity<ActivityCollapsingToolbarBinding>(
        R.layout.activity_collapsing_toolbar,
        "CollapsingToolbarSample"
    ) {

    override fun onCreateViewBinding(rootView: View): ActivityCollapsingToolbarBinding {
        return ActivityCollapsingToolbarBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.recyclerView
            .linearLayoutManager()
            .attachXAdapter<SampleEntity>()
            .setItemLayoutId(R.layout.layout_json_item)
            .openPullRefresh()
            .openLoadingMore()
            .setOnBind<SampleEntity> { holder, _, entity ->
                Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
            }
            .setRefreshListener {
                this@CollapsingToolbarActivity.viewBinding.recyclerView.postDelayed({
                    viewBinding.recyclerView.setRefreshState(
                        Callback.SUCCESS
                    )
                }, 1500)
            }
            .setLoadMoreListener {
                this@CollapsingToolbarActivity.viewBinding.recyclerView.postDelayed({
                    viewBinding.recyclerView.setLoadMoreState(
                        Callback.NO_MORE
                    )
                }, 1500)
            }
            .addHeaderView(
                LayoutInflater.from(this)
                    .inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false)
            )
            .addFooterView(
                LayoutInflater.from(this)
                    .inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false)
            )
            .xAdapter<SampleEntity>()
            .supportAppbar(viewBinding.appbar)
            .addAll(JsonUtils.jsonList)
    }
}
