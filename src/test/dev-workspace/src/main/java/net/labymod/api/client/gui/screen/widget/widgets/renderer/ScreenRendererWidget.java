package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/renderer/ScreenRendererWidget.class */
@AutoWidget
public class ScreenRendererWidget extends AbstractWidget<Widget> implements ParentScreen {
    private final List<Consumer<ScreenInstance>> preDisplayListeners;
    private final List<Consumer<ScreenInstance>> postDisplayListeners;
    private Predicate<ScreenInstance> previousScreenHandler;
    private ScreenInstance screen;
    private ScreenInstance originScreen;
    private final boolean forwardStyle;
    private final LssProperty<Boolean> interactable;
    private boolean initialized;
    private boolean screenInitialized;
    private ScreenInstance queuedScreen;
    private boolean queuedScreenInitialize;

    public ScreenRendererWidget() {
        this(false);
    }

    public ScreenRendererWidget(boolean forwardStyle) {
        this.preDisplayListeners = new ArrayList();
        this.postDisplayListeners = new ArrayList();
        this.interactable = new LssProperty<>(true);
        this.forwardStyle = forwardStyle;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        if (!this.initialized && this.screen != null) {
            this.screen.onOpenScreen();
        }
        super.initialize(parent);
        forwardStylesheets();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        if (this.screen != null && this.screenInitialized) {
            this.screen.onCloseScreen();
            this.screenInitialized = false;
        }
        this.initialized = false;
        super.dispose();
    }

    private void forwardStylesheets() {
        Parent parent;
        if (this.parent == null || !(this.screen instanceof Activity)) {
            return;
        }
        Deque<ScreenRendererWidget> widgets = new ArrayDeque<>();
        Parent current = this;
        do {
            if (current instanceof ScreenRendererWidget) {
                widgets.offerFirst((ScreenRendererWidget) current);
            }
            parent = current.getParent();
            current = parent;
        } while (parent != null);
        boolean forwarded = false;
        for (ScreenRendererWidget widget : widgets) {
            Parent root = widget.getRoot();
            if (root instanceof Activity) {
                if (widget.forwardStyle) {
                    forwarded = true;
                }
                if (forwarded) {
                    for (StyleSheet stylesheet : ((Activity) root).getStylesheets()) {
                        ((Activity) this.screen).addStyle(stylesheet);
                    }
                }
            }
        }
    }

    private void init() {
        this.initialized = true;
        if (this.screen != null) {
            if (this.screen instanceof LabyScreen) {
                ((LabyScreen) this.screen).load(this);
            } else {
                this.screen.initialize(this);
            }
            this.screenInitialized = true;
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (isDragging()) {
            return;
        }
        Bounds bounds = bounds();
        if (this.screen != null && !newRect.isRoundedSizeEqual(previousRect) && bounds.hasSize()) {
            this.screen.resize((int) bounds.getWidth(BoundsType.INNER), (int) bounds.getHeight(BoundsType.INNER));
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void postInitialize() {
        super.postInitialize();
        if (this.screen instanceof Activity) {
            ((Activity) this.screen).addInitializeRunnable(this::forwardStylesheets);
        }
        init();
    }

    public void initializeScreen(ScreenInstance screen) {
        if (screen == null) {
            displayScreen(null, true);
        } else {
            this.queuedScreen = screen;
            this.queuedScreenInitialize = true;
        }
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreen(ScreenInstance screen) {
        if (screen == null) {
            displayScreen(null, false);
        } else {
            this.queuedScreen = screen;
            this.queuedScreenInitialize = false;
        }
    }

    private void displayScreen(ScreenInstance screen, boolean initialize) {
        if (this.screen != null) {
            this.screen.onCloseScreen();
        }
        if (!initialize) {
            this.preDisplayListeners.forEach(consumer -> {
                consumer.accept(screen);
            });
        }
        this.screen = screen;
        if (this.originScreen == null) {
            this.originScreen = screen;
        }
        Bounds bounds = bounds();
        if (this.screen != null && bounds.hasSize()) {
            this.screen.resize((int) bounds.getWidth(BoundsType.INNER), (int) bounds.getHeight(BoundsType.INNER));
        }
        forwardStylesheets();
        if (!initialize && bounds.hasSize()) {
            init();
        }
        if (screen != null && this.initialized) {
            screen.onOpenScreen();
        }
        if (!initialize) {
            this.postDisplayListeners.forEach(consumer2 -> {
                consumer2.accept(screen);
            });
        }
    }

    public Object getPreviousScreen() {
        return this.originScreen;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public ScreenWrapper currentScreen() {
        if (this.screen == null) {
            return null;
        }
        if (this.screen instanceof ScreenWrapper) {
            return (ScreenWrapper) this.screen;
        }
        return this.labyAPI.minecraft().wrapScreen(this.screen);
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public LabyScreen currentLabyScreen() {
        if (this.screen instanceof LabyScreen) {
            return (LabyScreen) this.screen;
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public String getCurrentScreenName() {
        if (this.screen == null) {
            return null;
        }
        String currentScreenName = Laby.labyAPI().screenService().getScreenNameByClass(this.screen.getClass());
        if (currentScreenName == null) {
            ScreenInstance screenInstance = this.screen;
            if (screenInstance instanceof Activity) {
                Activity activity = (Activity) screenInstance;
                return activity.getName();
            }
        }
        return currentScreenName;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public Object mostInnerScreen() {
        if (this.screen != null) {
            return this.screen.mostInnerScreen();
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreenRaw(Object screen) {
        displayScreen((ScreenInstance) screen);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (this.queuedScreen != null) {
            displayScreen(this.queuedScreen, this.queuedScreenInitialize);
            this.queuedScreen = null;
        }
        if (isScreenInitialized() && isVisible()) {
            this.labyAPI.minecraft().minecraftWindow().transform(this.parent, context.stack(), bounds(), () -> {
                applyScreen(context.mouse(), () -> {
                    ScreenInstance screen = this.screen;
                    ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
                    try {
                        screenCustomFontStack.push(screen);
                        screen.render(context);
                        screenCustomFontStack.pop(screen);
                        return true;
                    } catch (Throwable th) {
                        screenCustomFontStack.pop(screen);
                        throw th;
                    }
                });
            });
        }
        super.renderWidget(context);
        context.canvas().nextLayer();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderOverlay(ScreenContext context) {
        MutableMouse originalMouse = context.mouse().copy().mutable();
        if (isScreenInitialized() && isVisible()) {
            this.labyAPI.minecraft().minecraftWindow().transform(this.parent, context.stack(), bounds(), () -> {
                applyScreen(context.mouse(), () -> {
                    this.screen.renderOverlay(context);
                    return true;
                });
            });
        }
        context.setMouse(originalMouse);
        super.renderOverlay(context);
        context.canvas().nextLayer();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderHoverComponent(ScreenContext context) {
        if (isScreenInitialized() && isVisible()) {
            this.screen.renderHoverComponent(context);
        }
        super.renderHoverComponent(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (isScreenInitialized()) {
            this.screen.tick();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && applyScreen(mouse, () -> {
            return this.screen.mouseReleased(mouse, mouseButton);
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && applyScreen(mouse, () -> {
            return this.screen.mouseClicked(mouse, mouseButton);
        });
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && applyScreen(mouse, () -> {
            return this.screen.mouseScrolled(mouse, scrollDelta);
        });
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && applyScreen(mouse, () -> {
            return this.screen.mouseDragged(mouse, button, deltaX, deltaY);
        });
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && applyScreen(mouse, () -> {
            return this.screen.fileDropped(mouse, paths);
        });
    }

    private boolean applyScreen(MutableMouse mouse, BooleanSupplier transformHandler) {
        Bounds bounds = bounds();
        return mouse.set(mouse.getXDouble() - ((double) bounds.getX(BoundsType.INNER)), mouse.getYDouble() - ((double) bounds.getY(BoundsType.INNER)), transformHandler);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (this.screen != null) {
            this.screen.onVisibilityChanged(visible);
        }
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && this.screen.keyPressed(key, type);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyReleased(Key key, InputType type) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && this.screen.keyReleased(key, type);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && this.screen.charTyped(key, character);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean handlePreeditText(@Nullable PreeditText text) {
        return isScreenInitialized() && isVisible() && this.interactable.get().booleanValue() && this.screen.handlePreeditText(text);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.ScreenUser
    public void doScreenAction(String action, Map<String, Object> parameters) {
        super.doScreenAction(action, parameters);
        if (isScreenInitialized() && isVisible() && this.interactable.get().booleanValue()) {
            this.screen.doScreenAction(action, parameters);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void updateBounds() {
        super.updateBounds();
        if (this.screen instanceof Activity) {
            ((Activity) this.screen).document().updateBounds();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void updateState(boolean force) {
        super.updateState(force);
        if (this.screen instanceof Activity) {
            ((Activity) this.screen).document().updateState(force);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void unfocus() {
        super.unfocus();
        if (this.screen instanceof Activity) {
            ((Activity) this.screen).document().unfocus();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        if (this.screen instanceof Activity) {
            return ((Activity) this.screen).document().getContentHeight(BoundsType.OUTER);
        }
        return super.getContentHeight(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void updateLssVariable(LssVariable variable) {
        super.updateLssVariable(variable);
        if (this.screen instanceof Activity) {
            ((Activity) this.screen).updateLssVariable(variable);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void forceUpdateLssVariable(LssVariable variable) {
        super.forceUpdateLssVariable(variable);
        if (this.screen instanceof Activity) {
            ((Activity) this.screen).forceUpdateLssVariable(variable);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean shouldHandleEscape() {
        if (this.screen instanceof Activity) {
            return ((Activity) this.screen).shouldHandleEscape();
        }
        return super.shouldHandleEscape();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void renderExtraDebugInformation() {
        super.renderExtraDebugInformation();
        String currentScreenName = getCurrentScreenName();
        if (currentScreenName == null) {
            return;
        }
        LabyImGui.sameLine(0.0f, 0.0f);
        LabyImGui.text(" [" + currentScreenName + "]", -2368696, -12417035);
        if (LabyImGui.isItemClicked()) {
            Laby.references().documentHandler().setSelectedActivity(currentScreenName);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public LssProperty<Boolean> interactable() {
        return this.interactable;
    }

    public ScreenInstance getScreen() {
        return this.screen;
    }

    private boolean isScreenInitialized() {
        return this.screen != null && this.screenInitialized;
    }

    public boolean handlesPreviousScreen() {
        if (this.previousScreenHandler == null) {
            return !this.labyAPI.minecraft().isIngame();
        }
        return this.previousScreenHandler.test(this.screen);
    }

    public void setPreviousScreenHandler(Predicate<ScreenInstance> handler) {
        this.previousScreenHandler = handler;
    }

    public void addDisplayListener(Consumer<ScreenInstance> callback) {
        addPostDisplayListener(callback);
    }

    public void addPreDisplayListener(Consumer<ScreenInstance> callback) {
        this.preDisplayListeners.add(callback);
    }

    public void addPostDisplayListener(Consumer<ScreenInstance> callback) {
        this.postDisplayListeners.add(callback);
    }

    public void setScreenFrom(ScreenRendererWidget screenRenderer) {
        this.preDisplayListeners.forEach(consumer -> {
            consumer.accept(screenRenderer.getScreen());
        });
        this.screen = screenRenderer.getScreen();
        this.screenInitialized = screenRenderer.screenInitialized;
        if (this.originScreen == null) {
            this.originScreen = this.screen;
        }
        this.postDisplayListeners.forEach(consumer2 -> {
            consumer2.accept(this.screen);
        });
    }
}
