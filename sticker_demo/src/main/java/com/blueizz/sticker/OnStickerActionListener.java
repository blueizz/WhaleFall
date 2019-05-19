package com.blueizz.sticker;

public interface OnStickerActionListener {

    /*删除贴纸*/
    void onDelete();

    /*编辑贴纸*/
    void onEdit(StickerView stickerView);
}
