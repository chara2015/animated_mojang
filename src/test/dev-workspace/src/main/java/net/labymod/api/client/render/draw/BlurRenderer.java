package net.labymod.api.client.render.draw;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/BlurRenderer.class */
@Referenceable
public interface BlurRenderer {
    void renderRectangle(@NotNull ScreenContext screenContext, @NotNull AbstractWidget<?> abstractWidget, int i);

    void invalidateCache();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/draw/BlurRenderer$BlurAlgorithm.class */
    public enum BlurAlgorithm {
        KAWASE_UP(ResourceLocation.create("labymod", "shaders/blur/kawase.vsh"), ResourceLocation.create("labymod", "shaders/blur/kawase_up.fsh")),
        KAWASE_DOWN(ResourceLocation.create("labymod", "shaders/blur/kawase.vsh"), ResourceLocation.create("labymod", "shaders/blur/kawase_down.fsh"));

        private final ResourceLocation vertexShaderLocation;
        private final ResourceLocation fragmentShaderLocation;

        BlurAlgorithm(ResourceLocation vertexShaderLocation, ResourceLocation fragmentShaderLocation) {
            this.vertexShaderLocation = vertexShaderLocation;
            this.fragmentShaderLocation = fragmentShaderLocation;
        }

        public ResourceLocation vertexShaderLocation() {
            return this.vertexShaderLocation;
        }

        public ResourceLocation fragmentShaderLocation() {
            return this.fragmentShaderLocation;
        }
    }
}
