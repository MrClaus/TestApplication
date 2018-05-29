package com.example.gifo.testapplication.home.pages.forecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.app.AppContext;
import com.example.gifo.testapplication.home.pages.forecast.radioview.HoursListView;
import com.example.gifo.testapplication.home.pages.forecast.radioview.HoursListAdapter;
import com.example.gifo.testapplication.service.models.CurrentWeather;
import com.example.gifo.testapplication.service.models.ForecastWeather;
import com.example.gifo.testapplication.service.models.Weather;

import java.util.ArrayList;

/**
 * Created by gifo on 16.05.2018.
 */

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ViewHolder> {


    // Родительский контекст MainActivity, который передаём при создании вложенных RecyclerView,
    // который реализует диалог с MainActivity через интерфейс
    private Context parent;
    private boolean isForecast = false;
    private ArrayList<ForecastWeather.DayliWeather> dataSet; // метаданные, на основе которых создаётся и заполняется массив View

    private SharedPreferences appSettings; // системные настройки приложения
    private int tempSet; // настройки единицы измерения температуры
    private int dayCountSet; // настройки отображения количества прогнозных дней

    // Создаём лист текущих holder-ов для доступа к ним извне и обновлению его данных, не задействуя notifyDataSetChanged()
    ArrayList<ViewHolder> listHolder = new ArrayList<>();


    // Объект ViewHolder, который содержит базовый экземпляр текущего View и его поля класса
    public  static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView field_day_date;
        public TextView field_tmperature;
        public TextView field_pressure;
        public TextView field_hmidity;
        public TextView field_wind;
        public TextView field_clouds;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            field_day_date = itemView.findViewById(R.id.day_date);
            field_tmperature = itemView.findViewById(R.id.value_temperature);
            field_pressure = itemView.findViewById(R.id.value_pressure);
            field_hmidity = itemView.findViewById(R.id.value_humidity);
            field_wind = itemView.findViewById(R.id.value_wind);
            field_clouds = itemView.findViewById(R.id.value_clouds);
        }
    }


    // Констуктор адаптера, принимает массив данных, либо число необходимых View для RecyclerView
    public ForecastRecyclerAdapter(Context parent, ArrayList<ForecastWeather.DayliWeather> myDataset) {
        this.parent = parent;
        this.dataSet = myDataset;

        // Загружаем необходимые настройки из SharedPreferences
        this.appSettings = this.parent.getSharedPreferences("main", Context.MODE_PRIVATE);
        this.tempSet = appSettings.getInt("TemperatureKey", 0);
        this.dayCountSet = appSettings.getInt("WeatherDays", 0);
        this.dayCountSet = (dayCountSet > 1) ? 5 : (dayCountSet > 0) ? 3 : 1;
    }


    // Возвращает массив образов View для каждого текущего создаваемого View
    @Override
    public ForecastRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View template = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_weather_content, parent, false);
        return new ViewHolder(template);
    }


    // Заполняем массив View (текущего индекса - position) данными
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isForecast) {
            // Заполняем лист holder-ов для дальнейшего доступа к нему извне
            if (listHolder.get(position) == null) listHolder.set(position, holder);

            // Задаём первому элементу списка item аттрибут - marginTop = '10dp'
            if (position == 0) setMargin(holder.view, "top", "dp", 10);

            setContent(holder, 0, position); // Заполняем первичный контент

            // Создаём вложенный в текущий holder RecyclerView
            new HoursListView(parent, holder.view, position, dataSet.get(position).getHourlyList());
        }
    }


    // Возвращает количество необходимых для создания View
    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    // Задаёт аттрибут Margin элементу текущего RecyclerView
    // Значения: orientation - 'left', 'right', 'top', 'bottom' / measure - 'px', 'dp'
    public void setMargin(View view, String orientation, String measure, int value) {
        RecyclerView.LayoutParams params = ((RecyclerView.LayoutParams) view.getLayoutParams());
        float dp = view.getResources().getDisplayMetrics().density;
        int val = (measure.equals("px")) ? value: (measure.equals("dp")) ? ((int) dp * value): 0;
        if (orientation.equals("left")) params.leftMargin = val;
        else if (orientation.equals("top")) params.topMargin = val;
        else if (orientation.equals("right")) params.rightMargin = val;
        else if (orientation.equals("bottom")) params.bottomMargin = val;
    }


    // Обновление данных holder-а (parentPosition) извне - текущего RecyclerView (вызывается из MainActivity)
    // Вызывается по клику вложенного RecyclerView (выбор отображаемого контента из списка)
    public void refreshFromHourslistSelected(int position, int parentPosition) {
        ViewHolder holder = listHolder.get(parentPosition);
        setContent(holder, position, parentPosition);
    }


    // Обновление данных holder-а (parentPosition) извне - текущего RecyclerView (вызывается из MainActivity)
    // Вызывается по состоянию обновления данных в БД
    public void refreshFromLiveData(ForecastWeather forecastWeather) {
        dataSet.clear();
        listHolder.clear();
        isForecast = true;
        if (dayCountSet == 5) dataSet = forecastWeather.getDayliList(); // погода на 40 часов может включать до 6 дней
        else for (int i = 0; i < dayCountSet; i++) dataSet.add(forecastWeather.getDayliList().get(i));
        for (int i = 0; i < dataSet.size(); i++) listHolder.add(null);
        notifyDataSetChanged();
    }


    // Обновления контента holder-а в позиции parentPosition на контент прогноза по времени position
    public void setContent(ViewHolder holder, int position, int parentPosition) {
        Weather weather = dataSet.get(parentPosition).getHourlyList().get(position).getWeather();
        holder.field_day_date.setText(dataSet.get(parentPosition).getDayDate());
        String temperature =
                (int) ((tempSet == 0) ? weather.getCelsiusTemp() : weather.getFahrenheitTemp())
                        + " \u00B0" + ((tempSet == 0) ? "C" : "F");
        holder.field_tmperature.setText(temperature);
        holder.field_pressure.setText(weather.getPressure() + " hPa");
        holder.field_hmidity.setText(weather.getHumidity() + " %");
        holder.field_wind.setText(weather.getWind() + " m/s");
        holder.field_clouds.setText(weather.getClouds() + " %");
    }
}
