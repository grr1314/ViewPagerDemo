package com.lc.viewpagerdemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lc.grrbanner.BannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> datas = new ArrayList<>();
    List<String> imgUrls = new ArrayList<>();
    BannerView bannerView;
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBannerView();
    }

    private void initBannerView() {
        bannerView = (BannerView) findViewById(R.id.bannerView);
        initBannerData();
        bannerView.setData(imgUrls,datas);
        bannerView.setItemInfoAdapter(new BannerView.Adapter() {
            @Override
            public void fillBannerItem(BannerView banner, View itemView, Object model, int position) {
                Glide.with(MainActivity.this)
                        .load((String) model)
                        .placeholder(R.drawable.banner_default)
                        .error(R.drawable.banner_default)
                        .into((ImageView) itemView);
            }
        });
    }

    private void initBannerData() {
        for (String url : Constant.imgUrls) {
            imgUrls.add(url);
            datas.add("data");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
