package net.minecraft.client.resources.metadata.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/metadata/animation/AnimationMetadataSection.class */
public final class AnimationMetadataSection extends Record {
    private final Optional<List<AnimationFrame>> frames;
    private final Optional<Integer> frameWidth;
    private final Optional<Integer> frameHeight;
    private final int defaultFrameTime;
    private final boolean interpolatedFrames;
    public static final Codec<AnimationMetadataSection> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(AnimationFrame.CODEC.listOf().optionalFieldOf("frames").forGetter((v0) -> {
            return v0.frames();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf(Display.TAG_WIDTH).forGetter((v0) -> {
            return v0.frameWidth();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf(Display.TAG_HEIGHT).forGetter((v0) -> {
            return v0.frameHeight();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf("frametime", 1).forGetter((v0) -> {
            return v0.defaultFrameTime();
        }), Codec.BOOL.optionalFieldOf("interpolate", false).forGetter((v0) -> {
            return v0.interpolatedFrames();
        })).apply($$0, (v1, v2, v3, v4, v5) -> {
            return new AnimationMetadataSection(v1, v2, v3, v4, v5);
        });
    });
    public static final MetadataSectionType<AnimationMetadataSection> TYPE = new MetadataSectionType<>("animation", CODEC);

    public AnimationMetadataSection(Optional<List<AnimationFrame>> $$0, Optional<Integer> $$1, Optional<Integer> $$2, int $$3, boolean $$4) {
        this.frames = $$0;
        this.frameWidth = $$1;
        this.frameHeight = $$2;
        this.defaultFrameTime = $$3;
        this.interpolatedFrames = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AnimationMetadataSection.class), AnimationMetadataSection.class, "frames;frameWidth;frameHeight;defaultFrameTime;interpolatedFrames", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frames:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frameWidth:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frameHeight:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->defaultFrameTime:I", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->interpolatedFrames:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AnimationMetadataSection.class), AnimationMetadataSection.class, "frames;frameWidth;frameHeight;defaultFrameTime;interpolatedFrames", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frames:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frameWidth:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frameHeight:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->defaultFrameTime:I", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->interpolatedFrames:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AnimationMetadataSection.class, Object.class), AnimationMetadataSection.class, "frames;frameWidth;frameHeight;defaultFrameTime;interpolatedFrames", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frames:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frameWidth:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->frameHeight:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->defaultFrameTime:I", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationMetadataSection;->interpolatedFrames:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<List<AnimationFrame>> frames() {
        return this.frames;
    }

    public Optional<Integer> frameWidth() {
        return this.frameWidth;
    }

    public Optional<Integer> frameHeight() {
        return this.frameHeight;
    }

    public int defaultFrameTime() {
        return this.defaultFrameTime;
    }

    public boolean interpolatedFrames() {
        return this.interpolatedFrames;
    }

    public FrameSize calculateFrameSize(int $$0, int $$1) {
        if (this.frameWidth.isPresent()) {
            if (this.frameHeight.isPresent()) {
                return new FrameSize(this.frameWidth.get().intValue(), this.frameHeight.get().intValue());
            }
            return new FrameSize(this.frameWidth.get().intValue(), $$1);
        }
        if (this.frameHeight.isPresent()) {
            return new FrameSize($$0, this.frameHeight.get().intValue());
        }
        int $$2 = Math.min($$0, $$1);
        return new FrameSize($$2, $$2);
    }
}
