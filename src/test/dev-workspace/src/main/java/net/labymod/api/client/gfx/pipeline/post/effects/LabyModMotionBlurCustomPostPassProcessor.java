package net.labymod.api.client.gfx.pipeline.post.effects;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor;
import net.labymod.api.client.gfx.pipeline.post.PostPassData;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.laby3d.shaders.block.CustomPostProcessorUniformBlock;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/effects/LabyModMotionBlurCustomPostPassProcessor.class */
public final class LabyModMotionBlurCustomPostPassProcessor implements CustomPostPassProcessor {
    private final Matrix4f currentViewProjection = new Matrix4f();
    private final Matrix4f prevViewProjection = new Matrix4f();
    private final Matrix4f invViewProjection = new Matrix4f();
    private boolean firstFrame = true;
    private final GameTransformations transformations = Laby.references().gameTransformations();
    private final MotionBlurConfig config = Laby.labyAPI().config().ingame().motionBlur();

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    @Override // net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor
    public void process(PostPassData data, CommandBuffer cmd, float time) throws RenderDeviceException {
        if (!data.name().equals("main")) {
            return;
        }
        Matrix4f view = this.transformations.viewMatrix();
        Matrix4f projection = this.transformations.projectionMatrix();
        projection.mul(view, this.currentViewProjection);
        this.currentViewProjection.invert(this.invViewProjection);
        CustomPostProcessorUniformBlock block = data.getBlock("MotionBlur");
        if (this.firstFrame) {
            this.prevViewProjection.set(this.currentViewProjection);
            this.firstFrame = false;
        }
        block.getProperty("BlurEnabled").set(Float.valueOf(this.config.enabled().get().booleanValue() ? 1.0f : 0.0f));
        block.getProperty("PrevViewProj").set(this.prevViewProjection);
        block.getProperty("InvViewProj").set(this.invViewProjection);
        block.getProperty("BlurStrength").set(Float.valueOf(this.config.blurStrength().get().floatValue() / 100.0f));
        block.getProperty("BlurQuality").set(this.config.blurQuality().get());
        Perspective perspective = Laby.labyAPI().minecraft().options().perspective();
        float centerExclusion = perspective == Perspective.FIRST_PERSON ? 0.0f : 0.25f;
        block.getProperty("CenterExclusion").set(Float.valueOf(centerExclusion));
        this.prevViewProjection.set(this.currentViewProjection);
    }
}
