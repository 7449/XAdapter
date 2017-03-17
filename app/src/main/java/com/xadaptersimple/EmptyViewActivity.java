package com.xadaptersimple;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xadapter.LoadListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.widget.HeaderLayout;
import com.xadaptersimple.data.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/11/17
 */

public class EmptyViewActivity extends AppCompatActivity implements LoadListener {

    private XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<MainBean> mainBean = new ArrayList<>();
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        View viewById = findViewById(R.id.emptyView);
//        viewById.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(EmptyViewActivity.this, "emptyView", Toast.LENGTH_SHORT).show();
//            }
//        });
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBean)
                        .addRecyclerView(recyclerView)
                        .setEmptyView(viewById, true)
                        .setPullRefreshEnabled(true)
                        .setLoadListener(this)
                        .setLayoutId(R.layout.item)
        );

        xRecyclerViewAdapter.isShowEmptyView();
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getApplicationContext(), "onRefresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerViewAdapter.refreshComplete(HeaderLayout.STATE_DONE);
                xRecyclerViewAdapter.isShowEmptyView();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {

    }
}
