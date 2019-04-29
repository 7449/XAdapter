package com.adapter.example.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.example.R
import com.adapter.example.SimpleRefreshAdapter
import com.adapter.example.net.DataModel
import com.adapter.example.net.NetApi
import com.adapter.example.net.NetWorkBean
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xadapter.addAll
import com.xadapter.holder.getContext
import com.xadapter.holder.getImageView
import com.xadapter.holder.setText
import com.xadapter.removeAll
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import kotlinx.android.synthetic.main.activity_swipe.*

/**
 * @author y
 */
class RefreshLayoutActivity : AppCompatActivity(), RxNetWorkListener<NetWorkBean> {

    private var page = 0

    private lateinit var mAdapter: SimpleRefreshAdapter<DataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        recyclerView.setHasFixedSize(true)

        mAdapter = SimpleRefreshAdapter(srf_layout)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = mAdapter
                .setOnLoadMoreRetry { netWork() }
                .apply {
                    onXBindListener = { holder, position, entity ->
                        Glide
                                .with(holder.getContext())
                                .load(entity.title_image)
                                .apply(option)
                                .into(holder.getImageView(R.id.list_image))
                        holder.setText(R.id.list_tv, entity.title)
                    }
                    recyclerView = this@RefreshLayoutActivity.recyclerView
                    itemLayoutId = R.layout.network_item
                    loadingMoreEnabled = true
                    xRefreshListener = {
                        page = 0
                        mAdapter.removeAll()
                        netWork()
                    }
                    xLoadMoreListener = {
                        netWork()
                    }
                }
                .refresh()
    }

    override fun onNetWorkStart() {
    }

    override fun onNetWorkComplete() {
        mAdapter.onComplete(if (page == 0) SimpleRefreshAdapter.TYPE_REFRESH else SimpleRefreshAdapter.TYPE_LOAD_MORE)
        page++
    }

    override fun onNetWorkError(e: Throwable) {
        mAdapter.onError(if (page == 0) SimpleRefreshAdapter.TYPE_REFRESH else SimpleRefreshAdapter.TYPE_LOAD_MORE)
    }

    override fun onNetWorkSuccess(data: NetWorkBean) {
        mAdapter.addAll(data.data)
    }

    private var option = RequestOptions().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).centerCrop()

    private fun netWork() {
        RxNetWork.instance
                .getApi(javaClass.simpleName,
                        RxNetWork.observable(NetApi.ZLService::class.java)
                                .getList("daily", 20, 0), this)
    }
}
