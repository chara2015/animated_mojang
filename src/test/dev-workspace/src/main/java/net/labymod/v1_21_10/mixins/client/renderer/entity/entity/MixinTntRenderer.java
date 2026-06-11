package net.labymod.v1_21_10.mixins.client.renderer.entity.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.util.CastUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/entity/entity/MixinTntRenderer.class */
@Mixin({hqq.class})
public abstract class MixinTntRenderer extends hnx<cvs, hws> {
    protected MixinTntRenderer(a context) {
        super(context);
    }

    @Inject(method = {"submit(Lnet/minecraft/client/renderer/entity/state/TntRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V"}, at = {@At("TAIL")})
    public void render(hws $$0, fua poseStack, hgy $$2, ibo $$3, CallbackInfo ci) {
        foh position = $$0.T;
        poseStack.a();
        if (position != null) {
            poseStack.a(position.a(), position.b() + 0.5d, position.c());
        }
        poseStack.a($$3.e);
        poseStack.b(0.025f, -0.025f, 0.025f);
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        Laby.labyAPI().tagRegistry().render(stack, (EntitySnapshot) CastUtil.requireInstanceOf($$0, EntitySnapshot.class), 0.0f, TagType.MAIN_TAG);
        poseStack.b();
    }
}
