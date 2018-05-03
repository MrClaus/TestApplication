package com.example.gifo.testapplication.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by gifo on 02.05.2018.
 */

public class HomePagesAdapter extends FragmentPagerAdapter {

    public static int pageCount; // количество вкладок HomePages (ViewPager)
    private int weatherDays; // количество дней, для которых составляется прогноз

    public HomePagesAdapter(int pageCount, FragmentManager mng, int weatherDays) {
        super(mng);
        HomePagesAdapter.pageCount = pageCount;
        this.weatherDays = weatherDays;
    }

    @Override
    public Fragment getItem(int position) {
        // Вторая вкладка HomePages - прогноз погоды, именно ей и передаём значение weatherDays
        return HomePages.newInstance(position, (position == 1) ? weatherDays: 0);
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}