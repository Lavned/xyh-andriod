package com.zejor.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zejor.R;
import com.zejor.adapter.ViewPagerAdatper;
import com.zejor.base.BaseActivity;
import com.zejor.component.ApplicationComponent;
import com.zejor.utils.SharedPerferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ViewPagerActivity extends BaseActivity {


    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    private List<View> mViewList;

    @Override
    public void onRetry() {

    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_view_pager;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        fullScreen();
    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        mViewList = new ArrayList<>();
        LayoutInflater lf = getLayoutInflater().from(this);
        View view1 = lf.inflate(R.layout.layout_guide1, null);
        View view2 = lf.inflate(R.layout.layout_guide1, null);
        View view3 = lf.inflate(R.layout.layout_guide1, null);
        ImageView iv_pic1 = view1.findViewById(R.id.iv_pic);
        ImageView iv_pic2 = view2.findViewById(R.id.iv_pic);
        ImageView iv_pic3 = view3.findViewById(R.id.iv_pic);
        Glide.with(this).load(R.drawable.pic_guide1).into(iv_pic1);
        Glide.with(this).load(R.drawable.pic_guide2).into(iv_pic2);
        Glide.with(this).load(R.drawable.pic_guide3).into(iv_pic3);
        iv_pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPerferenceUtil.saveData(ViewPagerActivity.this, "first", false);
                startActivity(new Intent(ViewPagerActivity.this, HomeActivity.class));
                finish();
            }
        });
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        vpGuide.setAdapter(new ViewPagerAdatper(mViewList));
    }

}
