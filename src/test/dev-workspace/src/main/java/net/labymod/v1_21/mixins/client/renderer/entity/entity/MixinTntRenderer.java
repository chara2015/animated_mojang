package net.labymod.v1_21.mixins.client.renderer.entity.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/renderer/entity/entity/MixinTntRenderer.class */
@Mixin({gmz.class})
public abstract class MixinTntRenderer extends gki<cji> {
    protected MixinTntRenderer(a context) {
        super(context);
    }

    @Inject(method = {"render(Lnet/minecraft/world/entity/item/PrimedTnt;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = {@At("TAIL")})
    public void render(cji entity, float f1, float partialTicks, fbi poseStack, gez bufferSource, int packedLight, CallbackInfo callbackInfo) {
        exc position;
        if (!b(entity) && this.d.b(entity) < 2048.0d && (position = entity.dl().a(bss.c, 0, packedLight)) != null) {
            poseStack.a();
            poseStack.a(position.a(), position.b() + 0.5d, position.c());
            poseStack.a(this.d.b());
            poseStack.b(0.025f, -0.025f, 0.025f);
            Stack stack = ((VanillaStackAccessor) poseStack).stack();
            Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
            AbstractLiveEntitySnapshot snapshot = ((EntitySnapshotCreator) entity).createSnapshot(partialTicks);
            Laby.labyAPI().tagRegistry().render(stack, snapshot, 0.0f, TagType.MAIN_TAG);
            poseStack.b();
        }
    }
}
