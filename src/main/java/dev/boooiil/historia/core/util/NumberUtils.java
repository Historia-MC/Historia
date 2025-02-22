package dev.boooiil.historia.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    /**
     * Generate a random number between the given values.
     * 
     * @param min - Minimum number.
     * @param max - Maximum number.
     * @return number between the minimum and maximum.
     */
    public static float random(int min, int max) {
        return (float) (Math.random() * (max - min + 1) + min);
    }

    /**
     * Generate a random number between the given values.
     * 
     * @param min - Minimum number.
     * @param max - Maximum number.
     * @return number between the minimum and maximum.
     */
    public static float random(float min, float max) {
        return (float) (Math.random() * (max - min + 1) + min);
    }

    /**
     * Generate a random number between the given values.
     * 
     * @param min - Minimum number.
     * @param max - Maximum number.
     * @return number between the minimum and maximum.
     */
    public static double random(double min, double max) {
        return (Math.random() * (max - min + 1) + min);
    }

    /**
     * Generate a random number between the given values to a tenth.
     * 
     * @param min - Minimum number.
     * @param max - Maximum number.
     * @return number between the minimum and maximum.
     */
    public static float randomToTenth(int min, int max) {
        return (float) (Math.round(Math.random() * (max - min + 1) + min) * 10) / 10;
    }

    /**
     * Generate a random number between the given values to a hundredth.
     * 
     * @param min - Minimum number.
     * @param max - Maximum number.
     * @return number between the minimum and maximum.
     */
    public static float randomToHundredth(int min, int max) {
        return (float) (Math.round(Math.random() * (max - min + 1) + min) * 100) / 100;
    }

    /**
     * Generate a random number between the given values.
     * 
     * @param min - Minimum number.
     * @param max - Maximum number.
     * @return number between the minimum and maximum.
     */
    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * Round a number to the given places.
     * 
     * <pre>
     * float f = 9.00001;
     * roundFloat(f, 2); // --> 9.00
     * </pre>
     * 
     * @param value  - The value.
     * @param places - The number of places.
     * @return a number that is trimmed to a number of places.
     */
    public static float roundFloat(float value, int places) {
        return (float) (Math.round(value * Math.pow(10, places)) / Math.pow(10, places));
    }

    /**
     * Round a number to the given places.
     * 
     * <pre>
     * double d = 9.00001;
     * roundDouble(d, 2); // --> 9.00
     * </pre>
     * 
     * @param value  - The value.
     * @param places - The number of places.
     * @return a number that is trimmed to a number of places.
     */
    public static double roundDouble(double value, int places) {
        return Double.parseDouble(
                new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
    }

}
