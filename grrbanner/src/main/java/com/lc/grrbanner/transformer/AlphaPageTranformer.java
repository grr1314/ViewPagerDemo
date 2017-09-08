package com.lc.grrbanner.transformer;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by hoomsun on 2017/9/7.
 */

public class AlphaPageTranformer extends BannerPageTransformer implements BannerPageTransformer.transformersInterface {

    //默认的透明度
    private float minAlph = 0.5f;

    public AlphaPageTranformer() {
        initListener();
    }

    /**
     * 通过构造函数可以设置默认的透明度
     *
     * @param minAlph
     */
    public AlphaPageTranformer(float minAlph) {
        this.minAlph = minAlph;
        initListener();
    }

    private void initListener() {
        setAnInterface(this);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        ViewCompat.setAlpha(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewCompat.setAlpha(view, minAlph + (1 - minAlph) * (1 + position));
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewCompat.setAlpha(view, minAlph + (1 - minAlph) * (1 - position));
    }
}
