package rv.adapter.sample.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import rv.adapter.layout.LayoutStatus
import rv.adapter.recyclerview.*
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityLinearManagerBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity

/**
 * by y on 2016/11/17
 */
class LinearLayoutActivity :
    BaseActivity<ActivityLinearManagerBinding>(
        R.layout.activity_linear_manager,
        "LinearLayoutManagerSample"
    ) {

    override fun onCreateViewBinding(rootView: View): ActivityLinearManagerBinding {
        return ActivityLinearManagerBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding.include.recyclerView
            .linearLayoutManager()
            .fixedSize()
            .setAdapter<SampleEntity> {
                loadingMore = true
                pullRefresh = true
                itemLayoutId = R.layout.layout_json_item
                addHeaderViews(
                    LayoutInflater.from(applicationContext).inflate(
                        R.layout.adapter_header_1,
                        findViewById(android.R.id.content),
                        false
                    ),
                    LayoutInflater.from(applicationContext).inflate(
                        R.layout.adapter_header_2,
                        findViewById(android.R.id.content),
                        false
                    ),
                    LayoutInflater.from(applicationContext).inflate(
                        R.layout.adapter_header_3,
                        findViewById(android.R.id.content),
                        false
                    )
                )
                addFooterViews(
                    LayoutInflater.from(applicationContext).inflate(
                        R.layout.adapter_footer_1,
                        findViewById(android.R.id.content),
                        false
                    ),
                    LayoutInflater.from(applicationContext).inflate(
                        R.layout.adapter_footer_2,
                        findViewById(android.R.id.content),
                        false
                    ),
                    LayoutInflater.from(applicationContext).inflate(
                        R.layout.adapter_footer_3,
                        findViewById(android.R.id.content),
                        false
                    )
                )
                onBind { holder, position, entity ->
                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                onItemLongClickListener { view, position, entity ->
                    Toast.makeText(baseContext, "onLongClick", Toast.LENGTH_SHORT).show()
                    true
                }
                onItemClickListener { view, position, entity ->
                    Toast.makeText(baseContext, "position:  $position", Toast.LENGTH_SHORT).show()
                }
                refreshListener {
                    this@LinearLayoutActivity.viewBinding.include.recyclerView.postDelayed({
                        it.setRefreshStatus(LayoutStatus.SUCCESS)
                    }, 1500)
                }
                loadMoreListener {
                    this@LinearLayoutActivity.viewBinding.include.recyclerView.postDelayed({
                        it.setLoadingMoreStatus(LayoutStatus.ERROR)
                    }, 1500)
                }
            }
            .addAll(JsonUtils.jsonList)
        viewBinding.include.recyclerView.getHeaderView(0).setOnClickListener {
            Toast.makeText(baseContext, "HeaderView", Toast.LENGTH_SHORT).show()
        }
    }
}
