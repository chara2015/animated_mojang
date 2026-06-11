package net.labymod.v1_20_1.client.crash;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.client.crash.GameCrashReport;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/crash/VersionedGameCrashReportCategory.class */
public final class VersionedGameCrashReportCategory extends Record implements GameCrashReport.Category {
    private final p category;

    public VersionedGameCrashReportCategory(p category) {
        this.category = category;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, VersionedGameCrashReportCategory.class), VersionedGameCrashReportCategory.class, "category", "FIELD:Lnet/labymod/v1_20_1/client/crash/VersionedGameCrashReportCategory;->category:Lp;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, VersionedGameCrashReportCategory.class), VersionedGameCrashReportCategory.class, "category", "FIELD:Lnet/labymod/v1_20_1/client/crash/VersionedGameCrashReportCategory;->category:Lp;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, VersionedGameCrashReportCategory.class, Object.class), VersionedGameCrashReportCategory.class, "category", "FIELD:Lnet/labymod/v1_20_1/client/crash/VersionedGameCrashReportCategory;->category:Lp;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public p category() {
        return this.category;
    }

    @Override // net.labymod.api.client.crash.GameCrashReport.Category
    public void setDetail(String name, Supplier<String> detail) {
        p pVar = this.category;
        Objects.requireNonNull(detail);
        pVar.a(name, detail::get);
    }
}
