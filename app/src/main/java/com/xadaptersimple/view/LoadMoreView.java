package com.xadaptersimple.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.xadapter.widget.XLoadMoreView;
import com.xadaptersimple.R;

/**
 * by y on 2017/6/21.
 */

public class LoadMoreView extends XLoadMoreView {

    private ProgressBar progressBar;
    private AppCompatTextView mText;


    public LoadMoreView(Context context) {
        super(context);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        progressBar = (ProgressBar) findViewById(com.xadapter.R.id.progressbar);
        mText = (AppCompatTextView) findViewById(com.xadapter.R.id.tv_tips);
        mText.setText("上拉加载");
        mText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_loadmore;
    }

    @Override
    protected void onStart() {
        progressBar.setVisibility(GONE);
    }

    @Override
    protected void onLoad() {
        progressBar.setVisibility(View.VISIBLE);
        mText.setText("正在加载...");
    }

    @Override
    protected void onNoMore() {
        progressBar.setVisibility(View.GONE);
        mText.setText("没有数据了");
    }

    @Override
    protected void onSuccess() {
        progressBar.setVisibility(View.GONE);
        mText.setText("加载成功");
    }

    @Override
    protected void onError() {
        progressBar.setVisibility(View.GONE);
        mText.setText("加载失败");
    }

    @Override
    protected void onNormal() {
        progressBar.setVisibility(View.GONE);
        mText.setText("上拉加载");
    }
}
