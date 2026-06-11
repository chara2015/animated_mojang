package net.minecraft.network.chat.contents.objects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.world.item.component.ResolvableProfile;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/objects/PlayerSprite.class */
public final class PlayerSprite extends Record implements ObjectInfo {
    private final ResolvableProfile player;
    private final boolean hat;
    public static final MapCodec<PlayerSprite> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ResolvableProfile.CODEC.fieldOf("player").forGetter((v0) -> {
            return v0.player();
        }), Codec.BOOL.optionalFieldOf(PartNames.HAT, true).forGetter((v0) -> {
            return v0.hat();
        })).apply($$0, (v1, v2) -> {
            return new PlayerSprite(v1, v2);
        });
    });

    public PlayerSprite(ResolvableProfile $$0, boolean $$1) {
        this.player = $$0;
        this.hat = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PlayerSprite.class), PlayerSprite.class, "player;hat", "FIELD:Lnet/minecraft/network/chat/contents/objects/PlayerSprite;->player:Lnet/minecraft/world/item/component/ResolvableProfile;", "FIELD:Lnet/minecraft/network/chat/contents/objects/PlayerSprite;->hat:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PlayerSprite.class), PlayerSprite.class, "player;hat", "FIELD:Lnet/minecraft/network/chat/contents/objects/PlayerSprite;->player:Lnet/minecraft/world/item/component/ResolvableProfile;", "FIELD:Lnet/minecraft/network/chat/contents/objects/PlayerSprite;->hat:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PlayerSprite.class, Object.class), PlayerSprite.class, "player;hat", "FIELD:Lnet/minecraft/network/chat/contents/objects/PlayerSprite;->player:Lnet/minecraft/world/item/component/ResolvableProfile;", "FIELD:Lnet/minecraft/network/chat/contents/objects/PlayerSprite;->hat:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ResolvableProfile player() {
        return this.player;
    }

    public boolean hat() {
        return this.hat;
    }

    @Override // net.minecraft.network.chat.contents.objects.ObjectInfo
    public FontDescription fontDescription() {
        return new FontDescription.PlayerSprite(this.player, this.hat);
    }

    @Override // net.minecraft.network.chat.contents.objects.ObjectInfo
    public String description() {
        return (String) this.player.name().map($$0 -> {
            return "[" + $$0 + " head]";
        }).orElse("[unknown player head]");
    }

    @Override // net.minecraft.network.chat.contents.objects.ObjectInfo
    public MapCodec<PlayerSprite> codec() {
        return MAP_CODEC;
    }
}
