package com.adapter.example

import android.content.Intent
import android.os.Bundle
import com.adapter.example.page.*
import io.reactivex.network.DefaultRxNetOption
import io.reactivex.network.RxNetWork
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity(R.layout.activity_main, "Sample", false) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxNetWork.initialization(DefaultRxNetOption(
                baseUrl = "https://news-at.zhihu.com/api/4/",
                converterFactory = GsonConverterFactory.create()
        ))

        sample.setOnClickListener { startActivity(SampleActivity::class.java) }
        network.setOnClickListener { startActivity(NetWorkActivity::class.java) }
        linearLayout.setOnClickListener { startActivity(LinearLayoutActivity::class.java) }
        gridLayout.setOnClickListener { startActivity(GridLayoutActivity::class.java) }
        staggeredGridLayout.setOnClickListener { startActivity(StaggeredGridActivity::class.java) }
        collapsingToolbar.setOnClickListener { startActivity(CollapsingToolbarActivity::class.java) }
        multiple.setOnClickListener { startActivity(MultipleActivity::class.java) }
        databinding.setOnClickListener { startActivity(DataBindingActivity::class.java) }
        custom.setOnClickListener { startActivity(CustomActivity::class.java) }
        swiperefreshlayout.setOnClickListener { startActivity(SwipeRefreshActivity::class.java) }
        empty.setOnClickListener { startActivity(EmptyActivity::class.java) }
        merge.setOnClickListener { startActivity(MergeActivity::class.java) }
    }

    private fun startActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }
}
