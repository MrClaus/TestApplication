package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClass {
    public static void main(String[] args) throws InterruptedException {

        /*String st = "aa,bb,cc,dd,ee";
        String[] arr = st.split("\\,");
        for (String i:arr) {
            System.out.println(i);
        }
        System.out.println(arr.length);*/

        //Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm / yyyy-MM-dd");

        System.out.println("              " + formatForDateNow.format(new Date())+"");
    }
}
