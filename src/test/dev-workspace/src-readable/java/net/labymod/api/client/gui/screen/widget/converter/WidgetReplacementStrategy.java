package net.labymod.api.client.gui.screen.widget.converter;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.BitSet;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/WidgetReplacementStrategy.class */
public final class WidgetReplacementStrategy extends Record {
    private final Supplier<AbstractWidget<?>> replacement;
    private final BitSet flags;

    public WidgetReplacementStrategy(Supplier<AbstractWidget<?>> replacement, BitSet flags) {
        this.replacement = replacement;
        this.flags = flags;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WidgetReplacementStrategy.class), WidgetReplacementStrategy.class, "replacement;flags", "FIELD:Lnet/labymod/api/client/gui/screen/widget/converter/WidgetReplacementStrategy;->replacement:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/api/client/gui/screen/widget/converter/WidgetReplacementStrategy;->flags:Ljava/util/BitSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WidgetReplacementStrategy.class), WidgetReplacementStrategy.class, "replacement;flags", "FIELD:Lnet/labymod/api/client/gui/screen/widget/converter/WidgetReplacementStrategy;->replacement:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/api/client/gui/screen/widget/converter/WidgetReplacementStrategy;->flags:Ljava/util/BitSet;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WidgetReplacementStrategy.class, Object.class), WidgetReplacementStrategy.class, "replacement;flags", "FIELD:Lnet/labymod/api/client/gui/screen/widget/converter/WidgetReplacementStrategy;->replacement:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/api/client/gui/screen/widget/converter/WidgetReplacementStrategy;->flags:Ljava/util/BitSet;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Supplier<AbstractWidget<?>> replacement() {
        return this.replacement;
    }

    public BitSet flags() {
        return this.flags;
    }

    public AbstractWidget<?> createReplacement() {
        return this.replacement.get();
    }

    public boolean isSet(@NotNull ReplacementBehavior behavior) {
        return behavior.isSet(flags());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/WidgetReplacementStrategy$ReplacementBehavior.class */
    public enum ReplacementBehavior {
        KEEP_BOUNDS,
        KEEP_STATES;

        private static final ReplacementBehavior[] VALUES = values();

        @NotNull
        public static BitSet of(ReplacementBehavior... flags) {
            BitSet bitSet = new BitSet();
            for (ReplacementBehavior flag : flags) {
                flag.set(bitSet);
            }
            return bitSet;
        }

        @NotNull
        public static BitSet all() {
            return of(VALUES);
        }

        public void set(@NotNull BitSet flags) {
            flags.set(ordinal());
        }

        public boolean isSet(@NotNull BitSet flags) {
            return flags.get(ordinal());
        }
    }
}
