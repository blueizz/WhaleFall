package com.blueizz.sticker.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 作者：ZhouYou
 * 日期：2016/12/2.
 */
public class FileUtils {

    private static File mCacheFile;

    /**
     * 获取临时图片的缓存路径
     *
     * @return
     */
    public static File getCacheFile(Context context) {
        File file = new File(getAppCacheDir(context), "image");
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = "temp_" + System.currentTimeMillis() + ".jpg";
        return new File(file, fileName);
    }

    private static File getAppCacheDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            mCacheFile = context.getExternalCacheDir();
        }
        if (mCacheFile == null) {
            mCacheFile = context.getCacheDir();
        }
        return mCacheFile;
    }
}
