package com.lc.grrbanner.BannerMode;

/**
 * Created by hoomsun on 2017/9/7.
 */

public class BannerMode_Order extends BannerViewMode {
    int size;

    @Override
    public int transformerSize(int size) {
        this.size = size;
        return size == 0 ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public int tranformerPosition(int pos) {
        if (size != 0)
            pos = pos % size;
        return pos;
    }
}
