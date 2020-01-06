package com.xadapter.vh

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.*
import androidx.annotation.*
import androidx.core.content.ContextCompat

fun <T : View> XViewHolder.findById(@IdRes id: Int) = getView<T>(id)

fun XViewHolder.getContext() = itemView.context
        ?: throw  KotlinNullPointerException("context == null")

fun XViewHolder.getView(@IdRes id: Int) = findById<View>(id)

fun XViewHolder.getRelativeLayout(@IdRes id: Int) = findById<RelativeLayout>(id)

fun XViewHolder.getLinearLayout(@IdRes id: Int) = findById<LinearLayout>(id)

fun XViewHolder.getFrameLayout(@IdRes id: Int) = findById<FrameLayout>(id)

fun XViewHolder.getButton(@IdRes id: Int) = findById<Button>(id)

fun XViewHolder.getImageButton(@IdRes id: Int) = findById<ImageButton>(id)

fun XViewHolder.getImageSwitcher(@IdRes id: Int) = findById<ImageSwitcher>(id)

fun XViewHolder.getRadioButton(@IdRes id: Int) = findById<RadioButton>(id)

fun XViewHolder.getCheckBox(@IdRes id: Int) = findById<CheckBox>(id)

fun XViewHolder.getProgressBar(@IdRes id: Int) = findById<ProgressBar>(id)

fun XViewHolder.getSeekBar(@IdRes id: Int) = findById<SeekBar>(id)

fun XViewHolder.getRatingBar(@IdRes id: Int) = findById<RatingBar>(id)

fun XViewHolder.getGridLayout(@IdRes id: Int) = findById<GridLayout>(id)

fun XViewHolder.getImageView(@IdRes id: Int) = findById<ImageView>(id)

fun XViewHolder.getTextView(@IdRes id: Int) = findById<TextView>(id)

fun XViewHolder.getEditText(@IdRes id: Int) = findById<EditText>(id)

fun XViewHolder.setText(@IdRes id: Int, charSequence: CharSequence) = also { getTextView(id).text = charSequence }

fun XViewHolder.setText(@IdRes id: Int, @StringRes strId: Int) = also { getTextView(id).setText(strId) }

fun XViewHolder.setTextColor(@IdRes id: Int, @ColorRes color: Int) = also { getTextView(id).setTextColor(ContextCompat.getColor(getContext(), color)) }

fun XViewHolder.setTextSize(@IdRes id: Int, size: Float) = also { getTextView(id).textSize = size }

fun XViewHolder.setProgress(@IdRes id: Int, progress: Int) = also { getProgressBar(id).progress = progress }

fun XViewHolder.setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int) = also { getImageView(viewId).setImageResource(imageResId) }

fun XViewHolder.setImageDrawable(@IdRes viewId: Int, drawable: Drawable?) = also { getImageView(viewId).setImageDrawable(drawable) }

fun XViewHolder.setImageBitmap(@IdRes viewId: Int, bitmap: Bitmap?) = also { getImageView(viewId).setImageBitmap(bitmap) }

fun XViewHolder.setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int) = also { getView(viewId).setBackgroundColor(color) }

fun XViewHolder.setBackgroundResource(@IdRes viewId: Int, @DrawableRes backgroundRes: Int) = also { getView(viewId).setBackgroundResource(backgroundRes) }

fun XViewHolder.setVisibility(@IdRes viewId: Int, isVisible: Boolean) = also { getView(viewId).visibility = if (isVisible) View.VISIBLE else View.INVISIBLE }

fun XViewHolder.setGone(@IdRes viewId: Int, isGone: Boolean) = also { getView(viewId).visibility = if (isGone) View.GONE else View.VISIBLE }

fun XViewHolder.setEnabled(@IdRes viewId: Int, isEnabled: Boolean) = also { getView(viewId).isEnabled = isEnabled }