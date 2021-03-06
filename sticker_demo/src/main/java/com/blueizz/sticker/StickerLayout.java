package com.blueizz.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class StickerLayout extends FrameLayout {

    private Context context;
    // 贴纸的集合
    private List<StickerView> stickerViews;
    // 贴纸的View参数
    private FrameLayout.LayoutParams stickerParams;
    // 背景图片控件
    private ImageView ivImage;

    // 缩放操作图片
    private int zoomRes;
    // 缩放操作图片
    private int removeRes;

    public StickerLayout(Context context) {
        this(context, null);
    }

    public StickerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        stickerViews = new ArrayList<>();
        stickerParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addBackgroundImage();
    }

    /**
     * 初始化背景图片控件
     */
    private void addBackgroundImage() {
        FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        ivImage = new ImageView(context);
        ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
        ivImage.setLayoutParams(bgParams);
        addView(ivImage);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isInEdit();
    }

    /**
     * 点击贴纸外区域时使贴纸变为不可编辑状态
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getPreview();
        return super.onTouchEvent(event);
    }

    private boolean isInEdit() {
        boolean isEdit = false;
        for (StickerView item : stickerViews) {
            if (item == null) continue;
            isEdit = item.isInEditMode();
            RectF rectF = item.getSticker().getSrcImageBound();
            Log.i("StickerLayout", rectF.left + "," + rectF.top + "," + rectF.right + "," + rectF.bottom);
        }
        return isEdit;
    }

    /**
     * 设置背景图片
     */
    public void setBackgroundImage(int resource) {
        ivImage.setImageResource(resource);
    }

    /**
     * 新增贴纸
     */
    public void addSticker(int resource) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
        addSticker(bitmap);
    }

    /**
     * 新增贴纸
     */
    public void addSticker(Bitmap bitmap) {
        final StickerView sv = new StickerView(context);
        sv.setImageBitmap(bitmap);
        sv.setLayoutParams(stickerParams);
        sv.setOnStickerActionListener(new OnStickerActionListener() {
            @Override
            public void onDelete() {
                // 处理删除操作
                removeView(sv);
                stickerViews.remove(sv);
                redraw();
            }

            @Override
            public void onEdit(StickerView stickerView) {
                int position = stickerViews.indexOf(stickerView);
                stickerView.setEdit(true);
                stickerView.bringToFront();

                int size = stickerViews.size();
                for (int i = 0; i < size; i++) {
                    StickerView item = stickerViews.get(i);
                    if (item == null) continue;
                    if (position != i) {
                        item.setEdit(false);
                    }
                }
            }
        });
        addView(sv);
        stickerViews.add(sv);
        redraw();
    }

    /**
     * 查看贴纸的预览操作
     */
    public void getPreview() {
        for (StickerView item : stickerViews) {
            if (item == null) continue;
            item.setEdit(false);
        }
    }

    /**
     * 重置贴纸的操作列表
     */
    private void redraw() {
        redraw(true);
    }

    /**
     * 重置贴纸的操作列表
     */
    private void redraw(boolean isNotGenerate) {
        int size = stickerViews.size();
        if (size <= 0) return;
        for (int i = size - 1; i >= 0; i--) {
            StickerView item = stickerViews.get(i);
            if (item == null) continue;
            item.setZoomRes(zoomRes);
            item.setRemoveRes(removeRes);
            if (i == size - 1) {
                item.setEdit(isNotGenerate);
            } else {
                item.setEdit(false);
            }
            stickerViews.set(i, item);
        }
    }

    /**
     * 生成合成图片
     *
     * @return
     */
    public Bitmap generateCombinedBitmap() {
        redraw(false);
        Bitmap dst = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dst);
        draw(canvas);
        return dst;
    }

    public void setZoomRes(int zoomRes) {
        this.zoomRes = zoomRes;
    }

    public void setRemoveRes(int removeRes) {
        this.removeRes = removeRes;
    }
}
