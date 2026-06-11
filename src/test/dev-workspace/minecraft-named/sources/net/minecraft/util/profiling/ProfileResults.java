package net.minecraft.util.profiling;

import java.nio.file.Path;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/ProfileResults.class */
public interface ProfileResults {
    public static final char PATH_SEPARATOR = 30;

    List<ResultField> getTimes(String str);

    boolean saveResults(Path path);

    long getStartTimeNano();

    int getStartTimeTicks();

    long getEndTimeNano();

    int getEndTimeTicks();

    String getProfilerResults();

    default long getNanoDuration() {
        return getEndTimeNano() - getStartTimeNano();
    }

    default int getTickDuration() {
        return getEndTimeTicks() - getStartTimeTicks();
    }

    static String demanglePath(String $$0) {
        return $$0.replace((char) 30, '.');
    }
}
