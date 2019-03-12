package com.xadaptersimple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xadapter.holder.XViewHolder
import com.xadapter.listener.OnXLoadMoreRetryListener
import com.xadapter.listener.OnXAdapterListener
import com.xadapter.listener.OnXBindListener
import com.xadaptersimple.net.DataModel
import com.xadaptersimple.net.NetApi
import com.xadaptersimple.net.NetWorkBean
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import kotlinx.android.synthetic.main.activity_swipe.*

/**
 * @author y
 */
class RefreshLayoutActivity : AppCompatActivity(),
        OnXAdapterListener,
        OnXBindListener<DataModel>,
        RxNetWorkListener<NetWorkBean>, OnXLoadMoreRetryListener {

    private var page = 0

    private lateinit var mAdapter: SimpleRefreshAdapter<DataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        recyclerView.setHasFixedSize(true)

        mAdapter = SimpleRefreshAdapter(srf_layout)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = mAdapter
                .setOnLoadMoreRetry(this)
                .apply {
                    onXBindListener = this@RefreshLayoutActivity
                    recyclerView = this@RefreshLayoutActivity.recyclerView
                    itemLayoutId = R.layout.network_item
                    loadingMoreEnabled = true
                    xAdapterListener = this@RefreshLayoutActivity
                }
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

    override fun onNetWorkSuccess(data: NetWorkBean) {
        mAdapter.addAll(data.data)
    }

    private var option = RequestOptions().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).centerCrop()

    override fun onXBind(holder: XViewHolder, position: Int, entity: DataModel) {
        Glide
                .with(holder.context)
                .load(entity.title_image)
                .apply(option)
                .into(holder.getImageView(R.id.list_image))
        holder.setTextView(R.id.list_tv, entity.title)
    }

    private fun netWork() {
        RxNetWork.instance
                .getApi(javaClass.simpleName,
                        RxNetWork.observable(NetApi.ZLService::class.java)
                                .getList("daily", 20, 0), this)
    }
}
