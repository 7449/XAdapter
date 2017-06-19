package com.xadapter;

import android.support.annotation.StringRes;

/**
 * by y on 2017/6/19.
 */

public interface LoadMoreText {
    @StringRes
    int loadText();

    @StringRes
    int completeText();

    @StringRes
    int noMoreText();

    @StringRes
    int errorText();

    @StringRes
    int normalText();
}
