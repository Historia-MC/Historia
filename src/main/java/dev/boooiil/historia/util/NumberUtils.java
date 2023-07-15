package dev.boooiil.historia.util;

public class NumberUtils {
    
    public static float random(int min, int max) {
        return (float) (Math.random() * (max - min + 1) + min);
    }

    public static float randomToTenth(int min, int max) {
        return (float) (Math.round(Math.random() * (max - min + 1) + min) * 10) / 10;
    }

    public static float randomToHundredth(int min, int max) {
        return (float) (Math.round(Math.random() * (max - min + 1) + min) * 100) / 100;
    }

    public static float round(float value, int places) {
        return (float) (Math.round(value * Math.pow(10, places)) / Math.pow(10, places));
    }

}
