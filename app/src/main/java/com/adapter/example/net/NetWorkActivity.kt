package com.adapter.example.net

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adapter.example.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xadapter.recyclerview.*
import com.xadapter.refresh.XLoadMoreView
import com.xadapter.vh.getContext
import com.xadapter.vh.getImageView
import com.xadapter.vh.setText
import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import io.reactivex.network.cancelTag
import io.reactivex.network.getApi
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * by y on 2016/11/17
 */
class NetWorkActivity : AppCompatActivity(), RxNetWorkListener<NetWorkBean> {

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "NetWork Example"
        setContentView(R.layout.recyclerview_layout)
        recyclerView
                .linearLayoutManager()
                .attachAdapter<DataModel>()
                .openLoadingMore()
                .openPullRefresh()
                .setItemLayoutId(R.layout.network_item)
                .setOnBind<DataModel> { holder, _, entity ->
                    Glide
                            .with(holder.getContext())
                            .load(entity.image)
                            .apply(option)
                            .into(holder.getImageView(R.id.list_image))
                    holder.setText(R.id.list_tv, entity.title)
                }
                .setRefreshListener {
                    page = 0
                    recyclerView.removeAll()
                    netWork()
                }
                .setLoadMoreListener {
                    if (page < 1) {
                        netWork()
                        ++page
                    } else {
                        recyclerView.setLoadMoreState(XLoadMoreView.NO_MORE)
                    }
                }
                .refresh()
    }

    private fun netWork() {
        RxNetWork
                .observable(NetApi.ZLService::class.java)
                .getList()
                .cancelTag(javaClass.simpleName)
                .getApi(javaClass.simpleName, this)
    }

    private var option = RequestOptions().error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).centerCrop()

    override fun onNetWorkStart() {
        if (page != 0) {
            recyclerView.setLoadMoreState(XLoadMoreView.LOAD)
        }
    }

    override fun onNetWorkError(e: Throwable) {
        if (page == 0) {
            recyclerView.setRefreshState(XLoadMoreView.ERROR)
        } else {
            recyclerView.setLoadMoreState(XLoadMoreView.ERROR)
        }
    }

    override fun onNetWorkComplete() {
        if (page == 0) {
            recyclerView.setRefreshState(XLoadMoreView.SUCCESS)
        } else {
            recyclerView.setLoadMoreState(XLoadMoreView.SUCCESS)
        }
    }

    override fun onNetWorkSuccess(data: NetWorkBean) {
        recyclerView.addAll(data.top_stories)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.cancelTag(javaClass.simpleName)
    }
}
