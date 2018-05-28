package com.example.gifo.testapplication.home.pages;

import android.content.Context;
import android.view.View;

/**
 * Created by gifo on 21.05.2018.
 */

/* Класс-модель для view-страниц HomePages
   Создаём собственный модельный класс View, так как наследник Fragment имеет
   собственный асинхронный жизненный цикл из коллбэков, и быстрый вызов getView()
   возвращает null */

public class PageView {

    protected View view; // основная View класса

    public PageView(View view) {
        this.view = view;
        initialize();
        onAttach();
        onCreate(this.view);
    }

    public PageView(View view, Context ctx) {
        this.view = view;
        initialize();
        onAttach(ctx);
        onCreate(this.view, ctx);
    }

    // Метод для переопределения - preLoad класса
    protected void initialize() {}

    // Метод для переопределения и инициализации контекста view PageView
    protected void onAttach() {}
    protected void onAttach(Context ctx) {}

    // Метод для переопределения и создания текущего view PageView
    protected void onCreate(View view) {}
    protected void onCreate(View view, Context ctx) {}

    // Возвращает view текущего класса
    public View getView() {
        return view;
    }
}
