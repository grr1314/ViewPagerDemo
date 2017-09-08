package com.lc.grrbanner.BannerMode;

/**
 * Created by hoomsun on 2017/9/7.
 */

public class BannerViewModeSwitch {
    public static BannerViewMode getBannerModeType(BannerModeType type) {
        switch (type) {
            default:
                return new BannerMode_Normal();

            case Default: {
                return new BannerMode_Normal();
            }
            case Order: {
                return new BannerMode_Order();
            }
            case Both: {
                return new BannerMode_Both();
            }
        }
    }
}
