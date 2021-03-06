package com.blueizz.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.blueizz.sticker.utils.PointUtils;

public class StickerView extends AppCompatImageView {

    private Context context;
    // 被操作的贴纸对象
    private Sticker sticker;
    // 手指按下时图片的矩阵
    private Matrix downMatrix = new Matrix();
    // 手指移动时图片的矩阵
    private Matrix moveMatrix = new Matrix();
    //图片左上角坐标
    private PointF imageOrgPoint = new PointF();
    // 缩放操作图片
    private StickerActionIcon zoomIcon;
    // 缩放操作图片
    private StickerActionIcon removeIcon;

    // 触控模式
    private int mode;
    // 是否正在处于编辑
    private boolean isEdit = true;
    // 贴纸的操作监听
    private OnStickerActionListener listener;

    public void setOnStickerActionListener(OnStickerActionListener listener) {
        this.listener = listener;
    }

    public StickerView(Context context) {
        this(context, null);
    }

    public StickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setScaleType(ScaleType.MATRIX);
        zoomIcon = new StickerActionIcon(context);
        removeIcon = new StickerActionIcon(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            sticker.getMatrix().postTranslate((getWidth() - sticker.getStickerWidth()) / 2,
                    (getHeight() - sticker.getStickerHeight()) / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (sticker == null) return;
        sticker.draw(canvas, getBitmap(isEdit ? R.drawable.sticker_add : R.drawable.sticker_done));
        float[] points = PointUtils.getBitmapPoints(sticker.getSrcImage(), sticker.getMatrix());
        float x1 = points[0];
        float y1 = points[1];
        float x2 = points[2];
        float y2 = points[3];
        float x3 = points[4];
        float y3 = points[5];
        float x4 = points[6];
        float y4 = points[7];
        if (isEdit) {
            // 画边框
            //            canvas.drawLine(x1, y1, x2, y2, paintEdge);
            //            canvas.drawLine(x2, y2, x4, y4, paintEdge);
            //            canvas.drawLine(x4, y4, x3, y3, paintEdge);
            //            canvas.drawLine(x3, y3, x1, y1, paintEdge);
            // 画操作按钮图片
            removeIcon.draw(canvas, x2, y2);
            zoomIcon.draw(canvas, x4, y4);
        }
    }

    // 手指按下屏幕的X坐标
    private float downX;
    // 手指按下屏幕的Y坐标
    private float downY;
    // 手指之间的初始距离
    private float oldDistance;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        boolean isStickerOnEdit = true;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                if (sticker == null) return false;
                // 删除操作
                if (removeIcon.isInActionCheck(event)) {
                    if (listener != null) {
                        listener.onDelete();
                    }
                }
                // 缩放手势验证
                else if (zoomIcon.isInActionCheck(event)) {
                    mode = ActionMode.ZOOM;
                    downMatrix.set(sticker.getMatrix());
                    imageOrgPoint = sticker.getImageOrgPoint(downMatrix);
                    oldDistance = sticker.getSingleTouchDistance(event, imageOrgPoint);
                    Log.d("onTouchEvent", "缩放手势");
                }
                // 平移手势验证
                else if (isInStickerArea(sticker, event)) {
                    mode = ActionMode.TRANS;
                    downMatrix.set(sticker.getMatrix());
                    Log.d("onTouchEvent", "平移手势");
                } else {
                    isStickerOnEdit = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 缩放
                if (mode == ActionMode.ZOOM) {
                    moveMatrix.set(downMatrix);
                    float scale = sticker.getSingleTouchDistance(event, imageOrgPoint) / oldDistance;
                    moveMatrix.postScale(scale, scale, imageOrgPoint.x, imageOrgPoint.y);
                    sticker.getMatrix().set(moveMatrix);
                    invalidate();
                }
                // 平移
                else if (mode == ActionMode.TRANS) {
                    moveMatrix.set(downMatrix);
                    moveMatrix.postTranslate(event.getX() - downX, event.getY() - downY);
                    sticker.getMatrix().set(moveMatrix);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                mode = ActionMode.NONE;
                imageOrgPoint = null;
                break;
            default:
                break;
        }
        if (isStickerOnEdit && listener != null) {
            listener.onEdit(this);
        }
        return isStickerOnEdit;
    }

    /**
     * 判断手指是否在操作区域内
     *
     * @param sticker
     * @param event
     * @return
     */
    private boolean isInStickerArea(Sticker sticker, MotionEvent event) {
        RectF dst = sticker.getSrcImageBound();
        return dst.contains(event.getX(), event.getY());
    }

    /**
     * 添加贴纸
     *
     * @param resId
     */
    @Override
    public void setImageResource(int resId) {
        sticker = new Sticker(getBitmap(resId));
    }

    /**
     * 获取贴纸对象
     *
     * @return
     */
    public Sticker getSticker() {
        return sticker;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        sticker = new Sticker(bm);
    }

    /**
     * 设置是否贴纸正在处于编辑状态
     *
     * @param edit
     */
    public void setEdit(boolean edit) {
        isEdit = edit;
        postInvalidate();
    }

    /**
     * 设置缩放操作的图片
     *
     * @param zoomRes
     */
    public void setZoomRes(int zoomRes) {
        zoomIcon.setSrcIcon(zoomRes);
    }

    /**
     * 设置删除操作的图片
     *
     * @param removeRes
     */
    public void setRemoveRes(int removeRes) {
        removeIcon.setSrcIcon(removeRes);
    }


    private Bitmap getBitmap(int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }
}
