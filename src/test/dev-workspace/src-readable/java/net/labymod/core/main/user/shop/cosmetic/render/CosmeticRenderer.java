package net.labymod.core.main.user.shop.cosmetic.render;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.item.geometry.effect.PhysicData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/CosmeticRenderer.class */
public interface CosmeticRenderer {
    void render(CosmeticDefinition cosmeticDefinition, RenderContext renderContext, Stack stack);

    boolean shouldRender(CosmeticDefinition cosmeticDefinition, RenderContext renderContext);

    boolean isVisibleInFirstPerson(CosmeticDefinition cosmeticDefinition);

    default void tick(CosmeticDefinition definition, Player player, GameUser user, PhysicData physicData) {
    }
}
