package com.lc.grrbanner.transformer;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by hoomsun on 2017/9/7.
 */

public class ScalePageTransformer extends BannerPageTransformer implements BannerPageTransformer.transformersInterface {
    // 缩放和透明比例，需要自己修改想要的比例
    private float scaleMin = 0.8f;

    public ScalePageTransformer() {
        initListener();
    }

    private void initListener() {
        setAnInterface(this);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {

    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewCompat.setScaleX(view, (1 - scaleMin) * position + 1);
        ViewCompat.setScaleY(view, (1 - scaleMin) * position + 1);
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewCompat.setScaleX(view, (scaleMin - 1) * position + 1);
        ViewCompat.setScaleY(view, (scaleMin - 1) * position + 1);
    }

}
