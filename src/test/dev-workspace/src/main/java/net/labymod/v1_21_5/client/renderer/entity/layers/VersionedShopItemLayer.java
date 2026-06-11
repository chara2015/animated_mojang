package net.labymod.v1_21_5.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.CastUtil;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/renderer/entity/layers/VersionedShopItemLayer.class */
public class VersionedShopItemLayer extends hcj<hfq, git> {
    private final git playerModel;
    private final ShopLayerVisualizer visualizer;

    public VersionedShopItemLayer(gzs<hfq, git> layerParent) {
        super(layerParent);
        this.playerModel = layerParent.c();
        this.visualizer = new ShopLayerVisualizer();
    }

    /* JADX INFO: renamed from: render, reason: merged with bridge method [inline-methods] */
    public void a(@NotNull fld poseStack, @NotNull grn bufferSource, int packedLightCoords, @NotNull hfq state, float yRot, float xRot) {
        gqj gqjVarLabyMod$getEntity;
        EntityRenderStateAccessor<gqj> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (gqjVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null || !Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get().booleanValue()) {
            return;
        }
        Stack stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        Player apiPlayer = (Player) gqjVarLabyMod$getEntity;
        if (bufferSource instanceof a) {
            a source = (a) bufferSource;
            source.a();
        }
        this.visualizer.renderLayerForPlayer(stack, apiPlayer, (PlayerModel) CastUtil.cast(this.playerModel), packedLightCoords, Laby.labyAPI().minecraft().getPartialTicks());
    }
}
