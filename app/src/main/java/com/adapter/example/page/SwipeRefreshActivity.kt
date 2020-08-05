package com.adapter.example.page

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.adapter.example.R
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.refresh.Callback
import kotlinx.android.synthetic.main.activity_swipe_refresh.*
import kotlinx.android.synthetic.main.layout_recyclerview.*

class SwipeRefreshActivity : BaseActivity(R.layout.activity_swipe_refresh, "SwipeRefreshSample"), SwipeRefreshLayout.OnRefreshListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        swipeRefresh.setOnRefreshListener(this)
        recyclerView
                .linearLayoutManager()
                .attachAdapter<SampleEntity>()
                .setItemLayoutId(R.layout.layout_json_item)
                .openLoadingMore()
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .setLoadMoreListener {
                    this@SwipeRefreshActivity.recyclerView.postDelayed({
                        it.setLoadMoreState(Callback.ERROR)
                    }, 1500)
                }
        swipeRefresh.post { onRefresh() }
        // 模拟一下
        swipeRefresh.postDelayed({
            recyclerView.addAll(JsonUtils.jsonList)
        }, 1500)
    }

    override fun onRefresh() {
        if (recyclerView.adapter<SampleEntity>().loadMoreState == Callback.LOAD) {
            return
        }
        swipeRefresh.isRefreshing = true
        recyclerView.postDelayed({
            swipeRefresh.isRefreshing = false
        }, 1500)
    }
}