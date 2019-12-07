package com.xadapter.refresh.extensions

import android.view.View
import com.wang.avi.indicators.*


fun View.dp2px(dpValue: Int) = context.resources.displayMetrics.density.toInt() * dpValue

fun getIndicator(avType: Int) = when (avType) {
    AVType.BALL_BEAT -> BallBeatIndicator()
    AVType.BALL_CLIP_ROTATE -> BallClipRotateIndicator()
    AVType.BALL_CLIP_ROTATE_MULTIPLE -> BallClipRotateMultipleIndicator()
    AVType.BALL_CLIP_ROTATE_PULSE -> BallClipRotatePulseIndicator()
    AVType.BALL_GRID_BEAT -> BallGridBeatIndicator()
    AVType.BALL_GRID_PULSE -> BallGridPulseIndicator()
    AVType.BALL_PULSE -> BallPulseIndicator()
    AVType.BALL_PULSE_RISE -> BallPulseRiseIndicator()
    AVType.BALL_PULSE_SYNC -> BallPulseSyncIndicator()
    AVType.BALL_ROTATE -> BallRotateIndicator()
    AVType.BALL_SCALE -> BallScaleIndicator()
    AVType.BALL_SCALE_MULTIPLE -> BallScaleMultipleIndicator()
    AVType.BALL_SCALE_RIPPLE -> BallScaleRippleIndicator()
    AVType.BALL_SCALE_RIPPLE_MULTIPLE -> BallScaleRippleMultipleIndicator()
    AVType.BALL_SPIN_FADE_LOADER -> BallSpinFadeLoaderIndicator()
    AVType.BALL_TRIANGLE_PATH -> BallTrianglePathIndicator()
    AVType.BALL_ZIG_ZAG -> BallZigZagIndicator()
    AVType.BALL_ZIG_ZAG_DEFLECT -> BallZigZagDeflectIndicator()
    AVType.CUBE_TRANSITION -> CubeTransitionIndicator()
    AVType.LINE_SCALE -> LineScaleIndicator()
    AVType.LINE_SCALE_PARTY -> LineScalePartyIndicator()
    AVType.LINE_SCALE_PULSE_OUT -> LineScalePulseOutIndicator()
    AVType.LINE_SCALE_PULSE_OUT_RAPID -> LineScalePulseOutRapidIndicator()
    AVType.LINE_SPIN_FADE_LOADER -> BallSpinFadeLoaderIndicator()
    AVType.PAC_MAN -> PacmanIndicator()
    AVType.SEMI_CIRCLE_SPIN -> SemiCircleSpinIndicator()
    AVType.SQUARE_SPIN -> SquareSpinIndicator()
    AVType.TRIANGLE_SKEW_SPIN -> TriangleSkewSpinIndicator()
    else -> BallBeatIndicator()
}
