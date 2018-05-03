package com.example.gifo.testapplication.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gifo.testapplication.R;

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

        if (pageNumber == 0) view = inflater.inflate(R.layout.home_page_nav, null);
        else if (pageNumber == 1) view = inflater.inflate(R.layout.home_page_weather, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (weatherPage != 0) { // Если текущая страница создаётся как 'Прогноз погоды', то ->
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.weather_content_1, WeatherDayContent.newInstance("one"), null);

            // Если количество прогнозных дней больше одного, то составляем список
            if (weatherPage > 1) transaction.add(R.id.weather_content_2, WeatherDayContent.newInstance("two"), null);
            if (weatherPage > 2) transaction.add(R.id.weather_content_3, WeatherDayContent.newInstance("tree"), null);
            transaction.commit();
        }
        super.onViewCreated(view, savedInstanceState);
    }
}
