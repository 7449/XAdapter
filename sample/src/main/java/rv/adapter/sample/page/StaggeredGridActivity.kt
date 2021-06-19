package rv.adapter.sample.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityStaggeredManagerBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity

/**
 * by y on 2016/11/17
 */
class StaggeredGridActivity :
    BaseActivity<ActivityStaggeredManagerBinding>(
        R.layout.activity_staggered_manager,
        "StaggeredGridLayoutManagerSample"
    ) {

    override fun onCreateViewBinding(rootView: View): ActivityStaggeredManagerBinding {
        return ActivityStaggeredManagerBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.include.recyclerView
            .staggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            .attachXAdapter<SampleEntity>()
            .setItemLayoutId(R.layout.layout_json_item)
            .openLoadingMore()
            .setOnBind<SampleEntity> { holder, _, entity ->
                Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
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
