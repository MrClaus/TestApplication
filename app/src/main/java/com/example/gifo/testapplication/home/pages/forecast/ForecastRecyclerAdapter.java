package com.example.gifo.testapplication.home.pages.forecast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gifo.testapplication.R;

/**
 * Created by gifo on 16.05.2018.
 */

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ViewHolder> {

    private String[] accessKey; // метаданные, на основе которых создаётся и заполняется массив View

    // Объект ViewHolder, который содержит базовый экземпляр текущего View и его поля класса
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mTextView = itemView.findViewById(R.id.text_w);
        }
    }

    // Констуктор адаптера, принимает массив данных, либо число необходимых View для RecyclerView
    public ForecastRecyclerAdapter(String[] myDataset) {
        accessKey = myDataset;
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
        holder.mTextView.setText(accessKey[position]);

        // Задаём первому элементу списка аттрибут - marginTop = '10dp'
        if (position == 0) setMargin(holder.view, "top", "dp", 10);
    }

    // Возвращает количество необходимых для создания View
    @Override
    public int getItemCount() {
        return accessKey.length;
    }

    // Задаёт аттрибут Margin элементу текущего RecyclerView
    // Значения: orientation - 'left', 'right', 'top', 'bottom' / measure - 'px', 'dp'
    public void setMargin(View view, String orientation, String measure, int value) {
        if ((orientation != null) && (measure != null)) {
            RecyclerView.LayoutParams params = ((RecyclerView.LayoutParams) view.getLayoutParams());
            float dp = view.getResources().getDisplayMetrics().density;
            int val = (measure.equals("px")) ? value: (measure.equals("dp")) ? ((int) dp * value): 0;
            if (orientation.equals("left")) params.leftMargin = val;
            else if (orientation.equals("top")) params.topMargin = val;
            else if (orientation.equals("right")) params.rightMargin = val;
            else if (orientation.equals("bottom")) params.bottomMargin = val;
        }
    }
}
