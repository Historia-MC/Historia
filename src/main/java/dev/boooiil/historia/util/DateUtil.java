package dev.boooiil.historia.util;

import java.util.Date;

public class DateUtil {

    private static String months[] = { "January", "Febuary", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December" };

    public static String getFullTimeFromMilliseconds(long milliseconds) {

        Date date = new Date(milliseconds);

        String string = "";

        int month = date.getMonth() + 1;
        int day = date.getDate();
        int year = date.getYear() + 1900;
        int hour = date.getHours() + 1;
        int minute = date.getMinutes();
        int second = date.getSeconds();

        string += month < 10 ? "0" + month + "-" : month + "-";
        string += day < 10 ? "0" + day + "-" : day + "-";
        string += year + " ";
        string += hour == 12 || (hour > 0 && hour < 12) ? hour + ":"
                : hour > 12 ? hour - 12 > 9 ? hour - 12 + ":" : "0" + (hour - 12) + ":" : "0" + hour + ":";
        string += minute < 10 ? "0" + minute + ":" : minute + ":";
        string += second < 10 ? "0" + second + "-" : second + "-";
        string += hour <= 12 ? "AM " : "PM ";
        string += "EST";

        return string;

    }

    public static String getDateFromMilliseconds(long milliseconds) {

        Date date = new Date(milliseconds);

        String string = "";

        int month = date.getMonth() + 1;
        int day = date.getDate();
        int year = date.getYear() + 1900;

        string += month < 10 ? "0" + month + "-" : month + "-";
        string += day < 10 ? "0" + day + "-" : day + "-";
        string += year;

        return string;
    }

    public static String getTimeFromMilliseconds(long milliseconds) {

        Date date = new Date(milliseconds);

        String string = "";

        int hour = date.getHours() + 1;
        int minute = date.getMinutes();
        int second = date.getSeconds();

        string += hour == 12 || (hour > 0 && hour < 12) ? hour + ":"
                : hour > 12 ? hour - 12 > 9 ? hour - 12 + ":" : "0" + (hour - 12) + ":" : "0" + hour + ":";
        string += minute < 10 ? "0" + minute + ":" : minute + ":";
        string += second < 10 ? "0" + second + "-" : second + "-";
        string += hour <= 12 ? "AM " : "PM ";
        string += "EST";

        return string;

    }

    public static String convertMillisecondsIntoStringTime(long milliseconds, boolean colonFormat) {

        long second = milliseconds / 1000;
        long minute = 0;
        long hour = 0;
        long day = 0;
        long week = 0;
        long month = 0;
        long year = 0;
        int highest = 0;

        String string = "";

        if (second / 31536000 >= 1) {

            highest = 6;

            year = Math.floorDiv(second, 31536000);

            second -= year * 31536000;
        }

        if (second / 2628000 >= 1) {

            if (highest == 0)
                highest = 5;

            month = Math.floorDiv(second, 2628000);

            second -= month * 2628000;
        }

        if (second / 604800 >= 1) {

            if (highest == 0)
                highest = 4;

            week = Math.floorDiv(second, 604800);

            second -= week * 604800;
        }

        if (second / 86400 >= 1) {

            if (highest == 0)
                highest = 3;

            day = Math.floorDiv(second, 86400);

            second -= day * 86400;
        }

        if (second / 3600 >= 1) {

            if (highest == 0)
                highest = 2;

            hour = Math.floorDiv(second, 3600);

            second -= hour * 3600;
        }

        if (second / 60 >= 1) {

            if (highest == 0)
                highest = 1;

            minute = Math.floorDiv(second, 60);

            second -= minute * 60;
        }

        if (colonFormat) {

            if (year > 0) {

                string += year > 9 ? year + ":" : "0" + year + ":";

            }

            if (month > 0 || (month == 0 && highest > 5)) {

                string += month > 0 ? month > 9 ? month + ":" : "0" + month + ":" : highest > 5 ? "00:" : "";

            }

            if (week > 0 || (week == 0 && highest > 4)) {

                string += week > 0 ? week > 9 ? week + ":" : "0" + week + ":" : highest > 4 ? "00:" : "";

            }

            if (day > 0 || (day == 0 && highest > 3)) {

                string += day > 0 ? day > 9 ? day + ":" : "0" + day + ":" : highest > 3 ? "00:" : "";

            }

            if (hour > 0 || (hour == 0 && highest > 2)) {

                string += hour > 0 ? hour > 9 ? hour + ":" : "0" + hour + ":" : highest > 2 ? "00:" : "";

            }

            if (minute > 0 || (minute == 0 && highest > 1)) {

                string += minute > 0 ? minute > 9 ? minute + ":" : "0" + minute + ":" : highest > 1 ? "00:" : "";

            }

            string += second > 0 ? second > 9 ? second : "0" + second : "00";

        } else {

            string += (year > 0 ? year > 1 ? year + " years, " : year + " year, " : "");
            string += (month > 0 ? month > 1 ? month + " months, " : month + " month, " : "");
            string += (week > 0 ? week > 1 ? week + " weeks, " : week + " week, " : "");
            string += (day > 0 ? day > 1 ? day + " days, " : day + " day, " : "");
            string += (hour > 0 ? hour > 1 ? hour + " hours, " : hour + " hour, " : "");
            string += (minute > 0 ? minute > 1 ? minute + " minutes, " : minute + " minute, " : "");
            string += (second > 0 ? second > 1 ? second + " seconds " : second + " second " : "");

        }

        return string;
    }

}