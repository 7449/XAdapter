package com.xadaptersimple

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.holder.getImageView
import com.xadapter.holder.setText
import com.xadapter.multiAdapter
import com.xadapter.simple.SimpleXMultiItem
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * by y on 2017/1/12.
 */

class MultipleXXItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiple_layout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = XMultiAdapter(initSettingData())
                .apply {
                    onXLongClickListener = { view, _, entity ->
                        Toast.makeText(view.context, "当前内容  = " + entity.message, Toast.LENGTH_SHORT).show()
                        true
                    }
                    onXItemClickListener = { view, _, entity ->
                        Toast.makeText(view.context, "当前 position:  " + entity.itemMultiPosition + "  " + entity.message, Toast.LENGTH_SHORT).show()
                    }
                }
        val multiAdapter = recyclerView.multiAdapter<SimpleXMultiItem>()
        multiAdapter.itemLayoutId = {
            when (it) {
                TYPE_LINE -> R.layout.item_line
                else -> R.layout.item_multi
            }
        }
        multiAdapter.gridLayoutManagerSpanSize = { itemViewType, gridManager, position ->
            if (itemViewType != TYPE_ITEM) {
                gridManager.spanCount
            } else {
                1
            }
        }
        multiAdapter.staggeredGridLayoutManagerFullSpan = { itemViewType -> itemViewType != TYPE_ITEM }

        multiAdapter.xMultiBind = { holder, entity, itemViewType, position ->
            when (itemViewType) {
                TYPE_ITEM -> {
                    holder.setText(R.id.tv_message, entity.message)
                    val imageView = holder.getImageView(R.id.iv_icon)
                    imageView.setImageResource(entity.icon)
                }
            }
        }
    }

    companion object {
        private const val TYPE_LINE = 1
        private const val TYPE_ITEM = -11
        fun initSettingData(): MutableList<SimpleXMultiItem> {
            return ArrayList<SimpleXMultiItem>().apply {
                add(SimpleXMultiItem(itemMultiType = TYPE_LINE))
                add(SimpleXMultiItem(itemMultiType = TYPE_ITEM, itemMultiPosition = 0, message = "头像", icon = R.mipmap.ic_launcher))
                add(SimpleXMultiItem(itemMultiType = TYPE_LINE))
                add(SimpleXMultiItem(itemMultiType = TYPE_ITEM, itemMultiPosition = 1, message = "收藏", icon = R.mipmap.ic_launcher))
                add(SimpleXMultiItem(itemMultiType = TYPE_ITEM, itemMultiPosition = 2, message = "相册", icon = R.mipmap.ic_launcher))
                add(SimpleXMultiItem(itemMultiType = TYPE_LINE))
                add(SimpleXMultiItem(itemMultiType = TYPE_ITEM, itemMultiPosition = 3, message = "钱包", icon = R.mipmap.ic_launcher))
                add(SimpleXMultiItem(itemMultiType = TYPE_ITEM, itemMultiPosition = 4, message = "卡包", icon = R.mipmap.ic_launcher))
                add(SimpleXMultiItem(itemMultiType = TYPE_LINE))
                add(SimpleXMultiItem(itemMultiType = TYPE_ITEM, itemMultiPosition = 5, message = "表情", icon = R.mipmap.ic_launcher))
                add(SimpleXMultiItem(itemMultiType = TYPE_LINE))
                add(SimpleXMultiItem(itemMultiType = TYPE_ITEM, itemMultiPosition = 6, message = "设置", icon = R.mipmap.ic_launcher))
            }
        }
    }
}
