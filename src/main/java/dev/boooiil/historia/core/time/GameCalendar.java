package dev.boooiil.historia.core.time;

import java.time.*;

/**
 * Custom game calendar.
 */
public class GameCalendar {

    private static final long REAL_START_DATE_MILLIS = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            .toInstant().toEpochMilli();
    public static final long GAME_DAY_MILLIS = 7200000L;
    private static final GameDate GAME_START_DATE = GameDate.of(1212, 1, 1);

    public static GameDate currentDate() {
        return dateOf(Instant.now().toEpochMilli());
    }

    // TODO does not account for leap years
    public static GameDate dateOf(long millisSinceEpoch) {
        long millisSinceStart = millisSinceEpoch - REAL_START_DATE_MILLIS;
        int gameDays = (int) (millisSinceStart / GAME_DAY_MILLIS);

        return GAME_START_DATE.plusDays(gameDays);
    }

    // TODO does not account for leap years
    public static long epochOf(GameDate gameDate) {
        int daysSinceStart = GAME_START_DATE.totalDays() - gameDate.totalDays();
        long millisSinceStart = daysSinceStart * GAME_DAY_MILLIS;

        return REAL_START_DATE_MILLIS + millisSinceStart;
    }
}