package net.labymod.api.client.gfx.pipeline.post.processors;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.animation.easing.Easings;
import net.labymod.api.client.animation.keyframe.AnimationChannel;
import net.labymod.api.client.animation.keyframe.Keyframe;
import net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor;
import net.labymod.api.client.gfx.pipeline.post.PostEffects;
import net.labymod.api.client.gfx.pipeline.post.PostPassData;
import net.labymod.api.client.gfx.pipeline.post.PostProcessor;
import net.labymod.api.client.gfx.pipeline.post.PostProcessorLoader;
import net.labymod.api.client.gfx.pipeline.post.effects.LabyModMotionBlurCustomPostPassProcessor;
import net.labymod.api.client.gfx.pipeline.post.effects.OldMotionBlurCustomPostPassProcessor;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.laby3d.shaders.block.CustomPostProcessorUniformBlock;
import net.labymod.api.util.Lazy;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/processors/PostProcessors.class */
public final class PostProcessors {
    private static final Lazy<PostProcessor> MENU_BLUR_POST_PROCESSOR = Lazy.of(() -> {
        LabyAPI labyAPI = Laby.labyAPI();
        PostProcessor processor = PostProcessorLoader.load(Laby.labyAPI().minecraft().mainTarget(), PostEffects.MENU_BLUR);
        processor.setCustomPostPassProcessor(new ConfigurableMenuBlurCustomPostPassProcessor(labyAPI.config().appearance().menuBlur()));
        return processor;
    });
    private static final Lazy<PostProcessor> MOTION_BLUR_POST_PROCESSOR = Lazy.of(() -> {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        PostProcessor processor = PostProcessorLoader.load(minecraft.mainTarget(), PostEffects.LABYMOD_MOTION_BLUR);
        processor.setCustomPostPassProcessor(new LabyModMotionBlurCustomPostPassProcessor());
        return processor;
    });
    private static final Lazy<PostProcessor> OLD_BLUR_POST_PROCESSOR = Lazy.of(() -> {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        PostProcessor processor = PostProcessorLoader.load(minecraft.mainTarget(), PostEffects.OLD_MOTION_BLUR);
        processor.setCustomPostPassProcessor(new OldMotionBlurCustomPostPassProcessor());
        return processor;
    });
    private static final AnimationChannel<Float> BLUR_PROGRESS_CHANNEL = new AnimationChannel(Float.class).addKeyframe(new Keyframe(0, Float.valueOf(0.0f))).addKeyframe(new Keyframe(300, Float.valueOf(1.0f), Easings.EASE_OUT));
    private static long menuBlurStartTime = -1;

    private PostProcessors() {
        throw new IllegalStateException();
    }

    public static void resetMenuBlur() {
        menuBlurStartTime = System.currentTimeMillis();
    }

    public static void processMenuBlur(MenuBlurConfig.ScreenType type, ScreenContext context) {
        MenuBlurConfig menuBlurConfig = Laby.labyAPI().config().appearance().menuBlur();
        if (!menuBlurConfig.isMenuBlurEnabled(type)) {
            return;
        }
        PostProcessor processor = MENU_BLUR_POST_PROCESSOR.get();
        CustomPostPassProcessor customPostPassProcessor = processor.getCustomPostPassProcessor();
        if (customPostPassProcessor instanceof ConfigurableMenuBlurCustomPostPassProcessor) {
            ConfigurableMenuBlurCustomPostPassProcessor pass = (ConfigurableMenuBlurCustomPostPassProcessor) customPostPassProcessor;
            long elapsed = menuBlurStartTime < 0 ? 300L : System.currentTimeMillis() - menuBlurStartTime;
            float progress = BLUR_PROGRESS_CHANNEL.evaluate(elapsed).floatValue();
            pass.setProgress(progress);
        }
        processor.process(context);
    }

    public static void processMotionBlur(float partialTicks) {
        MotionBlurConfig.MotionBlurType type = Laby.labyAPI().config().ingame().motionBlur().motionBlurType().get();
        PostProcessor processor = type.isOld() ? OLD_BLUR_POST_PROCESSOR.get() : MOTION_BLUR_POST_PROCESSOR.get();
        processor.process(partialTicks);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/processors/PostProcessors$ConfigurableMenuBlurCustomPostPassProcessor.class */
    private static class ConfigurableMenuBlurCustomPostPassProcessor implements CustomPostPassProcessor {
        private static final String BLUR_DIRECTION = "BlurDirection";
        private static final String RADIUS = "Radius";
        private static final String PROGRESS = "Progress";
        private static final String ENHANCED_CONTRAST = "EnhancedContrast";
        private final MenuBlurConfig config;
        private float progress;

        public ConfigurableMenuBlurCustomPostPassProcessor(MenuBlurConfig config) {
            this.config = config;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
        @Override // net.labymod.api.client.gfx.pipeline.post.CustomPostPassProcessor
        public void process(PostPassData data, CommandBuffer cmd, float time) throws RenderDeviceException {
            String name = data.name();
            CustomPostProcessorUniformBlock menuBlurBlock = data.getBlock("MenuBlur");
            if (name.equals("main")) {
                menuBlurBlock.getProperty(BLUR_DIRECTION).set(new Vector2f(1.0f, 0.0f));
            } else {
                menuBlurBlock.getProperty(BLUR_DIRECTION).set(new Vector2f(0.0f, 1.0f));
            }
            menuBlurBlock.getProperty(RADIUS).set(Float.valueOf(this.config.strength().get().floatValue() * 4.0f));
            menuBlurBlock.getProperty(PROGRESS).set(Float.valueOf(this.progress));
            menuBlurBlock.getProperty(ENHANCED_CONTRAST).set(Float.valueOf(this.config.enhancedContrast().get().booleanValue() ? 1.0f : 0.0f));
        }

        public void setProgress(float progress) {
            this.progress = progress;
        }
    }
}
