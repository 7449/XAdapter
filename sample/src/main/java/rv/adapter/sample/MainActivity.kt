package rv.adapter.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import rv.adapter.sample.databinding.ActivityMainBinding
import rv.adapter.sample.page.*

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main, false) {

    override fun onCreateViewBinding(rootView: View): ActivityMainBinding {
        return ActivityMainBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.gridLayout.setOnClickListener { startActivity(GridLayoutActivity::class.java) }
        viewBinding.staggeredGridLayout.setOnClickListener { startActivity(StaggeredGridActivity::class.java) }
        viewBinding.collapsingToolbar.setOnClickListener { startActivity(CollapsingToolbarActivity::class.java) }
        viewBinding.databinding.setOnClickListener { startActivity(DataBindingActivity::class.java) }
        viewBinding.viewBinding.setOnClickListener { startActivity(ViewBindingActivity::class.java) }
        viewBinding.swiperefreshlayout.setOnClickListener { startActivity(SwipeRefreshActivity::class.java) }
        viewBinding.empty.setOnClickListener { startActivity(EmptyActivity::class.java) }
    }

    private fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

}
