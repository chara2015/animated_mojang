package net.labymod.v1_8_9.client.renderer.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.CastUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/renderer/entity/VersionedShopItemLayer.class */
public class VersionedShopItemLayer implements blb<bet> {
    private final bbr playerModel;
    private final ShopLayerVisualizer visualizer = new ShopLayerVisualizer();

    public VersionedShopItemLayer(bln renderPlayer) {
        this.playerModel = renderPlayer.g();
    }

    /* JADX INFO: renamed from: doRenderLayer, reason: merged with bridge method [inline-methods] */
    public void a(bet player, float f, float f1, float partialTicks, float f3, float f4, float f5, float f6) {
        if (!Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get().booleanValue()) {
            return;
        }
        LabyMod labyMod = LabyMod.getInstance();
        Stack stack = VersionedStackProvider.DEFAULT_STACK;
        int lightCoords = MinecraftUtil.getPackedLight(player, partialTicks);
        labyMod.gfxRenderPipeline().renderEnvironmentContext().setPackedLight(lightCoords);
        this.visualizer.renderLayerForPlayer(stack, (Player) CastUtil.cast(player), (PlayerModel) CastUtil.cast(this.playerModel), lightCoords, partialTicks);
    }

    public boolean b() {
        return false;
    }
}
