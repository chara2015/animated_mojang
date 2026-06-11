package net.minecraft.util.profiling;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/EmptyProfileResults.class */
public class EmptyProfileResults implements ProfileResults {
    public static final EmptyProfileResults EMPTY = new EmptyProfileResults();

    private EmptyProfileResults() {
    }

    @Override // net.minecraft.util.profiling.ProfileResults
    public List<ResultField> getTimes(String $$0) {
        return Collections.emptyList();
    }

    @Override // net.minecraft.util.profiling.ProfileResults
    public boolean saveResults(Path $$0) {
        return false;
    }

    @Override // net.minecraft.util.profiling.ProfileResults
    public long getStartTimeNano() {
        return 0L;
    }

    @Override // net.minecraft.util.profiling.ProfileResults
    public int getStartTimeTicks() {
        return 0;
    }

    @Override // net.minecraft.util.profiling.ProfileResults
    public long getEndTimeNano() {
        return 0L;
    }

    @Override // net.minecraft.util.profiling.ProfileResults
    public int getEndTimeTicks() {
        return 0;
    }

    @Override // net.minecraft.util.profiling.ProfileResults
    public String getProfilerResults() {
        return "";
    }
}
