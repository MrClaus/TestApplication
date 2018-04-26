package com.example.gifo.testapplication;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import r21nomi.com.glrippleview.AnimationUtil;
import r21nomi.com.glrippleview.GLRippleView;
import java.util.Date;
import kotlin.Pair;


public class MainActivity extends AppCompatActivity {

    // Инициализируем объект Preferences для чтения настроек
    SharedPreferences appSettings;

    // Инициализируемые параметры экрана устройства в initDisplayParams()
    private float WIDTH;
    private float HEIGHT;

    // Текущее время объекта Date - методы getLastTime, setLastTime, getCurrentTime
    private long currentTimeMs;

    // Переменные объекта ViewPager
    ViewPager pager;
    PagerAdapter pagerAdapter;
    static final int PAGE_COUNT = 2; // количество вкладок ViewPager

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
        appSettings = getSharedPreferences("main", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        initDisplayParams(this);

        // Инициализируем glRippleView
        glRippleView = findViewById(R.id.glRippleView);

        // Отображаем элемент PageView - скользящие вкладки
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
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


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }
}
