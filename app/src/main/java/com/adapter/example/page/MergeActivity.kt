package com.adapter.example.page

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.adapter.example.R
import com.adapter.example.json.JsonUtils
import com.adapter.example.json.SampleEntity
import com.bumptech.glide.Glide
import com.xadapter.adapter.XAdapter
import com.xadapter.vh.context
import com.xadapter.vh.imageView
import com.xadapter.vh.setText
import kotlinx.android.synthetic.main.layout_recyclerview.*

class MergeActivity : BaseActivity(R.layout.activity_merge, "MergeAdapterSample") {

    private val headerAdapter by lazy {
        XAdapter<String>()
                .setItemLayoutId(R.layout.adapter_header_1)
                .setOnBind { holder, position, entity ->
                }
                .apply {
                    addAll(ArrayList<String>().apply {
                        this.add("header")
                    })
                }
    }

    private val contentAdapter by lazy {
        XAdapter<SampleEntity>()
                .setItemLayoutId(R.layout.layout_json_item)
                .setOnBind { holder, _, entity ->
                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
                }
                .apply { addAll(JsonUtils.jsonList) }
    }

    private val footerAdapter by lazy {
        XAdapter<String>()
                .setItemLayoutId(R.layout.adapter_footer_1)
                .setOnBind { holder, position, entity ->
                }
                .apply {
                    addAll(ArrayList<String>().apply {
                        this.add("footer")
                    })
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mergeAdapter = MergeAdapter(listOf(headerAdapter, contentAdapter, footerAdapter))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mergeAdapter
    }

}