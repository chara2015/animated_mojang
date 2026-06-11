package net.labymod.v1_18_2.mixins.client.renderer.entity.nametag;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import net.labymod.core.client.render.state.entity.EntitySnapshotCreator;
import net.labymod.core.client.render.state.entity.mutable.AbstractLiveEntitySnapshot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/entity/nametag/MixinEntityRendererNameTag.class */
@Mixin({evj.class})
public class MixinEntityRendererNameTag {

    @Shadow
    @Final
    private dzp a;

    @Unique
    private final NameTagRenderer labyMod$nameTagRenderer = new NameTagRenderer();

    @Inject(method = {"renderNameTag"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$transformNameTag(axk entity, qk displayName, dtm stack, eqs bufferSource, int packedLight, CallbackInfo ci, @Local(argsOnly = true) LocalRef<qk> displayNameRef) {
        AbstractLiveEntitySnapshot snapshot = ((EntitySnapshotCreator) entity).createSnapshot(Laby.labyAPI().minecraft().getPartialTicks());
        if (this.labyMod$nameTagRenderer.transformNameTagContent(((VanillaStackAccessor) stack).stack(), snapshot, displayName, newDisplayName -> {
            displayNameRef.set((qk) newDisplayName);
        })) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderNameTag"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;pose()Lcom/mojang/math/Matrix4f;", shift = At.Shift.BEFORE)})
    private void labyMod$renderCustomNameTags(axk entity, qk displayName, dtm stack, eqs bufferSource, int packedLight, CallbackInfo ci) {
        Stack apiStack = ((VanillaStackAccessor) stack).stack();
        AbstractLiveEntitySnapshot snapshot = ((EntitySnapshotCreator) entity).createSnapshot(Laby.labyAPI().minecraft().getPartialTicks());
        this.labyMod$nameTagRenderer.renderNameTags(apiStack, snapshot, this.a.a(displayName) + 2, packedLight);
    }

    @WrapOperation(method = {"renderNameTag"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I", ordinal = 0)})
    private int labyMod$applyCustomBackgroundColor(dzp instance, qk component, float x, float y, int color, boolean shadow, d modelMatrix, eqs bufferSource, boolean discrete, int backgroundColor, int packedLightCoords, Operation<Integer> original) {
        return ((Integer) original.call(new Object[]{instance, component, Float.valueOf(x), Float.valueOf(y), Integer.valueOf(color), Boolean.valueOf(shadow), modelMatrix, bufferSource, Boolean.valueOf(discrete), Integer.valueOf(this.labyMod$nameTagRenderer.getNameTagBackgroundColor(ColorFormat.ARGB32.normalizedAlpha(backgroundColor))), Integer.valueOf(packedLightCoords)})).intValue();
    }

    @Inject(method = {"renderNameTag"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE)})
    private void labyMod$resetNameTagType(axk entity, qk displayName, dtm stack, eqs bufferSource, int packedLight, CallbackInfo ci) {
        this.labyMod$nameTagRenderer.resetTag();
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE)})
    private void labyMod$setEntityNameTagType(axk entity, float $$1, float $$2, dtm $$3, eqs $$4, int $$5, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }
}
