package com.example.gifo.testapplication.home.pages;

import android.view.View;

/**
 * Created by gifo on 21.05.2018.
 */

// Класс-модель для view-страниц HomePages
public class PageView {

    protected View view; // основная View класса

    public PageView(View view) {
        this.view = view;
        onCreate(this.view);
    }

    // Метод для переопределения и редактирования текущего view PageView
    protected void onCreate(View view) {}

    // Возвращает view текущего класса
    public View getView() {
        return view;
    }
}
