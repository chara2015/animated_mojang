package net.labymod.v1_21_5.mixins.client.renderer.entity.nametag;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/entity/nametag/MixinEntityRendererNameTag.class */
@Mixin({gxu.class})
public class MixinEntityRendererNameTag {

    @Shadow
    @Final
    private fti a;

    @Unique
    private final NameTagRenderer labyMod$nameTagRenderer = new NameTagRenderer();

    @Inject(method = {"renderNameTag"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$transformNameTag(hec state, xg displayName, fld stack, grn bufferSource, int $$4, CallbackInfo ci, @Local(argsOnly = true) LocalRef<xg> displayNameRef) {
        if (this.labyMod$nameTagRenderer.transformNameTagContent(((VanillaStackAccessor) stack).stack(), (EntitySnapshot) CastUtil.requireInstanceOf(state, EntitySnapshot.class), displayName, newDisplayName -> {
            displayNameRef.set((xg) newDisplayName);
        })) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderNameTag"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;pose()Lorg/joml/Matrix4f;", shift = At.Shift.BEFORE)})
    private void labyMod$renderCustomNameTags(hec state, xg displayName, fld poseStack, grn $$3, int packedLightCoords, CallbackInfo ci) {
        Stack apiStack = ((VanillaStackAccessor) poseStack).stack();
        this.labyMod$nameTagRenderer.renderNameTags(apiStack, (EntitySnapshot) CastUtil.requireInstanceOf(state, EntitySnapshot.class), this.a.a(displayName) + 2, packedLightCoords);
    }

    @WrapOperation(method = {"renderNameTag"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I", ordinal = 0)})
    private int labyMod$applyCustomBackgroundColor(fti instance, xg component, float x, float y, int color, boolean shadow, Matrix4f modelMatrix, grn bufferSource, a displayMode, int backgroundColor, int packedLightCoords, Operation<Integer> original) {
        return ((Integer) original.call(new Object[]{instance, component, Float.valueOf(x), Float.valueOf(y), Integer.valueOf(color), Boolean.valueOf(shadow), modelMatrix, bufferSource, displayMode, Integer.valueOf(this.labyMod$nameTagRenderer.getNameTagBackgroundColor(ColorFormat.ARGB32.normalizedAlpha(backgroundColor))), Integer.valueOf(packedLightCoords)})).intValue();
    }

    @Inject(method = {"renderNameTag"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE)})
    private void labyMod$resetNameTagType(hec state, xg $$1, fld $$2, grn $$3, int $$4, CallbackInfo ci) {
        this.labyMod$nameTagRenderer.resetTag();
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE)})
    private void labyMod$setEntityNameTagType(hec state, fld $$1, grn $$2, int $$3, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }
}
