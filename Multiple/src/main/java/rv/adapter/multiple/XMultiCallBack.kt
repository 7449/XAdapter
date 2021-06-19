package rv.adapter.multiple

interface XMultiCallBack {

    val itemType: Int
    val position: Int

    companion object {
        const val NO_CLICK_POSITION = -10001
    }
}