package rv.adapter.sample.page

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivitySampleBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity

class SampleActivity :
    BaseActivity<ActivitySampleBinding>(R.layout.activity_sample, "SampleAdapter") {

    override fun onCreateViewBinding(rootView: View): ActivitySampleBinding {
        return ActivitySampleBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.include.recyclerView
            .linearLayoutManager()
            .attachXAdapter<SampleEntity>()
            .setItemLayoutId(R.layout.layout_json_item)
            .setOnBind<SampleEntity> { holder, _, entity ->
                Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
            }
            .addAll(JsonUtils.jsonList)
    }

}