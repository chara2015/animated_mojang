package net.labymod.core.client.world.rplace;

import java.io.InputStream;
import java.net.URI;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.SimpleTexture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.localization.keys.MiscTranslationKeys;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/rplace/RPlaceMapRenderer.class */
public class RPlaceMapRenderer {
    private static final long UPDATE_INTERVAL = 60000;
    private static final UVCoordinates IMAGE_COORDINATES = UVCoordinates.of(0, 0, 1, 1, 1, 1);
    private ResourceLocation resourceLocation;
    private boolean textureAvailable;
    private final RPlaceRegistry rPlaceRegistry = LabyMod.references().rPlaceRegistry();
    private long timeLastUpdated = 0;
    private float aspectRatio = 1.0f;

    public void render(ScreenContext context, float x, float y, float width, float height) {
        if (width <= 0.0f || height <= 0.0f) {
            return;
        }
        ScreenCanvas renderState = context.canvas();
        renderState.submitRelativeRect(x, y, width, height, Integer.MIN_VALUE);
        if (isTextureAvailable()) {
            renderState.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, this.resourceLocation), x + 1.0f, y + 1.0f, width - (1.0f * 2.0f), height - (1.0f * 2.0f), IMAGE_COORDINATES, -1);
        } else {
            renderState.submitRenderableComponent(RenderableComponent.of(MiscTranslationKeys.getLoading()), x + (width / 2.0f), y + (height / 2.0f), -1, 3);
        }
    }

    public void update() {
        long timePassed = TimeUtil.getMillis() - this.timeLastUpdated;
        if (timePassed <= 60000) {
            return;
        }
        this.timeLastUpdated = TimeUtil.getMillis();
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                String url = this.rPlaceRegistry.getMapUrl();
                if (url.contains("%s")) {
                    url = String.format(url, Long.valueOf(TimeUtil.getMillis()));
                }
                InputStream in = URI.create(url).toURL().openStream();
                GameImage gameImage = Laby.references().gameImageProvider().getImage(in);
                if (gameImage == null) {
                    return;
                }
                this.aspectRatio = gameImage.getWidth() / gameImage.getHeight();
                Resources resources = Laby.labyAPI().renderPipeline().resources();
                TextureRepository repository = resources.textureRepository();
                Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                    if (this.resourceLocation == null) {
                        this.resourceLocation = resources.resourceLocationFactory().create("labymod", "pixelart/map.png");
                    } else {
                        repository.releaseTexture(this.resourceLocation);
                        this.textureAvailable = false;
                    }
                    SimpleTexture texture = SimpleTexture.simple(this.resourceLocation, gameImage);
                    texture.upload();
                    texture.bindTo();
                    this.textureAvailable = true;
                });
            } catch (Exception e) {
                e.printStackTrace();
                this.textureAvailable = false;
            }
        });
    }

    public boolean isFeatureAvailable() {
        return this.rPlaceRegistry.isEnabled() && this.rPlaceRegistry.isOnTargetLobby() && this.rPlaceRegistry.getMapUrl() != null;
    }

    public boolean isTextureAvailable() {
        return this.textureAvailable && this.resourceLocation != null && isFeatureAvailable();
    }

    public RPlaceRegistry getRegistry() {
        return this.rPlaceRegistry;
    }

    public float getAspectRatio() {
        if (this.aspectRatio == 0.0f) {
            return 1.0f;
        }
        return this.aspectRatio;
    }
}
