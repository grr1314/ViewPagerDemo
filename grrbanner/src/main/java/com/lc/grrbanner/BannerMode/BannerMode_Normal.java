package com.lc.grrbanner.BannerMode;

/**
 * Created by hoomsun on 2017/9/7.
 */

public class BannerMode_Normal extends BannerViewMode {
    //处理size
    @Override
    public int transformerSize(int size) {
        return size;
    }

    @Override
    public int tranformerPosition(int pos) {
        return pos;
    }

}
