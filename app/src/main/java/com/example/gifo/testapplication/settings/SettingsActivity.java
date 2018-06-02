package com.example.gifo.testapplication.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.CheckBox;


import com.example.gifo.testapplication.local.LocalContext;
import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.settings.favcity.FavoritesCityActivity;

/**
 * Created by gifo on 26.04.2018.
 */

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    // Инициализируем объекты Preferences для сохранения и чтения настроек
    SharedPreferences appSettings;
    SharedPreferences.Editor appSettingsPut = null;

    AppCompatSpinner spinnerLang, spinnerDay, spinnerTemp; // Объявляем объект выпадающего списка Spinner
    CheckBox checkRefreshData; // чекбокс настройки автообновления данных приложения при старте


    @Override
    protected void attachBaseContext(Context lang) {
        String local = null;
        switch (lang.getSharedPreferences("main", MODE_PRIVATE).getInt("Lang", 0)) {
            case 1: local = "en"; break;
            case 2: local = "ru"; break; }
        if (local != null) super.attachBaseContext(LocalContext.wrap(lang, local));
        else super.attachBaseContext(lang);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Добавляем в приложение Меню из XML-вёрстки(3 вертикальные точки с раскрывающимся списком)
        getMenuInflater().inflate(R.menu.save_set, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обрабатываем выбор секций из списка меню
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if (appSettingsPut != null) {
                appSettingsPut.putInt("Lang", spinnerLang.getSelectedItemPosition());
                appSettingsPut.putInt("WeatherDays", spinnerDay.getSelectedItemPosition());
                appSettingsPut.putInt("TemperatureKey", spinnerTemp.getSelectedItemPosition());
                appSettingsPut.putBoolean("FirstRefreshWeather", checkRefreshData.isChecked());
                appSettingsPut.apply();
            }
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    // Обработка событий нажатия на экшен-бар (стрелка назад)
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    // Переопределяем onBackPressed для добавления анимации перехода активити
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.station, R.anim.transparent_close);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.settings_activity_name);
        setContentView(R.layout.activity_settings);

        // Добавляем тулбар
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Добавляем и показываем в экшен-баре стрелку 'назад'
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Инициализируем объекты Preferences для сохранения и чтения настроек
        appSettings = this.getSharedPreferences("main", Context.MODE_PRIVATE);
        appSettingsPut = appSettings.edit();

        // Инициализируем спиннер выбора языка
        String[] field_lang = getResources().getStringArray(R.array.select_lang);
        Integer[] icons_lang = { R.drawable.lang_auto, R.drawable.lang_us, R.drawable.lang_ru};
        spinnerLang = getSpinnerView(R.id.lang_spinner, field_lang, icons_lang);
        spinnerLang.setSelection(appSettings.getInt("Lang", 0));

        // Инициализируем спиннер выбора прогнозных дней
        String[] field_day = getResources().getStringArray(R.array.select_day);
        spinnerDay = getSpinnerView(R.id.days_spinner, field_day, null);
        spinnerDay.setSelection(appSettings.getInt("WeatherDays", 0));

        // Инициализируем спиннер выбора прогнозных дней
        String[] field_temp = getResources().getStringArray(R.array.select_temp);
        spinnerTemp = getSpinnerView(R.id.temp_spinner, field_temp, null);
        spinnerTemp.setSelection(appSettings.getInt("TemperatureKey", 0));

        // Инициализируем чекбокс выбора/ отмены выбора подгрузки данных при старте приложения
        checkRefreshData = findViewById(R.id.check_refresh_settings);
        boolean isFirstRefresh = appSettings.getBoolean("FirstRefreshWeather", false);
        checkRefreshData.setChecked(isFirstRefresh);

        // Добавляем ripple- эффект для следующих buttons
        addRippleButton(R.id.ripple_lang_settings);
        addRippleButton(R.id.ripple_favorites_city_settings);
        addRippleButton(R.id.ripple_days_settings);
        addRippleButton(R.id.ripple_temp_settings);
        addRippleButton(R.id.ripple_refresh_settings);
    }


    // Добавляем свойство Ripple принимаемому button-у
    private void addRippleButton(int src) {
        View button = findViewById(src);
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = this.obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        button.setBackgroundResource(backgroundResource);
    }


    // Слушатель нажатия button текущего layout-а
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.ripple_lang_settings:
                spinnerLang.performClick();
                break;
            case R.id.ripple_favorites_city_settings:
                Intent intent = new Intent(SettingsActivity.this, FavoritesCityActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.transition_open_goto, R.anim.transition_close_goto);
                break;
            case R.id.ripple_days_settings:
                spinnerDay.performClick();
                break;
            case R.id.ripple_temp_settings:
                spinnerTemp.performClick();
                break;
            case R.id.ripple_refresh_settings:
                checkRefreshData.performClick();
                break;
        }
    }


    // Возвращает объект спиннер по образцу 'fragment_spinner_content'
    private AppCompatSpinner getSpinnerView(int src, String[] field, Integer[] icons) {
        AppCompatSpinner spinner = findViewById(src);
        SettingsSpinnerAdapter adapter = new SettingsSpinnerAdapter(this, R.layout.fragment_spinner_content,
                R.layout.fragment_spinner_dropdown, R.id.text, R.id.icon, field, icons);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return spinner;
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }
    public void onNothingSelected(AdapterView<?> parent) { }
}
