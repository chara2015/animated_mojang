package net.minecraft.world.level.saveddata.maps;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/saveddata/maps/MapDecoration.class */
public final class MapDecoration extends Record {
    private final Holder<MapDecorationType> type;
    private final byte x;
    private final byte y;
    private final byte rot;
    private final Optional<Component> name;
    public static final StreamCodec<RegistryFriendlyByteBuf, MapDecoration> STREAM_CODEC = StreamCodec.composite(MapDecorationType.STREAM_CODEC, (v0) -> {
        return v0.type();
    }, ByteBufCodecs.BYTE, (v0) -> {
        return v0.x();
    }, ByteBufCodecs.BYTE, (v0) -> {
        return v0.y();
    }, ByteBufCodecs.BYTE, (v0) -> {
        return v0.rot();
    }, ComponentSerialization.OPTIONAL_STREAM_CODEC, (v0) -> {
        return v0.name();
    }, (v1, v2, v3, v4, v5) -> {
        return new MapDecoration(v1, v2, v3, v4, v5);
    });

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MapDecoration.class), MapDecoration.class, "type;x;y;rot;name", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->type:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->x:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->y:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->rot:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MapDecoration.class), MapDecoration.class, "type;x;y;rot;name", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->type:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->x:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->y:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->rot:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MapDecoration.class, Object.class), MapDecoration.class, "type;x;y;rot;name", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->type:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->x:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->y:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->rot:B", "FIELD:Lnet/minecraft/world/level/saveddata/maps/MapDecoration;->name:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Holder<MapDecorationType> type() {
        return this.type;
    }

    public byte x() {
        return this.x;
    }

    public byte y() {
        return this.y;
    }

    public byte rot() {
        return this.rot;
    }

    public Optional<Component> name() {
        return this.name;
    }

    public MapDecoration(Holder<MapDecorationType> $$0, byte $$1, byte $$2, byte $$3, Optional<Component> $$4) {
        this.type = $$0;
        this.x = $$1;
        this.y = $$2;
        this.rot = (byte) ($$3 & 15);
        this.name = $$4;
    }

    public Identifier getSpriteLocation() {
        return this.type.value().assetId();
    }

    public boolean renderOnFrame() {
        return this.type.value().showOnItemFrame();
    }
}
