package com.mojang.realmsclient.dto;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mojang.util.UUIDTypeAdapter;
import java.util.UUID;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/OutboundPlayer.class */
public class OutboundPlayer implements ReflectionBasedSerialization {

    @SerializedName(JigsawBlockEntity.NAME)
    public String name;

    @SerializedName("uuid")
    @JsonAdapter(UUIDTypeAdapter.class)
    public UUID uuid;
}
