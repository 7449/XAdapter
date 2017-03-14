package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.widget.HeaderLayout;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2016/11/17
 */
public class NetWorkActivity extends AppCompatActivity
        implements XBaseAdapter.LoadListener, XBaseAdapter.OnXBindListener<NetWorkBean.TngouBean> {

    private XRecyclerViewAdapter<NetWorkBean.TngouBean> xRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(xRecyclerViewAdapter
                .initXData(new ArrayList<NetWorkBean.TngouBean>())
                .setEmptyView(findViewById(R.id.emptyView))
                .addRecyclerView(recyclerView)
                .setLayoutId(R.layout.network_item)
                .onXBind(this)
                .setPullRefreshEnabled(true)
                .setLoadingMoreEnabled(true)
                .setLoadListener(this)
                .setRefreshing(true)
        );
    }


    @Override
    public void onRefresh() {
        xRecyclerViewAdapter.removeAll();
        netWork();
    }

    @Override
    public void onLoadMore() {
        netWork();
    }

    private void netWork() {
        initRetrofit()
                .create(NewsService.class)
                .getNewsList(1, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NetWorkBean>() {
                    @Override
                    public void onCompleted() {
                        xRecyclerViewAdapter.refreshComplete(HeaderLayout.STATE_DONE);
                        xRecyclerViewAdapter.isShowEmptyView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "error network", Toast.LENGTH_SHORT).show();
                        xRecyclerViewAdapter.refreshComplete(HeaderLayout.STATE_ERROR);
                        xRecyclerViewAdapter.isShowEmptyView();
                    }

                    @Override
                    public void onNext(NetWorkBean netWorkBean) {
                        xRecyclerViewAdapter.addAllData(netWorkBean.getTngou());
                    }
                });
    }

    @Override
    public void onXBind(XViewHolder holder, int position, NetWorkBean.TngouBean tngouBean) {
        holder.setTextView(R.id.tv_title, tngouBean.getTitle());
        Glide.with(getApplicationContext()).load("http://tnfs.tngou.net/image" + tngouBean.getImg()).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).centerCrop().into(holder.getImageView(R.id.image));
    }

    public interface NewsService {
        @GET("api/top/list")
        Observable<NetWorkBean> getNewsList(@Query("id") int id, @Query("page") int page);
    }

    public static Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl("http://www.tngou.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
