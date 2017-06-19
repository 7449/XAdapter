package com.xadapter;

import android.support.annotation.StringRes;

/**
 * by y on 2017/6/19.
 */

public interface RefreshText {
    @StringRes
    int normalText();

    @StringRes
    int readyText();

    @StringRes
    int refreshText();

    @StringRes
    int completeText();

    @StringRes
    int errorText();
}