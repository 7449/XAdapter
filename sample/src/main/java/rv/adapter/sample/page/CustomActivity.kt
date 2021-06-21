package rv.adapter.sample.page

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import rv.adapter.layout.LayoutStatus
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.custom.CustomOnScrollListener
import rv.adapter.sample.databinding.ActivityCustomBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity

class CustomActivity :
    BaseActivity<ActivityCustomBinding>(R.layout.activity_custom, "CustomSample") {

    override fun onCreateViewBinding(rootView: View): ActivityCustomBinding {
        return ActivityCustomBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.include.recyclerView
            .linearLayoutManager()
            .attachXAdapter<SampleEntity>()
            .customScrollListener(CustomOnScrollListener())
            .setItemLayoutId(R.layout.layout_json_item)
            .openLoadingMore()
            .openPullRefresh()
            .setOnBind<SampleEntity> { holder, _, entity ->
                Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
            }
            .setOnItemClickListener<SampleEntity> { _, position, _ ->
                Toast.makeText(baseContext, "position:  $position", Toast.LENGTH_SHORT).show()
            }
            .setOnItemLongClickListener<SampleEntity> { _, _, _ ->
                Toast.makeText(baseContext, "onLongClick", Toast.LENGTH_SHORT).show()
                true
            }
            .setRefreshListener {
                this@CustomActivity.viewBinding.include.recyclerView.postDelayed({
                    it.setRefreshStatus(LayoutStatus.SUCCESS)
                }, 1500)
            }
            .setLoadMoreListener {
                this@CustomActivity.viewBinding.include.recyclerView.postDelayed({
                    it.setLoadMoreStatus(LayoutStatus.ERROR)
                }, 1500)
            }
            .addAll(JsonUtils.jsonList)
    }

}