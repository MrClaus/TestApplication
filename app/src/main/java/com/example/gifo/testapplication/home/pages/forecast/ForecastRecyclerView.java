package com.example.gifo.testapplication.home.pages.forecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gifo.testapplication.R;

/**
 * Created by gifo on 16.05.2018.
 */

public class ForecastRecyclerView extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page_forecast, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView mRecyclerView = view.findViewById(R.id.forecast_frame_recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        SharedPreferences appSettings = getContext().getSharedPreferences("main", Context.MODE_PRIVATE);
        int daysCount = appSettings.getInt("WeatherDays", 0);
        daysCount = (daysCount == 0) ? 1 : (daysCount == 1) ? 3 : (daysCount == 2) ? 5 : 1;
        String[] accessKey = new String[daysCount];
        for (int i = 0; i < accessKey.length; i++) accessKey[i] = (i + 1) + " DAY";

        ForecastRecyclerAdapter mAdapter = new ForecastRecyclerAdapter(accessKey);
        mRecyclerView.setAdapter(mAdapter);
    }
}
