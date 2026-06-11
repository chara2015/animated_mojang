package net.labymod.v1_18_2.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.CastUtil;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/renderer/entity/layers/VersionedShopItemLayer.class */
public class VersionedShopItemLayer extends ezo<ept, ekm<ept>> {
    private final ekm<ept> playerModel;
    private final ShopLayerVisualizer visualizer;

    public VersionedShopItemLayer(exe<ept, ekm<ept>> layerParent) {
        super(layerParent);
        this.playerModel = layerParent.a();
        this.visualizer = new ShopLayerVisualizer();
    }

    /* JADX INFO: renamed from: render, reason: merged with bridge method [inline-methods] */
    public void a(@NotNull dtm poseStack, @NotNull eqs bufferSource, int lightCoords, @NotNull ept player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get().booleanValue()) {
            return;
        }
        Stack stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        Player apiPlayer = (Player) player;
        if (bufferSource instanceof a) {
            a source = (a) bufferSource;
            source.a();
        }
        this.visualizer.renderLayerForPlayer(stack, apiPlayer, (PlayerModel) CastUtil.cast(this.playerModel), lightCoords, partialTicks);
    }
}
