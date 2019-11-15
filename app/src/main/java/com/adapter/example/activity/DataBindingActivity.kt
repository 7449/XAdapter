package com.adapter.example.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.example.R
import com.adapter.example.data.DataUtils
import com.adapter.example.data.ExampleBean
import com.adapter.example.databinding.DatabindingLayoutBinding
import com.adapter.example.view.LoadMoreView
import com.adapter.example.view.RefreshView
import com.xadapter.*
import com.xadapter.recyclerview.adapter
import com.xadapter.recyclerview.addAll
import com.xadapter.recyclerview.addFooterView
import com.xadapter.recyclerview.addHeaderView
import com.xadapter.recyclerview.attachDataBindingAdapter
import com.xadapter.recyclerview.customLoadMoreView
import com.xadapter.recyclerview.customRefreshView
import com.xadapter.recyclerview.openLoadingMore
import com.xadapter.recyclerview.openPullRefresh
import com.xadapter.recyclerview.setFooterListener
import com.xadapter.recyclerview.setItemLayoutId
import com.xadapter.recyclerview.setLoadMoreListener
import com.xadapter.recyclerview.setOnItemClickListener
import com.xadapter.recyclerview.setOnItemLongClickListener
import com.xadapter.recyclerview.setRefreshListener
import com.xadapter.recyclerview.setScrollLoadMoreItemCount
import com.xadapter.setLoadMoreState

/**
 * @author y
 * @create 2018/12/25
 */
class DataBindingActivity : AppCompatActivity() {

    private lateinit var binding: DatabindingLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBeen = ObservableArrayList<ExampleBean>()
        DataUtils.getData(mainBeen)
        binding = DataBindingUtil.setContentView(this, R.layout.databinding_layout)
        binding.layoutManager = LinearLayoutManager(this)
        binding.recyclerView
                .attachDataBindingAdapter(XDataBindingAdapterFactory<ExampleBean>(BR.entity))
                .setItemLayoutId(R.layout.item_databinding)
                .customLoadMoreView(LoadMoreView(applicationContext))
                .customRefreshView(RefreshView(applicationContext))
                .setScrollLoadMoreItemCount(10)
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_2, findViewById(android.R.id.content), false))
                .addHeaderView(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_3, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_2, findViewById(android.R.id.content), false))
                .addFooterView(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_3, findViewById(android.R.id.content), false))
                .openPullRefresh()
                .openLoadingMore()
                .setOnItemClickListener<ExampleBean> { _, position, entity ->
                    Toast.makeText(baseContext, "name:  $entity.name  age:  $entity.age  position:  $position", Toast.LENGTH_SHORT).show()
                }
                .setOnItemLongClickListener<ExampleBean> { _, _, _ ->
                    Toast.makeText(baseContext, "onLongClick...", Toast.LENGTH_SHORT).show()
                    true
                }
                .setRefreshListener {
                    binding.recyclerView.postDelayed({
                        binding.recyclerView.adapter<ExampleBean>().setRefreshState(XRefreshView.SUCCESS)
                        Toast.makeText(baseContext, "refresh...", Toast.LENGTH_SHORT).show()
                    }, 1500)
                }
                .setLoadMoreListener {
                    binding.recyclerView.postDelayed({
                        binding.recyclerView.adapter<ExampleBean>().setLoadMoreState(XLoadMoreView.ERROR)
                        Toast.makeText(baseContext, "loadMore...", Toast.LENGTH_SHORT).show()
                    }, 1500)
                }
                .setFooterListener { _, adapter ->
                    Toast.makeText(baseContext, adapter.loadMoreState.toString(), Toast.LENGTH_SHORT).show()
                }
                .addAll(mainBeen)
    }
}
