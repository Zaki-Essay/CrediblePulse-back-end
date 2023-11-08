package xyz.crediblepulse.crediblepulsebackend.utils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public final class DatesUtils {

    private DatesUtils() {}

    public static String format(DateTimeFormatter dtf, TemporalAccessor date) {
        return dtf.format(date);
    }
}
