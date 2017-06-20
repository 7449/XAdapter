package com.xadapter.adapter.multi;

/**
 * by y on 2017/3/9
 * <p>
 * <p>
 * 简单的MultiItem，使用者可继承{@link MultiCallBack} 自己定制
 */

public class SimpleMultiItem implements MultiCallBack {

    public String message;
    public String messageSuffix;
    public int icon;
    private int itemType;
    private int itemPosition = -1;

    /**
     * @param itemType RecyclerView Type
     */
    public SimpleMultiItem(int itemType) {
        this.itemType = itemType;
    }

    /**
     * @param itemType     RecyclerView Type
     * @param itemPosition 正确的position
     * @param message      携带信息
     */
    public SimpleMultiItem(int itemType, int itemPosition, String message) {
        this.itemType = itemType;
        this.itemPosition = itemPosition;
        this.message = message;
    }

    /**
     * @param itemType      RecyclerView Type
     * @param itemPosition  正确的position
     * @param message       携带信息
     * @param messageSuffix 携带的第二个信息
     */
    public SimpleMultiItem(int itemType, int itemPosition, String message, String messageSuffix) {
        this.itemType = itemType;
        this.itemPosition = itemPosition;
        this.message = message;
        this.messageSuffix = messageSuffix;
    }

    /**
     * @param itemType      RecyclerView Type
     * @param message       携带信息
     * @param messageSuffix 携带的第二个信息
     */
    public SimpleMultiItem(int itemType, String message, String messageSuffix) {
        this.itemType = itemType;
        this.message = message;
        this.messageSuffix = messageSuffix;
    }


    /**
     * @param itemType     RecyclerView Type
     * @param itemPosition 正确的position
     * @param message      携带信息
     * @param icon         图标或者其他参数
     */
    public SimpleMultiItem(int itemType, int itemPosition, String message, int icon) {
        this.itemType = itemType;
        this.itemPosition = itemPosition;
        this.message = message;
        this.icon = icon;
    }

    /**
     * @param itemType      RecyclerView Type
     * @param itemPosition  正确的position
     * @param message       携带信息
     * @param icon          图标或者其他参数
     * @param messageSuffix 携带的第二个信息
     */
    public SimpleMultiItem(int itemType, int itemPosition, String message, String messageSuffix, int icon) {
        this.itemType = itemType;
        this.itemPosition = itemPosition;
        this.message = message;
        this.messageSuffix = messageSuffix;
        this.icon = icon;
    }

    @Override
    public int getPosition() {
        return itemPosition;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
