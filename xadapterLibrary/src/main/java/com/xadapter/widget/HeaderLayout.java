package com.xadapter.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xadapter.R;
import com.xadapter.progressindicator.AVLoadingIndicatorView;
import com.xadapter.progressindicator.ProgressStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2016/11/16
 */

public class HeaderLayout extends LinearLayout implements BaseRefreshHeader {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_RELEASE_TO_REFRESH = 1;
    public static final int STATE_REFRESHING = 2;
    public static final int STATE_DONE = 3;
    public static final int STATE_ERROR = 4;

    @IntDef({STATE_NORMAL,
            STATE_RELEASE_TO_REFRESH,
            STATE_REFRESHING,
            STATE_DONE,
            STATE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RefreshState {
    }

    private View mContainer;
    private ImageView mImageView;
    private SimpleViewSwitcher mProgressBar;
    private TextView mStatusTextView;
    private LinearLayout mRootView;
    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private static final int ROTATE_ANIM_DURATION = 180;

    private int mMeasuredHeight;

    public HeaderLayout(Context context) {
        super(context);
        initView();
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        init();
        initById();
        initLayout();
    }

    private void initLayout() {
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        mProgressBar.setView(progressView);
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    private void initById() {
        mImageView = (ImageView) findViewById(R.id.listview_header_arrow);
        mStatusTextView = (TextView) findViewById(R.id.refresh_status_textview);
        mRootView = (LinearLayout) findViewById(R.id.rootView);
        mProgressBar = (SimpleViewSwitcher) findViewById(R.id.listview_header_progressbar);
    }

    @SuppressLint("InflateParams")
    private void init() {
        mContainer = LayoutInflater.from(getContext()).inflate(R.layout.listview_header, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        setLayoutParams(lp);
        setPadding(0, 0, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(null);
        }
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);
    }

    public void setProgressStyle(int style) {
        if (style == ProgressStyle.SysProgress) {
            mProgressBar.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            mProgressBar.setView(progressView);
        }
    }

    public void setImageView(@DrawableRes int resid) {
        mImageView.setImageResource(resid);
    }

    public void setTextColor(@ColorRes int color) {
        mStatusTextView.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setViewBackgroundColor(@ColorRes int color) {
        mRootView.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setState(@RefreshState int state) {
        if (state == mState) return;

        if (state == STATE_REFRESHING) {    // 显示进度
            mImageView.clearAnimation();
            mImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else if (state == STATE_DONE) {
            mImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {    // 显示箭头图片
            mImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
                    mImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mImageView.clearAnimation();
                }
                mStatusTextView.setText(R.string.listview_header_hint_normal);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    mImageView.clearAnimation();
                    mImageView.startAnimation(mRotateUpAnim);
                    mStatusTextView.setText(R.string.listview_header_hint_release);
                }
                break;
            case STATE_REFRESHING:
                mStatusTextView.setText(R.string.refreshing);
                break;
            case STATE_DONE:
                mStatusTextView.setText(R.string.refresh_done);
                break;
            case STATE_ERROR:
                mStatusTextView.setText(R.string.refresh_error);
                break;
        }

        mState = state;
    }

    public int getState() {
        return mState;
    }

    @Override
    public void refreshComplete(int state) {
        setState(state);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 200);
    }

    private void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    @Override
    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) {
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0)
            isOnRefresh = false;
        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        int destHeight = 0;
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);
        return isOnRefresh;
    }

    private void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}
