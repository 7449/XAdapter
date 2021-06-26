package rv.adapter.sample.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import rv.adapter.core.XAdapter
import rv.adapter.layout.LayoutStatus
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityGridManagerBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity

/**
 * by y on 2016/11/17
 */
class GridLayoutActivity :
    BaseActivity<ActivityGridManagerBinding>(R.layout.activity_grid_manager) {

    override fun onCreateViewBinding(rootView: View): ActivityGridManagerBinding {
        return ActivityGridManagerBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.include.recyclerView
            .gridLayoutManager(2)
            .attachXAdapter(XAdapter<SampleEntity>())
            .setItemLayoutId(R.layout.layout_json_item)
            .openPullRefresh()
            .openLoadingMore()
            .setOnBind<SampleEntity> { holder, _, entity ->
                Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
            }
            .setRefreshListener {
                this@GridLayoutActivity.viewBinding.include.recyclerView.postDelayed({
                    viewBinding.include.recyclerView.setRefreshStatus(
                        LayoutStatus.SUCCESS
                    )
                }, 1500)
            }
            .setLoadMoreListener {
                this@GridLayoutActivity.viewBinding.include.recyclerView.postDelayed({
                    viewBinding.include.recyclerView.setLoadMoreStatus(
                        LayoutStatus.NO_MORE
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
            .addAll(JsonUtils.jsonList)
    }
}
