package com.adapter.example.page

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.adapter.example.R
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity(private val layoutId: Int, private val title: String = "", private val showBackIcon: Boolean = true) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        rootView.addView(View.inflate(this, layoutId, null))
        toolbar.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackIcon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}