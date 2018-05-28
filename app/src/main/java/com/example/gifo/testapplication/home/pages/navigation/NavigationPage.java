package com.example.gifo.testapplication.home.pages.navigation;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.home.pages.PageView;

/**
 * Created by gifo on 21.05.2018.
 */

public class NavigationPage extends PageView {

    View frame_view;
    ImageView refresh;
    TextView refresh_info;
    TextView weather_temp;

    // listener для общения с MainActivity через интерфейс NavigationPageWeatherInterface
    NavigationPageWeatherInterface someEventListener;

    // Интерфейс для связи с MainActivity
    public interface NavigationPageWeatherInterface {

        // Метод передаёт MainActivity инициализированную view (текущий прогноз погоды) из фрагмента страницы ViewPager
        public void onInitializeNavigationPageWeather(NavigationPage currentWeatherPage);
    }

    public NavigationPage(View view, Context ctx) {
        super(view, ctx);
    }

    @Override
    protected void onAttach(Context parent) {
        try { someEventListener = (NavigationPageWeatherInterface) parent;
        } catch (ClassCastException e) {
            throw new ClassCastException(parent.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    protected void onCreate(View view, Context parent) {
        refresh = view.findViewById(R.id.refresh_img);
        frame_view = view.findViewById(R.id.current_weather_frame);
        weather_temp = view.findViewById(R.id.current_weather_temp);
        refresh_info = view.findViewById(R.id.refresh_date);
        frame_view.setVisibility(View.INVISIBLE);

        // Передаём текущий объект в родительское MainActivity для дальнейших операций
        someEventListener.onInitializeNavigationPageWeather(this);
    }

    public void refreshContent(String date, String temperature) {
        refresh_info.setText(date);
        weather_temp.setText(temperature);
        refresh.setVisibility(View.INVISIBLE);
        frame_view.setVisibility(View.VISIBLE);
    }
}
