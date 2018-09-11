package com.xadaptersimple

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnLoadMoreRetryListener
import com.xadapter.listener.OnXAdapterListener
import com.xadapter.listener.OnXBindListener
import com.xadapter.simple.SimpleRefreshAdapter
import com.xadaptersimple.net.NetApi
import com.xadaptersimple.net.NetWorkBean
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener

/**
 * @author y
 */
class RefreshLayoutActivity : AppCompatActivity(),
        OnXAdapterListener,
        OnXBindListener<NetWorkBean>,
        RxNetWorkListener<List<NetWorkBean>>, OnLoadMoreRetryListener {


    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private var page = 0

    private lateinit var mAdapter: SimpleRefreshAdapter<NetWorkBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        swipeRefreshLayout = findViewById(R.id.srf_layout)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)

        mAdapter = SimpleRefreshAdapter(swipeRefreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = mAdapter
                .setOnLoadMoreRetry(this)
                .addRecyclerView(recyclerView)
                .setOnXBind(this)
                .apply {
                    itemLayoutId = R.layout.network_item
                    pullRefreshEnabled = true
                }
                .setOnXAdapterListener(this)
                .refresh()
    }

    override fun onXRefresh() {
        page = 0
        mAdapter.removeAll()
        netWork()
    }

    override fun onXLoadMore() {
        netWork()
    }

    override fun onXLoadMoreRetry() {
        onXLoadMore()
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

    override fun onNetWorkSuccess(data: List<NetWorkBean>) {
        mAdapter.addAll(data)
    }

    override fun onXBind(holder: XViewHolder, position: Int, entity: NetWorkBean) {
        Glide
                .with(holder.context)
                .load(entity.titleImage)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.getImageView(R.id.list_image))
        holder.setTextView(R.id.list_tv, entity.title)
    }

    private fun netWork() {
        RxNetWork.instance
                .setBaseUrl(NetApi.ZL_BASE_API)
                .getApi(javaClass.simpleName,
                        RxNetWork.observable(NetApi.ZLService::class.java)
                                .getList("daily", 20, 0), this)
    }
}
