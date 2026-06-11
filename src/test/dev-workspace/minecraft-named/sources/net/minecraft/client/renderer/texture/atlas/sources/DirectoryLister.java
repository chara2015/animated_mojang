package net.minecraft.client.renderer.texture.atlas.sources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/atlas/sources/DirectoryLister.class */
public final class DirectoryLister extends Record implements SpriteSource {
    private final String sourcePath;
    private final String idPrefix;
    public static final MapCodec<DirectoryLister> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf("source").forGetter((v0) -> {
            return v0.sourcePath();
        }), Codec.STRING.fieldOf("prefix").forGetter((v0) -> {
            return v0.idPrefix();
        })).apply($$0, DirectoryLister::new);
    });

    public DirectoryLister(String $$0, String $$1) {
        this.sourcePath = $$0;
        this.idPrefix = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DirectoryLister.class), DirectoryLister.class, "sourcePath;idPrefix", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/DirectoryLister;->sourcePath:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/DirectoryLister;->idPrefix:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DirectoryLister.class), DirectoryLister.class, "sourcePath;idPrefix", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/DirectoryLister;->sourcePath:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/DirectoryLister;->idPrefix:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DirectoryLister.class, Object.class), DirectoryLister.class, "sourcePath;idPrefix", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/DirectoryLister;->sourcePath:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/DirectoryLister;->idPrefix:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String sourcePath() {
        return this.sourcePath;
    }

    public String idPrefix() {
        return this.idPrefix;
    }

    @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource
    public void run(ResourceManager $$0, SpriteSource.Output $$1) {
        FileToIdConverter $$2 = new FileToIdConverter("textures/" + this.sourcePath, ".png");
        $$2.listMatchingResources($$0).forEach(($$22, $$3) -> {
            Identifier $$4 = $$2.fileToId($$22).withPrefix(this.idPrefix);
            $$1.add($$4, $$3);
        });
    }

    @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource
    public MapCodec<DirectoryLister> codec() {
        return MAP_CODEC;
    }
}
