package net.labymod.v1_21_10.client.renderer.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import java.util.concurrent.atomic.AtomicReference;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.RenderUtil;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/renderer/entity/NameTagTransformer.class */
public class NameTagTransformer {
    private static final NameTagRenderer TAG_RENDERER = new NameTagRenderer();

    public static void transform(@NotNull hgy instance, @NotNull fua poseStack, @NotNull foh vec3, int i0, @NotNull xx component, boolean b, int i1, double v, @NotNull ibo cameraRenderState, @NotNull Operation<Void> original, @NotNull huk entityRenderState) {
        poseStack.a();
        AtomicReference<xx> displayName = new AtomicReference<>(component);
        if (TAG_RENDERER.transformNameTagContent(((VanillaStackAccessor) poseStack).stack(), (EntitySnapshot) CastUtil.requireInstanceOf(entityRenderState, EntitySnapshot.class), displayName.get(), newDisplayName -> {
            displayName.set((xx) newDisplayName);
        })) {
            return;
        }
        MinecraftUtil.setNameTagRenderState(entityRenderState);
        original.call(new Object[]{instance, poseStack, vec3, Integer.valueOf(i0), displayName.get(), Boolean.valueOf(b), Integer.valueOf(i1), Double.valueOf(v), cameraRenderState});
        RenderUtil.setNameTagType(TagType.INVALID);
        MinecraftUtil.setNameTagRenderState(null);
        poseStack.b();
    }
}
