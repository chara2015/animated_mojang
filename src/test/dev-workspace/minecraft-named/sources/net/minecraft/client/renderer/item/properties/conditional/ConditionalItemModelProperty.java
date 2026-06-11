package net.minecraft.client.renderer.item.properties.conditional;

import com.mojang.serialization.MapCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/conditional/ConditionalItemModelProperty.class */
public interface ConditionalItemModelProperty extends ItemModelPropertyTest {
    MapCodec<? extends ConditionalItemModelProperty> type();
}
