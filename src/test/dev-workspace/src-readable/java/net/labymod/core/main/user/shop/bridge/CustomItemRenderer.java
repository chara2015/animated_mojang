package net.labymod.core.main.user.shop.bridge;

import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.renderer.GeometrySubmitter;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.util.math.position.Position;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.cosmetic.CosmeticType;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.resource.AssetId;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/bridge/CustomItemRenderer.class */
public class CustomItemRenderer {
    private static final AssetId HAND_PASS = AssetId.of("labymod", "pass/hand");
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final ShopItemLayer shopItemLayer = LabyMod.references().shopItemLayer();
    private final Laby3D laby3D = Laby.references().laby3D();
    private final GeometrySubmitter geometrySubmitter = Laby.references().geometrySubmitter();
    private final CosmeticsConfig config = this.labyAPI.config().ingame().cosmetics();

    @Subscribe
    public void handleModelHandRender(PlayerModelRenderHandEvent event) {
        if (!isVisibleInFirstPerson() || event.phase() != Phase.POST) {
            return;
        }
        GeometrySubmitter.Type prevType = this.geometrySubmitter.getType();
        try {
            FrameProfiler.push((Supplier<String>) () -> {
                return "player_hand_cosmetics(" + event.player().getName() + ")";
            });
            this.geometrySubmitter.setType(GeometrySubmitter.Type.HAND);
            this.shopItemLayer.applyTag(ItemTags.FIRST_PERSON).filter(ItemFilters.noWalkingPets()).hand(event.hand()).render(event.stack(), event.player(), event.model(), event.getLightCoords(), this.labyAPI.minecraft().getPartialTicks());
            FrameProfiler.pop();
            CommandBuffer cmd = this.laby3D.renderDevice().createCommandBuffer(1);
            try {
                cmd.beginPass(this.laby3D.resolveDrawRenderTarget(), LoadOp.LOAD);
                this.geometrySubmitter.render(cmd, false);
                cmd.endPass();
                cmd.submit();
                if (cmd != null) {
                    cmd.close();
                }
            } finally {
            }
        } finally {
            this.geometrySubmitter.setType(prevType);
        }
    }

    @Subscribe
    public void onRenderWorld(RenderWorldEvent event) {
        if (event.phase() != Phase.POST || !isVisible()) {
            return;
        }
        boolean walkingPetsVisible = Laby.labyAPI().config().ingame().cosmetics().walkingPets().get().booleanValue();
        if (!walkingPetsVisible) {
            return;
        }
        ClientWorld level = Laby.labyAPI().minecraft().clientWorld();
        Stack stack = event.stack();
        stack.push();
        for (Player player : level.getPlayers()) {
            FrameProfiler.push((Supplier<String>) () -> {
                return "player_world_cosmetics(" + player.getName() + ")";
            });
            renderCosmetics(stack, level, player, event.getPartialTicks());
            FrameProfiler.pop();
        }
        stack.pop();
    }

    private void renderCosmetics(Stack stack, ClientWorld level, Player player, float partialTicks) {
        if (player.isInvisible()) {
            return;
        }
        ShopItemLayer layer = this.shopItemLayer;
        if (this.labyAPI.minecraft().options().perspective() == Perspective.FIRST_PERSON) {
            layer.applyTag(ItemTags.FIRST_PERSON);
        }
        if (PlatformEnvironment.isAncientOpenGL() && player.isCrouching() && !layer.hasTag(ItemTags.FIRST_PERSON)) {
            stack.translate(0.0f, -0.2f, 0.0f);
        }
        layer.applyTag(ItemTags.WORLD);
        Position position = player.position();
        layer.filter(def -> {
            return (def == null || def.cosmeticType() == CosmeticType.WALKING_PET) ? false : true;
        }).render(stack, player, player.playerModel(), level.getPackedLight(position.getX(), position.getY(), position.getZ()), partialTicks);
    }

    private boolean isVisible() {
        return this.config.renderCosmetics().get().booleanValue();
    }

    private boolean isVisibleInFirstPerson() {
        return isVisible() && this.config.showCosmeticsInFirstPerson().get().booleanValue();
    }
}
