package com.xadapter.adapter.multi;

/**
 * by y on 2017/3/9
 */

public class MultiItem implements MultiCallBack {

    private int itemType;
    private int itemPosition = -1;
    public String message;
    public String messageSuffix;
    public int icon;

    /**
     * @param itemType RecyclerView Type
     */
    public MultiItem(int itemType) {
        this.itemType = itemType;
    }

    /**
     * @param itemType     RecyclerView Type
     * @param itemPosition 正确的position
     * @param message      携带信息
     */
    public MultiItem(int itemType, int itemPosition, String message) {
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
    public MultiItem(int itemType, int itemPosition, String message, String messageSuffix) {
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
    public MultiItem(int itemType, String message, String messageSuffix) {
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
    public MultiItem(int itemType, int itemPosition, String message, int icon) {
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
    public MultiItem(int itemType, int itemPosition, String message, String messageSuffix, int icon) {
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
    public boolean hasClick() {
        return itemPosition != -1;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
