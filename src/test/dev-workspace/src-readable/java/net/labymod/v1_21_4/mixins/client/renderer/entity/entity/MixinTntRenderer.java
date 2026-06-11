package net.labymod.v1_21_4.mixins.client.renderer.entity.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/renderer/entity/entity/MixinTntRenderer.class */
@Mixin({guw.class})
public abstract class MixinTntRenderer extends gse<cle, har> {
    protected MixinTntRenderer(a context) {
        super(context);
    }

    @Inject(method = {"render(Lnet/minecraft/client/renderer/entity/state/TntRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At("TAIL")})
    public void render(har state, ffv poseStack, glz bufferSource, int packedLight, CallbackInfo callbackInfo) {
        poseStack.a();
        fbb position = state.E;
        if (position != null) {
            poseStack.a(position.a(), position.b() + 0.5d, position.c());
        }
        poseStack.a(this.e.b());
        poseStack.b(0.025f, -0.025f, 0.025f);
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.labyAPI().tagRegistry().render(stack, (EntitySnapshot) CastUtil.requireInstanceOf(state, EntitySnapshot.class), 0.0f, TagType.MAIN_TAG);
        poseStack.b();
    }
}
