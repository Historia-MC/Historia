package dev.boooiil.historia.util;

public class StringUtils {

    public static String build(String... args) {

        String build = "";

        for (String string : args) {

            build += string;

        }


        return build;

    }
}
