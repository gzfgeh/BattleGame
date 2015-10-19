package com.gzfgeh.happytime.widget;

import android.view.View;

import com.flyco.banner.anim.BaseAnimator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by guzhenfu on 15/10/19.
 */
public class ZoomInEnter extends BaseAnimator {

    public ZoomInEnter() {
        this.duration = 200;
    }

    @Override
    public void setAnimation(View view) {
        this.animatorSet.playTogether(new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleX", new float[]{1.0F, 1.5F}),
                ObjectAnimator.ofFloat(view, "scaleY", new float[]{1.0F, 1.5F})});
    }
}
