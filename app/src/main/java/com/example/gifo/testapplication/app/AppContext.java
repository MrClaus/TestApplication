package com.example.gifo.testapplication.app;

import android.app.Application;
import android.arch.persistence.room.Room;

/**
 * Created by gifo on 27.05.2018.
 */

// Контекст текущего приложения
public class AppContext extends Application {

    private static AppContext context; // контекст приложения
    private AppDatabase database; // база данных

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database").build();
    }

    // Возвращает объект - базу данных текущего приложения
    public AppDatabase getDatabase() {
        return database;
    }

    // Возвращает контекст текущего приложения
    public static AppContext getContext() {
        return context;
    }
}
