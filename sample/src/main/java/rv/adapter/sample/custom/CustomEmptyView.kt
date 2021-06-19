package rv.adapter.sample.custom

import android.content.Context
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import rv.adapter.sample.R

class CustomEmptyView(context: Context) : AppCompatImageView(context) {
    init {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        setImageResource(R.drawable.icon_appbar_header)
    }
}