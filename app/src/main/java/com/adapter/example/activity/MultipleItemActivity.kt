package com.adapter.example.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.example.R
import com.adapter.example.data.DataUtils
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.recyclerview.*
import com.xadapter.simple.SimpleXMultiItem
import com.xadapter.vh.getImageView
import com.xadapter.vh.setText
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * by y on 2017/1/12.
 */
class MultipleItemActivity : AppCompatActivity() {

    companion object {
        const val TYPE_LINE = 1
        const val TYPE_ITEM = -11
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "XMultiple Example"
        setContentView(R.layout.multiple_layout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        recyclerView
                .attachMultiAdapter(XMultiAdapter(DataUtils.multipleData()))
                .multiSetItemLayoutId { viewType ->
                    when (viewType) {
                        TYPE_LINE -> R.layout.item_line
                        else -> R.layout.item_multi
                    }
                }
                .multiSetBind<SimpleXMultiItem> { holder, entity, itemViewType, _ ->
                    when (itemViewType) {
                        TYPE_ITEM -> {
                            holder.setText(R.id.tv_message, entity.message)
                            val imageView = holder.getImageView(R.id.iv_icon)
                            imageView.setImageResource(entity.icon)
                        }
                    }
                }
                .multiGridLayoutManagerSpanSize { itemViewType, manager, _ ->
                    if (itemViewType != TYPE_ITEM) {
                        manager.spanCount
                    } else {
                        1
                    }
                }
                .multiStaggeredGridLayoutManagerFullSpan {
                    it != TYPE_ITEM
                }
                .multiSetOnItemClickListener<SimpleXMultiItem> { view, _, entity ->
                    Toast.makeText(view.context, "当前 position:  " + entity.itemMultiPosition + "  " + entity.message, Toast.LENGTH_SHORT).show()
                }
                .multiSetOnItemLongClickListener<SimpleXMultiItem> { view, _, entity ->
                    Toast.makeText(view.context, "当前内容  = " + entity.message, Toast.LENGTH_SHORT).show()
                    true
                }
    }
}
