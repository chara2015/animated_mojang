package net.labymod.core.event.client.render.item;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/item/PlayerItemRenderContextEventCaller.class */
public class PlayerItemRenderContextEventCaller {
    public static PlayerItemRenderContextEvent call(Stack stack, ItemStack itemStack, ModelTransformType transformType, Player player, PlayerModel playerModel, int packedLight) {
        return (PlayerItemRenderContextEvent) Laby.fireEvent(new PlayerItemRenderContextEvent(stack, itemStack, transformType, player, playerModel, packedLight, Laby.labyAPI().minecraft().getPartialTicks()));
    }
}
