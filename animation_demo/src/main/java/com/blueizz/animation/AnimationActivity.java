package com.blueizz.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AnimationActivity extends Activity implements View.OnClickListener {
    private ImageView mImgIcon;

    private LinearLayout mButtonParent;
    private Button mTranslate;
    private Button mScale;
    private Button mRotate;
    private Button mAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation);

        findViews();
        initAnimation();
        setListener();
    }

    private void findViews() {
        mImgIcon = findViewById(R.id.img_icon);
        mButtonParent = findViewById(R.id.ll_button_parent);
        mTranslate = findViewById(R.id.translate);
        mScale = findViewById(R.id.scale);
        mRotate = findViewById(R.id.rotate);
        mAlpha = findViewById(R.id.alpha);
    }

    private void initAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mButtonParent.setLayoutAnimation(controller);
    }

    private void setListener() {
        mTranslate.setOnClickListener(this);
        mScale.setOnClickListener(this);
        mRotate.setOnClickListener(this);
        mAlpha.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Animation anim = null;
        int i = view.getId();
        if (i == R.id.translate) {
            anim = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        } else if (i == R.id.scale) {
            anim = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        } else if (i == R.id.rotate) {
            anim = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        } else if (i == R.id.alpha) {
            anim = new AlphaAnimation(0, 1);
            anim.setDuration(3000);
//            anim = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        }
        if (anim != null) {
            mImgIcon.startAnimation(anim);
        }
    }
}
