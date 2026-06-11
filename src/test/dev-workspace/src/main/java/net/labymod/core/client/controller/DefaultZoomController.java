package net.labymod.core.client.controller;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.controller.ZoomController;
import net.labymod.api.client.controller.ZoomTransitionType;
import net.labymod.api.client.gui.screen.key.HotkeyService;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomTransitionConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/controller/DefaultZoomController.class */
@Singleton
@Implements(ZoomController.class)
public class DefaultZoomController implements ZoomController {
    private final LabyAPI labyAPI;
    private final ZoomConfig settings;
    private boolean zoomActive = false;
    private int lastTickZoomChange = -1;
    private boolean cameraModified = false;
    private boolean cinematicCameraModified = false;
    private int currentTicks = 0;
    private float distanceScrollOffset = 0.0f;
    private boolean hadCinematicCameraActive = false;

    @Inject
    public DefaultZoomController(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        this.settings = labyAPI.config().ingame().zoom();
        labyAPI.eventBus().registerListener(this);
        Laby.references().hotkeyService().register("zoom", this.settings.zoomKey(), () -> {
            return this.settings.zoomHold().get().booleanValue() ? HotkeyService.Type.HOLD : HotkeyService.Type.TOGGLE;
        }, (v1) -> {
            updateZoomState(v1);
        });
    }

    private void updateZoomState(boolean active) {
        Long l;
        int i;
        if (this.zoomActive == active) {
            return;
        }
        this.zoomActive = active;
        ZoomTransitionConfig transitionConfig = this.settings.zoomTransition();
        int ticksElapsed = this.currentTicks - this.lastTickZoomChange;
        if (this.zoomActive) {
            l = transitionConfig.zoomInDuration().get();
        } else {
            l = transitionConfig.zoomOutDuration().get();
        }
        int durationTicks = Math.toIntExact(l.longValue()) / 50;
        if (transitionConfig.enabled().get().booleanValue() && ticksElapsed < durationTicks) {
            i = this.currentTicks - (durationTicks - ticksElapsed);
        } else {
            i = this.currentTicks;
        }
        this.lastTickZoomChange = i;
        this.cameraModified = true;
        if (this.settings.zoomCinematic().enabled().get().booleanValue()) {
            this.cinematicCameraModified = true;
            this.labyAPI.minecraft().options().setSmoothCamera(true);
        }
    }

    @Subscribe
    public void onFieldOfViewModifier(FieldOfViewModifierEvent event) {
        if (this.zoomActive) {
            double distance = this.labyAPI.config().ingame().zoom().zoomDistance().get().doubleValue() + ((double) this.distanceScrollOffset);
            float modifiedFov = event.getFieldOfView() / ((float) distance);
            event.setFieldOfView(modifiedFov);
        } else if (this.cameraModified) {
            float progress = getTransitionProgress();
            if (progress == 0.0f) {
                this.cameraModified = false;
                this.labyAPI.minecraft().requestChunkUpdate();
            }
        }
        if (!this.zoomActive) {
            if (!this.cameraModified || !this.settings.zoomCinematic().disableAfterTransition().get().booleanValue()) {
                if (this.cinematicCameraModified) {
                    this.cinematicCameraModified = false;
                    this.labyAPI.minecraft().options().setSmoothCamera(this.hadCinematicCameraActive);
                }
                this.hadCinematicCameraActive = this.labyAPI.minecraft().options().isSmoothCamera();
            }
        }
    }

    @Subscribe
    public void onScroll(ClientHotbarSlotChangeEvent event) {
        if ((this.zoomActive || this.cameraModified) && this.settings.scrollToZoom().get().booleanValue() && event.delta() != 0.0f) {
            event.setCancelled(true);
            float scrollSpeed = this.distanceScrollOffset <= -1.5f ? 0.05f : 0.25f;
            float scrollDelta = scrollSpeed * event.delta();
            float vanillaFov = (float) this.labyAPI.minecraft().options().getFov();
            double zoomDistance = this.labyAPI.config().ingame().zoom().zoomDistance().get().doubleValue();
            double distance = zoomDistance + ((double) this.distanceScrollOffset) + ((double) scrollDelta);
            float modifier = 1.0f - (1.0f / ((float) distance));
            float fov = vanillaFov * (1.0f - modifier);
            if (fov >= 10.0f && fov <= 130.0f) {
                this.distanceScrollOffset = (float) (distance - zoomDistance);
            }
        }
    }

    @Subscribe
    public void onFieldOfViewTick(FieldOfViewTickEvent event) {
        ConfigProperty<ZoomTransitionType> configPropertyZoomOutType;
        this.currentTicks = event.getTick();
        if (!this.zoomActive && !this.cameraModified) {
            this.distanceScrollOffset = 0.0f;
            return;
        }
        ZoomTransitionConfig transitionConfig = this.settings.zoomTransition();
        if (this.zoomActive) {
            configPropertyZoomOutType = transitionConfig.zoomInType();
        } else {
            configPropertyZoomOutType = transitionConfig.zoomOutType();
        }
        ConfigProperty<ZoomTransitionType> transition = configPropertyZoomOutType;
        boolean hasTransition = transitionConfig.enabled().get().booleanValue() && transition.get() != ZoomTransitionType.NO_TRANSITION;
        if (!this.zoomActive && !hasTransition) {
            this.cameraModified = false;
            event.setFov(event.getModifier());
            return;
        }
        CubicBezier cubicBezier = transition.get().getCubicBezier();
        if (cubicBezier == null || !hasTransition) {
            cubicBezier = CubicBezier.LINEAR;
        }
        double zoomDistance = this.labyAPI.config().ingame().zoom().zoomDistance().get().doubleValue();
        double distance = zoomDistance + ((double) this.distanceScrollOffset);
        float progress = 1.0f;
        if (hasTransition) {
            progress = getTransitionProgress();
        }
        float modifier = (float) ((1.0d - (1.0d / distance)) * cubicBezier.curve(progress));
        float vanillaFov = (float) this.labyAPI.minecraft().options().getFov();
        float fov = vanillaFov * (1.0f - modifier);
        float clampedFov = MathHelper.clamp(fov, 10.0f, 130.0f);
        float modifier2 = 1.0f - (clampedFov / vanillaFov);
        if (hasTransition) {
            event.setOldFov(event.getFov());
        } else {
            event.setModifier(modifier2);
            event.setOldFov(1.0f - modifier2);
        }
        event.setFov(1.0f - modifier2);
        event.setOverwriteVanilla(true);
    }

    @Subscribe
    public void onPreRenderHand(RenderHandEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        if ((this.zoomActive || this.cameraModified) && !this.settings.shouldRenderFirstPersonHand().get().booleanValue()) {
            event.setCancelled(true);
        }
    }

    private float getTransitionProgress() {
        Long l;
        int ticksElapsed = this.currentTicks - this.lastTickZoomChange;
        ZoomTransitionConfig transitionConfig = this.settings.zoomTransition();
        if (this.zoomActive) {
            l = transitionConfig.zoomInDuration().get();
        } else {
            l = transitionConfig.zoomOutDuration().get();
        }
        int durationTicks = Math.toIntExact(l.longValue()) / 50;
        if (!transitionConfig.enabled().get().booleanValue() || durationTicks == 0) {
            return this.zoomActive ? 0.0f : 1.0f;
        }
        if (ticksElapsed > durationTicks) {
            return this.zoomActive ? 1.0f : 0.0f;
        }
        float progress = ticksElapsed / durationTicks;
        return this.zoomActive ? progress : 1.0f - progress;
    }

    @Override // net.labymod.api.client.controller.ZoomController
    public boolean isZoomActive() {
        return this.zoomActive || this.cameraModified;
    }
}
