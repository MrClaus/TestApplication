package com.example.gifo.testapplication.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.local.LocalContext;

/**
 * Created by gifo on 08.05.2018.
 */

public class FavoritesCityActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.favorites_city_activity_name);
        setContentView(R.layout.activity_favorites_city);

        // Добавляем и показываем в экшен-баре стрелку 'назад'
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    // Обработка событий нажатия на экшен-бар
    public boolean onSupportNavigateUp() {
        onBackPressed(); // если нажать на стрелку 'назад -> домой'
        overridePendingTransition(R.anim.transition_open_back, R.anim.transition_close_back);
        return true;
    }
}
