package com.xadapter.holder

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.*
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * by y on 2016/9/29
 *
 *
 * A ViewHolder, inherited from RecyclerViewHolder, with sparseArray for the view optimization
 */

open class XViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val context: Context
        get() = itemView.context

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(id: Int): T {
        var viewSparseArray: SparseArray<View>? = itemView.tag as SparseArray<View>?
        if (null == viewSparseArray) {
            viewSparseArray = SparseArray()
            itemView.tag = viewSparseArray
        }
        var childView: View? = viewSparseArray.get(id)
        if (null == childView) {
            childView = itemView.findViewById(id)
            viewSparseArray.put(id, childView)
        }
        return childView as T
    }

    fun getRelativeLayout(id: Int): RelativeLayout = run { getView(id) }
    fun getLinearLayout(id: Int): LinearLayout = run { getView(id) }
    fun getFrameLayout(id: Int): FrameLayout = run { getView(id) }
    fun getButton(id: Int): Button = run { getView(id) }
    fun setButtonText(id: Int, text: String) = run { getButton(id).text = text }
    fun setButtonColor(id: Int, color: Int) { getButton(id).setTextColor(ContextCompat.getColor(context, color)) }
    fun getRadioButton(id: Int): RadioButton = run { getView(id) }
    fun getCheckBox(id: Int): CheckBox = run { getView(id) }
    fun getProgressBar(id: Int): ProgressBar = run { getView(id) }
    fun getSeekBar(id: Int): SeekBar = run { getView(id) }
    fun getRatingBar(id: Int): RatingBar = run { getView(id) }
    fun getGridLayout(id: Int): GridLayout = run { getView(id) }
    fun getRadioGroup(id: Int): RadioGroup = run { getView(id) }
    fun getImageView(id: Int): ImageView = run { getView(id) }
    fun getTextView(id: Int): TextView = run { getView(id) }
    fun setTextView(id: Int, charSequence: CharSequence) = run { getTextView(id).text = charSequence }
    fun setTextColor(id: Int, @ColorRes color: Int) = run { getTextView(id).setTextColor(ContextCompat.getColor(context, color)) }
    fun setTextSize(id: Int, size: Float) = run { getTextView(id).textSize = size }
}
