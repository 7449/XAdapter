package com.xadaptersimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.xadapter.adapter.multi.MultiAdapter;
import com.xadapter.adapter.multi.MultiCallBack;
import com.xadapter.adapter.multi.SimpleMultiItem;
import com.xadapter.adapter.multi.XMultiAdapterListener;
import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.OnItemClickListener;
import com.xadapter.listener.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/1/12.
 */

public class MultipleItemActivity extends AppCompatActivity
        implements
        OnItemClickListener<SimpleMultiItem>,
        OnItemLongClickListener<SimpleMultiItem>, XMultiAdapterListener<SimpleMultiItem> {
    private static final int TYPE_LINE = 1;

    public static List<SimpleMultiItem> initSettingData() {
        List<SimpleMultiItem> list = new ArrayList<>();
        list.add(new SimpleMultiItem(TYPE_LINE));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 0, "头像", R.mipmap.ic_launcher));
        list.add(new SimpleMultiItem(TYPE_LINE));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 1, "收藏", R.mipmap.ic_launcher));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 2, "相册", R.mipmap.ic_launcher));
        list.add(new SimpleMultiItem(TYPE_LINE));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 3, "钱包", R.mipmap.ic_launcher));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 4, "卡包", R.mipmap.ic_launcher));
        list.add(new SimpleMultiItem(TYPE_LINE));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 5, "表情", R.mipmap.ic_launcher));
        list.add(new SimpleMultiItem(TYPE_LINE));
        list.add(new SimpleMultiItem(SimpleMultiItem.TYPE_ITEM, 6, "设置", R.mipmap.ic_launcher));
        return list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(
                new MultiAdapter<>(initSettingData())
                        .setXMultiAdapterListener(this)
                        .setOnItemClickListener(this)
                        .setOnLongClickListener(this));
    }

    @Override
    public void onItemClick(View view, int position, SimpleMultiItem info) {
        Toast.makeText(view.getContext(), "当前 position:  " + position + "  " + info.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view, int position, SimpleMultiItem info) {
        Toast.makeText(view.getContext(), "当前内容  = " + info.message, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public int multiLayoutId(int viewItemType) {
        switch (viewItemType) {
            case TYPE_LINE:
                return R.layout.item_line;
            default:
                return R.layout.item_multi;
        }
    }

    @Override
    public int getGridLayoutManagerSpanSize(int itemViewType, GridLayoutManager gridManager, int position) {
        if (!(itemViewType == MultiCallBack.TYPE_ITEM)) {
            return gridManager.getSpanCount();
        } else {
            return 1;
        }
    }

    @Override
    public boolean getStaggeredGridLayoutManagerFullSpan(int itemViewType) {
        return itemViewType != MultiCallBack.TYPE_ITEM;
    }

    @Override
    public void onXMultiBind(XViewHolder holder, SimpleMultiItem simpleMultiItem, int itemViewType, int position) {
        switch (itemViewType) {
            case SimpleMultiItem.TYPE_ITEM:
                holder.setTextView(R.id.tv_message, simpleMultiItem.message);
                ImageView imageView = holder.getImageView(R.id.iv_icon);
                imageView.setImageResource(simpleMultiItem.icon);
                break;
        }
    }
}
