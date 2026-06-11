package net.labymod.v1_20_4.mixins.client.renderer.entity.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.client.render.state.entity.EntitySnapshotCreator;
import net.labymod.core.client.render.state.entity.mutable.AbstractLiveEntitySnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/renderer/entity/entity/MixinTntRenderer.class */
@Mixin({gbc.class})
public abstract class MixinTntRenderer extends fym<cbv> {
    protected MixinTntRenderer(a context) {
        super(context);
    }

    @Inject(method = {"render(Lnet/minecraft/world/entity/item/PrimedTnt;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At("TAIL")})
    public void render(cbv entity, float f1, float f2, eqb poseStack, fth bufferSource, int packedLight, CallbackInfo callbackInfo) {
        if (b(entity) || this.c.b(entity) >= 2048.0d) {
            return;
        }
        poseStack.a();
        poseStack.a(0.0d, entity.dh() + 0.5f, 0.0d);
        poseStack.a(this.c.b());
        poseStack.b(-0.025f, -0.025f, 0.025f);
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        AbstractLiveEntitySnapshot snapshot = ((EntitySnapshotCreator) entity).createSnapshot(Laby.labyAPI().minecraft().getPartialTicks());
        Laby.labyAPI().tagRegistry().render(stack, snapshot, 0.0f, TagType.MAIN_TAG);
        poseStack.b();
    }
}
