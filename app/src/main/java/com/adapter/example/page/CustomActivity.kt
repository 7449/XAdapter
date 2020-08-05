package com.adapter.example.page

import android.os.Bundle
import android.widget.Toast
import com.adapter.example.R
import com.adapter.example.custom.CustomLoadMoreView
import com.adapter.example.custom.CustomOnScrollListener
import com.adapter.example.custom.CustomRefreshView
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.refresh.Callback
import kotlinx.android.synthetic.main.layout_recyclerview.*

class CustomActivity : BaseActivity(R.layout.activity_custom, "CustomSample") {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView
                .linearLayoutManager()
                .attachAdapter<SampleEntity>()
                .customRefreshCallback(CustomRefreshView(applicationContext))
                .customLoadMoreCallback(CustomLoadMoreView(applicationContext))
                .customScrollListener(CustomOnScrollListener())
                .setItemLayoutId(R.layout.layout_json_item)
                .openLoadingMore()
                .openPullRefresh()
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .setOnItemClickListener<SampleEntity> { _, position, _ ->
                    Toast.makeText(baseContext, "position:  $position", Toast.LENGTH_SHORT).show()
                }
                .setOnItemLongClickListener<SampleEntity> { _, _, _ ->
                    Toast.makeText(baseContext, "onLongClick", Toast.LENGTH_SHORT).show()
                    true
                }
                .setRefreshListener {
                    this@CustomActivity.recyclerView.postDelayed({
                        it.setRefreshState(Callback.SUCCESS)
                    }, 1500)
                }
                .setLoadMoreListener {
                    this@CustomActivity.recyclerView.postDelayed({
                        it.setLoadMoreState(Callback.ERROR)
                    }, 1500)
                }
                .addAll(JsonUtils.jsonList)
    }

}