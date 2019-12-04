package com.xadapter.vh

import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

fun <T : View> XViewHolder.findById(id: Int) = getView<T>(id)

fun XViewHolder.getContext() = itemView.context

fun XViewHolder.getRelativeLayout(id: Int) = findById<RelativeLayout>(id)

fun XViewHolder.getLinearLayout(id: Int) = findById<LinearLayout>(id)

fun XViewHolder.getFrameLayout(id: Int) = findById<FrameLayout>(id)

fun XViewHolder.getButton(id: Int) = findById<Button>(id)

fun XViewHolder.getImageButton(id: Int) = findById<ImageButton>(id)

fun XViewHolder.getImageSwitcher(id: Int) = findById<ImageSwitcher>(id)

fun XViewHolder.getRadioButton(id: Int) = findById<RadioButton>(id)

fun XViewHolder.getCheckBox(id: Int) = findById<CheckBox>(id)

fun XViewHolder.getProgressBar(id: Int) = findById<ProgressBar>(id)

fun XViewHolder.getSeekBar(id: Int) = findById<SeekBar>(id)

fun XViewHolder.getRatingBar(id: Int) = findById<RatingBar>(id)

fun XViewHolder.getGridLayout(id: Int) = findById<GridLayout>(id)

fun XViewHolder.getImageView(id: Int) = findById<ImageView>(id)

fun XViewHolder.getTextView(id: Int) = findById<TextView>(id)

fun XViewHolder.getEditText(id: Int) = findById<EditText>(id)

fun XViewHolder.setText(id: Int, charSequence: CharSequence) = run { getTextView(id).text = charSequence }

fun XViewHolder.setTextColor(id: Int, color: Int) = run { getTextView(id).setTextColor(ContextCompat.getColor(getContext(), color)) }

fun XViewHolder.setTextSize(id: Int, size: Float) = run { getTextView(id).textSize = size }