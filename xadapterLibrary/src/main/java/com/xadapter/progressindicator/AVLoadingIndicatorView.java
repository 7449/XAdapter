package com.xadapter.progressindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;

import com.xadapter.R;
import com.xadapter.progressindicator.indicator.BallBeatIndicator;
import com.xadapter.progressindicator.indicator.BallClipRotateIndicator;
import com.xadapter.progressindicator.indicator.BallClipRotateMultipleIndicator;
import com.xadapter.progressindicator.indicator.BallClipRotatePulseIndicator;
import com.xadapter.progressindicator.indicator.BallGridBeatIndicator;
import com.xadapter.progressindicator.indicator.BallGridPulseIndicator;
import com.xadapter.progressindicator.indicator.BallPulseIndicator;
import com.xadapter.progressindicator.indicator.BallPulseRiseIndicator;
import com.xadapter.progressindicator.indicator.BallPulseSyncIndicator;
import com.xadapter.progressindicator.indicator.BallRotateIndicator;
import com.xadapter.progressindicator.indicator.BallScaleIndicator;
import com.xadapter.progressindicator.indicator.BallScaleMultipleIndicator;
import com.xadapter.progressindicator.indicator.BallScaleRippleIndicator;
import com.xadapter.progressindicator.indicator.BallScaleRippleMultipleIndicator;
import com.xadapter.progressindicator.indicator.BallSpinFadeLoaderIndicator;
import com.xadapter.progressindicator.indicator.BallTrianglePathIndicator;
import com.xadapter.progressindicator.indicator.BallZigZagDeflectIndicator;
import com.xadapter.progressindicator.indicator.BallZigZagIndicator;
import com.xadapter.progressindicator.indicator.BaseIndicatorController;
import com.xadapter.progressindicator.indicator.CubeTransitionIndicator;
import com.xadapter.progressindicator.indicator.LineScaleIndicator;
import com.xadapter.progressindicator.indicator.LineScalePartyIndicator;
import com.xadapter.progressindicator.indicator.LineScalePulseOutIndicator;
import com.xadapter.progressindicator.indicator.LineScalePulseOutRapidIndicator;
import com.xadapter.progressindicator.indicator.LineSpinFadeLoaderIndicator;
import com.xadapter.progressindicator.indicator.PacmanIndicator;
import com.xadapter.progressindicator.indicator.SemiCircleSpinIndicator;
import com.xadapter.progressindicator.indicator.SquareSpinIndicator;
import com.xadapter.progressindicator.indicator.TriangleSkewSpinIndicator;

/**
 * Created by Jack on 2015/10/15
 * <p>
 * .BallPulse,
 * .BallGridPulse,
 * .BallClipRotate,
 * .BallClipRotatePulse,
 * .SquareSpin,
 * .BallClipRotateMultiple,
 * .BallPulseRise,
 * .BallRotate,
 * .CubeTransition,
 * .BallZigZag,
 * .BallZigZagDeflect,
 * .BallTrianglePath,
 * .BallScale,
 * .LineScale,
 * .LineScaleParty,
 * .BallScaleMultiple,
 * .BallPulseSync,
 * .BallBeat,
 * .LineScalePulseOut,
 * .LineScalePulseOutRapid,
 * .BallScaleRipple,
 * .BallScaleRippleMultiple,
 * .BallSpinFadeLoader,
 * .LineSpinFadeLoader,
 * .TriangleSkewSpin,
 * .Pacman,
 * .BallGridBeat,
 * .SemiCircleSpin
 */
public class AVLoadingIndicatorView extends View {
    //indicators
    private static final int BallPulse = 0;
    private static final int BallGridPulse = 1;
    private static final int BallClipRotate = 2;
    private static final int BallClipRotatePulse = 3;
    private static final int SquareSpin = 4;
    private static final int BallClipRotateMultiple = 5;
    private static final int BallPulseRise = 6;
    private static final int BallRotate = 7;
    private static final int CubeTransition = 8;
    private static final int BallZigZag = 9;
    private static final int BallZigZagDeflect = 10;
    private static final int BallTrianglePath = 11;
    private static final int BallScale = 12;
    private static final int LineScale = 13;
    private static final int LineScaleParty = 14;
    private static final int BallScaleMultiple = 15;
    private static final int BallPulseSync = 16;
    private static final int BallBeat = 17;
    private static final int LineScalePulseOut = 18;
    private static final int LineScalePulseOutRapid = 19;
    private static final int BallScaleRipple = 20;
    private static final int BallScaleRippleMultiple = 21;
    private static final int BallSpinFadeLoader = 22;
    private static final int LineSpinFadeLoader = 23;
    private static final int TriangleSkewSpin = 24;
    private static final int Pacman = 25;
    private static final int BallGridBeat = 26;
    private static final int SemiCircleSpin = 27;


    @IntDef(flag = true,
            value = {
                    BallPulse,
                    BallGridPulse,
                    BallClipRotate,
                    BallClipRotatePulse,
                    SquareSpin,
                    BallClipRotateMultiple,
                    BallPulseRise,
                    BallRotate,
                    CubeTransition,
                    BallZigZag,
                    BallZigZagDeflect,
                    BallTrianglePath,
                    BallScale,
                    LineScale,
                    LineScaleParty,
                    BallScaleMultiple,
                    BallPulseSync,
                    BallBeat,
                    LineScalePulseOut,
                    LineScalePulseOutRapid,
                    BallScaleRipple,
                    BallScaleRippleMultiple,
                    BallSpinFadeLoader,
                    LineSpinFadeLoader,
                    TriangleSkewSpin,
                    Pacman,
                    BallGridBeat,
                    SemiCircleSpin
            })
    public @interface Indicator {
    }

    //Sizes (with defaults in DP)
    private static final int DEFAULT_SIZE = 30;

    //attrs
    private int mIndicatorId;
    private int mIndicatorColor;

    private Paint mPaint;

    private BaseIndicatorController mIndicatorController;

    private boolean mHasAnimation;


    public AVLoadingIndicatorView(Context context) {
        super(context);
        init(null, 0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AVLoadingIndicatorView);
        mIndicatorId = a.getInt(R.styleable.AVLoadingIndicatorView_indicator, BallPulse);
        mIndicatorColor = a.getColor(R.styleable.AVLoadingIndicatorView_indicator_color, Color.WHITE);
        a.recycle();
        mPaint = new Paint();
        mPaint.setColor(mIndicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        applyIndicator();
    }

    public void setIndicatorId(int indicatorId) {
        mIndicatorId = indicatorId;
        applyIndicator();
    }

    public void setIndicatorColor(int color) {
        mIndicatorColor = color;
        mPaint.setColor(mIndicatorColor);
        this.invalidate();
    }

    private void applyIndicator() {
        switch (mIndicatorId) {
            case BallPulse:
                mIndicatorController = new BallPulseIndicator();
                break;
            case BallGridPulse:
                mIndicatorController = new BallGridPulseIndicator();
                break;
            case BallClipRotate:
                mIndicatorController = new BallClipRotateIndicator();
                break;
            case BallClipRotatePulse:
                mIndicatorController = new BallClipRotatePulseIndicator();
                break;
            case SquareSpin:
                mIndicatorController = new SquareSpinIndicator();
                break;
            case BallClipRotateMultiple:
                mIndicatorController = new BallClipRotateMultipleIndicator();
                break;
            case BallPulseRise:
                mIndicatorController = new BallPulseRiseIndicator();
                break;
            case BallRotate:
                mIndicatorController = new BallRotateIndicator();
                break;
            case CubeTransition:
                mIndicatorController = new CubeTransitionIndicator();
                break;
            case BallZigZag:
                mIndicatorController = new BallZigZagIndicator();
                break;
            case BallZigZagDeflect:
                mIndicatorController = new BallZigZagDeflectIndicator();
                break;
            case BallTrianglePath:
                mIndicatorController = new BallTrianglePathIndicator();
                break;
            case BallScale:
                mIndicatorController = new BallScaleIndicator();
                break;
            case LineScale:
                mIndicatorController = new LineScaleIndicator();
                break;
            case LineScaleParty:
                mIndicatorController = new LineScalePartyIndicator();
                break;
            case BallScaleMultiple:
                mIndicatorController = new BallScaleMultipleIndicator();
                break;
            case BallPulseSync:
                mIndicatorController = new BallPulseSyncIndicator();
                break;
            case BallBeat:
                mIndicatorController = new BallBeatIndicator();
                break;
            case LineScalePulseOut:
                mIndicatorController = new LineScalePulseOutIndicator();
                break;
            case LineScalePulseOutRapid:
                mIndicatorController = new LineScalePulseOutRapidIndicator();
                break;
            case BallScaleRipple:
                mIndicatorController = new BallScaleRippleIndicator();
                break;
            case BallScaleRippleMultiple:
                mIndicatorController = new BallScaleRippleMultipleIndicator();
                break;
            case BallSpinFadeLoader:
                mIndicatorController = new BallSpinFadeLoaderIndicator();
                break;
            case LineSpinFadeLoader:
                mIndicatorController = new LineSpinFadeLoaderIndicator();
                break;
            case TriangleSkewSpin:
                mIndicatorController = new TriangleSkewSpinIndicator();
                break;
            case Pacman:
                mIndicatorController = new PacmanIndicator();
                break;
            case BallGridBeat:
                mIndicatorController = new BallGridBeatIndicator();
                break;
            case SemiCircleSpin:
                mIndicatorController = new SemiCircleSpinIndicator();
                break;
        }
        mIndicatorController.setTarget(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation) {
            mHasAnimation = true;
            applyAnimation();
        }
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.END);
            } else {
                mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.START);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.CANCEL);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mIndicatorController.setAnimationStatus(BaseIndicatorController.AnimStatus.START);
    }

    void drawIndicator(Canvas canvas) {
        mIndicatorController.draw(canvas, mPaint);
    }

    void applyAnimation() {
        mIndicatorController.initAnimation();
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }


}
