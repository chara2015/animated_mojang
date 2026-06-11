package net.labymod.core.main.user.shop.cosmetic.pet;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/PetAI.class */
public interface PetAI {
    void earlyRender(PlayerModel playerModel, Player player, PetDataStorage petDataStorage, Stack stack, float f);

    void tick(PlayerModel playerModel, Player player, PetDataStorage petDataStorage, float f, float f2);

    default boolean canAttach() {
        return true;
    }
}
