package com.lc.grrbanner.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 这是一个总的Transformer
 * Created by hoomsun on 2017/9/6.
 */

public class BannerPageTransformer implements ViewPager.PageTransformer {
    transformersInterface anInterface;

    @Override
    public void transformPage(View page, float position) {
        //ViewPager页面的切换效果
        if (position < -1.0f) {
            anInterface.handleInvisiblePage(page, position);
        } else if (position < 0f) {
            anInterface.handleLeftPage(page, position);
        } else if (position < 1.0f) {
            anInterface.handleRightPage(page, position);
        } else {
            anInterface.handleInvisiblePage(page, position);
        }
    }

    interface transformersInterface {
        void handleInvisiblePage(View view, float position);

        void handleLeftPage(View view, float position);

        void handleRightPage(View view, float position);

    }

    public void setAnInterface(transformersInterface anInterface) {
        this.anInterface = anInterface;
    }

}
