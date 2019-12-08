package com.adapter.example.custom

import android.content.Context
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.adapter.example.R

class CustomEmptyView(context: Context) : AppCompatImageView(context) {
    init {
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        setImageResource(R.drawable.icon_appbar_header)
    }
}