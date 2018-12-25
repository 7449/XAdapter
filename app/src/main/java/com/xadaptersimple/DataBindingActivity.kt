package com.xadaptersimple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.xadapter.*
import com.xadapter.adapter.XDataBindingAdapter
import com.xadapter.adapter.XDataBindingAdapterFactory
import com.xadapter.holder.XViewHolder
import com.xadaptersimple.data.DataUtils
import com.xadaptersimple.data.MainBean
import com.xadaptersimple.databinding.DatabindingLayoutBinding
import com.xadaptersimple.view.LoadMoreView
import com.xadaptersimple.view.RefreshView

/**
 * @author y
 * @create 2018/12/25
 */
class DataBindingActivity : AppCompatActivity(), OnItemLongClickListener<MainBean>, OnXBindListener<MainBean>,
        OnItemClickListener<MainBean>, OnXAdapterListener {

    private lateinit var binding: DatabindingLayoutBinding
    private lateinit var xRecyclerViewAdapter: XDataBindingAdapter<MainBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBeen = ObservableArrayList<MainBean>()
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
            onXBindListener = this@DataBindingActivity
            onLongClickListener = this@DataBindingActivity
            onItemClickListener = this@DataBindingActivity
            xAdapterListener = this@DataBindingActivity
            itemLayoutId = R.layout.item_databinding
            addAll(mainBeen)
        }
    }

    override fun onXBind(holder: XViewHolder, position: Int, entity: MainBean) {
        holder.setTextView(R.id.tv_name, entity.name)
        holder.setTextView(R.id.tv_age, entity.age.toString() + "")
    }

    override fun onXRefresh() {
        binding.recyclerView.postDelayed({
            xRecyclerViewAdapter.refreshState = XRefreshView.SUCCESS
            Toast.makeText(baseContext, "refresh...", Toast.LENGTH_SHORT).show()
        }, 1500)
    }

    override fun onXLoadMore() {
        binding.recyclerView.postDelayed({
            xRecyclerViewAdapter.loadMoreState = XLoadMoreView.ERROR
            Toast.makeText(baseContext, "loadMore...", Toast.LENGTH_SHORT).show()
        }, 1500)
    }

    override fun onItemClick(view: View, position: Int, entity: MainBean) {
        Toast.makeText(baseContext, "name:  $entity.name  age:  $entity.age  position:  $position", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(view: View, position: Int, entity: MainBean) {
        Toast.makeText(baseContext, "onLongClick...", Toast.LENGTH_SHORT).show()
    }
}
