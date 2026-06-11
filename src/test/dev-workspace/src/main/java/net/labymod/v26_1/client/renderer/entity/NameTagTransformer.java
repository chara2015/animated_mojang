package net.labymod.v26_1.client.renderer.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.concurrent.atomic.AtomicReference;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.RenderUtil;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/renderer/entity/NameTagTransformer.class */
public class NameTagTransformer {
    private static final NameTagRenderer TAG_RENDERER = new NameTagRenderer();

    public static void transform(@NotNull SubmitNodeCollector instance, @NotNull PoseStack poseStack, @NotNull Vec3 vec3, int i0, @NotNull Component component, boolean b, int i1, double v, @NotNull CameraRenderState cameraRenderState, @NotNull Operation<Void> original, @NotNull EntityRenderState entityRenderState) {
        poseStack.pushPose();
        AtomicReference<Component> displayName = new AtomicReference<>(component);
        if (TAG_RENDERER.transformNameTagContent(((VanillaStackAccessor) poseStack).stack(), (EntitySnapshot) CastUtil.requireInstanceOf(entityRenderState, EntitySnapshot.class), displayName.get(), newDisplayName -> {
            displayName.set((Component) newDisplayName);
        })) {
            return;
        }
        MinecraftUtil.setNameTagRenderState(entityRenderState);
        original.call(new Object[]{instance, poseStack, vec3, Integer.valueOf(i0), displayName.get(), Boolean.valueOf(b), Integer.valueOf(i1), Double.valueOf(v), cameraRenderState});
        RenderUtil.setNameTagType(TagType.INVALID);
        MinecraftUtil.setNameTagRenderState(null);
        poseStack.popPose();
    }
}
