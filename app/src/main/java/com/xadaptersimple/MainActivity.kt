package com.xadaptersimple

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.xadaptersimple.net.NetWorkActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.network).setOnClickListener(this)
        findViewById<View>(R.id.LinearLayout).setOnClickListener(this)
        findViewById<View>(R.id.GridLayout).setOnClickListener(this)
        findViewById<View>(R.id.StaggeredGridLayout).setOnClickListener(this)
        findViewById<View>(R.id.collapsingToolbar).setOnClickListener(this)
        findViewById<View>(R.id.emptyView).setOnClickListener(this)
        findViewById<View>(R.id.multiple).setOnClickListener(this)
        findViewById<View>(R.id.test).setOnClickListener(this)
        findViewById<View>(R.id.refreshLayout).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.network -> startActivity(NetWorkActivity::class.java)
            R.id.LinearLayout -> startActivity(LinearLayoutManagerActivity::class.java)
            R.id.GridLayout -> startActivity(GridLayoutManagerActivity::class.java)
            R.id.StaggeredGridLayout -> startActivity(StaggeredGridLayoutManagerActivity::class.java)
            R.id.collapsingToolbar -> startActivity(CollapsingToolbarLayoutActivity::class.java)
            R.id.emptyView -> startActivity(EmptyViewActivity::class.java)
            R.id.multiple -> startActivity(MultipleItemActivity::class.java)
            R.id.test -> startActivity(TestActivity::class.java)
            R.id.refreshLayout -> startActivity(RefreshLayoutActivity::class.java)
        }
    }

    private fun startActivity(clz: Class<*>) {
        val intent = Intent(applicationContext, clz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
