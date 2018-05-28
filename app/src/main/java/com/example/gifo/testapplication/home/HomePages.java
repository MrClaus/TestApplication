package com.example.gifo.testapplication.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.home.pages.forecast.ForecastPage;
import com.example.gifo.testapplication.home.pages.navigation.NavigationPage;

/**
 * Created by gifo on 23.04.2018.
 */

public class HomePages extends Fragment {

    int pageNumber; // номер текущего фрагмента страницы PageView
    int weatherPage; // для страницы Page с прогнозом погоды на weatherPage- количество дней

    static HomePages newInstance(int page, int weatherPage) {
        HomePages pageFragment = new HomePages();
        pageFragment.pageNumber = page;
        pageFragment.weatherPage = weatherPage;
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        /** Первая страница - навигационная
         *  Вторая страница - прогноз погоды на N- количество дней
         *  Чтобы не писать здесь кучукода по созданию контента текущих страниц для каждой,
         *  передадим их создание на сторону (наследникам PageView)
         */

        if (pageNumber == 0) view = new NavigationPage(inflater.inflate(R.layout.home_page_navigation, null), getContext()).getView();
        if (pageNumber == 1) view = new ForecastPage(inflater.inflate(R.layout.home_page_forecast, null), getContext()).getView();
        return view;
    }
}
