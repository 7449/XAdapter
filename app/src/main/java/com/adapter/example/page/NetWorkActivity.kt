package com.adapter.example.page

import android.os.Bundle
import com.adapter.example.R
import com.adapter.example.json.SampleEntity
import com.adapter.example.json.StoriesEntity
import com.bumptech.glide.Glide
import com.xadapter.recyclerview.*
import com.xadapter.refresh.XLoadMoreView
import com.xadapter.vh.context
import com.xadapter.vh.imageView
import com.xadapter.vh.setText
import io.reactivex.Observable
import io.reactivex.network.*
import kotlinx.android.synthetic.main.layout_recyclerview.*
import retrofit2.http.GET

/**
 * by y on 2016/11/17
 */
class NetWorkActivity : BaseActivity(R.layout.activity_network, "NetWorkSample"), RxRequestCallback<StoriesEntity> {

    interface ZLService {
        @GET("news/latest")
        fun getList(): Observable<StoriesEntity>
    }

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView
                .linearLayoutManager()
                .attachAdapter<SampleEntity>()
                .openLoadingMore()
                .openPullRefresh()
                .setItemLayoutId(R.layout.layout_json_item)
                .setOnBind<SampleEntity> { holder, _, entity ->
                    Glide
                            .with(holder.context)
                            .load(entity.image)
                            .into(holder.imageView(R.id.image))
                    holder.setText(R.id.title, entity.title)
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
        ZLService::class.java
                .create()
                .getList()
                .cancel(javaClass.simpleName)
                .request(javaClass.simpleName, this)
    }

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

    override fun onNetWorkSuccess(data: StoriesEntity) {
        recyclerView.addAll(data.stories)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.instance.cancel(javaClass.simpleName)
    }
}
