package net.labymod.api.client.gfx.pipeline.post.effects;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor;
import net.labymod.api.client.gfx.pipeline.post.PostPassData;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.laby3d.shaders.block.BlurDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.CustomPostProcessorUniformBlock;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/effects/OldMotionBlurCustomPostPassProcessor.class */
public final class OldMotionBlurCustomPostPassProcessor implements CustomPostPassProcessor {
    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    @Override // net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor
    public void process(PostPassData data, CommandBuffer cmd, float time) throws RenderDeviceException {
        String name = data.name();
        if (!name.equals("main")) {
            return;
        }
        MotionBlurConfig motionBlurConfig = Laby.labyAPI().config().ingame().motionBlur();
        ConfigProperty<MotionBlurConfig.MotionBlurType> typeProperty = motionBlurConfig.motionBlurType();
        MotionBlurConfig.MotionBlurType type = typeProperty.get();
        CustomPostProcessorUniformBlock blurDataBlock = data.getBlock(BlurDataUniformBlock.NAME);
        blurDataBlock.getProperty("BlurStrength").set(new Vector3f(type.clamp(motionBlurConfig.blurStrength().get().floatValue() / 100.0f), type == MotionBlurConfig.MotionBlurType.MAX ? 1.0f : 0.0f, motionBlurConfig.enabled().get().booleanValue() ? 1.0f : 0.0f));
    }
}
