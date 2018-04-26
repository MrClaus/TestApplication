package com.example.gifo.testapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by gifo on 26.04.2018.
 */

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Инициализируем объекты Preferences для сохранения и чтения настроек
    SharedPreferences appSettings;
    SharedPreferences.Editor appSettingsPut = null;

    Spinner spinner; // Объявляем объект выпадающего списка Spinner

    @Override
    protected void attachBaseContext(Context lang) {
        String local = (lang.getSharedPreferences("main", MODE_PRIVATE).getInt("Lang", 0) != 0) ? "ru" : "en";
        System.out.println("--------------------------------------" + lang.getSharedPreferences("main", MODE_PRIVATE).getInt("Lang", 0));
        super.attachBaseContext(LocalContext.wrap(lang, local));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        System.out.println("Create Set-------------------!!!!!!!!!!!!!!");

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
    }

    // Слушатель событий нажатий на Button
    public void onClickSave(View view) {
        switch (view.getId()) {
            case R.id.button_save_set:
                if (appSettingsPut != null) appSettingsPut.putInt("Lang", spinner.getSelectedItemPosition());
                appSettingsPut.apply();
                System.out.println("--------------GET--------------GET-------------" + spinner.getSelectedItemPosition());
                System.out.println("-----------------------NEW---------------" + appSettings.getInt("Lang", 0));
                break;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }
    public void onNothingSelected(AdapterView<?> parent) { }
}
