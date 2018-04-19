package com.example.gifo.testapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (id == R.id.action_exit) finish();
        return super.onOptionsItemSelected(item);
    }

    // Слушатель событий нажатий на Button
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.but_plus:
                setCounterView(1);
                break;
            case R.id.but_minus:
                setCounterView(-1);
                break;
            case R.id.but_order:
                setPriceView();
                break;
        }
    }

    // Метод уменьшения/увеличения значения тега counter при нажатии клавиш тега but_plus/but_minus
    private void setCounterView(int sign) {
        int countGet = getCountGet();
        TextView counterTextView = (TextView) findViewById(R.id.counter);
        countGet += (sign > 0) ? 1 : (countGet != 0) ? -1 : 0;
        counterTextView.setText("" + countGet);
    }

    // Изменение значения тега price
    private void setPriceView() {
        int price = 5;
        TextView priceTextView = (TextView) findViewById(R.id.price);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(getCountGet() * price));
    }

    // Чтение числового значения тега counter
    private int getCountGet() {
        TextView counterTextView = (TextView) findViewById(R.id.counter);
        String value = "" + counterTextView.getText();
        try { return Integer.parseInt(value);
        } catch (Exception e) { return  0; }
    }
}
