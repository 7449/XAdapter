package com.adapter.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adapter.example.activity.*
import com.adapter.example.net.NetApi
import com.adapter.example.net.NetWorkActivity
import io.reactivex.network.RxNetWork
import io.reactivex.network.SimpleRxNetOptionFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxNetWork.initialization(SimpleRxNetOptionFactory(NetApi.ZL_BASE_API, GsonConverterFactory.create()))

        setContentView(R.layout.activity_main)
        network.setOnClickListener { startActivity(NetWorkActivity::class.java) }
        LinearLayout.setOnClickListener { startActivity(LinearLayoutManagerActivity::class.java) }
        GridLayout.setOnClickListener { startActivity(GridLayoutManagerActivity::class.java) }
        StaggeredGridLayout.setOnClickListener { startActivity(StaggeredGridLayoutManagerActivity::class.java) }
        collapsingToolbar.setOnClickListener { startActivity(CollapsingToolbarLayoutActivity::class.java) }
        emptyView.setOnClickListener { startActivity(EmptyViewActivity::class.java) }
        multiple.setOnClickListener { startActivity(MultipleXXItemActivity::class.java) }
        databinding.setOnClickListener { startActivity(DataBindingActivity::class.java) }
    }

    private fun startActivity(clz: Class<*>) {
        val intent = Intent(applicationContext, clz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
