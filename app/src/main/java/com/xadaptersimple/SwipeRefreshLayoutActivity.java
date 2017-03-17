package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadaptersimple.net.NetWorkActivity;
import com.xadaptersimple.net.NetWorkBean;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.xadaptersimple.net.NetWorkActivity.initRetrofit;

/**
 * by y on 2016/11/22
 */

public class SwipeRefreshLayoutActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, OnXBindListener<NetWorkBean.TngouBean> {

    private XRecyclerViewAdapter<NetWorkBean.TngouBean> xRecyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srf_layout);
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(xRecyclerViewAdapter
                .setEmptyView(findViewById(R.id.emptyView))
                .initXData(new ArrayList<NetWorkBean.TngouBean>())
                .addRecyclerView(recyclerView)
                .setLayoutId(R.layout.network_item)
                .onXBind(this)
        );
    }

    @Override
    public void onRefresh() {
        xRecyclerViewAdapter.removeAll();
        netWork();
    }

    @Override
    public void onXBind(XViewHolder holder, int position, NetWorkBean.TngouBean tngouBean) {
        holder.setTextView(R.id.tv_title, tngouBean.getTitle());
        Glide.with(getApplicationContext()).load("http://tnfs.tngou.net/image" + tngouBean.getImg()).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).centerCrop().into(holder.getImageView(R.id.image));
    }

    private void netWork() {
        initRetrofit()
                .create(NetWorkActivity.NewsService.class)
                .getNewsList(1, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NetWorkBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        swipeRefreshLayout.setRefreshing(true);
                    }

                    @Override
                    public void onCompleted() {
                        swipeRefreshLayout.setRefreshing(false);
                        xRecyclerViewAdapter.isShowEmptyView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);
                        xRecyclerViewAdapter.isShowEmptyView();
                        Toast.makeText(getApplicationContext(), "error network:" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(NetWorkBean netWorkBean) {
                        xRecyclerViewAdapter.addAllData(netWorkBean.getTngou());
                    }
                });
    }
}
