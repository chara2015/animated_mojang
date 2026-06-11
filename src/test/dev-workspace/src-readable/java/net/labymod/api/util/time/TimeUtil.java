package net.labymod.api.util.time;

import net.labymod.api.util.ide.IgnoreInstrumentation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/time/TimeUtil.class */
@IgnoreInstrumentation
public final class TimeUtil {
    public static final float TICKS_TO_MILLISECONDS = 50.0f;
    public static final float MILLISECONDS_TO_TICKS = 0.02f;
    public static NanosecondsTimeSource nanosecondsTimeSource = TimeUtil::getNanoTime;

    private TimeUtil() {
    }

    public static float convertDeltaToMilliseconds(float ticks) {
        return convertTicksToMilliseconds(Math.min(1.0f, ticks));
    }

    public static float convertTicksToMilliseconds(float ticks) {
        return ticks * 50.0f;
    }

    public static float convertMillisecondsToTicks(long milliseconds) {
        return milliseconds * 0.02f;
    }

    public static long getMillis() {
        return getNanos() / 1000000;
    }

    public static long getNanos() {
        return nanosecondsTimeSource.getAsLong();
    }

    @Deprecated(forRemoval = true, since = "4.1.23")
    public static long getCurrentTimeMills() {
        return getCurrentTimeMillis();
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long getNanoTime() {
        return System.nanoTime();
    }

    public static String formatTickDuration(int duration) {
        int seconds = duration / 20;
        int minutes = seconds / 60;
        int seconds2 = seconds % 60;
        int hours = minutes / 60;
        int minutes2 = minutes % 60;
        if (hours > 0) {
            return hours + ":" + formatMinutesAndSeconds(minutes2, seconds2);
        }
        return formatMinutesAndSeconds(minutes2, seconds2);
    }

    private static String formatMinutesAndSeconds(int minutes, int seconds) {
        return String.valueOf(minutes < 10 ? "0" + minutes : Integer.valueOf(minutes)) + ":" + String.valueOf(seconds < 10 ? "0" + seconds : Integer.valueOf(seconds));
    }
}
