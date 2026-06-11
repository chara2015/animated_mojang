package net.labymod.core.client.gui.screen.widget.widgets.model;

import java.util.UUID;
import java.util.function.Supplier;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ModelWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.util.Disposable;
import net.labymod.api.util.math.Transformation;
import net.labymod.core.client.entity.player.DummyPlayer;
import net.labymod.core.client.entity.player.DummyPlayerModel;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.bridge.ItemFilters;
import net.labymod.core.main.user.shop.bridge.ItemTags;
import net.labymod.core.main.user.shop.bridge.ShopItemLayer;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/model/CosmeticModelWidget.class */
@AutoWidget
public class CosmeticModelWidget extends ModelWidget {
    private final DummyPlayer player;
    private final ShopItemLayer layer;
    private final VanillaLayer vanillaLayer;
    private PlayerModel playerModel;

    public CosmeticModelWidget(ResourceLocation id, Model model, AnimationController animationController, float modelWidth, float modelHeight) {
        super(id, model, animationController, modelWidth, modelHeight);
        this.pivotPoint.set(0.0f, -17.5f, 0.0f);
        this.player = new DummyPlayer();
        this.layer = LabyMod.references().shopItemLayer();
        this.vanillaLayer = new VanillaLayer(this.labyAPI, false);
        this.playerModel = new DummyPlayerModel(model);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.ModelWidget
    protected void renderModelAttachments(CommandBuffer cmd, Stack stack, int lightCoords, float tickDelta) {
        ModelPart body = this.playerModel.getPart(HumanoidModel.BODY_NAME);
        if (body == null) {
            return;
        }
        stack.push();
        if (cmd != null) {
            FrameProfiler.push((Supplier<String>) () -> {
                return "player_model_attachments(" + this.player.getName() + ")";
            });
            Transformation modelPartTransformation = body.getModelPartTransform();
            modelPartTransformation.transform(stack, body.getAnimationTransformation());
            stack.translate(0.0f, -0.390625f, 0.0f);
            this.vanillaLayer.render(cmd, stack, this.player);
            float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
            this.layer.applyTag(ItemTags.ACTIVITY).filter(ItemFilters.noFilter()).render(stack, this.player, this.playerModel, lightCoords, partialTicks);
            FrameProfiler.pop();
            Laby.references().geometrySubmitter().render(cmd, true);
        }
        stack.pop();
    }

    public GameUser gameUser() {
        return this.player.gameUser();
    }

    public void updatePlayer(UUID uniqueId) {
        UUID prevUniqueId = this.player.getUniqueId();
        if (prevUniqueId.equals(uniqueId)) {
            return;
        }
        this.player.setUniqueId(uniqueId);
        setResourceLocation(this.player.skinTexture());
        rebuildModel();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.ModelWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        super.dispose();
        if (this.vanillaLayer != null) {
            this.vanillaLayer.dispose();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.ModelWidget
    public void setModel(Model model) {
        super.setModel(model);
        this.playerModel = new DummyPlayerModel(model);
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerModel playerModel() {
        return this.playerModel;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/model/CosmeticModelWidget$VanillaLayer.class */
    static class VanillaLayer implements Disposable {
        private final LabyAPI labyAPI;
        private final ModelBuffer modelBuffer;
        private final Model capeModel;
        private final boolean showElytra;

        public VanillaLayer(LabyAPI labyAPI, boolean showElytra) {
            this.labyAPI = labyAPI;
            this.showElytra = showElytra;
            this.capeModel = labyAPI.renderPipeline().modelService().loadBlockBenchModel(Constants.Resources.CLOAK_AND_ELYTRA);
            if (showElytra) {
                this.capeModel.findPart(PlayerModel.CLOAK_NAME).ifPresent(part -> {
                    part.setVisible(false);
                });
            } else {
                this.capeModel.findPart("elytra").ifPresent(part2 -> {
                    part2.setVisible(false);
                });
            }
            this.modelBuffer = new DefaultModelBuffer(this.capeModel, true);
        }

        public void render(CommandBuffer cmd, Stack stack, Player player) {
            ResourceLocation cloakTexture = player.getCloakTexture();
            if (cloakTexture == null) {
                return;
            }
            if (player instanceof DummyPlayer) {
                ((DummyPlayer) player).setWearingElytra(this.showElytra);
            }
            stack.push();
            stack.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            stack.translate(0.0f, 0.0f, -0.125f);
            stack.rotate(-12.0f, 1.0f, 0.0f, 0.0f);
            stack.translate(0.0f, 1.0f, 0.0f);
            GameUser clientUser = this.labyAPI.gameUserService().clientGameUser();
            boolean hasCloak = clientUser.hasTag(GameUser.HIDE_CAPE);
            boolean showVanillaCape = !hasCloak;
            if (hasCloak) {
                showVanillaCape = this.labyAPI.config().ingame().cosmetics().cloakPriority().get() == CloakPriority.VANILLA;
            }
            if (this.showElytra || (showVanillaCape && player.isShownModelPart(PlayerClothes.CAPE))) {
                this.modelBuffer.setResourceLocation(cloakTexture);
                this.modelBuffer.render(cmd, stack);
            }
            if (player instanceof DummyPlayer) {
                ((DummyPlayer) player).setWearingElytra(false);
            }
            stack.pop();
        }

        @Override // net.labymod.api.util.Disposable
        public void dispose() {
            this.modelBuffer.dispose();
        }
    }
}
