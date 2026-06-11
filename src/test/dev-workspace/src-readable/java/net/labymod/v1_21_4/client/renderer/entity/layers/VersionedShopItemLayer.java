package net.labymod.v1_21_4.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.CastUtil;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import net.labymod.v1_21_4.client.util.EntityRenderStateAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/renderer/entity/layers/VersionedShopItemLayer.class */
public class VersionedShopItemLayer extends gwu<gzx, gdh> {
    private final gdh playerModel;
    private final ShopLayerVisualizer visualizer;

    public VersionedShopItemLayer(guc<gzx, gdh> layerParent) {
        super(layerParent);
        this.playerModel = layerParent.c();
        this.visualizer = new ShopLayerVisualizer();
    }

    /* JADX INFO: renamed from: render, reason: merged with bridge method [inline-methods] */
    public void a(@NotNull ffv poseStack, @NotNull glz bufferSource, int packedLightCoords, @NotNull gzx state, float yRot, float xRot) {
        gku gkuVarLabyMod$getEntity;
        EntityRenderStateAccessor<gku> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (gkuVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null || !Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get().booleanValue()) {
            return;
        }
        Stack stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        Player apiPlayer = (Player) gkuVarLabyMod$getEntity;
        if (bufferSource instanceof a) {
            a source = (a) bufferSource;
            source.a();
        }
        this.visualizer.renderLayerForPlayer(stack, apiPlayer, (PlayerModel) CastUtil.cast(this.playerModel), packedLightCoords, Laby.labyAPI().minecraft().getPartialTicks());
    }
}
