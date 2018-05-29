package com.example.gifo.testapplication.utils.gfx;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

/**
 * Created by gifo on 14.05.2018.
 */

// Графический эффект размытия изображений (Bitmap), используя RenderScript
public class EffectBlur {

    // Параметры по умолчанию
    private static float scale = 1.0f;
    private static float radius = 1.0f;

    // Переопределение параметров по умолчанию
    public static void params(float imgScale, float imgRadius) {
        scale = (imgScale < 0) ? 0.0f : imgScale;
        radius = (imgRadius < 1.0f) ? 1.0f : (imgRadius > 25.0f) ? 25.0f : imgRadius;
    }

    // Возвращает размытое изображение в соответствие с заданными параметрами SCALE и RADIUS
    public static Bitmap blur(Context context, Bitmap image) {
        int width = Math.round(image.getWidth() * scale);
        int height = Math.round(image.getHeight() * scale);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(radius);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        inputBitmap.recycle();
        rs.destroy();

        return outputBitmap;
    }
}
