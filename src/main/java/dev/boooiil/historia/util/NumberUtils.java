package dev.boooiil.historia.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {
    
    public static float random(int min, int max) {
        return (float) (Math.random() * (max - min + 1) + min);
    }

    public static float random(float min, float max) {
        return (float) (Math.random() * (max - min + 1) + min);
    }

    public static double random(double min, double max) {
        return (Math.random() * (max - min + 1) + min);
    }

    public static float randomToTenth(int min, int max) {
        return (float) (Math.round(Math.random() * (max - min + 1) + min) * 10) / 10;
    }

    public static float randomToHundredth(int min, int max) {
        return (float) (Math.round(Math.random() * (max - min + 1) + min) * 100) / 100;
    }

    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static float roundFloat(float value, int places) {
        return (float) (Math.round(value * Math.pow(10, places)) / Math.pow(10, places));
    }

    public static double roundDouble(double value, int places) {
        return Double.parseDouble(new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
    }

}
