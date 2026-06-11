package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import jdk.jfr.consumer.RecordedEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/jfr/stats/FpsStat.class */
public final class FpsStat extends Record {
    private final int fps;

    public FpsStat(int $$0) {
        this.fps = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FpsStat.class), FpsStat.class, "fps", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FpsStat;->fps:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FpsStat.class), FpsStat.class, "fps", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FpsStat;->fps:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FpsStat.class, Object.class), FpsStat.class, "fps", "FIELD:Lnet/minecraft/util/profiling/jfr/stats/FpsStat;->fps:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int fps() {
        return this.fps;
    }

    public static FpsStat from(RecordedEvent $$0, String $$1) {
        return new FpsStat($$0.getInt($$1));
    }
}
