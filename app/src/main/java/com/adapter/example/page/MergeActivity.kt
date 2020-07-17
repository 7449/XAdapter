package com.adapter.example.page

import com.adapter.example.R

class MergeActivity : BaseActivity(R.layout.activity_merge, "MergeAdapterSample") {

//    private val headerAdapter by lazy {
//        XAdapter<String>()
//                .setItemLayoutId(R.layout.adapter_header_1)
//                .setOnBind { holder, position, entity ->
//                }
//                .apply {
//                    addAll(ArrayList<String>().apply {
//                        this.add("header")
//                    })
//                }
//    }
//
//    private val contentAdapter by lazy {
//        XAdapter<SampleEntity>()
//                .setItemLayoutId(R.layout.layout_json_item)
//                .setOnBind { holder, _, entity ->
//                    Glide.with(holder.context).load(entity.image).into(holder.imageView(R.id.image))
//                    holder.setText(R.id.title, entity.title)
//                }
//                .apply { addAll(JsonUtils.jsonList) }
//    }
//
//    private val footerAdapter by lazy {
//        XAdapter<String>()
//                .setItemLayoutId(R.layout.adapter_footer_1)
//                .setOnBind { holder, position, entity ->
//                }
//                .apply {
//                    addAll(ArrayList<String>().apply {
//                        this.add("footer")
//                    })
//                }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val mergeAdapter = MergeAdapter(listOf(headerAdapter, contentAdapter, footerAdapter))
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = mergeAdapter
//    }

}