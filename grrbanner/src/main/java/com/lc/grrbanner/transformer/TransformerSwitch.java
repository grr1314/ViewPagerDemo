package com.lc.grrbanner.transformer;

/**
 * Created by hoomsun on 2017/9/7.
 */

public class TransformerSwitch {
    public static BannerPageTransformer bannerPageTransformer(TransformerType type) {
        switch (type) {
            default:
                return new DefaultPageTransformer();
            case Alpha: {
                return new AlphaPageTranformer();
            }
            case Default: {
                return new DefaultPageTransformer();
            }
            case Scale: {
                return new ScalePageTransformer();
            }
        }
    }
}
