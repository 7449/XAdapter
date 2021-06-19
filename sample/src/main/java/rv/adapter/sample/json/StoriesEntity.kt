package rv.adapter.sample.json

import com.google.gson.annotations.SerializedName

/**
 * by y on 2016/11/17
 */
class StoriesEntity(@SerializedName("top_stories") val stories: ArrayList<SampleEntity>)

