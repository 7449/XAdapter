package com.xadaptersimple.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;

import com.xadapter.widget.XRefreshView;
import com.xadaptersimple.R;

/**
 * by y on 2017/6/21.
 */

public class RefreshView extends XRefreshView {
    private ProgressBar progressBar;
    private AppCompatTextView mText;
    private AppCompatImageView mTipsIv;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    public RefreshView(Context context) {
        super(context);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initView() {
        progressBar = (ProgressBar) findViewById(com.xadapter.R.id.progressbar);
        mText = (AppCompatTextView) findViewById(com.xadapter.R.id.tv_tips);
        mTipsIv = (AppCompatImageView) findViewById(com.xadapter.R.id.iv_tips);
        mText.setText("下拉刷新");
        mText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        initAnimation();
    }

    private void initAnimation() {
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(180);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(180);
        mRotateDownAnim.setFillAfter(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_refresh;
    }

    @Override
    protected void onStart() {
        mTipsIv.clearAnimation();
        mTipsIv.setVisibility(VISIBLE);
        progressBar.setVisibility(INVISIBLE);
    }

    @Override
    protected void onNormal() {
        if (mState == READY) {
            mTipsIv.startAnimation(mRotateDownAnim);
        } else {
            mTipsIv.clearAnimation();
        }
        mTipsIv.setVisibility(VISIBLE);
        progressBar.setVisibility(INVISIBLE);
        mText.setText("下拉刷新");
    }

    @Override
    protected void onReady() {
        mTipsIv.startAnimation(mRotateUpAnim);
        progressBar.setVisibility(INVISIBLE);
        mTipsIv.setVisibility(VISIBLE);
        mText.setText("释放立即刷新");
    }

    @Override
    protected void onRefresh() {
        progressBar.setVisibility(VISIBLE);
        mTipsIv.setVisibility(INVISIBLE);
        mText.setText("正在刷新...");
    }

    @Override
    protected void onSuccess() {
        progressBar.setVisibility(INVISIBLE);
        mTipsIv.setVisibility(INVISIBLE);
        mTipsIv.clearAnimation();
        mText.setText("刷新成功");
    }

    @Override
    protected void onError() {
        progressBar.setVisibility(INVISIBLE);
        mTipsIv.setVisibility(INVISIBLE);
        mTipsIv.clearAnimation();
        mText.setText("刷新失败");
    }


}
