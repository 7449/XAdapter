package rv.adapter.sample.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import rv.adapter.layout.LayoutStatus
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityViewbindingBinding
import rv.adapter.sample.databinding.LayoutJsonItemBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity
import rv.adapter.view.binding.XViewBindingAdapter

/**
 * by y on 2016/11/17
 */
class ViewBindingActivity :
    BaseActivity<ActivityViewbindingBinding>(
        R.layout.activity_linear_manager,
        "ViewBindingSample"
    ) {

    override fun onCreateViewBinding(rootView: View): ActivityViewbindingBinding {
        return ActivityViewbindingBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.include.recyclerView
            .linearLayoutManager()
            .fixedSize()
            .convertViewBindingAdapter(XViewBindingAdapter<SampleEntity, LayoutJsonItemBinding>())
            .onCreateViewBinding {
                LayoutJsonItemBinding.inflate(LayoutInflater.from(it.context), it, false)
            }
            .onBindItem { viewBinding, _, entity ->
                Glide.with(viewBinding.root.context).load(entity.image).into(viewBinding.image)
                viewBinding.title.text = entity.title
            }
            .setScrollLoadMoreItemCount(10)
            .openPullRefresh()
            .openLoadingMore()
            .addHeaderView(
                LayoutInflater.from(applicationContext)
                    .inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false)
            )
            .addFooterView(
                LayoutInflater.from(applicationContext)
                    .inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false)
            )
            .setOnItemClickListener { _, position, _ ->
                Toast.makeText(baseContext, "position:$position", Toast.LENGTH_SHORT).show()
            }
            .setOnItemLongClickListener { _, _, _ ->
                Toast.makeText(baseContext, "onLongClick", Toast.LENGTH_SHORT).show()
                true
            }
            .setRefreshListener {
                viewBinding.include.recyclerView.postDelayed({
                    viewBinding.include.recyclerView.setRefreshStatus(LayoutStatus.SUCCESS)
                }, 4000)
            }
            .setLoadingMoreListener {
                viewBinding.include.recyclerView.postDelayed({
                    viewBinding.include.recyclerView.setLoadMoreStatus(LayoutStatus.ERROR)
                }, 4000)
            }
            .addAll(JsonUtils.jsonList)
    }
}
