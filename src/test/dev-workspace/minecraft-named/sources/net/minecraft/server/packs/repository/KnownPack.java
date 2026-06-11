package net.minecraft.server.packs.repository;

import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.SharedConstants;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/repository/KnownPack.class */
public final class KnownPack extends Record {
    private final String namespace;
    private final String id;
    private final String version;
    public static final StreamCodec<ByteBuf, KnownPack> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, (v0) -> {
        return v0.namespace();
    }, ByteBufCodecs.STRING_UTF8, (v0) -> {
        return v0.id();
    }, ByteBufCodecs.STRING_UTF8, (v0) -> {
        return v0.version();
    }, KnownPack::new);
    public static final String VANILLA_NAMESPACE = "minecraft";

    public KnownPack(String $$0, String $$1, String $$2) {
        this.namespace = $$0;
        this.id = $$1;
        this.version = $$2;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, KnownPack.class), KnownPack.class, "namespace;id;version", "FIELD:Lnet/minecraft/server/packs/repository/KnownPack;->namespace:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/repository/KnownPack;->id:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/repository/KnownPack;->version:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, KnownPack.class, Object.class), KnownPack.class, "namespace;id;version", "FIELD:Lnet/minecraft/server/packs/repository/KnownPack;->namespace:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/repository/KnownPack;->id:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/repository/KnownPack;->version:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String namespace() {
        return this.namespace;
    }

    public String id() {
        return this.id;
    }

    public String version() {
        return this.version;
    }

    public static KnownPack vanilla(String $$0) {
        return new KnownPack("minecraft", $$0, SharedConstants.getCurrentVersion().id());
    }

    public boolean isVanilla() {
        return this.namespace.equals("minecraft");
    }

    @Override // java.lang.Record
    public String toString() {
        return this.namespace + ":" + this.id + ":" + this.version;
    }
}
