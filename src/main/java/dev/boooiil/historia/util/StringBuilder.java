package dev.boooiil.historia.util;

public class StringBuilder {

    public static String build(String... args) {

        String build = "";

        for (String string : args) {

            build += string;

        }


        return build;

    }
    
}
