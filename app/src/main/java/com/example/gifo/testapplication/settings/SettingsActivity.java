package com.example.gifo.testapplication.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.gifo.testapplication.local.LocalContext;
import com.example.gifo.testapplication.R;

/**
 * Created by gifo on 26.04.2018.
 */

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Инициализируем объекты Preferences для сохранения и чтения настроек
    SharedPreferences appSettings;
    SharedPreferences.Editor appSettingsPut = null;

    Spinner spinner, spinnerDay; // Объявляем объект выпадающего списка Spinner

    @Override
    protected void attachBaseContext(Context lang) {
        String local = (lang.getSharedPreferences("main", MODE_PRIVATE).getInt("Lang", 0) != 0) ? "ru" : "en";
        super.attachBaseContext(LocalContext.wrap(lang, local));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.settings_activity_name);
        setContentView(R.layout.activity_settings);

        // Инициализируем объекты Preferences для сохранения и чтения настроек
        appSettings = this.getSharedPreferences("main", Context.MODE_PRIVATE);
        appSettingsPut = appSettings.edit();

        // Инициализируем спиннер выбора языка
        spinner = (Spinner) findViewById(R.id.lang_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.select_lang, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(appSettings.getInt("Lang", 0));
        spinner.setOnItemSelectedListener(this);

        // Инициализируем спиннер выбора прогнозных дней
        spinnerDay = (Spinner) findViewById(R.id.day_spinner);
        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this,
                R.array.select_day, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setSelection(appSettings.getInt("WeatherDays", 0));
        spinnerDay.setOnItemSelectedListener(this);
    }

    // Слушатель событий нажатий на Button
    public void onClickSave(View view) {
        switch (view.getId()) {
            case R.id.button_save_set:
                if (appSettingsPut != null) {
                    appSettingsPut.putInt("Lang", spinner.getSelectedItemPosition());
                    appSettingsPut.putInt("WeatherDays", spinnerDay.getSelectedItemPosition());
                }
                appSettingsPut.apply();
                break;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }
    public void onNothingSelected(AdapterView<?> parent) { }
}
