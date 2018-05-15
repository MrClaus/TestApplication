package com.example.gifo.testapplication.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.gifo.testapplication.R;

/**
 * Created by gifo on 03.05.2018.
 */

public class WeatherDayContent extends Fragment {

    private String sys_info;

    public static WeatherDayContent newInstance(String sys_info) {
        WeatherDayContent fragment = new WeatherDayContent();
        fragment.sys_info = sys_info;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_content, container, false); // учитываем родительские аттрибуты фрагмента
        addContent(view);
        return view;
    }

    private void addContent(View container) {
        TextView txt = (TextView) container.findViewById(R.id.text_w);

        if (sys_info.equals("one")) txt.setText("ONE DAY!");
        else if (sys_info.equals("two")) txt.setText("TWO DAY!");
        else if (sys_info.equals("tree")) txt.setText("TREE DAY!");
    }
}
