@file:JvmName("XViewHolderUtils")

package com.xadapter.vh

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.*
import androidx.annotation.*
import androidx.core.content.ContextCompat

val XViewHolder.context: Context
    get() = itemView.context

fun XViewHolder.viewById(@IdRes id: Int) = findViewById<View>(id)

fun XViewHolder.relativeLayout(@IdRes id: Int) = findViewById<RelativeLayout>(id)

fun XViewHolder.linearLayout(@IdRes id: Int) = findViewById<LinearLayout>(id)

fun XViewHolder.frameLayout(@IdRes id: Int) = findViewById<FrameLayout>(id)

fun XViewHolder.button(@IdRes id: Int) = findViewById<Button>(id)

fun XViewHolder.imageButton(@IdRes id: Int) = findViewById<ImageButton>(id)

fun XViewHolder.imageSwitcher(@IdRes id: Int) = findViewById<ImageSwitcher>(id)

fun XViewHolder.radioButton(@IdRes id: Int) = findViewById<RadioButton>(id)

fun XViewHolder.checkBox(@IdRes id: Int) = findViewById<CheckBox>(id)

fun XViewHolder.progressBar(@IdRes id: Int) = findViewById<ProgressBar>(id)

fun XViewHolder.seekBar(@IdRes id: Int) = findViewById<SeekBar>(id)

fun XViewHolder.ratingBar(@IdRes id: Int) = findViewById<RatingBar>(id)

fun XViewHolder.gridLayout(@IdRes id: Int) = findViewById<GridLayout>(id)

fun XViewHolder.imageView(@IdRes id: Int) = findViewById<ImageView>(id)

fun XViewHolder.textView(@IdRes id: Int) = findViewById<TextView>(id)

fun XViewHolder.editText(@IdRes id: Int) = findViewById<EditText>(id)

fun XViewHolder.setText(@IdRes id: Int, charSequence: CharSequence) = also { textView(id).text = charSequence }

fun XViewHolder.setText(@IdRes id: Int, @StringRes strId: Int) = also { textView(id).setText(strId) }

fun XViewHolder.setTextColor(@IdRes id: Int, @ColorRes color: Int) = also { textView(id).setTextColor(ContextCompat.getColor(context, color)) }

fun XViewHolder.setTextSize(@IdRes id: Int, size: Float) = also { textView(id).textSize = size }

fun XViewHolder.setProgress(@IdRes id: Int, progress: Int) = also { progressBar(id).progress = progress }

fun XViewHolder.setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int) = also { imageView(viewId).setImageResource(imageResId) }

fun XViewHolder.setImageDrawable(@IdRes viewId: Int, drawable: Drawable?) = also { imageView(viewId).setImageDrawable(drawable) }

fun XViewHolder.setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?) = also { imageView(viewId).setImageBitmap(bitmap) }

fun XViewHolder.setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int) = also { viewById(viewId).setBackgroundColor(color) }

fun XViewHolder.setBackgroundResource(@IdRes viewId: Int, @DrawableRes backgroundRes: Int) = also { viewById(viewId).setBackgroundResource(backgroundRes) }

fun XViewHolder.setVisibility(@IdRes viewId: Int, isVisible: Boolean) = also { viewById(viewId).visibility = if (isVisible) View.VISIBLE else View.INVISIBLE }

fun XViewHolder.setGone(@IdRes viewId: Int, isGone: Boolean) = also { viewById(viewId).visibility = if (isGone) View.GONE else View.VISIBLE }

fun XViewHolder.setEnabled(@IdRes viewId: Int, isEnabled: Boolean) = also { viewById(viewId).isEnabled = isEnabled }

fun XViewHolder.setClickable(@IdRes viewId: Int, isEnabled: Boolean) = also { viewById(viewId).isClickable = isEnabled }