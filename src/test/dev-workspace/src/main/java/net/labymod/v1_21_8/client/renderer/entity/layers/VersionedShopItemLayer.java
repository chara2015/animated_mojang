package net.labymod.v1_21_8.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import net.labymod.v1_21_8.client.util.EntityRenderStateAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/renderer/entity/layers/VersionedShopItemLayer.class */
public class VersionedShopItemLayer extends hit<hmc, gop> {
    private final gop playerModel;
    private final ShopLayerVisualizer visualizer;

    public VersionedShopItemLayer(hgc<hmc, gop> layerParent) {
        super(layerParent);
        this.playerModel = layerParent.c();
        this.visualizer = new ShopLayerVisualizer();
    }

    /* JADX INFO: renamed from: render, reason: merged with bridge method [inline-methods] */
    public void a(@NotNull fod poseStack, @NotNull gxn bufferSource, int lightCoords, @NotNull hmc state, float yRot, float xRot) {
        gwf gwfVarLabyMod$getEntity;
        EntityRenderStateAccessor<gwf> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (gwfVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null || !Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get().booleanValue()) {
            return;
        }
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        Player apiPlayer = (Player) gwfVarLabyMod$getEntity;
        PlayerModel playerModel = this.playerModel;
        if (bufferSource instanceof a) {
            a source = (a) bufferSource;
            source.a();
        }
        this.visualizer.renderLayerForPlayer(stack, apiPlayer, playerModel, lightCoords, Laby.labyAPI().minecraft().getPartialTicks());
    }
}
