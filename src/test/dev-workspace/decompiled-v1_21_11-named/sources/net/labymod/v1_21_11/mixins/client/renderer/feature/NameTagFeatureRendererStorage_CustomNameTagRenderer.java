package net.labymod.v1_21_11.mixins.client.renderer.feature;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/feature/NameTagFeatureRendererStorage_CustomNameTagRenderer.class */
@Mixin({NameTagFeatureRenderer.Storage.class})
public class NameTagFeatureRendererStorage_CustomNameTagRenderer {

    @Unique
    private final NameTagRenderer labyMod$nameTagRenderer = new NameTagRenderer();

    @Inject(method = {"add"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = At.Shift.AFTER)})
    private void labyMod$renderCustomNameTags(PoseStack $$0, Vec3 $$1, int y, Component $$2, boolean $$3, int $$4, double $$5, CameraRenderState renderState, CallbackInfo ci) {
        EntityRenderState nameTagRenderState = MinecraftUtil.getNameTagRenderState();
        if (nameTagRenderState == null) {
            return;
        }
        Stack apiStack = ((VanillaStackAccessor) $$0).stack();
        this.labyMod$nameTagRenderer.renderNameTags(apiStack, (EntitySnapshot) CastUtil.requireInstanceOf(nameTagRenderState, EntitySnapshot.class), Minecraft.getInstance().font.width($$2) + 2, $$4);
    }

    @WrapOperation(method = {"add"}, at = {@At(value = "NEW", target = "(Lorg/joml/Matrix4f;FFLnet/minecraft/network/chat/Component;IIID)Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;")})
    private SubmitNodeStorage.NameTagSubmit labyMod$applyCustomBackground(Matrix4f $$0, float $$1, float $$2, Component $$3, int $$4, int $$5, int backgroundColor, double $$7, Operation<SubmitNodeStorage.NameTagSubmit> original) {
        return (SubmitNodeStorage.NameTagSubmit) original.call(new Object[]{$$0, Float.valueOf($$1), Float.valueOf($$2), $$3, Integer.valueOf($$4), Integer.valueOf($$5), Integer.valueOf(this.labyMod$nameTagRenderer.getNameTagBackgroundColor(ColorFormat.ARGB32.normalizedAlpha(backgroundColor))), Double.valueOf($$7)});
    }

    @Inject(method = {"add"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;pose()Lorg/joml/Matrix4f;", shift = At.Shift.BEFORE)})
    private void labyMod$resetNameTagType(PoseStack $$0, Vec3 $$1, int y, Component $$2, boolean $$3, int $$4, double $$5, CameraRenderState renderState, CallbackInfo ci) {
        this.labyMod$nameTagRenderer.resetTag();
    }
}
