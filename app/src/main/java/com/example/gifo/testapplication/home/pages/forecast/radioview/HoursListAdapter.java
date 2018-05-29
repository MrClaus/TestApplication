package com.example.gifo.testapplication.home.pages.forecast.radioview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.app.AppContext;
import com.example.gifo.testapplication.service.models.ForecastWeather;

import java.util.ArrayList;

/**
 * Created by gifo on 23.05.2018.
 */

public class HoursListAdapter extends RecyclerView.Adapter<HoursListAdapter.ViewHolder> {


    private int parentPosition;
    private int mSelectedItem = 0; // первоначальная выбранная позиция RadioButton из списка view
    private ArrayList<ForecastWeather.HourlyWeather> dataSet;

    String[] day_time_info; // загружаем из ресурсов xml string-array массив с названиями времени дня (ночь, день...)

    // listener для общения с MainActivity через интерфейс ForecastRecyclerHourslistInterface
    ForecastRecyclerHourslistInterface someEventListener;


    // Осуществляет диалог с родительским RecyclerView через родительское MainActivity
    public interface ForecastRecyclerHourslistInterface {

        // Вызывает метод обновления данных родительского RecyclerView
        public void onSelectForecastHourslist(int hourslistRecyclerPosition, int forecastRecyclerPosition);
    }


    public HoursListAdapter(Context ctx, ArrayList<ForecastWeather.HourlyWeather> items, int parentPosition) {
        this.dataSet = items;
        this.parentPosition = parentPosition;
        this.day_time_info = ctx.getResources().getStringArray(R.array.day_time_forecast);
        onRealizeInterface(ctx);
    }


    @Override
    public void onBindViewHolder(HoursListAdapter.ViewHolder viewHolder, final int i) {

        // Задаётся первоначальный выбор для RadioButton i-го элемента списка
        viewHolder.mRadio.setChecked(i == mSelectedItem);

        // Скрываем левый бордер у последнего элемента
        viewHolder.border.setVisibility(View.VISIBLE);
        if (i == (getItemCount() - 1)) viewHolder.border.setVisibility(View.INVISIBLE);

        // Меняем текстовый контент
        String hour = dataSet.get(i).getHour() + ":00";
        int pos = Integer.parseInt(dataSet.get(i).getHour()) / 3;
        String dayTime = day_time_info[pos];
        viewHolder.dayTimeText.setText(dayTime);
        viewHolder.hoursText.setText(hour);
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.fragment_weather_hourslist, parent, false);
        return new ViewHolder(view);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public View border;
        public TextView hoursText;
        public TextView dayTimeText;
        public RadioButton mRadio;

        public ViewHolder(final View inflate) {
            super(inflate);
            view = inflate;
            border = inflate.findViewById(R.id.border);
            hoursText = inflate.findViewById(R.id.hours);
            dayTimeText = inflate.findViewById(R.id.day_time);
            mRadio = inflate.findViewById(R.id.radio);

            // Выполняем действия при клике на RadioButton
            mRadio.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();

                    // Вызываем метод в родительском активити, который вызывает метод обновления данных в родительском RecyclerView
                    someEventListener.onSelectForecastHourslist(mSelectedItem, parentPosition);
                }
            });
        }
    }


    // Привязка listner-а к контексту родительского активити, который реализует интерфейс ForecastRecyclerHourslistInterface
    private void onRealizeInterface(Context parent) {
        try { someEventListener = (ForecastRecyclerHourslistInterface) parent;
        } catch (ClassCastException e) {
            throw new ClassCastException(parent.toString() + " must implement onSomeEventListener");
        }
    }
}
