package net.labymod.api.util.time;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/time/DateUtil.class */
public class DateUtil {
    public static final ZoneId DEFAULT_TIMEZONE = ZoneId.systemDefault();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/time/DateUtil$TemporalAccessorType.class */
    public enum TemporalAccessorType {
        TIME,
        DATE
    }

    public static DateTimeFormatter ofPattern(String pattern, TemporalAccessorType type) {
        return ofPattern(pattern, DEFAULT_TIMEZONE, type);
    }

    public static DateTimeFormatter ofPattern(String pattern, ZoneId zoneId, TemporalAccessorType type) {
        return ofPattern(pattern, Locale.ROOT, zoneId, type);
    }

    public static DateTimeFormatter ofPattern(String pattern, Locale locale, TemporalAccessorType type) {
        return ofPattern(pattern, locale, DEFAULT_TIMEZONE, type);
    }

    public static DateTimeFormatter ofPattern(String pattern, Locale locale, ZoneId zoneId, TemporalAccessorType type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        formatNow(formatter, zoneId, type);
        return formatter;
    }

    public static String formatNow(DateTimeFormatter formatter, ZoneId zoneId, TemporalAccessorType type) {
        return format(formatter, Instant.now(), zoneId, type);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static String format(DateTimeFormatter formatter, Instant instant, ZoneId zoneId, TemporalAccessorType type) throws MatchException {
        BiFunction<Instant, ZoneId, TemporalAccessor> biFunction;
        switch (type) {
            case TIME:
                biFunction = LocalTime::ofInstant;
                break;
            case DATE:
                biFunction = LocalDate::ofInstant;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        BiFunction<Instant, ZoneId, TemporalAccessor> factory = biFunction;
        return format(formatter, instant, zoneId, factory);
    }

    private static String format(DateTimeFormatter formatter, Instant instant, ZoneId zoneId, BiFunction<Instant, ZoneId, TemporalAccessor> temporalAccessorFactory) {
        try {
            return formatter.format(temporalAccessorFactory.apply(instant, zoneId));
        } catch (DateTimeException exception) {
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
    }
}
