package rv.adapter.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.reactivex.network.DefaultRxNetOption
import io.reactivex.network.RxNetWork
import retrofit2.converter.gson.GsonConverterFactory
import rv.adapter.sample.databinding.ActivityMainBinding
import rv.adapter.sample.page.*

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main, "Sample", false) {

    override fun onCreateViewBinding(rootView: View): ActivityMainBinding {
        return ActivityMainBinding.bind(rootView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxNetWork.initialization(
            DefaultRxNetOption(
                baseUrl = "https://news-at.zhihu.com/api/4/",
                converterFactory = GsonConverterFactory.create()
            )
        )

        viewBinding.sample.setOnClickListener { startActivity(SampleActivity::class.java) }
        viewBinding.network.setOnClickListener { startActivity(NetWorkActivity::class.java) }
        viewBinding.linearLayout.setOnClickListener { startActivity(LinearLayoutActivity::class.java) }
        viewBinding.gridLayout.setOnClickListener { startActivity(GridLayoutActivity::class.java) }
        viewBinding.staggeredGridLayout.setOnClickListener { startActivity(StaggeredGridActivity::class.java) }
        viewBinding.collapsingToolbar.setOnClickListener { startActivity(CollapsingToolbarActivity::class.java) }
        viewBinding.multiple.setOnClickListener { startActivity(MultipleActivity::class.java) }
        viewBinding.databinding.setOnClickListener { startActivity(DataBindingActivity::class.java) }
        viewBinding.viewBinding.setOnClickListener { startActivity(ViewBindingActivity::class.java) }
        viewBinding.swiperefreshlayout.setOnClickListener { startActivity(SwipeRefreshActivity::class.java) }
        viewBinding.empty.setOnClickListener { startActivity(EmptyActivity::class.java) }
    }

    private fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

}
