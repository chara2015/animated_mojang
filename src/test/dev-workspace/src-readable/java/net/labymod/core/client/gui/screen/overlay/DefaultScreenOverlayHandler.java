package net.labymod.core.client.gui.screen.overlay;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.activity.activities.OldOverlayWidgetActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetScreenOverlay;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.FileDroppedEvent;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.event.client.input.CharacterTypedEvent;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.client.input.MouseDragEvent;
import net.labymod.api.event.client.input.MouseScrollEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.screen.activity.activities.ingame.NotificationOverlay;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/overlay/DefaultScreenOverlayHandler.class */
@Singleton
@Implements(ScreenOverlayHandler.class)
public final class DefaultScreenOverlayHandler implements ScreenOverlayHandler {
    private final List<ScreenOverlay> overlays = new CopyOnWriteArrayList();
    private final LabyAPI labyAPI;
    private final ScreenTreeTopRegistry screenTreeTopRegistry;
    private final ScreenCustomFontStack screenCustomFontStack;

    @Inject
    public DefaultScreenOverlayHandler(LabyAPI labyAPI, ScreenTreeTopRegistry screenTreeTopRegistry, ScreenCustomFontStack screenCustomFontStack) {
        this.labyAPI = labyAPI;
        this.screenTreeTopRegistry = screenTreeTopRegistry;
        this.screenCustomFontStack = screenCustomFontStack;
        labyAPI.eventBus().registerListener(this);
    }

    @Subscribe(-64)
    public void registerDefaultOverlays(GameInitializeEvent event) {
        if (event.getLifecycle() == GameInitializeEvent.Lifecycle.POST_GAME_STARTED) {
            registerDefaultOverlays();
        }
    }

    @Subscribe
    public void onGameRender(GameRenderEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        Minecraft minecraft = this.labyAPI.minecraft();
        MutableMouse mouse = minecraft.mouse();
        float tickDelta = minecraft.getTickDelta();
        Stack stack = event.stack();
        try {
            stack.push();
            ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
            ScreenCanvas renderState = screenContext.canvas();
            renderState.nextLayer();
            screenContext.runInContext(stack, mouse, tickDelta, this::renderScreenOverlay);
            stack.pop();
        } catch (Throwable th) {
            stack.pop();
            throw th;
        }
    }

    private void renderScreenOverlay(ScreenContext context) {
        for (ScreenOverlay overlay : overlays()) {
            if (overlay.isActive() && (!overlay.isHideGui() || !isHideGui())) {
                this.screenCustomFontStack.push(overlay);
                try {
                    overlay.render(context);
                    overlay.renderOverlay(context);
                    overlay.renderHoverComponent(context);
                } catch (Throwable throwable) {
                    GameCrashReport report = GameCrashReport.forThrowable("Rendering Screen Overlay", throwable);
                    GameCrashReport.Category screenOverlayDetails = report.addCategory("Screen Overlay Details");
                    Objects.requireNonNull(overlay);
                    screenOverlayDetails.setDetail("Screen Overlay name", overlay::getName);
                    screenOverlayDetails.setDetail("Screen Overlay bounds", () -> {
                        Bounds bounds = overlay.bounds();
                        return String.format(Locale.ROOT, "X: %f, Y: %f, Width: %f, Height: %f", Float.valueOf(bounds.getX()), Float.valueOf(bounds.getY()), Float.valueOf(bounds.getWidth()), Float.valueOf(bounds.getHeight()));
                    });
                    report.crashGame();
                }
                this.screenCustomFontStack.pop(overlay);
            }
        }
    }

    @Subscribe
    public void onMouseButton(MouseButtonEvent event) {
        switch (event.action()) {
            case CLICK:
                forEachReversed(overlay -> {
                    if (!overlay.isActive() || event.isCancelled()) {
                        return;
                    }
                    MutableMouse mouse = event.mouse();
                    MouseButton button = event.button();
                    this.screenCustomFontStack.push(overlay);
                    this.screenTreeTopRegistry.mouseClicked(ScreenPhase.PRE, overlay, mouse, button);
                    boolean res = overlay.mouseClicked(mouse, button);
                    this.screenTreeTopRegistry.mouseClicked(ScreenPhase.POST, overlay, mouse, button);
                    this.screenCustomFontStack.pop(overlay);
                    if (res) {
                        event.setCancelled(true);
                    }
                });
                break;
            case RELEASE:
                forEachReversed(overlay2 -> {
                    if (!overlay2.isActive() || event.isCancelled()) {
                        return;
                    }
                    MutableMouse mouse = event.mouse();
                    MouseButton button = event.button();
                    this.screenCustomFontStack.push(overlay2);
                    this.screenTreeTopRegistry.mouseReleased(ScreenPhase.PRE, overlay2, mouse, button);
                    boolean res = overlay2.mouseReleased(mouse, button);
                    this.screenTreeTopRegistry.mouseReleased(ScreenPhase.POST, overlay2, mouse, button);
                    this.screenCustomFontStack.pop(overlay2);
                    if (res) {
                        event.setCancelled(true);
                    }
                });
                break;
        }
    }

    @Subscribe
    public void onMouseDrag(MouseDragEvent event) {
        forEachReversed(overlay -> {
            if (!overlay.isActive() || event.isCancelled()) {
                return;
            }
            MutableMouse mouse = event.mouse();
            MouseButton button = event.button();
            double deltaX = event.deltaX();
            double deltaY = event.deltaY();
            this.screenCustomFontStack.push(overlay);
            this.screenTreeTopRegistry.mouseDragged(ScreenPhase.PRE, overlay, mouse, button, deltaX, deltaY);
            boolean res = overlay.mouseDragged(mouse, button, deltaX, deltaY);
            this.screenTreeTopRegistry.mouseDragged(ScreenPhase.POST, overlay, mouse, button, deltaX, deltaY);
            this.screenCustomFontStack.pop(overlay);
            if (res) {
                event.setCancelled(true);
            }
        });
    }

    @Subscribe
    public void onMouseScroll(MouseScrollEvent event) {
        forEachReversed(overlay -> {
            if (!overlay.isActive() || event.isCancelled()) {
                return;
            }
            MutableMouse mouse = event.mouse();
            float delta = event.delta();
            this.screenCustomFontStack.push(overlay);
            this.screenTreeTopRegistry.mouseScrolled(ScreenPhase.PRE, overlay, mouse, delta);
            boolean res = overlay.mouseScrolled(mouse, delta);
            this.screenTreeTopRegistry.mouseScrolled(ScreenPhase.POST, overlay, mouse, delta);
            this.screenCustomFontStack.pop(overlay);
            if (res) {
                event.setCancelled(true);
            }
        });
    }

    @Subscribe
    public void onFileDrop(FileDroppedEvent event) {
        forEachReversed(overlay -> {
            if (!overlay.isActive() || event.isCancelled()) {
                return;
            }
            MutableMouse mouse = event.mouse();
            List<Path> paths = event.paths();
            this.screenCustomFontStack.push(overlay);
            this.screenTreeTopRegistry.fileDropped(ScreenPhase.PRE, overlay, mouse, paths);
            boolean res = overlay.fileDropped(mouse, paths);
            this.screenTreeTopRegistry.fileDropped(ScreenPhase.POST, overlay, mouse, paths);
            this.screenCustomFontStack.pop(overlay);
            if (res) {
                event.setCancelled(true);
            }
        });
    }

    @Subscribe
    public void onKey(KeyEvent event) {
        if (event.key() instanceof MouseButton) {
        }
        Key key = event.key();
        InputType type = event.inputType();
        switch (event.state()) {
            case UNPRESSED:
                for (ScreenOverlay overlay : overlays()) {
                    if (overlay.isActive() && !event.isCancelled()) {
                        this.screenCustomFontStack.push(overlay);
                        this.screenTreeTopRegistry.keyReleased(ScreenPhase.PRE, overlay, key, type);
                        overlay.keyReleased(key, type);
                        this.screenTreeTopRegistry.keyReleased(ScreenPhase.POST, overlay, key, type);
                        this.screenCustomFontStack.pop(overlay);
                    }
                }
                break;
            case PRESS:
                forEachReversed(overlay2 -> {
                    if (!overlay2.isActive() || event.isCancelled()) {
                        return;
                    }
                    this.screenCustomFontStack.push(overlay2);
                    this.screenTreeTopRegistry.keyPressed(ScreenPhase.PRE, overlay2, key, type);
                    boolean res = overlay2.keyPressed(key, type);
                    this.screenTreeTopRegistry.keyPressed(ScreenPhase.POST, overlay2, key, type);
                    this.screenCustomFontStack.pop(overlay2);
                    if (res) {
                        event.setCancelled(true);
                    }
                });
                break;
        }
    }

    @Subscribe
    public void onCharacterTyped(CharacterTypedEvent event) {
        forEachReversed(overlay -> {
            if (!overlay.isActive() || event.isCancelled()) {
                return;
            }
            Key key = event.key();
            char character = event.getCharacter();
            this.screenCustomFontStack.push(overlay);
            this.screenTreeTopRegistry.charTyped(ScreenPhase.PRE, overlay, key, character);
            boolean res = overlay.charTyped(key, character);
            this.screenTreeTopRegistry.charTyped(ScreenPhase.POST, overlay, key, character);
            this.screenCustomFontStack.pop(overlay);
            if (res) {
                event.setCancelled(true);
            }
        });
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        Window window = this.labyAPI.minecraft().minecraftWindow();
        for (ScreenOverlay overlay : overlays()) {
            if (overlay.isActive()) {
                this.screenCustomFontStack.push(overlay);
                checkWindowSize(window, overlay);
                overlay.tick();
                this.screenCustomFontStack.pop(overlay);
            }
        }
    }

    @Subscribe
    public void onWindowResize(WindowResizeEvent event) {
        Window window = this.labyAPI.minecraft().minecraftWindow();
        int absoluteScaledWidth = window.getAbsoluteScaledWidth();
        int absoluteScaledHeight = window.getAbsoluteScaledHeight();
        for (ScreenOverlay overlay : overlays()) {
            this.screenCustomFontStack.push(overlay);
            resizeOverlay(window, overlay, absoluteScaledWidth, absoluteScaledHeight);
            this.screenCustomFontStack.pop(overlay);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler
    public void registerOverlay(ScreenOverlay overlay) {
        this.overlays.add(overlay);
        this.overlays.sort(Comparator.comparingInt((v0) -> {
            return v0.getZLayer();
        }));
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler
    public void unregisterOverlay(ScreenOverlay overlay) {
        if (!this.overlays.remove(overlay)) {
            return;
        }
        overlay.onCloseScreen();
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler
    @Nullable
    public <T extends ScreenOverlay> T screenOverlay(Class<T> screenOverlayClass) {
        Iterator<ScreenOverlay> it = this.overlays.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            if (t.getClass().equals(screenOverlayClass)) {
                return t;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler
    public <T extends ScreenOverlay> Optional<T> findScreenOverlay(Class<T> screenOverlayClass) {
        ScreenOverlay screenOverlay = screenOverlay(screenOverlayClass);
        return screenOverlay == null ? Optional.empty() : Optional.of(screenOverlay);
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler
    public List<ScreenOverlay> overlays() {
        return this.overlays;
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler
    public void forEachReversed(Consumer<ScreenOverlay> consumer) {
        for (int i = this.overlays.size() - 1; i >= 0; i--) {
            consumer.accept(this.overlays.get(i));
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler
    public boolean isOverlayHovered() {
        for (ScreenOverlay overlay : this.overlays) {
            if (overlay.isActive() && (overlay instanceof OldOverlayWidgetActivity) && ((OldOverlayWidgetActivity) overlay).isHovered()) {
                return true;
            }
            if (overlay.isActive()) {
                for (Widget child : overlay.document().getChildren()) {
                    if (child.isVisible() && child.isHovered()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler
    public boolean isActive(Function<ScreenOverlay, Boolean> overlayFunction) {
        if (overlayFunction == null) {
            return false;
        }
        for (ScreenOverlay overlay : overlays()) {
            if (overlayFunction.apply(overlay).booleanValue()) {
                return overlay.isActive();
            }
        }
        return false;
    }

    private void registerDefaultOverlays() {
        registerOverlay(new WidgetScreenOverlay());
        registerOverlay(new NotificationOverlay());
    }

    private void checkWindowSize(Window window, ScreenOverlay overlay) {
        Bounds bounds = overlay.bounds();
        int absoluteScaledWidth = window.getAbsoluteScaledWidth();
        int absoluteScaledHeight = window.getAbsoluteScaledHeight();
        if (bounds.getWidth() != absoluteScaledWidth || bounds.getHeight() != absoluteScaledHeight) {
            resizeOverlay(window, overlay, absoluteScaledWidth, absoluteScaledHeight);
        }
    }

    private void resizeOverlay(Window window, ScreenOverlay overlay, int absoluteScaledWidth, int absoluteScaledHeight) {
        overlay.resize(absoluteScaledWidth, absoluteScaledHeight);
        overlay.load(window);
    }

    private boolean isHideGui() {
        Minecraft minecraft = this.labyAPI.minecraft();
        return minecraft.isIngame() && minecraft.options().isHideGUI();
    }
}
