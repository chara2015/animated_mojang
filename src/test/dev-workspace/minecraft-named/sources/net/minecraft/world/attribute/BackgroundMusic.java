package net.minecraft.world.attribute;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/attribute/BackgroundMusic.class */
public final class BackgroundMusic extends Record {
    private final Optional<Music> defaultMusic;
    private final Optional<Music> creativeMusic;
    private final Optional<Music> underwaterMusic;
    public static final BackgroundMusic EMPTY = new BackgroundMusic(Optional.empty(), Optional.empty(), Optional.empty());
    public static final BackgroundMusic OVERWORLD = new BackgroundMusic(Optional.of(Musics.GAME), Optional.of(Musics.CREATIVE), Optional.empty());
    public static final Codec<BackgroundMusic> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Music.CODEC.optionalFieldOf(GameTestEnvironments.DEFAULT).forGetter((v0) -> {
            return v0.defaultMusic();
        }), Music.CODEC.optionalFieldOf("creative").forGetter((v0) -> {
            return v0.creativeMusic();
        }), Music.CODEC.optionalFieldOf("underwater").forGetter((v0) -> {
            return v0.underwaterMusic();
        })).apply($$0, BackgroundMusic::new);
    });

    public BackgroundMusic(Optional<Music> $$0, Optional<Music> $$1, Optional<Music> $$2) {
        this.defaultMusic = $$0;
        this.creativeMusic = $$1;
        this.underwaterMusic = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BackgroundMusic.class), BackgroundMusic.class, "defaultMusic;creativeMusic;underwaterMusic", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->defaultMusic:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->creativeMusic:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->underwaterMusic:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BackgroundMusic.class), BackgroundMusic.class, "defaultMusic;creativeMusic;underwaterMusic", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->defaultMusic:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->creativeMusic:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->underwaterMusic:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BackgroundMusic.class, Object.class), BackgroundMusic.class, "defaultMusic;creativeMusic;underwaterMusic", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->defaultMusic:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->creativeMusic:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/attribute/BackgroundMusic;->underwaterMusic:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<Music> defaultMusic() {
        return this.defaultMusic;
    }

    public Optional<Music> creativeMusic() {
        return this.creativeMusic;
    }

    public Optional<Music> underwaterMusic() {
        return this.underwaterMusic;
    }

    public BackgroundMusic(Music $$0) {
        this(Optional.of($$0), Optional.empty(), Optional.empty());
    }

    public BackgroundMusic(Holder<SoundEvent> $$0) {
        this(Musics.createGameMusic($$0));
    }

    public BackgroundMusic withUnderwater(Music $$0) {
        return new BackgroundMusic(this.defaultMusic, this.creativeMusic, Optional.of($$0));
    }

    public Optional<Music> select(boolean $$0, boolean $$1) {
        if ($$1 && this.underwaterMusic.isPresent()) {
            return this.underwaterMusic;
        }
        if ($$0 && this.creativeMusic.isPresent()) {
            return this.creativeMusic;
        }
        return this.defaultMusic;
    }
}
