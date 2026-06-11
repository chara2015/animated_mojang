package net.labymod.v1_12_2.client.renderer.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.CastUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/renderer/entity/VersionedShopItemLayer.class */
public class VersionedShopItemLayer implements ccg<bua> {
    private final bqj playerModel;
    private final ShopLayerVisualizer visualizer = new ShopLayerVisualizer();

    public VersionedShopItemLayer(cct renderPlayer) {
        this.playerModel = renderPlayer.h();
    }

    /* JADX INFO: renamed from: doRenderLayer, reason: merged with bridge method [inline-methods] */
    public void a(bua player, float f, float f1, float partialTicks, float f3, float f4, float f5, float f6) {
        if (bib.z().ac().isGlowing() || !Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get().booleanValue()) {
            return;
        }
        LabyMod labyMod = LabyMod.getInstance();
        Stack stack = VersionedStackProvider.DEFAULT_STACK;
        int lightCoords = MinecraftUtil.getPackedLight(player, partialTicks);
        labyMod.gfxRenderPipeline().renderEnvironmentContext().setPackedLight(lightCoords);
        this.visualizer.renderLayerForPlayer(stack, (Player) CastUtil.cast(player), (PlayerModel) CastUtil.cast(this.playerModel), lightCoords, partialTicks);
    }

    public boolean a() {
        return false;
    }
}
