package com.adapter.example

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.xadapter.adapter.XAdapter
import com.xadapter.material.AppBarStateChangeListener

fun <T> XAdapter<T>.supportAppbar(appBarLayout: AppBarLayout) = also {
    val appBarStateChangeListener = AppBarStateChangeListener()
    appBarLayout.addOnOffsetChangedListener(appBarStateChangeListener)
    xAppbarCallback = { appBarStateChangeListener.currentState == AppBarStateChangeListener.EXPANDED }
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}
