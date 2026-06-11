package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/NameAndId.class */
public final class NameAndId extends Record {
    private final UUID id;
    private final String name;
    public static final Codec<NameAndId> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(UUIDUtil.STRING_CODEC.fieldOf(Entity.TAG_ID).forGetter((v0) -> {
            return v0.id();
        }), Codec.STRING.fieldOf(JigsawBlockEntity.NAME).forGetter((v0) -> {
            return v0.name();
        })).apply($$0, NameAndId::new);
    });

    public NameAndId(UUID $$0, String $$1) {
        this.id = $$0;
        this.name = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NameAndId.class), NameAndId.class, "id;name", "FIELD:Lnet/minecraft/server/players/NameAndId;->id:Ljava/util/UUID;", "FIELD:Lnet/minecraft/server/players/NameAndId;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NameAndId.class), NameAndId.class, "id;name", "FIELD:Lnet/minecraft/server/players/NameAndId;->id:Ljava/util/UUID;", "FIELD:Lnet/minecraft/server/players/NameAndId;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NameAndId.class, Object.class), NameAndId.class, "id;name", "FIELD:Lnet/minecraft/server/players/NameAndId;->id:Ljava/util/UUID;", "FIELD:Lnet/minecraft/server/players/NameAndId;->name:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public UUID id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public NameAndId(GameProfile $$0) {
        this($$0.id(), $$0.name());
    }

    public NameAndId(com.mojang.authlib.yggdrasil.response.NameAndId $$0) {
        this($$0.id(), $$0.name());
    }

    public static NameAndId fromJson(JsonObject $$0) {
        if (!$$0.has("uuid") || !$$0.has(JigsawBlockEntity.NAME)) {
            return null;
        }
        String $$1 = $$0.get("uuid").getAsString();
        try {
            UUID $$4 = UUID.fromString($$1);
            return new NameAndId($$4, $$0.get(JigsawBlockEntity.NAME).getAsString());
        } catch (Throwable th) {
            return null;
        }
    }

    public void appendTo(JsonObject $$0) {
        $$0.addProperty("uuid", id().toString());
        $$0.addProperty(JigsawBlockEntity.NAME, name());
    }

    public static NameAndId createOffline(String $$0) {
        UUID $$1 = UUIDUtil.createOfflinePlayerUUID($$0);
        return new NameAndId($$1, $$0);
    }
}
