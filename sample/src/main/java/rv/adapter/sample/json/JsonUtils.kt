package rv.adapter.sample.json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rv.adapter.multiple.SimpleXMultiItem
import rv.adapter.sample.R
import rv.adapter.sample.page.MultipleActivity

/**
 * by y on 2016/11/17
 */
object JsonUtils {

    private const val json =
        "[{\"image_hue\":\"0x7d95b3\",\"hint\":\"作者 \\/ 张文远\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9717792\",\"image\":\"https:\\/\\/pic4.zhimg.com\\/v2-51e98034d2b0f95a6ac8311a5ffb5c9f.jpg\",\"title\":\"如何快速记下所有奥特曼？\",\"ga_prefix\":\"120620\",\"type\":0,\"id\":9717792},{\"image_hue\":\"0x879943\",\"hint\":\"作者 \\/ 苏澄宇\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9717531\",\"image\":\"https:\\/\\/pic2.zhimg.com\\/v2-5c87a645d36d140fa87df6e8ca7cb989.jpg\",\"title\":\"斑马的条纹到底是干嘛用的？\",\"ga_prefix\":\"120407\",\"type\":0,\"id\":9717531},{\"image_hue\":\"0xb39a7d\",\"hint\":\"作者 \\/ 混乱博物馆\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9717547\",\"image\":\"https:\\/\\/pic4.zhimg.com\\/v2-60f220ee6c5bf035d0eaf2dd4736342b.jpg\",\"title\":\"为什么狗会如此亲近人类?\",\"ga_prefix\":\"120107\",\"type\":0,\"id\":9717547},{\"image_hue\":\"0x384c62\",\"hint\":\"作者 \\/ 丘寒\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9717774\",\"image\":\"https:\\/\\/pic4.zhimg.com\\/v2-2379bf3e788a57c55a036bfb0b9b4aff.jpg\",\"title\":\"我在成都，观雪山\",\"ga_prefix\":\"112716\",\"type\":0,\"id\":9717774},{\"image_hue\":\"0x57778f\",\"hint\":\"作者 \\/ 卢西\",\"url\":\"https:\\/\\/daily.zhihu.com\\/story\\/9717456\",\"image\":\"https:\\/\\/pic3.zhimg.com\\/v2-8e9852df41f6c0624096f01dd9f72e0e.jpg\",\"title\":\"假如乘坐超音速飞机时突然弹射出去，会不会在音障上被撞成碎片？\",\"ga_prefix\":\"112509\",\"type\":0,\"id\":9717456}]"

    val jsonList: ArrayList<SampleEntity> =
        Gson().fromJson(json, object : TypeToken<ArrayList<SampleEntity>>() {}.type)

    val multipleList = ArrayList<SimpleXMultiItem>().apply {
        add(SimpleXMultiItem(itemMultiType = MultipleActivity.TYPE_LINE))
        add(
            SimpleXMultiItem(
                itemMultiType = MultipleActivity.TYPE_ITEM,
                itemMultiPosition = 0,
                message = "头像",
                icon = R.mipmap.ic_launcher
            )
        )
        add(SimpleXMultiItem(itemMultiType = MultipleActivity.TYPE_LINE))
        add(
            SimpleXMultiItem(
                itemMultiType = MultipleActivity.TYPE_ITEM,
                itemMultiPosition = 1,
                message = "收藏",
                icon = R.mipmap.ic_launcher
            )
        )
        add(
            SimpleXMultiItem(
                itemMultiType = MultipleActivity.TYPE_ITEM,
                itemMultiPosition = 2,
                message = "相册",
                icon = R.mipmap.ic_launcher
            )
        )
        add(SimpleXMultiItem(itemMultiType = MultipleActivity.TYPE_LINE))
        add(
            SimpleXMultiItem(
                itemMultiType = MultipleActivity.TYPE_ITEM,
                itemMultiPosition = 3,
                message = "钱包",
                icon = R.mipmap.ic_launcher
            )
        )
        add(
            SimpleXMultiItem(
                itemMultiType = MultipleActivity.TYPE_ITEM,
                itemMultiPosition = 4,
                message = "卡包",
                icon = R.mipmap.ic_launcher
            )
        )
        add(SimpleXMultiItem(itemMultiType = MultipleActivity.TYPE_LINE))
        add(
            SimpleXMultiItem(
                itemMultiType = MultipleActivity.TYPE_ITEM,
                itemMultiPosition = 5,
                message = "表情",
                icon = R.mipmap.ic_launcher
            )
        )
        add(SimpleXMultiItem(itemMultiType = MultipleActivity.TYPE_LINE))
        add(
            SimpleXMultiItem(
                itemMultiType = MultipleActivity.TYPE_ITEM,
                itemMultiPosition = 6,
                message = "设置",
                icon = R.mipmap.ic_launcher
            )
        )
    }

}
