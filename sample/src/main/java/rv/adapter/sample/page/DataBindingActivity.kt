package rv.adapter.sample.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import rv.adapter.data.binding.XDataBindingAdapter
import rv.adapter.layout.LayoutStatus
import rv.adapter.layout.XLoadMoreStatus
import rv.adapter.layout.XRefreshStatus
import rv.adapter.recyclerview.convertDataBindingAdapter
import rv.adapter.recyclerview.setLoadMoreStatus
import rv.adapter.recyclerview.setRefreshStatus
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityDatabindingBinding
import rv.adapter.sample.json.JsonUtils
import rv.adapter.sample.json.SampleEntity

/**
 * @author y
 * @create 2018/12/25
 */
class DataBindingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatabindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<Toolbar>(R.id.toolbar).title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.layoutManager = LinearLayoutManager(this)
        binding.recyclerView
            .convertDataBindingAdapter(XDataBindingAdapter<SampleEntity>(BR.entity))
            .setItemLayoutId(R.layout.item_databinding)
            .setScrollLoadMoreItemCount(10)
            .openPullRefresh()
            .openLoadingMore()
            .addHeaderView(
                LayoutInflater.from(applicationContext)
                    .inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false)
            )
            .addHeaderView(
                LayoutInflater.from(applicationContext)
                    .inflate(R.layout.adapter_header_2, findViewById(android.R.id.content), false)
            )
            .addHeaderView(
                LayoutInflater.from(applicationContext)
                    .inflate(R.layout.adapter_header_3, findViewById(android.R.id.content), false)
            )
            .addFooterView(
                LayoutInflater.from(applicationContext)
                    .inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false)
            )
            .addFooterView(
                LayoutInflater.from(applicationContext)
                    .inflate(R.layout.adapter_footer_2, findViewById(android.R.id.content), false)
            )
            .addFooterView(
                LayoutInflater.from(applicationContext)
                    .inflate(R.layout.adapter_footer_3, findViewById(android.R.id.content), false)
            )
            .setOnItemClickListener { _, position, _ ->
                Toast.makeText(baseContext, "position:$position", Toast.LENGTH_SHORT).show()
            }
            .setOnItemLongClickListener { _, _, _ ->
                Toast.makeText(baseContext, "onLongClick", Toast.LENGTH_SHORT).show()
                true
            }
            .setRefreshListener {
                binding.recyclerView.postDelayed({
                    binding.recyclerView.setRefreshStatus(LayoutStatus.SUCCESS)
                }, 4000)
            }
            .setLoadMoreListener {
                binding.recyclerView.postDelayed({
                    binding.recyclerView.setLoadMoreStatus(LayoutStatus.ERROR)
                }, 4000)
            }
            .addAll(JsonUtils.jsonList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
