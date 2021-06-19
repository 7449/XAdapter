package rv.adapter.view.holder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.SparseArray
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

open class XViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val idsTag = -1

    val context: Context
        get() = itemView.context

    fun viewById(id: Int) = findViewById<View>(id)
    fun recyclerView(id: Int) = findViewById<RecyclerView>(id)
    fun relativeLayout(id: Int) = findViewById<RelativeLayout>(id)
    fun linearLayout(id: Int) = findViewById<LinearLayout>(id)
    fun frameLayout(id: Int) = findViewById<FrameLayout>(id)
    fun button(id: Int) = findViewById<Button>(id)
    fun imageButton(id: Int) = findViewById<ImageButton>(id)
    fun imageSwitcher(id: Int) = findViewById<ImageSwitcher>(id)
    fun radioButton(id: Int) = findViewById<RadioButton>(id)
    fun checkBox(id: Int) = findViewById<CheckBox>(id)
    fun progressBar(id: Int) = findViewById<ProgressBar>(id)
    fun seekBar(id: Int) = findViewById<SeekBar>(id)
    fun ratingBar(id: Int) = findViewById<RatingBar>(id)
    fun gridLayout(id: Int) = findViewById<GridLayout>(id)
    fun imageView(id: Int) = findViewById<ImageView>(id)
    fun textView(id: Int) = findViewById<TextView>(id)
    fun editText(id: Int) = findViewById<EditText>(id)

    fun setText(id: Int, charSequence: CharSequence) = also { textView(id).text = charSequence }
    fun setText(id: Int, strId: Int) = also { textView(id).setText(strId) }
    fun setTextColor(id: Int, color: Int) = also { textView(id).setTextColor(color) }
    fun setTextSize(id: Int, size: Float) = also { textView(id).textSize = size }
    fun setProgress(id: Int, progress: Int) = also { progressBar(id).progress = progress }
    fun setImageResource(viewId: Int, imageResId: Int) =
        also { imageView(viewId).setImageResource(imageResId) }

    fun setImageDrawable(viewId: Int, drawable: Drawable?) =
        also { imageView(viewId).setImageDrawable(drawable) }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap?) =
        also { imageView(viewId).setImageBitmap(bitmap) }

    fun setBackgroundColor(viewId: Int, color: Int) =
        also { viewById(viewId).setBackgroundColor(color) }

    fun setBackgroundResource(viewId: Int, backgroundRes: Int) =
        also { viewById(viewId).setBackgroundResource(backgroundRes) }

    fun setVisibility(viewId: Int, isVisible: Boolean) =
        also { viewById(viewId).visibility = if (isVisible) View.VISIBLE else View.INVISIBLE }

    fun setGone(viewId: Int, isGone: Boolean) =
        also { viewById(viewId).visibility = if (isGone) View.GONE else View.VISIBLE }

    fun setEnabled(viewId: Int, isEnabled: Boolean) =
        also { viewById(viewId).isEnabled = isEnabled }

    fun setClickable(viewId: Int, isEnabled: Boolean) =
        also { viewById(viewId).isClickable = isEnabled }

    private fun <T : View> findViewById(id: Int): T {
        val tag = itemView.getTag(idsTag)
        return if (tag !is SparseArray<*>) {
            itemView.setTag(idsTag, SparseArray<View>())
            findViewById(id)
        } else {
            val sparseArray = tag as SparseArray<View>
            sparseArray[id] as T?
                ?: itemView.findViewById<T>(id).apply { sparseArray.put(id, this) }
        }
    }

}