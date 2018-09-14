package com.zhitou.job.main.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.zhitou.job.R;
import com.zhitou.job.main.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCH on 2018/9/13.
 *
 * 淘宝相关的数据
 */
public class TaoBaoFragment extends BaseFragment {
    private ViewPager mViewPager;
    private SlidingTabLayout mStl;
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_taobao_main;
    }

    @Override
    protected void setUpView() {
        mViewPager = (ViewPager) getContentView().findViewById(R.id.vp);
        mStl = (SlidingTabLayout) getContentView().findViewById(R.id.stl);
    }

    @Override
    protected void setUpData() {
        names.add("电脑数码");
        names.add("运动户外");
        names.add("服饰皮包");
        names.add("个护化妆");
        names.add("日用百货");
        names.add("宿舍神器");
        names.add("图书音像");

        for (String name:names) {
            mFragments.add(new TaoBaoProductListFragment(name));
        }

        mAdapter = new MyPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mStl.setViewPager(mViewPager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return names.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
