package dev.boooiil.historia.core.time;

import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ??
 */
@NullMarked
public final class GameDate {
    private static final Map<Integer, Integer> daysOfMonth = new HashMap<>();

    private final int year;
    private final short month;
    private final short day;

    private GameDate(int year, int month, int day) {
        this.year = year;
        this.month = (short) month;
        this.day = (short) day;
    }

    public static GameDate of(int year, int month, int dayOfMonth) {
        return new GameDate(year, month, dayOfMonth);
    }

    /**
     * @param formattedDate a string in the format YYYY-MM-DD
     * @return the corresponding GameDate
     */
    public static GameDate of(String formattedDate) {
        String[] split = formattedDate.split("-");
        return of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public GameDate plusDays(int days) {
        int newYear = this.year;
        int newMonth = this.month;
        int newDayOfMonth = this.day + days;

        while (newDayOfMonth > daysOfMonth.get(newMonth)) {
            newDayOfMonth -= daysOfMonth.get(newMonth);
            newMonth++;
            if (newMonth > 12) {
                newMonth = 1;
                newYear++;
            }
        }
        return new GameDate(newYear, newMonth, newDayOfMonth);
    }

    public GameDate plusYears(int years) {
        return new GameDate(year + years, month, day);
    }

    public int totalDays() {
        int monthDays = 0;
        for (int m = 1; m <= month; m++) {
            monthDays += daysOfMonth.get(m);
        }
        return year * 365 + monthDays + day;
    }

    @Override
    public String toString() {
        return String.format("%02d-%02d-%02d", year, month, day);
    }

    public int year() {
        return year;
    }

    public short month() {
        return month;
    }

    public short dayOfMonth() {
        return day;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (GameDate) obj;
        return this.year == that.year &&
                this.month == that.month &&
                this.day == that.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    static {
        daysOfMonth.put(1, 31); // January
        daysOfMonth.put(2, 28); // February (no leap year)
        daysOfMonth.put(3, 31); // March
        daysOfMonth.put(4, 30); // April
        daysOfMonth.put(5, 31); // May
        daysOfMonth.put(6, 30); // June
        daysOfMonth.put(7, 31); // July
        daysOfMonth.put(8, 31); // August
        daysOfMonth.put(9, 30); // September
        daysOfMonth.put(10, 31); // October
        daysOfMonth.put(11, 30); // November
        daysOfMonth.put(12, 31); // December
    }
}
