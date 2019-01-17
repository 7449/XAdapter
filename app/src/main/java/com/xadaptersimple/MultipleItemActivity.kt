package com.xadaptersimple

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xadapter.OnItemClickListener
import com.xadapter.OnItemLongClickListener
import com.xadapter.OnXMultiAdapterListener
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.simple.SimpleXMultiItem
import kotlinx.android.synthetic.main.recyclerview_layout.*

/**
 * by y on 2017/1/12.
 */

class MultipleItemActivity : AppCompatActivity(), OnItemClickListener<SimpleXMultiItem>,
        OnItemLongClickListener<SimpleXMultiItem>,
        OnXMultiAdapterListener<SimpleXMultiItem> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiple_layout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = XMultiAdapter(initSettingData())
                .apply {
                    onXMultiAdapterListener = this@MultipleItemActivity
                    onItemClickListener = this@MultipleItemActivity
                    onLongClickListener = this@MultipleItemActivity
                }
    }

    override fun onItemClick(view: View, position: Int, entity: SimpleXMultiItem) {
        Toast.makeText(view.context, "当前 position:  " + entity.itemMultiPosition + "  " + entity.message, Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(view: View, position: Int, entity: SimpleXMultiItem): Boolean {
        Toast.makeText(view.context, "当前内容  = " + entity.message, Toast.LENGTH_SHORT).show()
        return true
    }

    override fun multiLayoutId(viewItemType: Int): Int {
        return when (viewItemType) {
            TYPE_LINE -> R.layout.item_line
            else -> R.layout.item_multi
        }
    }

    override fun getGridLayoutManagerSpanSize(itemViewType: Int, gridManager: GridLayoutManager, position: Int): Int {
        return if (itemViewType != TYPE_ITEM) {
            gridManager.spanCount
        } else {
            1
        }
    }

    override fun getStaggeredGridLayoutManagerFullSpan(itemViewType: Int): Boolean {
        return itemViewType != TYPE_ITEM
    }

    override fun onXMultiBind(holder: XViewHolder, entity: SimpleXMultiItem, itemViewType: Int, position: Int) {
        when (itemViewType) {
            TYPE_ITEM -> {
                holder.setTextView(R.id.tv_message, entity.message)
                val imageView = holder.getImageView(R.id.iv_icon)
                imageView.setImageResource(entity.icon)
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
