package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.xadapter.LoadListener;
import com.xadapter.LoadMoreText;
import com.xadapter.OnXBindListener;
import com.xadapter.RefreshText;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.widget.LoadMore;
import com.xadapter.widget.Refresh;
import com.xadaptersimple.data.DataUtils;
import com.xadaptersimple.data.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/6/19.
 */

public class TextActivity extends AppCompatActivity implements OnXBindListener<MainBean>,
        LoadListener {
    private XRecyclerViewAdapter<MainBean> xRecyclerViewAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<MainBean> mainBeen = new ArrayList<>();
        DataUtils.getData(mainBeen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(mainBeen)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item)
                        .setPullRefreshEnabled(true)
                        .setRefreshText(new RefreshTextSimple())
                        .setLoadMoreText(new LoadMoreTextSimple())
                        .setLoadingMoreEnabled(true)
                        .onXBind(this)
                        .setLoadListener(this)
        );
    }


    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerViewAdapter.refreshComplete(Refresh.COMPLETE);
                Toast.makeText(getBaseContext(), "refresh...", Toast.LENGTH_SHORT).show();
            }
        }, 1500);
    }

    @Override
    public void onLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerViewAdapter.loadMoreComplete(LoadMore.ERROR);
                Toast.makeText(getBaseContext(), "loadMore...", Toast.LENGTH_SHORT).show();
            }
        }, 1500);
    }

    @Override
    public void onXBind(XViewHolder holder, int position, MainBean mainBean) {
        holder.setTextView(R.id.tv_name, mainBean.getName());
        holder.setTextView(R.id.tv_age, mainBean.getAge() + "");
    }


    private static class RefreshTextSimple implements RefreshText {

        /**
         * @return 初始状态
         */
        @Override
        public int normalText() {
            return R.string.app_name;
        }

        /**
         * @return 下拉到刷新临界点提示语
         */
        @Override
        public int readyText() {
            return R.string.app_name;
        }

        /**
         * @return 正在刷新
         */
        @Override
        public int refreshText() {
            return R.string.app_name;
        }

        /**
         * @return 刷新成功
         */
        @Override
        public int completeText() {
            return R.string.app_name;
        }

        /**
         * @return 刷新错误
         */
        @Override
        public int errorText() {
            return R.string.app_name;
        }
    }

    private static class LoadMoreTextSimple implements LoadMoreText {
        /**
         * @return 正在加载
         */
        @Override
        public int loadText() {
            return R.string.app_name;
        }

        /**
         * @return 加载成功
         */
        @Override
        public int completeText() {
            return R.string.app_name;
        }

        /**
         * @return 没有数据
         */
        @Override
        public int noMoreText() {
            return R.string.app_name;
        }

        /**
         * @return 加载错误
         */
        @Override
        public int errorText() {
            return R.string.app_name;
        }

        /**
         * @return 初始状态
         */
        @Override
        public int normalText() {
            return R.string.app_name;
        }
    }
}
