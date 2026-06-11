package net.labymod.core.main.user.shop.item.positionprovider;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/positionprovider/MinecraftItemPositionProvider.class */
public interface MinecraftItemPositionProvider {
    public static final float UNIT = 0.0625f;

    void apply(Stack stack, PlayerModel playerModel);
}
