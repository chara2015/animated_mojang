package net.minecraft.network.chat.contents.objects;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.FontDescription;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/objects/ObjectInfo.class */
public interface ObjectInfo {
    FontDescription fontDescription();

    String description();

    MapCodec<? extends ObjectInfo> codec();
}
