package com.lc.grrbanner.transformer;

import android.view.View;

/**
 * Created by hoomsun on 2017/9/7.
 */

public class DefaultPageTransformer extends BannerPageTransformer implements BannerPageTransformer.transformersInterface {
    public DefaultPageTransformer() {
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

    }

    @Override
    public void handleRightPage(View view, float position) {

    }
}
