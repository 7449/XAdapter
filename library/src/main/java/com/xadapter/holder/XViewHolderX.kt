@file:Suppress("NOTHING_TO_INLINE")

package com.xadapter.holder

import android.view.View
import android.widget.*
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * @author y
 * @create 2019/3/15
 */

fun <T : View> XViewHolder.findById(id: Int) = getView<T>(id)

inline fun XViewHolder.getContext() = itemView.context

inline fun XViewHolder.getRelativeLayout(id: Int) = findById<RelativeLayout>(id)

inline fun XViewHolder.getLinearLayout(id: Int) = findById<LinearLayout>(id)

inline fun XViewHolder.getFrameLayout(id: Int) = findById<FrameLayout>(id)

inline fun XViewHolder.getButton(id: Int) = findById<Button>(id)

inline fun XViewHolder.getRadioButton(id: Int) = findById<RadioButton>(id)

inline fun XViewHolder.getCheckBox(id: Int) = findById<CheckBox>(id)

inline fun XViewHolder.getProgressBar(id: Int) = findById<ProgressBar>(id)

inline fun XViewHolder.getSeekBar(id: Int) = findById<SeekBar>(id)

inline fun XViewHolder.getRatingBar(id: Int) = findById<RatingBar>(id)

inline fun XViewHolder.getGridLayout(id: Int) = findById<GridLayout>(id)

inline fun XViewHolder.getImageView(id: Int) = findById<ImageView>(id)

inline fun XViewHolder.getTextView(id: Int) = findById<TextView>(id)

inline fun XViewHolder.setText(id: Int, charSequence: CharSequence) = run { getTextView(id).text = charSequence }

inline fun XViewHolder.setTextColor(id: Int, @ColorRes color: Int) = run { getTextView(id).setTextColor(ContextCompat.getColor(getContext(), color)) }

inline fun XViewHolder.setTextSize(id: Int, size: Float) = run { getTextView(id).textSize = size }
