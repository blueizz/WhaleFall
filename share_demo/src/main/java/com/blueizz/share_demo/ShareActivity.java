package com.blueizz.share_demo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ShareActivity===>";
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    private static final String EXTERNAL_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_share);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        }

        findViewById(R.id.weibo).setOnClickListener(this);
        findViewById(R.id.wechat).setOnClickListener(this);
        findViewById(R.id.wecha_moments).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.weibo) {
            share(SinaWeibo.NAME);
        } else if (v.getId() == R.id.wechat) {
            share(Wechat.NAME);
        } else if (v.getId() == R.id.wecha_moments) {
            share(WechatMoments.NAME);
        }
    }

    private void share(String platform) {
        HashMap<String,Object> optionMap = new HashMap<>();
        optionMap.put("AppId","d580ad56b4b5");
        optionMap.put("AppSecret","7fcae59a62342e7e2759e9e397c82bdd");
        optionMap.put("BypassApproval",true);
        optionMap.put("Enable",true);
        ShareSDK.setPlatformDevInfo(platform,optionMap);

        ShareParams params = new ShareParams();
        params.setShareType(Platform.SHARE_IMAGE);
        params.setText("测试分享文本");
        String img1 = EXTERNAL_PATH + "/DCIM/Picture/头像.jpg";
        String img2 = EXTERNAL_PATH + "/DCIM/Picture/梦呓.jpg";
        params.setImageArray(new String[]{img1, img2});
//        params.setImagePath(img1);
        Platform mPlatform = ShareSDK.getPlatform(platform);
        mPlatform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });

        mPlatform.share(params);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // Permission Denied
                Toast.makeText(ShareActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

