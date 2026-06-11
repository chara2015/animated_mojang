package net.minecraft.world.attribute;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/AmbientAdditionsSettings.class */
public final class AmbientAdditionsSettings extends Record {
    private final Holder<SoundEvent> soundEvent;
    private final double tickChance;
    public static final Codec<AmbientAdditionsSettings> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(SoundEvent.CODEC.fieldOf("sound").forGetter($$0 -> {
            return $$0.soundEvent;
        }), Codec.DOUBLE.fieldOf("tick_chance").forGetter($$02 -> {
            return Double.valueOf($$02.tickChance);
        })).apply($$0, (v1, v2) -> {
            return new AmbientAdditionsSettings(v1, v2);
        });
    });

    public AmbientAdditionsSettings(Holder<SoundEvent> $$0, double $$1) {
        this.soundEvent = $$0;
        this.tickChance = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AmbientAdditionsSettings.class), AmbientAdditionsSettings.class, "soundEvent;tickChance", "FIELD:Lnet/minecraft/world/attribute/AmbientAdditionsSettings;->soundEvent:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/attribute/AmbientAdditionsSettings;->tickChance:D").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AmbientAdditionsSettings.class), AmbientAdditionsSettings.class, "soundEvent;tickChance", "FIELD:Lnet/minecraft/world/attribute/AmbientAdditionsSettings;->soundEvent:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/attribute/AmbientAdditionsSettings;->tickChance:D").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AmbientAdditionsSettings.class, Object.class), AmbientAdditionsSettings.class, "soundEvent;tickChance", "FIELD:Lnet/minecraft/world/attribute/AmbientAdditionsSettings;->soundEvent:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/attribute/AmbientAdditionsSettings;->tickChance:D").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Holder<SoundEvent> soundEvent() {
        return this.soundEvent;
    }

    public double tickChance() {
        return this.tickChance;
    }
}
