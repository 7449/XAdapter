package com.xadapter.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.xadapter.R;
import com.xadapter.RefreshText;
import com.xadapter.progressindicator.AVLoadingIndicatorView;
import com.xadapter.progressindicator.ProgressStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2016/11/16
 */

public class Refresh extends LinearLayout {
    private int mState = NORMAL;
    public static final int NORMAL = 0; //初始状态
    public static final int READY = 1; //准备
    public static final int REFRESH = 2; //正在刷新
    public static final int COMPLETE = 3; // 刷新完成
    public static final int ERROR = 4; // 刷新失败

    @IntDef({NORMAL,
            READY,
            REFRESH,
            COMPLETE,
            ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshState {
    }

    private View mContainer;
    private AppCompatImageView mImageView;
    private SimpleViewSwitcher mProgressBar;
    private AppCompatTextView mStatusTextView;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private RefreshText refreshText = null;

    public void setRefreshText(RefreshText refreshText) {
        this.refreshText = refreshText;
    }

    private static final int ROTATE_ANIM_DURATION = 180;

    private int mMeasuredHeight;

    public Refresh(Context context) {
        super(context);
        initView();
    }

    public Refresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        init();
        initById();
        initLayout();
    }

    private void init() {
        mContainer = View.inflate(getContext(), R.layout.listview_header, null);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);
    }

    private void initLayout() {
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
        initStyle(ProgressStyle.BallSpinFadeLoader);
    }

    private void initById() {
        mImageView = (AppCompatImageView) findViewById(R.id.header_arrow);
        mStatusTextView = (AppCompatTextView) findViewById(R.id.status_tv);
        mProgressBar = (SimpleViewSwitcher) findViewById(R.id.refresh_progressbar);
    }

    public void setProgressStyle(int style) {
        if (style == ProgressStyle.SysProgress) {
            mProgressBar.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            initStyle(style);
        }
    }

    private void initStyle(int ballSpinFadeLoader) {
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ballSpinFadeLoader);
        mProgressBar.setView(progressView);
    }

    public void setImageView(@DrawableRes int resid) {
        mImageView.setImageResource(resid);
    }

    public void setTextColor(@ColorRes int color) {
        mStatusTextView.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setViewBackgroundColor(@ColorRes int color) {
        mContainer.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setState(@RefreshState int state) {
        if (state == mState) {
            return;
        }
        mImageView.clearAnimation();
        switch (state) {
            case NORMAL:
                mImageView.startAnimation(mRotateDownAnim);
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(VISIBLE);
                if (refreshText != null) {
                    mStatusTextView.setText(refreshText.normalText());
                } else {
                    mStatusTextView.setText(R.string.refresh_hint_normal);
                }
                break;
            case READY:
                mImageView.startAnimation(mRotateUpAnim);
                mProgressBar.setVisibility(View.GONE);
                mImageView.setVisibility(VISIBLE);
                if (refreshText != null) {
                    mStatusTextView.setText(refreshText.readyText());
                } else {
                    mStatusTextView.setText(R.string.refresh_hint_release);
                }
                break;
            case REFRESH:
                mImageView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                if (refreshText != null) {
                    mStatusTextView.setText(refreshText.refreshText());
                } else {
                    mStatusTextView.setText(R.string.refresh);
                }
                break;
            case COMPLETE:
                mImageView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                if (refreshText != null) {
                    mStatusTextView.setText(refreshText.completeText());
                } else {
                    mStatusTextView.setText(R.string.refresh_done);
                }
                break;
            case ERROR:
                mImageView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                if (refreshText != null) {
                    mStatusTextView.setText(refreshText.errorText());
                } else {
                    mStatusTextView.setText(R.string.refresh_error);
                }
                break;
        }

        mState = state;
    }

    public int getState() {
        return mState;
    }

    public void refreshComplete(int state) {
        setState(state);
        postDelayed(new Runnable() {
            public void run() {
                smoothScrollTo(0);
            }
        }, 200);
    }

    private void setVisibleHeight(int height) {
        if (height == 0) {
            setState(NORMAL);
        }
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= READY) {
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(READY);
                } else {
                    setState(NORMAL);
                }
            }
        }
    }

    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0)
            isOnRefresh = false;
        if (getVisibleHeight() > mMeasuredHeight && mState < REFRESH) {
            setState(REFRESH);
            isOnRefresh = true;
        }
        int destHeight = 0;
        if (mState == REFRESH) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);
        return isOnRefresh;
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight).setDuration(300);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setVisibleHeight((int) animation.getAnimatedValue());
                    }
                });
        animator.start();
    }
}
