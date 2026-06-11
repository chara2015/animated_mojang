package net.labymod.core.client.gui.screen.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.labymod.api.models.Implements;
import net.labymod.core.addon.AddonClassLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/DefaultIngameActivityOverlay.class */
@Singleton
@Implements(IngameActivityOverlay.class)
public class DefaultIngameActivityOverlay implements IngameActivityOverlay {
    private final List<IngameOverlayActivity> activities = new ArrayList();
    private final List<IngameOverlayActivity> unmodifiableActivities = Collections.unmodifiableList(this.activities);
    private final Map<ClassLoader, List<IngameOverlayActivity>> activitiesByAddon = new HashMap();
    private final LabyAPI labyAPI;
    private final ScreenCustomFontStack screenCustomFontStack;

    @Inject
    public DefaultIngameActivityOverlay(LabyAPI labyAPI, EventBus eventBus, ScreenCustomFontStack screenCustomFontStack) {
        this.labyAPI = labyAPI;
        this.screenCustomFontStack = screenCustomFontStack;
        eventBus.registerListener(this);
    }

    @Subscribe
    public void render(IngameOverlayRenderEvent event) {
        MutableMouse mutableMouseMutable;
        if (event.phase() != Phase.POST || this.activities.isEmpty()) {
            return;
        }
        Minecraft minecraft = this.labyAPI.minecraft();
        float delta = minecraft.getTickDelta();
        Window window = minecraft.minecraftWindow();
        Stack stack = event.stack();
        ScreenContext screenContext = event.context();
        ScreenCanvas renderState = screenContext.canvas();
        for (IngameOverlayActivity activity : this.activities) {
            if (activity.isVisible() && (!event.isGuiHidden() || activity.isRenderedHidden())) {
                renderState.nextLayer();
                this.screenCustomFontStack.push(activity);
                if (activity.bounds().getWidth() != window.getScaledWidth() || activity.bounds().getHeight() != window.getScaledHeight()) {
                    activity.resize(window.getScaledWidth(), window.getScaledHeight());
                }
                if (!activity.isOpen()) {
                    activity.load(window);
                    activity.onOpenScreen();
                }
                boolean input = activity.isAcceptingInput();
                if (input) {
                    mutableMouseMutable = minecraft.absoluteMouse().mutable();
                } else {
                    mutableMouseMutable = Mouse.OUT_OF_BOUNDS.mutable();
                }
                MutableMouse mouse = mutableMouseMutable;
                screenContext.runInContext(stack, mouse, delta, context -> {
                    renderActivity(false, activity, context);
                });
                this.screenCustomFontStack.pop(activity);
            }
        }
        renderState.nextLayer();
    }

    @Subscribe
    public void onScreenRender(ScreenRenderEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        Minecraft minecraft = this.labyAPI.minecraft();
        MutableMouse mouse = minecraft.absoluteMouse().mutable();
        ScreenContext screenContext = event.screenContext();
        ScreenCanvas canvas = screenContext.canvas();
        for (IngameOverlayActivity activity : this.activities) {
            if (activity.isVisible()) {
                canvas.nextLayer();
                this.screenCustomFontStack.push(activity);
                MutableMouse prevMouse = screenContext.mouse();
                boolean input = activity.isAcceptingInput();
                MutableMouse overlayMouse = input ? mouse : Mouse.OUT_OF_BOUNDS.mutable();
                screenContext.setMouse(overlayMouse);
                renderActivity(true, activity, screenContext);
                screenContext.setMouse(prevMouse);
                this.screenCustomFontStack.pop(activity);
            }
        }
        canvas.nextLayer();
    }

    private void renderActivity(boolean overlay, IngameOverlayActivity activity, ScreenContext context) {
        try {
            ScreenCanvas canvas = context.canvas();
            if (overlay) {
                activity.renderOverlay(context);
                activity.renderHoverComponent(context);
                canvas.nextLayer();
            } else {
                activity.render(context);
                canvas.nextLayer();
            }
        } catch (Throwable throwable) {
            GameCrashReport report = GameCrashReport.forThrowable("Rendering Ingame Activity " + (overlay ? "(Overlay)" : ""), throwable);
            GameCrashReport.Category activityDetails = report.addCategory("Activity Details");
            Objects.requireNonNull(activity);
            activityDetails.setDetail("Activity name", activity::getName);
            activityDetails.setDetail("Activity bounds", () -> {
                Bounds bounds = activity.bounds();
                return String.format(Locale.ROOT, "X: %f, Y: %f, Width: %f, Height: %f", Float.valueOf(bounds.getX()), Float.valueOf(bounds.getY()), Float.valueOf(bounds.getWidth()), Float.valueOf(bounds.getHeight()));
            });
            report.crashGame();
        }
    }

    @Subscribe
    public void unfocusOnClose(ScreenDisplayEvent event) {
        if (event.getScreen() != null) {
            return;
        }
        for (IngameOverlayActivity activity : this.activities) {
            if (activity.isAcceptingInput()) {
                this.screenCustomFontStack.push(activity);
                activity.document().unfocus();
                this.screenCustomFontStack.pop(activity);
            }
        }
    }

    @Subscribe
    public void tick(GameTickEvent event) {
        if (event.phase() != Phase.POST || this.activities.isEmpty()) {
            return;
        }
        for (IngameOverlayActivity activity : this.activities) {
            if (activity.isOpen()) {
                this.screenCustomFontStack.push(activity);
                activity.tick();
                this.screenCustomFontStack.pop(activity);
            }
        }
    }

    @Subscribe
    public void initializeActivities(WorldEnterEvent event) {
        for (IngameOverlayActivity activity : this.activities) {
            if (activity.isOpen()) {
                this.screenCustomFontStack.push(activity);
                Window window = this.labyAPI.minecraft().minecraftWindow();
                activity.resize(window.getScaledWidth(), window.getScaledHeight());
                activity.reload();
                this.screenCustomFontStack.pop(activity);
            }
        }
    }

    @Subscribe
    public void updateScreenSize(ScreenResizeEvent event) {
        for (IngameOverlayActivity activity : this.activities) {
            if (activity.isOpen()) {
                this.screenCustomFontStack.push(activity);
                activity.resize(event.getWidth(), event.getHeight());
                activity.reload();
                this.screenCustomFontStack.pop(activity);
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay
    public List<IngameOverlayActivity> getActivities() {
        return this.unmodifiableActivities;
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay
    public List<IngameOverlayActivity> getActivities(LoadedAddon addon) {
        return this.activitiesByAddon.getOrDefault(addon.getClassLoader(), Collections.emptyList());
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay
    public Optional<IngameOverlayActivity> getActivity(Class<? extends IngameOverlayActivity> clazz) {
        for (IngameOverlayActivity activity : this.activities) {
            if (activity.getClass() == clazz) {
                return Optional.of(activity);
            }
        }
        return Optional.empty();
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay
    public void registerActivity(IngameOverlayActivity activity) {
        this.activities.add(activity);
        this.activities.sort(Comparator.comparingInt((v0) -> {
            return v0.getPriority();
        }));
        if (activity.getClass().getClassLoader() instanceof AddonClassLoader) {
            AddonClassLoader addonClassLoader = (AddonClassLoader) activity.getClass().getClassLoader();
            this.activitiesByAddon.computeIfAbsent(addonClassLoader, a -> {
                return new ArrayList();
            }).add(activity);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay
    public void unregisterActivity(IngameOverlayActivity activity) {
        this.activities.remove(activity);
        this.activities.sort(Comparator.comparingInt((v0) -> {
            return v0.getPriority();
        }));
        this.activitiesByAddon.values().removeIf(activities -> {
            return activities.contains(activity);
        });
        this.activitiesByAddon.entrySet().removeIf(entry -> {
            return ((List) entry.getValue()).isEmpty();
        });
        if (activity.isOpen()) {
            activity.onCloseScreen();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay
    public void unregisterActivities(LoadedAddon addon) {
        List<IngameOverlayActivity> activities = this.activitiesByAddon.remove(addon.getClassLoader());
        if (activities == null) {
            return;
        }
        this.activities.removeAll(activities);
        for (IngameOverlayActivity activity : activities) {
            if (activity.isOpen()) {
                activity.onCloseScreen();
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.overlay.IngameActivityOverlay
    public void replaceActivities(UnaryOperator<IngameOverlayActivity> operator) {
        this.activities.replaceAll(activity -> {
            IngameOverlayActivity replaced = (IngameOverlayActivity) operator.apply(activity);
            for (List<IngameOverlayActivity> addonActivities : this.activitiesByAddon.values()) {
                if (addonActivities.remove(activity)) {
                    addonActivities.add(replaced);
                }
            }
            return replaced;
        });
    }
}
