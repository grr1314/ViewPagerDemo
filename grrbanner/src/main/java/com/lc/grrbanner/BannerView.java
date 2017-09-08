package com.lc.grrbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lc.grrbanner.BannerMode.BannerModeType;
import com.lc.grrbanner.BannerMode.BannerViewMode;
import com.lc.grrbanner.BannerMode.BannerViewModeSwitch;
import com.lc.grrbanner.transformer.TransformerSwitch;
import com.lc.grrbanner.transformer.TransformerType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoomsun on 2017/9/5.
 */

public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager = null;

    BannerViewAdapter adapter;
    private Context mContext;
    private int limit = 1;

    private List<String> bannerViewData = new ArrayList<>();
    private List<String> bannerDesData = new ArrayList<>();

    private List<View> views = new ArrayList<>();

    private int defaultMAginAndPadding = 10;
    //indicator的直接容器，是indicatorLayout的子布局
    private LinearLayout layoutIndicator;


    //自定义属性
    private boolean isShowIndicator;
    private int indicatorGravity;
    private int indicatorLeftAndRightMargin;
    private int indicatorTopAndBottomMargin;
    private int indicatorLayout_BackgroungRes = 0;
    private int indicatorLayout_BackgroundColor;
    private TransformerType transitionEffect;
    private BannerModeType mBannerViewMode;

    public BannerView(@NonNull Context context) {
        this(context, null);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        transitionEffect = TransformerType.Default;
        mBannerViewMode = BannerModeType.Default;
        initAttr(attrs);
        initView();
    }

    /**
     * 初始化一些自定义熟悉
     *
     * @param attrs
     */
    private void initAttr(AttributeSet attrs) {
        // 目前可以想到的自定义属性：1是否自动轮播 2ViewPager的Page的大下比例 3Page切换动画 4是否显示indicator
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        isShowIndicator = typedArray.getBoolean(R.styleable.BannerView_isShowIndicator, false);
        indicatorGravity = typedArray.getInt(R.styleable.BannerView_indicator_Gravity, Gravity.LEFT);
        indicatorLeftAndRightMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorLeftAndRightMargin, defaultMAginAndPadding);
        indicatorTopAndBottomMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_indicatorTopAndBottomMargin, defaultMAginAndPadding);
        indicatorLayout_BackgroungRes = typedArray.getResourceId(R.styleable.BannerView_indicatorLayout_BackgroungRes, 0);
        indicatorLayout_BackgroundColor = typedArray.getResourceId(R.styleable.BannerView_indicatorLayout_BackgroundColor, Color.TRANSPARENT);
        int ordinal = typedArray.getInt(R.styleable.BannerView_transitionEffect, TransformerType.Scale.ordinal());
        transitionEffect = TransformerType.values()[ordinal];
        int ordinal1 = typedArray.getInt(R.styleable.BannerView_bannerModeType, BannerModeType.Default.ordinal());
        mBannerViewMode = BannerModeType.values()[ordinal1];
        typedArray.recycle();
    }

    /**
     * 定义好ViewPager
     */
    private void initView() {
        if (mViewPager == null) {
            mViewPager = new ViewPager(mContext);
        }
        addView(mViewPager);

        //设置ViewPager的基本属性
        mViewPager.setOffscreenPageLimit(limit);
        adapter = new BannerViewAdapter();
        mViewPager.setAdapter(adapter);
        adapter.setBannerViewMode(BannerViewModeSwitch.getBannerModeType(mBannerViewMode));
        mViewPager.addOnPageChangeListener(this);

        //indicator部分
        if (isShowIndicator) {
            //添加indicator (可以通过一些属性设置indicator的显示位置)
            RelativeLayout indicatorLayout = new RelativeLayout(mContext);
            //设置BackGroundColor或者是res,优先级res高
            if (indicatorLayout_BackgroungRes == 0) {
                indicatorLayout.setBackgroundColor(indicatorLayout_BackgroundColor);
            } else {
                indicatorLayout.setBackgroundResource(indicatorLayout_BackgroungRes);
            }

            indicatorLayout.setPadding(defaultMAginAndPadding, defaultMAginAndPadding, defaultMAginAndPadding, defaultMAginAndPadding);
            LayoutParams indicatorLayoutLP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            indicatorLayoutLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            addView(indicatorLayout, indicatorLayoutLP);

            layoutIndicator = new LinearLayout(mContext);
            LayoutParams layoutIndicatorLP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            switch (indicatorGravity) {
                case Gravity.LEFT: {
                    layoutIndicatorLP.addRule(ALIGN_PARENT_LEFT);
                }
                break;
                case Gravity.RIGHT: {
                    layoutIndicatorLP.addRule(ALIGN_PARENT_RIGHT);
                }
                break;
                case Gravity.CENTER_HORIZONTAL: {
                    layoutIndicatorLP.addRule(CENTER_HORIZONTAL);
                }
                break;
            }

            layoutIndicator.setOrientation(LinearLayout.HORIZONTAL);
            indicatorLayout.addView(layoutIndicator, layoutIndicatorLP);
        }

        //通过修改布局参数来修改ViewPager的Page比例
        LayoutParams params = (LayoutParams) mViewPager.getLayoutParams();
        mViewPager.setLayoutParams(params);
        mViewPager.setCurrentItem(0);
        mViewPager.setPageTransformer(false, TransformerSwitch.bannerPageTransformer(transitionEffect));
    }

    public int getScreenWidth() {
        return ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }


    //设置数据放在BannerView内部，也就是说调用者不用关注数据适配
    public void setData(List<String> list) {
        setData(list, null);
    }

    public void setData(List<String> imgUrls, List<String> des) {
        if (bannerViewData != null && bannerViewData.size() != 0) {
            bannerViewData.clear();
        }
        bannerViewData.addAll(imgUrls);
        for (String str : imgUrls) {
            views.add(View.inflate(mContext, R.layout.item, null));
        }

        if (bannerDesData != null && bannerDesData.size() != 0) {
            bannerDesData.clear();
        }
        if (des != null) {
            bannerDesData.addAll(des);
        }
        adapter.notifyDataSetChanged();

        if (isShowIndicator) {
            initIndicator();
        }
        switchToSomePage(0);
    }

    /**
     * 初始化Indicator的视图
     */
    private void initIndicator() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(indicatorLeftAndRightMargin, indicatorTopAndBottomMargin, indicatorLeftAndRightMargin, indicatorTopAndBottomMargin);
        int mPointDrawableResId = R.drawable.bga_banner_selector_point_solid;
        ImageView indicatorView;
        for (int i = 0; i < views.size(); i++) {
            indicatorView = new ImageView(mContext);
            indicatorView.setLayoutParams(lp);
            indicatorView.setImageResource(mPointDrawableResId);
            layoutIndicator.addView(indicatorView);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        if (position == views.size() && positionOffset > 0.99) {
//            //在position4左滑且左滑positionOffset百分比接近1时，偷偷替换为position1（原本会滑到position5）
//            mViewPager.setCurrentItem(1, false);
//        } else if (position == 0 && positionOffset < 0.01) {
//            //在position1右滑且右滑百分比接近0时，偷偷替换为position4（原本会滑到position0）
//            mViewPager.setCurrentItem(4, false);
//        }
    }

    @Override
    public void onPageSelected(int position) {
        switchToSomePage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 0://什么都没做  空闲状态
                break;
            case 1://正在滑动
                break;
            case 2://滑动完毕
                break;
        }
    }

    /**
     * ViewPage切换到某页
     */
    private void switchToSomePage(int pos) {
        mViewPager.setCurrentItem(pos);
        //处理indicator
        if (isShowIndicator && pos <= views.size()) {
            for (int i = 0; i < views.size(); i++) {
                if (pos == i)
                    layoutIndicator.getChildAt(i).setEnabled(false);
                else
                    layoutIndicator.getChildAt(i).setEnabled(true);
            }
        }
    }

    /**
     * 将Adapter作为内部类，优势在于用户并不需要知道数据是如何适配的
     */
    class BannerViewAdapter extends PagerAdapter {
        BannerViewMode mBannerViewMode;

        public void setBannerViewMode(BannerViewMode bannerViewMode) {
            if (bannerViewMode == null) {
                mBannerViewMode = BannerViewModeSwitch.getBannerModeType(BannerModeType.Default);
            } else
                this.mBannerViewMode = bannerViewMode;

        }

        @Override
        public int getCount() {
            if (mBannerViewMode == null) {
                setBannerViewMode(null);
            }
            int size = mBannerViewMode.transformerSize(bannerViewData.size());
            return size;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //轮播策略，是否可以用策略模式？
            final int pos = mBannerViewMode.tranformerPosition(position);

            View itemView = views.get(pos);
            ImageView imageView = (ImageView) views.get(pos).findViewById(R.id.tv_item);
            TextView tv_item_text = (TextView) itemView.findViewById(R.id.tv_item_text);
            if (bannerDesData.size() != 0)
                tv_item_text.setText(bannerDesData.get(pos));
            itenInfoAdapter.fillBannerItem(BannerView.this, imageView, bannerViewData.get(pos), pos);

            ViewParent viewParent = itemView.getParent();
            if (viewParent != null) {
//                ((ViewGroup) viewParent).removeView(itemView);
            } else
                container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        /**
         * 判断ViewPager是否刷新当前页，默认是不刷新
         *
         * @param object
         * @return
         */
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    public void setItemInfoAdapter(Adapter itenInfoAdapter) {
        this.itenInfoAdapter = itenInfoAdapter;
    }

    Adapter itenInfoAdapter;

    public interface Adapter<V extends View, M> {
        void fillBannerItem(BannerView banner, V itemView, M model, int position);
    }

}
