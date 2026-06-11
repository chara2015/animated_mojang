package net.labymod.v1_21_11.mixins.client.renderer.entity.player;

import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.v1_21_11.client.render.LivingEntityRendererAccessor;
import net.labymod.v1_21_11.client.renderer.entity.layers.VersionedShopItemLayer;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/entity/player/MixinPlayerRenderer.class */
@Mixin({AvatarRenderer.class})
public class MixinPlayerRenderer {
    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$addNewLayers(EntityRendererProvider.Context context, boolean slim, CallbackInfo ci) {
        LivingEntityRendererAccessor livingEntityRendererAccessor = (AvatarRenderer) this;
        livingEntityRendererAccessor.addCustomLayer(new VersionedShopItemLayer(livingEntityRendererAccessor));
    }

    @Inject(method = {"renderHand"}, at = {@At("HEAD")})
    private void labyMod$preHandRender(PoseStack poseStack, SubmitNodeCollector collector, int packedLightCoords, Identifier skinTexture, ModelPart modelPart, boolean secondLayer, CallbackInfo ci) {
        Entity cameraEntity = Minecraft.getInstance().getCameraEntity();
        if (!(cameraEntity instanceof AbstractClientPlayer)) {
            return;
        }
        AbstractClientPlayer player = (AbstractClientPlayer) cameraEntity;
        labyMod$firePlayerModelRenderEvent(player, poseStack, collector, modelPart, Phase.PRE, packedLightCoords);
    }

    @Inject(method = {"renderHand"}, at = {@At("TAIL")})
    private void labyMod$postHandRender(PoseStack poseStack, SubmitNodeCollector collector, int packedLightCoords, Identifier skinTexture, ModelPart modelPart, boolean secondLayer, CallbackInfo ci) {
        Entity cameraEntity = Minecraft.getInstance().getCameraEntity();
        if (!(cameraEntity instanceof AbstractClientPlayer)) {
            return;
        }
        AbstractClientPlayer player = (AbstractClientPlayer) cameraEntity;
        labyMod$firePlayerModelRenderEvent(player, poseStack, collector, modelPart, Phase.POST, packedLightCoords);
    }

    private void labyMod$firePlayerModelRenderEvent(AbstractClientPlayer clientPlayer, PoseStack poseStack, SubmitNodeCollector collector, ModelPart modelPart, Phase phase, int packedLight) {
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        DefaultStackProvider provider = new DefaultStackProvider();
        Stack modelStack = Stack.create(provider);
        modelStack.mul(stack.getProvider().getPose());
        PlayerModel playerModel = (net.minecraft.client.model.player.PlayerModel) ((AvatarRenderer) this).getModel();
        MainHand hand = ((net.minecraft.client.model.player.PlayerModel) playerModel).leftArm.equals(modelPart) ? MainHand.LEFT : MainHand.RIGHT;
        PlayerModelRenderHandEvent event = new PlayerModelRenderHandEvent((Player) clientPlayer, playerModel, modelStack, phase, hand, packedLight);
        if (phase == Phase.PRE) {
            MinecraftUtil.prePlayerModelRenderHandEvent = event;
        } else {
            MinecraftUtil.postPlayerModelRenderHandEvent = event;
        }
    }
}
