package com.zejor.mvp.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zejor.App;
import com.zejor.R;
import com.zejor.base.BaseActivity;
import com.zejor.component.ApplicationComponent;
import com.zejor.mvp.fragments.HomeFragment;
import com.zejor.mvp.fragments.MyFragment;
import com.zejor.mvp.fragments.OrderFragment;
import com.zejor.utils.SharedPerferenceUtil;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_order)
    RadioButton rbOrder;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_lead)
    RadioGroup rgLead;
    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private MyFragment myFragment;
    private Fragment presentFragment;
    private boolean turnoff = false;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void changeFragment(Fragment nextFragment) {
        if (nextFragment.isAdded()) {
            getFragmentManager().beginTransaction().hide(presentFragment).show(nextFragment).commit();
        } else {
            getFragmentManager().beginTransaction().hide(presentFragment).add(R.id.fl_fragment, nextFragment).commit();
        }
        presentFragment = nextFragment;
    }

    @Override
    public void onRetry() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getInstance().isToHome()) {
            rbHome.setChecked(true);
        }
    }

    public void viewOrder() {
        rbOrder.setChecked(true);
    }

    public void viewHome() {
        rbHome.setChecked(true);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }


    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        rbHome.setChecked(true);
        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        myFragment = new MyFragment();
        presentFragment = homeFragment;
        getFragmentManager().beginTransaction().add(R.id.fl_fragment, presentFragment).commit();
        rgLead.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        changeFragment(homeFragment);
                        break;
                    case R.id.rb_order:
                        if ((boolean) SharedPerferenceUtil.getData(HomeActivity.this, "isLogin", false)) {
                            changeFragment(orderFragment);
                        } else {
                            rbHome.setChecked(true);
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        }
                        break;
                    case R.id.rb_mine:
                        if ((boolean) SharedPerferenceUtil.getData(HomeActivity.this, "isLogin", false)) {
                            changeFragment(myFragment);
                        } else {
                            rbHome.setChecked(true);
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!turnoff) {
            turnoff = true;
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    turnoff = false;
                }
            }, 1000);
        } else {
            finish();
        }
    }
    public void setChangFragment(int i){
        changeFragment(homeFragment);
        rbHome.setChecked(true);
    }
}
