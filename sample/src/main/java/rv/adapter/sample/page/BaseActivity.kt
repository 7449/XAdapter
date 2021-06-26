package rv.adapter.sample.page

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import rv.adapter.sample.R
import rv.adapter.sample.databinding.ActivityBaseBinding
import rv.adapter.sample.viewBindingInflater

abstract class BaseActivity<VB : ViewBinding>(
    private val layoutId: Int,
    private val showBackIcon: Boolean = true
) : AppCompatActivity() {

    private val viewBind by viewBindingInflater(ActivityBaseBinding::inflate)

    protected val viewBinding by lazy { onCreateViewBinding(viewBind.rootView.getChildAt(0)) }

    abstract fun onCreateViewBinding(rootView: View): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(viewBind.toolbar)
        viewBind.rootView.addView(View.inflate(this, layoutId, null))
        viewBind.toolbar.title = javaClass.simpleName
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackIcon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}