package com.xadaptersimple

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xadapter.OnItemClickListener
import com.xadapter.OnItemLongClickListener
import com.xadapter.OnXMultiAdapterListener
import com.xadapter.XMultiCallBack
import com.xadapter.adapter.XMultiAdapter
import com.xadapter.holder.XViewHolder
import com.xadapter.simple.SimpleXMultiItem

/**
 * by y on 2017/1/12.
 */

class MultipleItemActivity : AppCompatActivity(), OnItemClickListener<SimpleXMultiItem>, OnItemLongClickListener<SimpleXMultiItem>, OnXMultiAdapterListener<SimpleXMultiItem> {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multiple_layout)
        recyclerView = findViewById(R.id.recyclerView)
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
        Toast.makeText(view.context, "当前 position:  " + position + "  " + entity.message, Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(view: View, position: Int, entity: SimpleXMultiItem) {
        Toast.makeText(view.context, "当前内容  = " + entity.message, Toast.LENGTH_SHORT).show()
    }

    override fun multiLayoutId(viewItemType: Int): Int {
        return when (viewItemType) {
            TYPE_LINE -> R.layout.item_line
            else -> R.layout.item_multi
        }
    }

    override fun getGridLayoutManagerSpanSize(itemViewType: Int, gridManager: GridLayoutManager, position: Int): Int {
        return if (itemViewType != XMultiCallBack.TYPE_ITEM) {
            gridManager.spanCount
        } else {
            1
        }
    }

    override fun getStaggeredGridLayoutManagerFullSpan(itemViewType: Int): Boolean {
        return itemViewType != XMultiCallBack.TYPE_ITEM
    }

    override fun onXMultiBind(holder: XViewHolder, entity: SimpleXMultiItem, itemViewType: Int, position: Int) {
        when (itemViewType) {
            XMultiCallBack.TYPE_ITEM -> {
                holder.setTextView(R.id.tv_message, entity.message)
                val imageView = holder.getImageView(R.id.iv_icon)
                imageView.setImageResource(entity.icon)
            }
        }
    }

    companion object {
        private const val TYPE_LINE = 1
        fun initSettingData(): MutableList<SimpleXMultiItem> {
            return ArrayList<SimpleXMultiItem>().apply {
                add(SimpleXMultiItem(TYPE_LINE))
                add(SimpleXMultiItem(XMultiCallBack.TYPE_ITEM, 0, "头像", R.mipmap.ic_launcher))
                add(SimpleXMultiItem(TYPE_LINE))
                add(SimpleXMultiItem(XMultiCallBack.TYPE_ITEM, 1, "收藏", R.mipmap.ic_launcher))
                add(SimpleXMultiItem(XMultiCallBack.TYPE_ITEM, 2, "相册", R.mipmap.ic_launcher))
                add(SimpleXMultiItem(TYPE_LINE))
                add(SimpleXMultiItem(XMultiCallBack.TYPE_ITEM, 3, "钱包", R.mipmap.ic_launcher))
                add(SimpleXMultiItem(XMultiCallBack.TYPE_ITEM, 4, "卡包", R.mipmap.ic_launcher))
                add(SimpleXMultiItem(TYPE_LINE))
                add(SimpleXMultiItem(XMultiCallBack.TYPE_ITEM, 5, "表情", R.mipmap.ic_launcher))
                add(SimpleXMultiItem(TYPE_LINE))
                add(SimpleXMultiItem(XMultiCallBack.TYPE_ITEM, 6, "设置", R.mipmap.ic_launcher))
            }
        }
    }
}
