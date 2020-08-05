package com.adapter.example.page

import android.os.Bundle
import android.widget.Toast
import com.adapter.example.R
import com.adapter.example.custom.CustomEmptyView
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.refresh.Callback
import kotlinx.android.synthetic.main.layout_recyclerview.*

class EmptyActivity : BaseActivity(R.layout.activity_empty, "EmptyAdapter") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView
                .linearLayoutManager()
                .attachAdapter<SampleEntity>()
                .openPullRefresh()
                .openLoadingMore()
                .setEmptyView(CustomEmptyView(applicationContext))
                .setItemLayoutId(R.layout.layout_json_item)
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .setRefreshListener {
                    this@EmptyActivity.recyclerView.postDelayed({
                        it.setRefreshState(Callback.SUCCESS)
                        recyclerView.removeAll()
                        recyclerView.addAll(JsonUtils.jsonList)
                    }, 1500)
                }
                .setLoadMoreListener {
                    this@EmptyActivity.recyclerView.postDelayed({
                        it.setLoadMoreState(Callback.ERROR)
                    }, 1500)
                }
                .addAll(ArrayList())
    }

}