package net.labymod.core.main.listener;

import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.post.PostProcessingScreenEvent;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/MotionBlurListener.class */
public final class MotionBlurListener {
    private static final String IRIS_ID = "iris";
    private final MotionBlurConfig config;
    private boolean iris;
    private boolean checkIris;

    public MotionBlurListener(LabyConfig config) {
        this.config = config.ingame().motionBlur();
    }

    @Subscribe
    public void onPostProcessingScreen(PostProcessingScreenEvent event) {
        if (!this.config.enabled().get().booleanValue() || event.phase() != getMotionBlurPhase()) {
            return;
        }
        PostProcessors.processMotionBlur(event.partialTicks());
    }

    private PostProcessingScreenEvent.Phase getMotionBlurPhase() {
        if (OptiFine.isPresent()) {
            return PostProcessingScreenEvent.Phase.WORLD;
        }
        if (isIrisLoaded()) {
            return PostProcessingScreenEvent.Phase.WORLD;
        }
        return PostProcessingScreenEvent.Phase.BEFORE_HAND;
    }

    private boolean isIrisLoaded() {
        if (this.checkIris) {
            return this.iris;
        }
        this.checkIris = true;
        Iterator<KeyValue<ModLoader>> it = ModLoaderRegistry.instance().getElements().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            KeyValue<ModLoader> element = it.next();
            ModLoader loader = element.getValue();
            if (loader.isModLoaded(IRIS_ID)) {
                this.iris = true;
                break;
            }
        }
        return this.iris;
    }
}
