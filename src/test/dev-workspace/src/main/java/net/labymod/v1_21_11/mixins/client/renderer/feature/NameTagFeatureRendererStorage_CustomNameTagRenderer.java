package net.labymod.v1_21_11.mixins.client.renderer.feature;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/feature/NameTagFeatureRendererStorage_CustomNameTagRenderer.class */
@Mixin({a.class})
public class NameTagFeatureRendererStorage_CustomNameTagRenderer {

    @Unique
    private final NameTagRenderer labyMod$nameTagRenderer = new NameTagRenderer();

    @Inject(method = {"add"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = At.Shift.AFTER)})
    private void labyMod$renderCustomNameTags(fzm $$0, ftm $$1, int y, yh $$2, boolean $$3, int $$4, double $$5, ikp renderState, CallbackInfo ci) {
        idf nameTagRenderState = MinecraftUtil.getNameTagRenderState();
        if (nameTagRenderState == null) {
            return;
        }
        Stack apiStack = ((VanillaStackAccessor) $$0).stack();
        this.labyMod$nameTagRenderer.renderNameTags(apiStack, (EntitySnapshot) CastUtil.requireInstanceOf(nameTagRenderState, EntitySnapshot.class), gfj.V().g.a($$2) + 2, $$4);
    }

    @WrapOperation(method = {"add"}, at = {@At(value = "NEW", target = "(Lorg/joml/Matrix4f;FFLnet/minecraft/network/chat/Component;IIID)Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;")})
    private j labyMod$applyCustomBackground(Matrix4f $$0, float $$1, float $$2, yh $$3, int $$4, int $$5, int backgroundColor, double $$7, Operation<j> original) {
        return (j) original.call(new Object[]{$$0, Float.valueOf($$1), Float.valueOf($$2), $$3, Integer.valueOf($$4), Integer.valueOf($$5), Integer.valueOf(this.labyMod$nameTagRenderer.getNameTagBackgroundColor(ColorFormat.ARGB32.normalizedAlpha(backgroundColor))), Double.valueOf($$7)});
    }

    @Inject(method = {"add"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;pose()Lorg/joml/Matrix4f;", shift = At.Shift.BEFORE)})
    private void labyMod$resetNameTagType(fzm $$0, ftm $$1, int y, yh $$2, boolean $$3, int $$4, double $$5, ikp renderState, CallbackInfo ci) {
        this.labyMod$nameTagRenderer.resetTag();
    }
}
