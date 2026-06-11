package net.minecraft.sounds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/sounds/Music.class */
public final class Music extends Record {
    private final Holder<SoundEvent> sound;
    private final int minDelay;
    private final int maxDelay;
    private final boolean replaceCurrentMusic;
    public static final Codec<Music> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(SoundEvent.CODEC.fieldOf("sound").forGetter((v0) -> {
            return v0.sound();
        }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("min_delay").forGetter((v0) -> {
            return v0.minDelay();
        }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("max_delay").forGetter((v0) -> {
            return v0.maxDelay();
        }), Codec.BOOL.optionalFieldOf("replace_current_music", false).forGetter((v0) -> {
            return v0.replaceCurrentMusic();
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new Music(v1, v2, v3, v4);
        });
    });

    public Music(Holder<SoundEvent> $$0, int $$1, int $$2, boolean $$3) {
        this.sound = $$0;
        this.minDelay = $$1;
        this.maxDelay = $$2;
        this.replaceCurrentMusic = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Music.class), Music.class, "sound;minDelay;maxDelay;replaceCurrentMusic", "FIELD:Lnet/minecraft/sounds/Music;->sound:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/sounds/Music;->minDelay:I", "FIELD:Lnet/minecraft/sounds/Music;->maxDelay:I", "FIELD:Lnet/minecraft/sounds/Music;->replaceCurrentMusic:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Music.class), Music.class, "sound;minDelay;maxDelay;replaceCurrentMusic", "FIELD:Lnet/minecraft/sounds/Music;->sound:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/sounds/Music;->minDelay:I", "FIELD:Lnet/minecraft/sounds/Music;->maxDelay:I", "FIELD:Lnet/minecraft/sounds/Music;->replaceCurrentMusic:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Music.class, Object.class), Music.class, "sound;minDelay;maxDelay;replaceCurrentMusic", "FIELD:Lnet/minecraft/sounds/Music;->sound:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/sounds/Music;->minDelay:I", "FIELD:Lnet/minecraft/sounds/Music;->maxDelay:I", "FIELD:Lnet/minecraft/sounds/Music;->replaceCurrentMusic:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Holder<SoundEvent> sound() {
        return this.sound;
    }

    public int minDelay() {
        return this.minDelay;
    }

    public int maxDelay() {
        return this.maxDelay;
    }

    public boolean replaceCurrentMusic() {
        return this.replaceCurrentMusic;
    }
}
