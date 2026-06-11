package net.minecraft.world.attribute;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/AmbientMoodSettings.class */
public final class AmbientMoodSettings extends Record {
    private final Holder<SoundEvent> soundEvent;
    private final int tickDelay;
    private final int blockSearchExtent;
    private final double soundPositionOffset;
    public static final Codec<AmbientMoodSettings> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(SoundEvent.CODEC.fieldOf("sound").forGetter($$0 -> {
            return $$0.soundEvent;
        }), Codec.INT.fieldOf("tick_delay").forGetter($$02 -> {
            return Integer.valueOf($$02.tickDelay);
        }), Codec.INT.fieldOf("block_search_extent").forGetter($$03 -> {
            return Integer.valueOf($$03.blockSearchExtent);
        }), Codec.DOUBLE.fieldOf("offset").forGetter($$04 -> {
            return Double.valueOf($$04.soundPositionOffset);
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new AmbientMoodSettings(v1, v2, v3, v4);
        });
    });
    public static final AmbientMoodSettings LEGACY_CAVE_SETTINGS = new AmbientMoodSettings(SoundEvents.AMBIENT_CAVE, 6000, 8, 2.0d);

    public AmbientMoodSettings(Holder<SoundEvent> $$0, int $$1, int $$2, double $$3) {
        this.soundEvent = $$0;
        this.tickDelay = $$1;
        this.blockSearchExtent = $$2;
        this.soundPositionOffset = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AmbientMoodSettings.class), AmbientMoodSettings.class, "soundEvent;tickDelay;blockSearchExtent;soundPositionOffset", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->soundEvent:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->tickDelay:I", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->blockSearchExtent:I", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->soundPositionOffset:D").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AmbientMoodSettings.class), AmbientMoodSettings.class, "soundEvent;tickDelay;blockSearchExtent;soundPositionOffset", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->soundEvent:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->tickDelay:I", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->blockSearchExtent:I", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->soundPositionOffset:D").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AmbientMoodSettings.class, Object.class), AmbientMoodSettings.class, "soundEvent;tickDelay;blockSearchExtent;soundPositionOffset", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->soundEvent:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->tickDelay:I", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->blockSearchExtent:I", "FIELD:Lnet/minecraft/world/attribute/AmbientMoodSettings;->soundPositionOffset:D").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Holder<SoundEvent> soundEvent() {
        return this.soundEvent;
    }

    public int tickDelay() {
        return this.tickDelay;
    }

    public int blockSearchExtent() {
        return this.blockSearchExtent;
    }

    public double soundPositionOffset() {
        return this.soundPositionOffset;
    }
}
