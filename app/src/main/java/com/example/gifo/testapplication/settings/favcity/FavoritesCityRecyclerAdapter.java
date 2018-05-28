package com.example.gifo.testapplication.settings.favcity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.utils.preferences.StringArray;

import java.util.ArrayList;

/**
 * Created by gifo on 18.05.2018.
 */

public class FavoritesCityRecyclerAdapter extends RecyclerView.Adapter<FavoritesCityRecyclerAdapter.ViewHolder> {

    SharedPreferences.Editor saveSettings;
    private ArrayList<String> citiesField; // метаданные, на основе которых создаётся и заполняется массив View

    // Объект ViewHolder, который содержит базовый экземпляр текущего View и его поля класса
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public Button mButtonViev;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.city_name);
            mButtonViev = itemView.findViewById(R.id.del_city);

            mButtonViev.setOnClickListener (new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeFavcityView(getAdapterPosition());
                }
            });
        }
    }

    // Констуктор адаптера, принимает массив данных, либо число необходимых View для RecyclerView
    public FavoritesCityRecyclerAdapter(ArrayList<String> set, Context ctx) {

        // Инициализируем объекты Preferences для сохранения и чтения настроек
        saveSettings = ctx.getSharedPreferences("main", Context.MODE_PRIVATE).edit();
        citiesField = set;
    }

    // Возвращает массив образцов View для каждого текущего создаваемого View
    @Override
    public FavoritesCityRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View template = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favcity_content, parent, false);
        return new FavoritesCityRecyclerAdapter.ViewHolder(template);
    }

    // Заполняем массив View (текущего индекса - position) данными
    @Override
    public void onBindViewHolder(final FavoritesCityRecyclerAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(citiesField.get(position));
    }

    @Override
    public int getItemCount() {
        return citiesField.size();
    }

    // Удаляет view-элемент из списка по передаваемой позиции
    public void removeFavcityView(int position) {
        citiesField.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, citiesField.size());
        savePreferences();
    }

    // Добавляет view-элемент с 'favcity_name' - контентом в конец списка
    public void addFavcityView(String favcity_name) {
        int last = getItemCount(); // текущая последняя позиция списка
        citiesField.add(favcity_name);
        notifyItemInserted(last);
        notifyItemRangeChanged(last, citiesField.size());
        savePreferences();
    }

    // Сохраняет изменения в поле 'CitiesField' настройках SharedPreferences
    private void savePreferences() {
        saveSettings.putString("CitiesField", StringArray.getStringFromList(citiesField));
        saveSettings.apply();
    }
}
