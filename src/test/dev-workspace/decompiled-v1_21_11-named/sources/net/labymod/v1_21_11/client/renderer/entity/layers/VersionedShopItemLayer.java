package net.labymod.v1_21_11.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.util.CastUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.bridge.ShopLayerVisualizer;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.v1_21_11.client.util.EntityRenderStateAccessor;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/renderer/entity/layers/VersionedShopItemLayer.class */
public class VersionedShopItemLayer extends RenderLayer<AvatarRenderState, PlayerModel> {
    private final PlayerModel playerModel;
    private final ShopLayerVisualizer visualizer;

    public VersionedShopItemLayer(RenderLayerParent<AvatarRenderState, PlayerModel> layerParent) {
        super(layerParent);
        this.playerModel = layerParent.getModel();
        this.visualizer = new ShopLayerVisualizer();
    }

    public void submit(PoseStack var1, SubmitNodeCollector var2, int lightCoords, AvatarRenderState var4, float var5, float var6) {
        Avatar avatarLabyMod$getEntity;
        EntityRenderStateAccessor<Avatar> playerState = EntityRenderStateAccessor.self(var4);
        if (playerState == null || (avatarLabyMod$getEntity = playerState.labyMod$getEntity()) == null || !(avatarLabyMod$getEntity instanceof Player)) {
            return;
        }
        Player apiPlayer = (Player) avatarLabyMod$getEntity;
        if (!((Boolean) Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get()).booleanValue()) {
            return;
        }
        Stack stack = ((VanillaStackAccessor) var1).stack();
        EmoteService emoteService = LabyMod.references().emoteService();
        emoteService.transformBody(stack, apiPlayer);
        this.visualizer.renderLayerForPlayer(stack, apiPlayer, (net.labymod.api.client.render.model.entity.player.PlayerModel) CastUtil.cast(this.playerModel), lightCoords, Laby.labyAPI().minecraft().getPartialTicks());
    }
}
