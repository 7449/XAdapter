package com.xadapter.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xadapter.LoadMoreText;
import com.xadapter.R;
import com.xadapter.progressindicator.AVLoadingIndicatorView;
import com.xadapter.progressindicator.ProgressStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2016/9/29
 */

public class LoadMore extends LinearLayout {

    @IntDef({LOAD,
            NORMAL,
            COMPLETE,
            NOMORE,
            ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreStatus {
    }

    private SimpleViewSwitcher progressCon;
    public final static int NORMAL = -1;
    public final static int LOAD = 0;
    public final static int COMPLETE = 1;
    public final static int NOMORE = 2;
    public final static int ERROR = 3;
    private TextView mText;
    private int footerHeight = 100;
    private int mState;

    private LoadMoreText loadMoreText = null;

    public void setLoadMoreText(LoadMoreText loadMoreText) {
        this.loadMoreText = loadMoreText;
    }

    public int getState() {
        return mState;
    }

    public LoadMore(Context context) {
        super(context);
        initView();
    }

    public LoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setGravity(Gravity.CENTER);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, footerHeight));
        progressCon = new SimpleViewSwitcher(getContext());
        progressCon.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);
        addView(progressCon);
        mText = new TextView(getContext());
        mText.setText(getContext().getText(R.string.listview_loading));
        LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.textandiconmargin), 0, 0, 0);
        mText.setLayoutParams(layoutParams);
        addView(mText);
    }


    public void setState(@LoadMoreStatus int state) {
        if (state == mState) {
            return;
        }
        switch (state) {
            case LOAD:
                progressCon.setVisibility(View.VISIBLE);
                if (loadMoreText != null) {
                    mText.setText(loadMoreText.loadText());
                } else {
                    mText.setText(R.string.listview_loading);
                }
                break;
            case COMPLETE:
                progressCon.setVisibility(View.GONE);
                if (loadMoreText != null) {
                    mText.setText(loadMoreText.completeText());
                } else {
                    mText.setText(R.string.listview_loading_success);
                }
                break;
            case NOMORE:
                progressCon.setVisibility(View.GONE);
                if (loadMoreText != null) {
                    mText.setText(loadMoreText.noMoreText());
                } else {
                    mText.setText(R.string.no_more_loading);
                }
                break;
            case ERROR:
                progressCon.setVisibility(View.GONE);
                if (loadMoreText != null) {
                    mText.setText(loadMoreText.errorText());
                } else {
                    mText.setText(R.string.listview_loading_error);
                }
                break;
            case NORMAL:
                progressCon.setVisibility(View.GONE);
                if (loadMoreText != null) {
                    mText.setText(loadMoreText.normalText());
                } else {
                    mText.setText(R.string.refresh_hint_normal);
                }
                break;
        }
        mState = state;
    }

    public void setViewBackgroundColor(@ColorRes int color) {
        this.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setTextColor(@ColorRes int color) {
        mText.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setFooterHeight(int footerHeight) {
        this.footerHeight = footerHeight;
    }

    public void setProgressStyle(int style) {
        if (style == ProgressStyle.SysProgress) {
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

}
