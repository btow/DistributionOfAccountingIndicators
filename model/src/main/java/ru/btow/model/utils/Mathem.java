package ru.btow.model.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Mathem {

    public static float round(final float value, final int accuracy){
        if (accuracy < 0) throw new IllegalArgumentException("The accuracy should not be negative.");

        BigDecimal bigDecimal = new BigDecimal(Float.toString(value));
        bigDecimal = bigDecimal.setScale(accuracy, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }
}
