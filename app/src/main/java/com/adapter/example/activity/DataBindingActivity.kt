package com.adapter.example.activity

import android.os.Bundle
import android.util.Log
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
import com.xadapter.XDataBindingAdapterFactory
import com.xadapter.adapter.XDataBindingAdapter
import com.xadapter.addAll
import com.xadapter.defaultAdapter
import com.xadapter.observableArrayList
import com.xadapter.widget.XLoadMoreView
import com.xadapter.widget.XRefreshView

/**
 * @author y
 * @create 2018/12/25
 */
class DataBindingActivity : AppCompatActivity() {

    private lateinit var binding: DatabindingLayoutBinding
    private lateinit var xRecyclerViewAdapter: XDataBindingAdapter<ExampleBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBeen = ObservableArrayList<ExampleBean>()
        DataUtils.getData(mainBeen)
        binding = DataBindingUtil.setContentView(this, R.layout.databinding_layout)
        xRecyclerViewAdapter = XDataBindingAdapterFactory(BR.entity)
        binding.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = xRecyclerViewAdapter.apply {
            loadMoreView = LoadMoreView(applicationContext)
            refreshView = RefreshView(applicationContext)
            recyclerView = binding.recyclerView
            pullRefreshEnabled = true
            loadingMoreEnabled = true
            scrollLoadMoreItemCount = 10
            headerViewContainer.apply {
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_1, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_2, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_header_3, findViewById(android.R.id.content), false))
            }
            footerViewContainer.apply {
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_1, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_2, findViewById(android.R.id.content), false))
                add(LayoutInflater.from(applicationContext).inflate(R.layout.item_footer_3, findViewById(android.R.id.content), false))
            }
            onXBindListener = { xViewHolder, i, mainBean ->
            }
            onXLongClickListener = { _, _, _ ->
                Toast.makeText(baseContext, "onLongClick...", Toast.LENGTH_SHORT).show()
                true
            }
            onXItemClickListener = { _, position, entity ->
                Toast.makeText(baseContext, "name:  $entity.name  age:  $entity.age  position:  $position", Toast.LENGTH_SHORT).show()
            }
            itemLayoutId = R.layout.item_databinding
            addAll(mainBeen)
        }

        Log.d("DataBindingActivity", xRecyclerViewAdapter.observableArrayList()::class.java.simpleName)

        binding.recyclerView.defaultAdapter<ExampleBean>().xRefreshListener = {
            binding.recyclerView.postDelayed({
                xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
                Toast.makeText(baseContext, "refresh...", Toast.LENGTH_SHORT).show()
            }, 1500)
        }
        binding.recyclerView.defaultAdapter<ExampleBean>().xLoadMoreListener = {
            binding.recyclerView.postDelayed({
                xRecyclerViewAdapter.loadMoreState = XLoadMoreView.ERROR
                Toast.makeText(baseContext, "loadMore...", Toast.LENGTH_SHORT).show()
            }, 1500)
        }
    }
}
