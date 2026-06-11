package net.labymod.api.client.gui.screen.state;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderStates;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaDynamicTransformsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaFogUniformBlock;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaFogUniformBlockData;
import net.labymod.api.laby3d.shaders.block.vanilla.VanillaProjectionUniformBlockData;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.shaders.UniformValue;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/VanillaMeshUtil.class */
@ApiStatus.Internal
public final class VanillaMeshUtil {
    private static final boolean UBO = MinecraftVersions.V1_21_6.orNewer();

    private VanillaMeshUtil() {
    }

    public static Consumer<DrawCommandContext> applyVanillaWorkaround(Laby3D laby3D, RenderState renderState, Consumer<DrawCommandContext> consumer) {
        return consumer.andThen(context -> {
            DynamicTransformsUniformBlockData dynamicTransforms = context.dynamicTransformsData();
            applyVanillaWorkaround(laby3D, renderState, context.commandBuffer(), context.projectionData().projectionMatrix(), dynamicTransforms.modelViewMatrix(), dynamicTransforms.colorModulator());
        });
    }

    public static void applyVanillaWorkaround(Laby3D laby3D, RenderState renderState, CommandBuffer cmd, Matrix4f projectionMatrix, Matrix4f modelViewMatrix, Vector4f colorModulator) {
        if (isVanillaRenderState(renderState)) {
            if (UBO) {
                cmd.bindUniformBlock("Projection", laby3D.vanillaProjection());
                cmd.bindUniformBlockData("Projection", new VanillaProjectionUniformBlockData(projectionMatrix));
                cmd.bindUniformBlock("DynamicTransforms", laby3D.vanillaDynamicTransforms());
                VanillaDynamicTransformsUniformBlockData vanillaDynamicTransforms = new VanillaDynamicTransformsUniformBlockData();
                vanillaDynamicTransforms.modelViewMatrix().set(modelViewMatrix);
                vanillaDynamicTransforms.colorModulator().set(colorModulator);
                cmd.bindUniformBlockData("DynamicTransforms", vanillaDynamicTransforms);
                cmd.bindUniformBlock(VanillaFogUniformBlock.NAME, laby3D.vanillaFog());
                cmd.bindUniformBlockData(VanillaFogUniformBlock.NAME, new VanillaFogUniformBlockData());
                laby3D.setupOverlayAndLightingTextures(cmd);
                return;
            }
            cmd.setUniform("ProjMat", UniformValue.mat4(projectionMatrix));
            cmd.setUniform("ModelViewMat", UniformValue.mat4(modelViewMatrix));
            cmd.setUniform("ColorModulator", UniformValue.vec4(colorModulator));
            cmd.setUniform("FogShape", UniformValue.ofInt(0));
            cmd.setUniform("FogStart", UniformValue.ofFloat(Float.MAX_VALUE));
            cmd.setUniform("FogEnd", UniformValue.ofFloat(0.0f));
            cmd.setUniform("FogColor", UniformValue.vec4(0.0f, 0.0f, 0.0f, 0.0f));
            long gameTime = Laby.references().clientWorld().getGameTime();
            float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
            float time = ((gameTime % 24000) + partialTicks) / 24000.0f;
            cmd.setUniform("GameTime", UniformValue.ofFloat(time));
            laby3D.setupOverlayAndLightingTextures(cmd);
        }
    }

    private static boolean isVanillaRenderState(RenderState renderState) {
        return FontRenderStates.isVanillaRenderState(renderState);
    }
}
