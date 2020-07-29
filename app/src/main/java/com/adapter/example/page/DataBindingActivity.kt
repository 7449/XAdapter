package com.adapter.example.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.example.BR
import com.adapter.example.R
import com.adapter.example.databinding.ActivityDatabindingBinding
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.xadapter.databinding.XDataBindingAdapter
import com.xadapter.recyclerview.convertDataBindingAdapter
import com.xadapter.recyclerview.setLoadMoreState
import com.xadapter.recyclerview.setRefreshState
import com.xadapter.refresh.Callback
import kotlinx.android.synthetic.main.activity_databinding.*

/**
 * @author y
 * @create 2018/12/25
 */
class DataBindingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDatabindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding)
        setSupportActionBar(toolbar)
        toolbar.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.layoutManager = LinearLayoutManager(this)
        binding.recyclerView
                .convertDataBindingAdapter(XDataBindingAdapter<SampleEntity>(BR.entity))
                .setItemLayoutId(R.layout.item_databinding)
                .setScrollLoadMoreItemCount(10)
                .openPullRefresh()
                .openLoadingMore()
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_1, findViewById(android.R.id.content), false))
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_2, findViewById(android.R.id.content), false))
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_header_3, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_2, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.adapter_footer_3, findViewById(android.R.id.content), false))
                .setOnItemClickListener { _, position, _ ->
                    Toast.makeText(baseContext, "position:$position", Toast.LENGTH_SHORT).show()
                }
                .setOnItemLongClickListener { _, _, _ ->
                    Toast.makeText(baseContext, "onLongClick", Toast.LENGTH_SHORT).show()
                    true
                }
                .setRefreshListener {
                    binding.recyclerView.postDelayed({
                        binding.recyclerView.setRefreshState(Callback.SUCCESS)
                    }, 4000)
                }
                .setLoadMoreListener {
                    binding.recyclerView.postDelayed({
                        binding.recyclerView.setLoadMoreState(Callback.ERROR)
                    }, 4000)
                }
                .setFooterListener { _, adapter ->
                    Toast.makeText(baseContext, adapter.loadMoreState.toString(), Toast.LENGTH_SHORT).show()
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
