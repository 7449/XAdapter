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

class XViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

    fun getRelativeLayout(id: Int): RelativeLayout = kotlin.run { getView(id) }
    fun getLinearLayout(id: Int): LinearLayout = kotlin.run { getView(id) }
    fun getFrameLayout(id: Int): FrameLayout = kotlin.run { getView(id) }
    fun getButton(id: Int): Button = kotlin.run { getView(id) }
    fun setButtonText(id: Int, text: String) = kotlin.run { getButton(id).text = text }
    fun setButtonColor(id: Int, color: Int) { getButton(id).setTextColor(ContextCompat.getColor(context, color)) }
    fun getRadioButton(id: Int): RadioButton = kotlin.run { getView(id) }
    fun getCheckBox(id: Int): CheckBox = kotlin.run { getView(id) }
    fun getProgressBar(id: Int): ProgressBar = kotlin.run { getView(id) }
    fun getSeekBar(id: Int): SeekBar = kotlin.run { getView(id) }
    fun getRatingBar(id: Int): RatingBar = kotlin.run { getView(id) }
    fun getGridLayout(id: Int): GridLayout = kotlin.run { getView(id) }
    fun getRadioGroup(id: Int): RadioGroup = kotlin.run { getView(id) }
    fun getImageView(id: Int): ImageView = kotlin.run { getView(id) }
    fun getTextView(id: Int): TextView = kotlin.run { getView(id) }
    fun setTextView(id: Int, charSequence: CharSequence) = kotlin.run { getTextView(id).text = charSequence }
    fun setTextColor(id: Int, @ColorRes color: Int) = kotlin.run { getTextView(id).setTextColor(ContextCompat.getColor(context, color)) }
    fun setTextSize(id: Int, size: Float) = kotlin.run { getTextView(id).textSize = size }
}
