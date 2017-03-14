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

import com.xadapter.R;
import com.xadapter.progressindicator.AVLoadingIndicatorView;
import com.xadapter.progressindicator.ProgressStyle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2016/9/29
 */

public class FooterLayout extends LinearLayout {

    @IntDef({FooterLayout.STATE_LOADING,
            FooterLayout.STATE_COMPLETE,
            FooterLayout.STATE_NOMORE,
            FooterLayout.STATE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreStatus {
    }

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    public final static int STATE_ERROR = 3;
    private TextView mText;
    private int footerHeight = 100;

    public FooterLayout(Context context) {
        super(context);
        initView();
    }

    public FooterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setGravity(Gravity.CENTER);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, footerHeight));
        initProgress();
        initText();
    }

    private void initText() {
        mText = new TextView(getContext());
        mText.setText(getContext().getText(R.string.listview_loading));
        LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.textandiconmargin), 0, 0, 0);
        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    private void initProgress() {
        progressCon = new SimpleViewSwitcher(getContext());
        progressCon.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);
        addView(progressCon);
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

    public void setState(@LoadMoreStatus int state) {
        switch (state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                mText.setText(getContext().getText(R.string.listview_loading));
                break;
            case STATE_COMPLETE:
                mText.setText(getContext().getText(R.string.listview_loading_success));
                progressCon.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(getContext().getText(R.string.no_more_loading));
                progressCon.setVisibility(View.GONE);
                break;
            case STATE_ERROR:
                mText.setText(getContext().getText(R.string.listview_loading_error));
                progressCon.setVisibility(View.GONE);
                break;
        }
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


}
