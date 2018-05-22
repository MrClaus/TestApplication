package com.example.gifo.testapplication.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.home.pages.forecast.ForecastView;
import com.example.gifo.testapplication.home.pages.navigation.NavigationView;

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
         */

        if (pageNumber == 0) view = new NavigationView(inflater.inflate(R.layout.home_page_navigation, null)).getView();
        else if (pageNumber == 1) view = new ForecastView(inflater.inflate(R.layout.home_page_forecast, null)).getView();
        return view;
    }
}
