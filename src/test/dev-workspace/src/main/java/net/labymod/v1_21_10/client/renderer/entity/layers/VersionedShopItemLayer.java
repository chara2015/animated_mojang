package net.labymod.v1_21_10.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.CastUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.v1_21_10.client.util.EntityRenderStateAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/renderer/entity/layers/VersionedShopItemLayer.class */
public class VersionedShopItemLayer extends hso<htp, gwq> {
    private final gwq playerModel;
    private final ShopLayerVisualizer visualizer;

    public VersionedShopItemLayer(hpw<htp, gwq> layerParent) {
        super(layerParent);
        this.playerModel = layerParent.c();
        this.visualizer = new ShopLayerVisualizer();
    }

    /* JADX INFO: renamed from: submit, reason: merged with bridge method [inline-methods] */
    public void a(fua poseStack, hgy collector, int lightCoords, htp renderState, float var5, float var6) {
        cdn cdnVarLabyMod$getEntity;
        EntityRenderStateAccessor<cdn> playerState = EntityRenderStateAccessor.self(renderState);
        if (playerState == null || (cdnVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null || !(cdnVarLabyMod$getEntity instanceof Player)) {
            return;
        }
        Player apiPlayer = (Player) cdnVarLabyMod$getEntity;
        if (!Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get().booleanValue()) {
            return;
        }
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        EmoteService emoteService = LabyMod.references().emoteService();
        emoteService.transformBody(stack, apiPlayer);
        this.visualizer.renderLayerForPlayer(stack, apiPlayer, (PlayerModel) CastUtil.cast(this.playerModel), lightCoords, Laby.labyAPI().minecraft().getPartialTicks());
    }
}
