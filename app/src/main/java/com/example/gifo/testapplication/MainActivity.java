package com.example.gifo.testapplication;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        ConstraintLayout background = (ConstraintLayout) findViewById(R.id.screen);
        switch (view.getId()) {
            case R.id.button_RED:
                background.setBackgroundColor(getResources().getColor(R.color.colorRed));
                break;
            case R.id.button_GREEN:
                background.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                break;
            case R.id.button_BLUE:
                background.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                break;
        }
    }
}
