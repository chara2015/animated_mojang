package net.labymod.v26_1_1.mixins.client.renderer.entity.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.util.CastUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.entity.state.TntRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/renderer/entity/entity/MixinTntRenderer.class */
@Mixin({TntRenderer.class})
public abstract class MixinTntRenderer extends EntityRenderer<PrimedTnt, TntRenderState> {
    protected MixinTntRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = {"submit(Lnet/minecraft/client/renderer/entity/state/TntRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V"}, at = {@At("TAIL")})
    public void render(TntRenderState $$0, PoseStack poseStack, SubmitNodeCollector $$2, CameraRenderState $$3, CallbackInfo ci) {
        Vec3 position = $$0.nameTagAttachment;
        poseStack.pushPose();
        if (position != null) {
            poseStack.translate(position.x(), position.y() + 0.5d, position.z());
        }
        poseStack.mulPose($$3.orientation);
        poseStack.scale(0.025f, -0.025f, 0.025f);
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        Laby.labyAPI().tagRegistry().render(stack, (EntitySnapshot) CastUtil.requireInstanceOf($$0, EntitySnapshot.class), 0.0f, TagType.MAIN_TAG);
        poseStack.popPose();
    }
}
