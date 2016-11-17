package com.xadapter.progressindicator.indicator;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

/**
 * by Jack on 2015/10/15.
 */
public abstract class BaseIndicatorController {


    private View mTarget;

    private List<Animator> mAnimators;


    public void setTarget(View target) {
        this.mTarget = target;
    }

    View getTarget() {
        return mTarget;
    }


    int getWidth() {
        return mTarget.getWidth();
    }

    int getHeight() {
        return mTarget.getHeight();
    }

    void postInvalidate() {
        mTarget.postInvalidate();
    }

    /**
     * draw indicator
     *
     * @param canvas
     * @param paint
     */
    @SuppressWarnings("JavaDoc")
    public abstract void draw(Canvas canvas, Paint paint);

    /**
     * create animation or animations
     */
    protected abstract List<Animator> createAnimation();

    public void initAnimation() {
        mAnimators = createAnimation();
    }

    /**
     * make animation to start or end when target
     * view was be Visible or Gone or Invisible.
     * make animation to cancel when target view
     * be onDetachedFromWindow.
     *
     * @param animStatus
     */
    @SuppressWarnings("JavaDoc")
    public void setAnimationStatus(AnimStatus animStatus) {
        if (mAnimators == null) {
            return;
        }
        int count = mAnimators.size();
        for (int i = 0; i < count; i++) {
            Animator animator = mAnimators.get(i);
            boolean isRunning = animator.isRunning();
            switch (animStatus) {
                case START:
                    if (!isRunning) {
                        animator.start();
                    }
                    break;
                case END:
                    if (isRunning) {
                        animator.end();
                    }
                    break;
                case CANCEL:
                    if (isRunning) {
                        animator.cancel();
                    }
                    break;
            }
        }
    }


    public enum AnimStatus {
        START, END, CANCEL
    }


}
