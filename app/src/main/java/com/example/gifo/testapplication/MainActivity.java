package com.example.gifo.testapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.example.gifo.testapplication.home.HomePagesAdapter;
import com.example.gifo.testapplication.local.LocalContext;
import com.example.gifo.testapplication.settings.SettingsActivity;
import r21nomi.com.glrippleview.AnimationUtil;
import r21nomi.com.glrippleview.GLRippleView;
import java.util.Date;
import kotlin.Pair;

/**
 * Created by gifo.
 */

public class MainActivity extends AppCompatActivity {

    // Инициализируемые параметры экрана устройства в initDisplayParams()
    private float WIDTH;
    private float HEIGHT;

    // Инициализируем объект Preferences для чтения настроек
    SharedPreferences appSettings;

    // Текущее время объекта Date - методы getLastTime, setLastTime, getCurrentTime
    private long currentTimeMs;

    // Переменные объекта ViewPager
    ViewPager pager;
    PagerAdapter pagerAdapter;


    // Переменные объекта GLRippleView
    volatile GLRippleView glRippleView = null;
    Thread glRipplePulse = null; // поток обработчик затухания волны
    float intervalWave = 3000.0f; // время затухания колебаний волны

    // Методы для измерения таймаута между операциями
    public long getCurrentTime() { return new Date().getTime(); } // получить текущее время
    public void setLastTime() { currentTimeMs = new Date().getTime(); } // задать точку отсчета
    public long getLastTime() { return currentTimeMs; } // получить некогда заданную точку отсчета

    public Float getWidthDisplay() { return WIDTH; } // Получить значение ширины экрана устройства
    public Float getHeightDisplay() { return HEIGHT; } // Получить значение высоты экрана устройства

    // Инициализация параметров ширины и высоты экрана устройства
    private void initDisplayParams(Context ctx) {
        WindowManager srv = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Point screen = new Point();
        srv.getDefaultDisplay().getSize(screen);
        WIDTH = (float) screen.x;
        HEIGHT = (float) screen.y;
    }


    @Override
    protected void attachBaseContext(Context lang) {
        String local = (lang.getSharedPreferences("main", MODE_PRIVATE).getInt("Lang", 0) != 0) ? "ru" : "en";
        super.attachBaseContext(LocalContext.wrap(lang, local));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Добавляем в приложение Меню из XML-вёрстки(3 вертикальные точки с раскрывающимся списком)
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обрабатываем выбор секций из списка меню
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_exit) finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    // Колбэк события произвольного сенсорного касания на экране устройства
    public boolean dispatchTouchEvent(MotionEvent event) {

        // Проверка для объекта активити glRippleView
        if ((glRippleView != null)&&(event.getAction() == MotionEvent.ACTION_MOVE)) {
            setLastTime(); // возвращаем время последнего касания для пересчета силы колебаний

            // Запускаем параллельный обработчик затухания колебаний волны объекта glRippleView
            if (glRipplePulse != null) glRipplePulse.interrupt();
            glRipplePulse = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(20); // (чтобы не насиловать процессор)
                            long delta = getCurrentTime() - getLastTime();
                            if (delta < intervalWave)
                                glRippleView.setRippleOffset(0.009f * (intervalWave - delta) / intervalWave);
                            else break;
                        }
                    } catch (Exception e) {}
                }
            });
            glRipplePulse.start();

            // Меняем позицию источника колебаний волны
            Pair<Float, Float> pair = new Pair<Float, Float>(
                    AnimationUtil.INSTANCE.map(event.getX(), 0f, getWidthDisplay(), -1f, 1f),
                    AnimationUtil.INSTANCE.map(event.getY(), 0f, getHeightDisplay(), -1f, 1f));
            glRippleView.setRipplePoint(pair);
        }

        return super.dispatchTouchEvent(event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        initDisplayParams(this);

        // appSettings - объект Preferences для чтения настроек
        appSettings = this.getSharedPreferences("main", Context.MODE_PRIVATE);

        // Инициализируем glRippleView
        glRippleView = findViewById(R.id.glRippleView);

        /** Отображаем домашние страницы HomePages (PageView - скользящие вкладки)
         *  Первая страница - навигационная (краткая информация о сегодняшней погоде и пользовательские button's)
         *  Вторая страница - прогноз погоды на N- количество дней (значение N меняется в настройках)
         */
        int homePagesCount = 2; // количество страниц элемента HomePages
        int weatherPages = appSettings.getInt("WeatherDays", 0) + 1; // читаем из настроек количество дней для прогнозного списка
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new HomePagesAdapter(homePagesCount, getSupportFragmentManager(), weatherPages);
        pager.setAdapter(pagerAdapter);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        recreate(); // пересоздать активити после возврата в случае возможного изменения параметров локализации
    }


    @Override
    protected void onStart() {
        super.onStart();
        glRippleView.onResume();
    }


    @Override
    protected void onStop() {
        glRippleView.onPause();
        super.onStop();
    }
}
