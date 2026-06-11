package net.labymod.core.client.gui.screen.widget.window;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.window.AbstractScreenWindowOverlay;
import net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.client.input.MouseDragEvent;
import net.labymod.api.event.client.input.MouseScrollEvent;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.screen.widget.window.debug.bounds.DefaultBoundsDebugScreenWindowOverlay;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/window/DefaultScreenWindowManagement.class */
@Singleton
@Implements(ScreenWindowManagement.class)
@Deprecated
public class DefaultScreenWindowManagement implements ScreenWindowManagement {
    private final LabyAPI labyAPI;
    private final ScreenTreeTopRegistry treeTopRegistry;
    private final List<AbstractScreenWindowOverlay> overlays = new ArrayList();

    @Inject
    public DefaultScreenWindowManagement(LabyAPI labyAPI, ScreenTreeTopRegistry treeTopRegistry) {
        this.labyAPI = labyAPI;
        this.treeTopRegistry = treeTopRegistry;
        registerOverlay(new DefaultBoundsDebugScreenWindowOverlay());
        this.labyAPI.eventBus().registerListener(this);
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement
    public void registerOverlay(AbstractScreenWindowOverlay overlay) {
        this.overlays.add(overlay);
        this.labyAPI.eventBus().registerListener(overlay);
        this.overlays.sort(Comparator.comparingInt((v0) -> {
            return v0.getIdentifier();
        }));
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement
    public void widgetPreInitialize(Widget widget, Parent parent) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            overlay.widgetPreInitialize(widget, parent);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement
    public void renderWidgetOverlay(ScreenContext context, Widget widget) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            overlay.renderWidget(context, widget);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement
    public void preRenderActivity(ScreenContext context, Activity activity) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            overlay.preRenderActivity(context, activity);
        }
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.mouseClicked(ScreenPhase.PRE, overlay, mouse, mouseButton);
            boolean result = overlay.mouseClicked(mouse, mouseButton);
            this.treeTopRegistry.mouseClicked(ScreenPhase.POST, overlay, mouse, mouseButton);
            if (result) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.mouseReleased(ScreenPhase.PRE, overlay, mouse, mouseButton);
            boolean result = overlay.mouseReleased(mouse, mouseButton);
            this.treeTopRegistry.mouseReleased(ScreenPhase.POST, overlay, mouse, mouseButton);
            if (result) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double delta) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.mouseScrolled(ScreenPhase.PRE, overlay, mouse, delta);
            boolean result = overlay.mouseScrolled(mouse, delta);
            this.treeTopRegistry.mouseScrolled(ScreenPhase.POST, overlay, mouse, delta);
            if (result) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.mouseDragged(ScreenPhase.PRE, overlay, mouse, button, deltaX, deltaY);
            boolean result = overlay.mouseDragged(mouse, button, deltaX, deltaY);
            this.treeTopRegistry.mouseDragged(ScreenPhase.POST, overlay, mouse, button, deltaX, deltaY);
            if (result) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.fileDropped(ScreenPhase.PRE, overlay, mouse, paths);
            boolean result = overlay.fileDropped(mouse, paths);
            this.treeTopRegistry.fileDropped(ScreenPhase.POST, overlay, mouse, paths);
            if (result) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType inputType) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.keyPressed(ScreenPhase.PRE, overlay, key, inputType);
            boolean result = overlay.keyPressed(key, inputType);
            this.treeTopRegistry.keyPressed(ScreenPhase.POST, overlay, key, inputType);
            if (result) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean keyReleased(Key key, InputType type) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.keyReleased(ScreenPhase.PRE, overlay, key, type);
            boolean result = overlay.keyReleased(key, type);
            this.treeTopRegistry.keyReleased(ScreenPhase.POST, overlay, key, type);
            if (result) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.charTyped(ScreenPhase.PRE, overlay, key, character);
            boolean result = overlay.charTyped(key, character);
            this.treeTopRegistry.charTyped(ScreenPhase.POST, overlay, key, character);
            if (result) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean handlePreeditText(@Nullable PreeditText text) {
        for (AbstractScreenWindowOverlay overlay : this.overlays) {
            if (overlay.handlePreeditText(text)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement
    public <T extends AbstractScreenWindowOverlay> T overlay(Class<T> hudWidgetScreenWindowOverlayClass) {
        Iterator<AbstractScreenWindowOverlay> it = this.overlays.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            if (t.getClass().equals(hudWidgetScreenWindowOverlayClass)) {
                return t;
            }
        }
        return null;
    }

    @Subscribe(125)
    public void onScreenRender(GameRenderEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        Stack stack = event.stack();
        Minecraft minecraft = this.labyAPI.minecraft();
        MutableMouse mouse = minecraft.absoluteMouse().mutable();
        Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, mouse, event.getPartialTicks(), context -> {
            for (AbstractScreenWindowOverlay overlay : this.overlays) {
                overlay.renderWindow(context);
            }
        });
    }

    @Subscribe
    public void onMouseButton(MouseButtonEvent event) {
        switch (event.action()) {
            case CLICK:
                boolean cancelled = mouseClicked(event.mouse(), event.button());
                if (cancelled) {
                    event.setCancelled(true);
                }
                break;
            case RELEASE:
                mouseReleased(event.mouse(), event.button());
                break;
        }
    }

    @Subscribe
    public void onMouseDrag(MouseDragEvent event) {
        mouseDragged(event.mouse(), event.button(), event.deltaX(), event.deltaY());
    }

    @Subscribe
    public void onMouseScroll(MouseScrollEvent event) {
        event.setCancelled(mouseScrolled(event.mouse(), event.delta()));
    }

    @Subscribe
    public void onKey(KeyEvent event) {
        Key key = event.key();
        InputType inputType = KeyMapper.getInputType(key);
        switch (event.state()) {
            case UNPRESSED:
                keyReleased(key, inputType);
                break;
            case PRESS:
                keyPressed(key, inputType);
                break;
        }
        Laby.references().documentHandler().onKey(event);
    }
}
