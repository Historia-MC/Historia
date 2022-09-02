package dev.boooiil.historia.util;

public class StringUtils {

    public static String build(String... args) {

        String build = "";

        for (String string : args) {

            build += string;

        }


        return build;

    }

    public static String getFullTimeFromMilliseconds(long milliseconds) {

        //TODO: Finish
        // MM-DD-YYYY HH:MM:SS-PM/AM TZ
        // Note: 00-00-0000 and 00:00:00

        String string = "";

        return string;

    }

    public static String getDateFromMilliseconds(long milliseconds) {

        //TODO: Finish
        // MM-DD-YYYY
        // Note: 00-00-0000

        String string = "";

        return string;
    }

    public static String getTimeFromMilliseconds(long milliseconds) {

        //TODO: Finish
        // HH:MM:SS-PM/AM TZ
        // Note: 00:00:00

        String string = "";

        return string;

    }

    public static String convertMillisecondsIntoStringTime(long milliseconds) {

        String string = "";

        return string;
    }
    
}
