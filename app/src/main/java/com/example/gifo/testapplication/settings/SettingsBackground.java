package com.example.gifo.testapplication.settings;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.utils.gfx.EffectBlur;

/**
 * Created by gifo on 14.05.2018.
 */

public class SettingsBackground extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_background, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBlurView(view);
    }

    // Размывает бэкграунд текущего фрагмента
    public void onBlurView(View view) {
        ImageView img = view.findViewById(R.id.background);
        Bitmap background = ((BitmapDrawable) img.getDrawable()).getBitmap();
        EffectBlur.params(0.3f, 16.0f); // устанавливаются значения SCALE и RADIUS размытия
        Bitmap blurred = EffectBlur.blur(getContext(), background);
        img.setImageDrawable(new BitmapDrawable(getResources(), blurred));
    }
}
