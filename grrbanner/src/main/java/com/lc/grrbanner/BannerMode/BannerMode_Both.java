package com.lc.grrbanner.BannerMode;

/**
 * Created by hoomsun on 2017/9/7.
 */

public class BannerMode_Both extends BannerViewMode {
    int sizeFinal;
    int size;

    @Override
    public int transformerSize(int size) {
        this.size=size;
        sizeFinal = size + 2;
        return sizeFinal;
    }

    @Override
    public int tranformerPosition(int pos) {
        pos = (pos - 1 + size) % size;
        return pos;
    }
}
