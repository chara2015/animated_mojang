package net.minecraft.server.network;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import net.minecraft.network.chat.FilterMask;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/FilteredText.class */
public final class FilteredText extends Record {
    private final String raw;
    private final FilterMask mask;
    public static final FilteredText EMPTY = passThrough("");

    public FilteredText(String $$0, FilterMask $$1) {
        this.raw = $$0;
        this.mask = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FilteredText.class), FilteredText.class, "raw;mask", "FIELD:Lnet/minecraft/server/network/FilteredText;->raw:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/network/FilteredText;->mask:Lnet/minecraft/network/chat/FilterMask;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FilteredText.class), FilteredText.class, "raw;mask", "FIELD:Lnet/minecraft/server/network/FilteredText;->raw:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/network/FilteredText;->mask:Lnet/minecraft/network/chat/FilterMask;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FilteredText.class, Object.class), FilteredText.class, "raw;mask", "FIELD:Lnet/minecraft/server/network/FilteredText;->raw:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/network/FilteredText;->mask:Lnet/minecraft/network/chat/FilterMask;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String raw() {
        return this.raw;
    }

    public FilterMask mask() {
        return this.mask;
    }

    public static FilteredText passThrough(String $$0) {
        return new FilteredText($$0, FilterMask.PASS_THROUGH);
    }

    public static FilteredText fullyFiltered(String $$0) {
        return new FilteredText($$0, FilterMask.FULLY_FILTERED);
    }

    public String filtered() {
        return this.mask.apply(this.raw);
    }

    public String filteredOrEmpty() {
        return (String) Objects.requireNonNullElse(filtered(), "");
    }

    public boolean isFiltered() {
        return !this.mask.isEmpty();
    }
}
