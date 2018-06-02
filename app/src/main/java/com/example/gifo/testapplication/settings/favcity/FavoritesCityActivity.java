package com.example.gifo.testapplication.settings.favcity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.local.LocalContext;

/**
 * Created by gifo on 08.05.2018.
 */

public class FavoritesCityActivity
        extends AppCompatActivity
        implements FavoritesCityRecyclerView.onSomeEventListener {

    EditText favcityEditText; // текстовое поле ввода
    private FavoritesCityRecyclerAdapter cityRecyclerAdapter; // адптер FavoritesCityRecyclerAdapter

    // Реализуем метод интерфейса FavoritesCityRecyclerView.onSomeEventListener
    @Override
    public void recyclerCityAdapter(FavoritesCityRecyclerAdapter adapter) {
        cityRecyclerAdapter = adapter;
    }

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

        // Добавляем тулбар
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Добавляем и показываем в экшен-баре стрелку 'назад'
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Инициализируем текстовое поле ввода города
        favcityEditText = findViewById(R.id.edit_favcity_name);
    }

    public void onClickButton(View view) {
        switch (view.getId()) {

            // При клике на button(add_favcity) создаём в адаптере списка городов view(новый город)
            case R.id.add_favcity:
                String newCity = favcityEditText.getText().toString();
                if (newCity.trim().length() != 0) cityRecyclerAdapter.addFavcityView(newCity);
                favcityEditText.getText().clear();
                break;
        }
    }

    @Override
    // Обработка событий нажатия на экшен-бар
    public boolean onSupportNavigateUp() {
        onBackPressed(); // если нажать на стрелку 'назад -> домой'
        return true;
    }

    @Override
    // Переопределяем onBackPressed для добавления анимации перехода активити
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.transition_open_back, R.anim.transition_close_back);
    }
}
