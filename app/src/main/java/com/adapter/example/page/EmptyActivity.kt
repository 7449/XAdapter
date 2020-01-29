package com.adapter.example.page

import android.os.Bundle
import android.widget.Toast
import com.adapter.example.R
import com.adapter.example.custom.CustomEmptyView
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.refresh.XLoadMoreView
import com.xadapter.refresh.XRefreshView
import com.xadapter.vh.getContext
import com.xadapter.vh.getImageView
import com.xadapter.vh.setText
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
                    Glide.with(holder.getContext()).load(entity.image).into(holder.getImageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .setRefreshListener {
                    this@EmptyActivity.recyclerView.postDelayed({
                        it.setRefreshState(XRefreshView.SUCCESS)
                        recyclerView.removeAll()
                        recyclerView.addAll(JsonUtils.jsonList)
                    }, 1500)
                }
                .setLoadMoreListener {
                    this@EmptyActivity.recyclerView.postDelayed({
                        it.setLoadMoreState(XLoadMoreView.ERROR)
                    }, 1500)
                }
                .setOnEmptyViewClickListener {
                    Toast.makeText(baseContext, "EmptyView", Toast.LENGTH_SHORT).show()
                }
                .addAll(ArrayList())
    }

}