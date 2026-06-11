package net.labymod.v1_21_3.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.CastUtil;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/renderer/entity/layers/VersionedShopItemLayer.class */
public class VersionedShopItemLayer extends gwf<gzg, gcr> {
    private final gcr playerModel;
    private final ShopLayerVisualizer visualizer;

    public VersionedShopItemLayer(gtn<gzg, gcr> layerParent) {
        super(layerParent);
        this.playerModel = layerParent.c();
        this.visualizer = new ShopLayerVisualizer();
    }

    /* JADX INFO: renamed from: render, reason: merged with bridge method [inline-methods] */
    public void a(@NotNull fgs poseStack, @NotNull gll bufferSource, int packedLightCoords, @NotNull gzg state, float yRot, float xRot) {
        gke gkeVarLabyMod$getEntity;
        EntityRenderStateAccessor<gke> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (gkeVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null || !Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get().booleanValue()) {
            return;
        }
        Stack stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        Player apiPlayer = (Player) gkeVarLabyMod$getEntity;
        if (bufferSource instanceof a) {
            a source = (a) bufferSource;
            source.a();
        }
        this.visualizer.renderLayerForPlayer(stack, apiPlayer, (PlayerModel) CastUtil.cast(this.playerModel), packedLightCoords, Laby.labyAPI().minecraft().getPartialTicks());
    }
}
