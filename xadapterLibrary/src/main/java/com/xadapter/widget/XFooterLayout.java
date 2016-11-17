package com.xadapter.widget;

import android.content.Context;
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

/**
 * by y on 2016/9/29
 */

public class XFooterLayout extends LinearLayout {

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    public final static int STATE_ERROR = 3;
    private TextView mText;
    private int mState = STATE_LOADING;
    private FooterLayoutInterface footerLayoutInterface;


    private int footerHeight = 100;

    public XFooterLayout(Context context, FooterLayoutInterface footerLayoutInterface) {
        super(context);
        this.footerLayoutInterface = footerLayoutInterface;
        initView();
    }

    public XFooterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setGravity(Gravity.CENTER);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, footerHeight));
        initProgress();
        initText();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mState == STATE_ERROR) {
                    if (footerLayoutInterface != null) {
                        footerLayoutInterface.onXFooterClick(view);
                    }
                }
            }
        });
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

    public void setState(int state) {
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
        mState = state;
    }

    public interface FooterLayoutInterface {
        void onXFooterClick(View view);
    }

    public void setViewBackgroundColor(int color) {
        this.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setTextColor(int color) {
        mText.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setFooterHeight(int footerHeight) {
        this.footerHeight = footerHeight;
    }


}
