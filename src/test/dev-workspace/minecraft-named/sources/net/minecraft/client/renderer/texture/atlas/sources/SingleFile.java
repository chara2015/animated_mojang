package net.minecraft.client.renderer.texture.atlas.sources;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/atlas/sources/SingleFile.class */
public final class SingleFile extends Record implements SpriteSource {
    private final Identifier resourceId;
    private final Optional<Identifier> spriteId;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final MapCodec<SingleFile> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Identifier.CODEC.fieldOf("resource").forGetter((v0) -> {
            return v0.resourceId();
        }), Identifier.CODEC.optionalFieldOf("sprite").forGetter((v0) -> {
            return v0.spriteId();
        })).apply($$0, SingleFile::new);
    });

    public SingleFile(Identifier $$0, Optional<Identifier> $$1) {
        this.resourceId = $$0;
        this.spriteId = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SingleFile.class), SingleFile.class, "resourceId;spriteId", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/SingleFile;->resourceId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/SingleFile;->spriteId:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SingleFile.class), SingleFile.class, "resourceId;spriteId", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/SingleFile;->resourceId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/SingleFile;->spriteId:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SingleFile.class, Object.class), SingleFile.class, "resourceId;spriteId", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/SingleFile;->resourceId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/SingleFile;->spriteId:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier resourceId() {
        return this.resourceId;
    }

    public Optional<Identifier> spriteId() {
        return this.spriteId;
    }

    public SingleFile(Identifier $$0) {
        this($$0, Optional.empty());
    }

    @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource
    public void run(ResourceManager $$0, SpriteSource.Output $$1) {
        Identifier $$2 = TEXTURE_ID_CONVERTER.idToFile(this.resourceId);
        Optional<Resource> $$3 = $$0.getResource($$2);
        if ($$3.isPresent()) {
            $$1.add(this.spriteId.orElse(this.resourceId), $$3.get());
        } else {
            LOGGER.warn("Missing sprite: {}", $$2);
        }
    }

    @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource
    public MapCodec<SingleFile> codec() {
        return MAP_CODEC;
    }
}
