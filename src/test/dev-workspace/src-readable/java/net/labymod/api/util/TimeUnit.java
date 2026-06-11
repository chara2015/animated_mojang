package net.labymod.api.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/TimeUnit.class */
public enum TimeUnit {
    YEAR('y', 31536000000L),
    MONTH('M', 2592000000L),
    DAY('d', 86400000),
    HOUR('h', 3600000),
    MINUTE('m', 60000),
    SECOND('s', 1000),
    UNKNOWN('?', 0);

    private static final TimeUnit[] VALUES = values();
    private final char suffix;
    private final long factor;

    TimeUnit(char suffix, long factor) {
        this.suffix = suffix;
        this.factor = factor;
    }

    @NotNull
    public static TimeUnit from(String input) {
        for (TimeUnit unit : VALUES) {
            if (unit != UNKNOWN && input.endsWith(unit.getSuffix())) {
                return unit;
            }
        }
        return UNKNOWN;
    }

    public static long parseToLong(String input) {
        boolean canSeparate = false;
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (Character.isDigit(character)) {
                if (canSeparate) {
                    string.append(" ");
                    canSeparate = false;
                }
            } else {
                canSeparate = true;
            }
            if (character != ' ') {
                string.append(character);
            }
        }
        long duration = 0;
        String[] units = string.toString().split(" ");
        for (String unit : units) {
            duration += from(unit).toLong(unit);
        }
        return duration;
    }

    @NotNull
    public static String parseToString(long duration) {
        return parseToString(duration, false);
    }

    @NotNull
    public static String parseToString(long duration, boolean trimmed) {
        StringBuilder string = new StringBuilder();
        for (String unit : parseToList(duration)) {
            if (!string.isEmpty() && !trimmed) {
                string.append(" ");
            }
            string.append(unit);
        }
        return string.toString();
    }

    @NotNull
    public static List<String> parseToList(long duration) {
        List<String> units = new ArrayList<>();
        for (TimeUnit unit : VALUES) {
            if (unit != UNKNOWN) {
                long amount = duration / unit.getFactor();
                if (amount > 0 || (unit == SECOND && units.isEmpty())) {
                    units.add(String.format(Locale.ROOT, "%s%s", Long.valueOf(amount), Character.valueOf(unit.getIdentifier())));
                    duration -= amount * unit.getFactor();
                }
            }
        }
        return units;
    }

    @NotNull
    public String getSuffix() {
        return String.valueOf(this.suffix);
    }

    public char getIdentifier() {
        return this.suffix;
    }

    public long getFactor() {
        return this.factor;
    }

    private long toLong(@NotNull String input) {
        if (equals(UNKNOWN)) {
            return 0L;
        }
        String amount = input.substring(0, input.length() - getSuffix().length());
        if (StringUtil.isNumeric(amount)) {
            return Long.parseLong(amount) * this.factor;
        }
        return 0L;
    }
}
