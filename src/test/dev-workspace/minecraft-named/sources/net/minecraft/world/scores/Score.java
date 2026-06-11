package net.minecraft.world.scores;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.chat.numbers.NumberFormatTypes;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/Score.class */
public class Score implements ReadOnlyScoreInfo {
    private int value;
    private boolean locked;
    private Component display;
    private NumberFormat numberFormat;

    public Score() {
        this.locked = true;
    }

    public Score(Packed $$0) {
        this.locked = true;
        this.value = $$0.value;
        this.locked = $$0.locked;
        this.display = $$0.display.orElse(null);
        this.numberFormat = $$0.numberFormat.orElse(null);
    }

    public Packed pack() {
        return new Packed(this.value, this.locked, Optional.ofNullable(this.display), Optional.ofNullable(this.numberFormat));
    }

    @Override // net.minecraft.world.scores.ReadOnlyScoreInfo
    public int value() {
        return this.value;
    }

    public void value(int $$0) {
        this.value = $$0;
    }

    @Override // net.minecraft.world.scores.ReadOnlyScoreInfo
    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean $$0) {
        this.locked = $$0;
    }

    public Component display() {
        return this.display;
    }

    public void display(Component $$0) {
        this.display = $$0;
    }

    @Override // net.minecraft.world.scores.ReadOnlyScoreInfo
    public NumberFormat numberFormat() {
        return this.numberFormat;
    }

    public void numberFormat(NumberFormat $$0) {
        this.numberFormat = $$0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/Score$Packed.class */
    public static final class Packed extends Record {
        private final int value;
        private final boolean locked;
        private final Optional<Component> display;
        private final Optional<NumberFormat> numberFormat;
        public static final MapCodec<Packed> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.INT.optionalFieldOf("Score", 0).forGetter((v0) -> {
                return v0.value();
            }), Codec.BOOL.optionalFieldOf("Locked", false).forGetter((v0) -> {
                return v0.locked();
            }), ComponentSerialization.CODEC.optionalFieldOf("display").forGetter((v0) -> {
                return v0.display();
            }), NumberFormatTypes.CODEC.optionalFieldOf("format").forGetter((v0) -> {
                return v0.numberFormat();
            })).apply($$0, (v1, v2, v3, v4) -> {
                return new Packed(v1, v2, v3, v4);
            });
        });

        public Packed(int $$0, boolean $$1, Optional<Component> $$2, Optional<NumberFormat> $$3) {
            this.value = $$0;
            this.locked = $$1;
            this.display = $$2;
            this.numberFormat = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Packed.class), Packed.class, "value;locked;display;numberFormat", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->value:I", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->locked:Z", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->display:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->numberFormat:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Packed.class), Packed.class, "value;locked;display;numberFormat", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->value:I", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->locked:Z", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->display:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->numberFormat:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Packed.class, Object.class), Packed.class, "value;locked;display;numberFormat", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->value:I", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->locked:Z", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->display:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/scores/Score$Packed;->numberFormat:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int value() {
            return this.value;
        }

        public boolean locked() {
            return this.locked;
        }

        public Optional<Component> display() {
            return this.display;
        }

        public Optional<NumberFormat> numberFormat() {
            return this.numberFormat;
        }
    }
}
