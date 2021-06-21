package rv.adapter.sample.page

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import rv.adapter.layout.LayoutStatus
import rv.adapter.layout.XLoadMoreStatus
import rv.adapter.layout.XRefreshStatus
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.custom.CustomEmptyView
import rv.adapter.sample.databinding.ActivityEmptyBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity

class EmptyActivity : BaseActivity<ActivityEmptyBinding>(R.layout.activity_empty, "EmptyAdapter") {

    override fun onCreateViewBinding(rootView: View): ActivityEmptyBinding {
        return ActivityEmptyBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.include.recyclerView
            .linearLayoutManager()
            .attachXAdapter<SampleEntity>()
            .openPullRefresh()
            .openLoadingMore()
            .setEmptyView(CustomEmptyView(applicationContext))
            .setItemLayoutId(R.layout.layout_json_item)
            .setOnBind<SampleEntity> { holder, _, entity ->
                Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
            }
            .setRefreshListener {
                this@EmptyActivity.viewBinding.include.recyclerView.postDelayed({
                    it.setRefreshStatus(LayoutStatus.SUCCESS)
                    viewBinding.include.recyclerView.removeAll()
                    viewBinding.include.recyclerView.addAll(JsonUtils.jsonList)
                }, 1500)
            }
            .setLoadMoreListener {
                this@EmptyActivity.viewBinding.include.recyclerView.postDelayed({
                    it.setLoadMoreStatus(LayoutStatus.ERROR)
                }, 1500)
            }
            .addAll(ArrayList())
    }

}