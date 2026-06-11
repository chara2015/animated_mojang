package net.minecraft.network.chat.contents.objects;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/objects/ObjectInfos.class */
public class ObjectInfos {
    private static final ExtraCodecs.LateBoundIdMapper<String, MapCodec<? extends ObjectInfo>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final MapCodec<ObjectInfo> CODEC = ComponentSerialization.createLegacyComponentMatcher(ID_MAPPER, (v0) -> {
        return v0.codec();
    }, "object");

    static {
        ID_MAPPER.put("atlas", AtlasSprite.MAP_CODEC);
        ID_MAPPER.put("player", PlayerSprite.MAP_CODEC);
    }
}
