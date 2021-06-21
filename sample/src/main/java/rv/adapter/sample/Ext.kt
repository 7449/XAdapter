package rv.adapter.sample

import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import rv.adapter.core.XAdapter
import rv.adapter.material.AppBarStateChangeListener

fun <T> XAdapter<T>.supportAppbar(appBarLayout: AppBarLayout) = also {
    val listener = AppBarStateChangeListener()
    appBarLayout.addOnOffsetChangedListener(listener)
    setAppbarCallback { listener.currentState == AppBarStateChangeListener.EXPANDED }
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}

inline fun <reified T : ViewBinding> AppCompatActivity.viewBindingInflater(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    bindingInflater.invoke(layoutInflater).apply { setContentView(root) }
}
