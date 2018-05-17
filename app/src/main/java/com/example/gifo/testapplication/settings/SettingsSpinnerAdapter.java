package com.example.gifo.testapplication.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gifo on 05.05.2018.
 * I was take as a basis from - https://stackoverflow.com/questions/24422236/
 */

public class SettingsSpinnerAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private String[] contentArray;
    private Integer[] imageArray;
    private int templateSource, templateSourceDropDown, textSourceID, imageSourceID;

    public SettingsSpinnerAdapter(Context context, int template_src, int template_field_src, int src_textID, int src_imageID,
                                  String[] textField, Integer[] imageField) {
        super(context, template_src, src_textID, textField);
        this.ctx = context;
        this.templateSourceDropDown = template_field_src;
        this.templateSource = template_src;
        this.imageSourceID = src_imageID;
        this.textSourceID = src_textID;
        this.contentArray = textField;
        this.imageArray = imageField;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomViewDropDown(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, templateSource, convertView, parent);
    }

    // Возвращает контент спиннера в активном состоянии - список выбора
    public View getCustomViewDropDown(int position, View convertView, ViewGroup parent) {

        View spinner = getCustomView(position, templateSourceDropDown, convertView, parent);

        // Если в конструктор адаптера был передан массив иконок для списка выбора, то показываем их
        if (imageArray != null) {
            ImageView imageView = spinner.findViewById(imageSourceID);
            imageView.setImageResource(imageArray[position]);
            imageView.setVisibility(View.VISIBLE);
        }

        return spinner;
    }

    public View getCustomView(int position, int sourceView, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View spinner = inflater.inflate(sourceView, parent, false);
        TextView textView = spinner.findViewById(textSourceID);
        textView.setText(contentArray[position]);
        return spinner;
    }
}