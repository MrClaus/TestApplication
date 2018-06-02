package com.example.gifo.testapplication.home.pages.forecast.radioview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.service.models.ForecastWeather;

import java.util.ArrayList;
//import com.example.gifo.testapplication.utils.parser.weatherpars.models.WeatherHoursList;

/**
 * Created by gifo on 23.05.2018.
 */

/* Создаёт горизонтальный список RecyclerView, вложенный в holder родительского RecyclerView
   В адаптере реализован интерфейс для диалого дочернего и родительского RecyclerView через MainActivity  */

public class HoursListView {

    private View view;
    private HoursListAdapter adapter;
    private int parentPosition; // позиция holdera родительского RecyclerView - для последующего обновления его данных
    private Context parent; // родительский контекст активити для реализации интерфейса
    private ArrayList<ForecastWeather.HourlyWeather> dataSet;

    public HoursListView (Context parent, View view, int parentPosition, ArrayList<ForecastWeather.HourlyWeather> data) {
        this.parent = parent;
        this.view = view;
        this.parentPosition = parentPosition;
        this.dataSet = data;
        onCreate(this.view);
    }

    private void onCreate(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_hours_list);

        // Ставим на горизонтальную прокрутку
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        // Даём шанс скроллиться вложенному текущему RecyclerView в родительский RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
        });

        adapter = new HoursListAdapter(parent, dataSet, parentPosition);
        recyclerView.setAdapter(adapter);
    }

    // Возвращает созданный RecyclerView
    public View getView() {
        return view;
    }

    // Возвращает созданный адаптер для RecyclerView
    public HoursListAdapter getAdapter() {
        return adapter;
    }
}
