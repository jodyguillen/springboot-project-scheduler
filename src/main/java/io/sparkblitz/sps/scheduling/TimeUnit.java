package io.sparkblitz.sps.scheduling;
import org.springframework.util.StringUtils;

import java.sql.Time;
import java.util.Calendar;
import java.time.temporal.ChronoUnit;

public enum TimeUnit {
    DAY("Day", Calendar.DATE),
    WEEK("Week", Calendar.WEEK_OF_MONTH),
    MONTH("Month", Calendar.MONTH),
    YEAR("Year", Calendar.YEAR);

    private final String name;
    private final int calendarUnit;
    private TimeUnit(String name, int calendarUnit) {
        this.name = name;
        this.calendarUnit = calendarUnit;
    }

    public static TimeUnit from(String name) {
        if(StringUtils.isEmpty(name)) {
            return null;
        }

        for(TimeUnit u : TimeUnit.values()) {
            if(name.toUpperCase().startsWith(u.name.toUpperCase())) {
                return u;
            }
        }

        return null;
    }
    public String getName() {
        return name;
    }

    public int getCalendarUnit() {
        return calendarUnit;
    }
}
