package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.LoadListener;
import com.xadapter.listener.OnXBindListener;
import com.xadapter.widget.SimpleLoadMore;
import com.xadapter.widget.SimpleRefresh;
import com.xadaptersimple.data.DataUtils;
import com.xadaptersimple.data.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/6/20.
 */

public class TestActivity extends AppCompatActivity implements OnXBindListener<MainBean>, LoadListener {

    private XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<MainBean> mainBeen = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBeen)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item)
                        .setPullRefreshEnabled(true)
                        .setLoadingMoreEnabled(true)
                        .onXBind(this)
                        .setLoadListener(this)
                        .refresh()
        );

    }

    @Override
    public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
        holder.setTextView(R.id.tv_name, mainBean.getName());
        holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
    }

    @Override
    public void onRefresh() {
        xRecyclerViewAdapter.removeAll();
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerViewAdapter.addAllData(DataUtils.getTestData(new ArrayList<MainBean>()));
                xRecyclerViewAdapter.refreshState(SimpleRefresh.SUCCESS);
                if (xRecyclerViewAdapter.getData().size() < 7) {
                    xRecyclerViewAdapter.loadMoreState(SimpleLoadMore.NOMORE);
                }
            }
        }, 1500);
    }

    @Override
    public void onLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (xRecyclerViewAdapter.getData().size() < 7) {
                    xRecyclerViewAdapter.loadMoreState(SimpleLoadMore.NOMORE);
                } else {
                    xRecyclerViewAdapter.loadMoreState(SimpleLoadMore.ERROR);
                }
            }
        }, 1500);
    }
}
