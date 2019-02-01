package com.wolf.bestarch;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wolf.bestarch.home.HomeFragment;
import com.wolf.bestarch.hotel.HotelFragment;
import com.wolf.bestarch.mine.MineFragment;
import com.zzc.mobilearch.uilibs.activity.IntelligentActivityImpl;
import com.zzc.mobilearch.uilibs.widget.badgetab.BadgeTabLayout;
import com.zzc.mobilearch.uilibs.widget.badgetab.BadgeTabView;

public class MainActivity extends IntelligentActivityImpl implements BadgeTabLayout.OnCheckedChangeListener, BadgeTabView.OnCheckedChangeListener {

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    BadgeTabLayout mBottomTabLayout;
    MainViewModel mMainViewModel;

    @Override
    public void initView() {
        mBottomTabLayout = findViewById(R.id.home_tab_layout);

        mBottomTabLayout.setOnCheckedChangeListener(this);

        initFragments();

        selectFragmentById(R.id.tab_home);

        getUIHelper().setupToolbar();

        getUIHelper().toolbar().setTitle("Main");

        getUIHelper().setupLoading();

        getUIHelper().setupStatusbar();

        getUIHelper().statusbar().fullScreen();

        findViewById(R.id.showLoading).setOnClickListener(v -> {
            mMainViewModel.getUIBehavior().showLoading("hello world!");
        });
        findViewById(R.id.hideLoading).setOnClickListener(v -> {
            mMainViewModel.getUIBehavior().hideLoading();
        });
    }

    @Override
    public void initData() {
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getLifecycle().addObserver(mMainViewModel);
    }

    Fragment[] mFragments;

    void initFragments() {
        mFragments = new Fragment[3];
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        Fragment fragment;

        fragment = fragmentManager.findFragmentByTag(String.valueOf(R.id.tab_home));
        if (fragment == null) {
            fragment = HomeFragment.newInstance();
        }
        mFragments[0] = fragment;
        ft.add(R.id.home_content_layout, fragment, String.valueOf(R.id.tab_home));

        fragment = fragmentManager.findFragmentByTag(String.valueOf(R.id.tab_destination));
        if (fragment == null) {
            fragment = HotelFragment.newInstance();
        }
        mFragments[1] = fragment;
        ft.add(R.id.home_content_layout, fragment, String.valueOf(R.id.tab_destination));

        fragment = fragmentManager.findFragmentByTag(String.valueOf(R.id.tab_mine));
        if (fragment == null) {
            fragment = MineFragment.newInstance();
        }
        mFragments[2] = fragment;
        ft.add(R.id.home_content_layout, fragment, String.valueOf(R.id.tab_mine));

        ft.commitNowAllowingStateLoss();
    }

    Fragment mSelectedFragment;

    private boolean selectFragmentById(int checkedId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        Fragment fragment;
        fragment = fragmentManager.findFragmentByTag(String.valueOf(checkedId));
        if (fragment != null) {
            if (mSelectedFragment != null) {
                ft.hide(mSelectedFragment);
            } else {
                // 首次 切换按钮
                for (int i = 0; i < mFragments.length; i++) {
                    if (fragment != mFragments[i]) {
                        ft.hide(mFragments[i]);
                    }
                }
            }
            boolean hidden = fragment.isHidden();
            if (!hidden) {
                fragment.onHiddenChanged(false);
            }
            ft.show(fragment).commitNowAllowingStateLoss();
            mSelectedFragment = fragment;
            return true;
        }
        return false;
    }


    @Override
    public void onCheckedChanged(BadgeTabLayout badgeTabLayout, int checkedId) {
        selectFragmentById(checkedId);
        BadgeTabView tabView = badgeTabLayout.findViewById(checkedId);
        tabView.setChecked(true);
    }

    @Override
    public void onCheckedChanged(BadgeTabView buttonView, boolean isChecked) {

    }
}
