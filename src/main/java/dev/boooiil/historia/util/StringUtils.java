package dev.boooiil.historia.util;

/**
 * It takes a variable amount of strings and returns them as one string
 */
public class StringUtils {

    /**
     * It takes a variable number of strings and returns a single string
     * 
     * @return The method is returning a string.
     */
    public static String build(String... args) {

        String build = "";

        for (String string : args) {

            build += string;

        }

        return build;

    }
}
