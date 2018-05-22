package com.example.gifo.testapplication.utils.preferences;

import java.util.ArrayList;

/**
 * Created by gifo on 18.05.2018.
 */

/* Класс позволяет оперировать единичной строкой как массивом или коллекцией
 * для удобного хранения и использования строк по подобию <string-array> в SharedPreferences */

public class StringArray {

    private static char splitter = '|'; // строковый разделитель по умолчанию

    // Задаёт персональный строковый разделитель
    public static void splitChar(char symbol) {
        splitter = symbol;
    }

    // Возвращает используемый строковый разделитель
    public static char getSplitChar() {
        return splitter;
    }

    // Возвращает строковую коллекцию из единичной строки
    public static ArrayList<String> getList(String string) {
        ArrayList<String> list = new ArrayList<>();
        String[] array = getArray(string);
        if (string.length() != 0) for (String st: array) list.add(st);
        return list;
    }

    // Возвращает строковый массив из единичной строки
    public static String[] getArray(String string) {
        return string.split("\\" + splitter);
    }

    // Возвращает единичную строку из переданной коллекции
    public static String getStringFromList(ArrayList<String> list) {
        String string = "";
        for (String st: list) string += st + splitter;
        return (!list.isEmpty()) ? string.substring(0, string.length() - 1) : string;
    }

    // Возвращает единичную строку из переданного массива
    public static String getStringFromArray(String[] array) {
        String string = "";
        for (String st: array) string += st + splitter;
        return (array.length != 0) ? string.substring(0, string.length() - 1) : string;
    }
}
