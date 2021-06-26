package rv.adapter.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import rv.adapter.recyclerview.convertAdapter
import rv.adapter.sample.databinding.ActivityMainBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity
import rv.adapter.sample.page.BaseActivity
import rv.adapter.sample.page.DataBindingActivity
import rv.adapter.sample.page.ViewBindingActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main, false) {

    override fun onCreateViewBinding(rootView: View): ActivityMainBinding {
        return ActivityMainBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.dataBinding.setOnClickListener { startActivity(DataBindingActivity::class.java) }
        viewBinding.viewBinding.setOnClickListener { startActivity(ViewBindingActivity::class.java) }
        viewBinding.linear.setOnClickListener {
            bindRecyclerView(LinearLayoutManager(this))
        }
        viewBinding.grid.setOnClickListener {
            bindRecyclerView(GridLayoutManager(this, 2))
        }
        viewBinding.staggered.setOnClickListener {
            bindRecyclerView(StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL))
        }
        viewBinding.linear.performClick()
    }

    private fun bindRecyclerView(layoutManager: RecyclerView.LayoutManager) {
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerView
            .convertAdapter<SampleEntity>()
            .setItemLayoutId(R.layout.layout_json_item)
            .bindItem { holder, _, entity ->
                Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                holder.setText(R.id.title, entity.title)
            }
            .setOnItemClickListener { _, position, _ ->
                Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
            }
            .addAll(JsonUtils.jsonList)
    }

    private fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

}
