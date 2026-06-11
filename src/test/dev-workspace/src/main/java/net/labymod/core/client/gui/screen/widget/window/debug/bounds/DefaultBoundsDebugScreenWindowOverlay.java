package net.labymod.core.client.gui.screen.widget.window.debug.bounds;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.states.GuiRectangleRenderState;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.RectangleModification;
import net.labymod.api.util.bounds.RectangleState;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.widget.window.AbstractCoreScreenWindowOverlay;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/window/debug/bounds/DefaultBoundsDebugScreenWindowOverlay.class */
public class DefaultBoundsDebugScreenWindowOverlay extends AbstractCoreScreenWindowOverlay {
    private static final float ARROW_LENGTH = 3.0f;
    private static final float TEXT_PADDING = 4.0f;
    private static final float TEXT_MARGIN = 10.0f;
    private static final Rectangle EMPTY = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    private Component component;
    private boolean fixed;
    private boolean expanded;
    private boolean storeMods;
    private final Map<Widget, Map<RectangleState, RectangleModification>> lastMods;

    public DefaultBoundsDebugScreenWindowOverlay() {
        super(1400);
        this.component = Component.empty();
        this.fixed = false;
        this.expanded = false;
        this.storeMods = false;
        this.lastMods = new HashMap();
    }

    @Subscribe
    public void onKey(KeyEvent event) {
        if (!this.labyAPI.labyModLoader().isLabyModDevelopmentEnvironment() || event.state() != KeyEvent.State.PRESS || this.labyAPI.minecraft().isMouseLocked()) {
            return;
        }
        if (KeyHandler.isControlDown() && event.key() == Key.J) {
            this.enabled = !this.enabled;
            this.fixed = false;
            for (Activity activity : Laby.references().activityController().getOpenActivities()) {
                adaptRecording(activity);
            }
        }
        if (KeyHandler.isControlDown() && event.key() == Key.I) {
            this.expanded = !this.expanded;
        }
        if (this.enabled && KeyHandler.isControlDown() && event.key() == Key.U) {
            this.fixed = !this.fixed;
            if (this.fixed) {
                this.storeMods = true;
            }
        }
    }

    private void adaptRecording(Activity activity) {
        for (Widget child : activity.document().getChildren()) {
            if (this.enabled) {
                child.bounds().recordModifications();
            } else {
                child.bounds().stopRecordingModifications();
            }
            if (child instanceof ScreenRendererWidget) {
                LabyScreen screen = ((ScreenRendererWidget) child).currentLabyScreen();
                if (screen instanceof Activity) {
                    adaptRecording((Activity) screen);
                }
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.AbstractScreenWindowOverlay
    public void renderWindow(ScreenContext context) {
        float scaledWidth;
        float scaledHeight;
        if (!isEnabled()) {
            return;
        }
        ScreenCanvas renderState = context.canvas();
        if (!this.component.getChildren().isEmpty()) {
            Window window = this.labyAPI.minecraft().minecraftWindow();
            RenderableComponent component = RenderableComponent.of(this.component);
            float fontSize = 1.0f;
            if (component.getHeight() > window.getScaledHeight() - TEXT_MARGIN) {
                fontSize = Math.max(((window.getScaledHeight() - TEXT_MARGIN) / component.getHeight()) - 0.05f, 0.55f);
            }
            float width = component.getWidth() * fontSize;
            float height = component.getHeight() * fontSize;
            MutableMouse mouse = context.mouse();
            if (this.fixed || mouse.getXDouble() > window.getScaledWidth() / 2.0f) {
                scaledWidth = TEXT_MARGIN;
            } else {
                scaledWidth = ((window.getScaledWidth() - TEXT_MARGIN) - width) - TEXT_PADDING;
            }
            float x = scaledWidth;
            if (this.fixed || mouse.getYDouble() > window.getScaledHeight() / 2.0f) {
                scaledHeight = TEXT_MARGIN;
            } else {
                scaledHeight = ((window.getScaledHeight() - TEXT_MARGIN) - height) - TEXT_PADDING;
            }
            float y = scaledHeight;
            renderState.submitGuiRect(x - TEXT_PADDING, y - TEXT_PADDING, width + 8.0f, height + 8.0f, GuiRectangleRenderState.RectConfig.builder().setRoundedData(RoundedData.builder().setRadius(5.0f).build()).setGradient(GradientDirection.TOP_TO_BOTTOM, ColorFormat.ARGB32.pack(11141120, 255), ColorFormat.ARGB32.pack(4456448, 255)).build());
            ComponentRenderer componentRenderer = this.labyAPI.renderPipeline().componentRenderer();
            ComponentRenderMeta meta = componentRenderer.builder().pos(x / fontSize, y / fontSize).mousePos(new MutableMouse(mouse.getXDouble() / ((double) fontSize), mouse.getYDouble() / ((double) fontSize))).text(component).scale(fontSize).render(context.stack());
            if (this.fixed) {
                Optional<RenderableComponent> optional = meta.getHovered();
                if (optional.isPresent()) {
                    RenderableComponent renderableComponent = optional.get();
                    Component hoverValue = (Component) renderableComponent.getHoverValue(HoverEvent.Action.SHOW_TEXT);
                    if (hoverValue != null) {
                        componentRenderer.renderHoverComponent(context, RenderableComponent.of(hoverValue));
                    }
                }
            }
        }
        if (!this.fixed) {
            this.lastMods.clear();
            this.component = Component.empty();
        }
        this.storeMods = false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.AbstractScreenWindowOverlay
    public void widgetPreInitialize(Widget widget, Parent parent) {
        if (!isEnabled()) {
            return;
        }
        widget.bounds().recordModifications();
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.AbstractScreenWindowOverlay
    public void renderWidget(ScreenContext context, Widget widget) {
        if (!isEnabled()) {
            return;
        }
        widget.bounds().recordModifications();
        Map<RectangleState, RectangleModification> mods = widget.bounds().lastModifications();
        if (this.fixed && !this.storeMods) {
            mods = this.lastMods.get(widget);
        }
        if (mods != null && !mods.isEmpty()) {
            renderMods(context, widget, mods);
            if (this.storeMods) {
                this.lastMods.put(widget, mods);
            }
        }
    }

    private void renderMods(ScreenContext context, Widget widget, Map<RectangleState, RectangleModification> mods) {
        Component component = Component.text(widget.getTypeName() + " (" + String.join(", ", widget.getIds()) + ")", NamedTextColor.GOLD);
        Component component2 = renderVertical(context, widget, mods.get(RectangleState.HEIGHT), renderHorizontal(context, widget, mods.get(RectangleState.WIDTH), renderVertical(context, widget, mods.get(RectangleState.BOTTOM), renderHorizontal(context, widget, mods.get(RectangleState.RIGHT), renderVertical(context, widget, mods.get(RectangleState.TOP), renderHorizontal(context, widget, mods.get(RectangleState.LEFT), component))))));
        if (isWidgetHovered(context, widget) && !component2.getChildren().isEmpty()) {
            if (!this.fixed || this.storeMods) {
                if (this.component.getChildren().isEmpty()) {
                    this.component = this.component.append(component2);
                } else {
                    this.component = this.component.append(Component.newline().append(Component.newline()).append(component2));
                }
            }
        }
    }

    private Component renderHorizontal(ScreenContext context, Widget widget, RectangleModification mod, Component component) {
        if (mod != null && !mod.reason().isRenderOnly()) {
            return component.append(makeComponent(mod, isHovered(context.mouse(), renderHorizontal(context, widget.bounds(), mod)), true));
        }
        return component;
    }

    private Component renderVertical(ScreenContext context, Widget widget, RectangleModification mod, Component component) {
        if (mod != null && !mod.reason().isRenderOnly()) {
            return component.append(makeComponent(mod, isHovered(context.mouse(), renderVertical(context, widget.bounds(), mod)), true));
        }
        return component;
    }

    private boolean isWidgetHovered(ScreenContext context, Widget widget) {
        Rectangle rect = widget.bounds().rectangle(BoundsType.MIDDLE);
        if (rect.getWidth() == 0.0f || rect.getHeight() == 0.0f) {
            Rectangle rect2 = rect.copy().expand(rect.getWidth() == 0.0f ? 2.0f : 0.0f, rect.getHeight() == 0.0f ? 2.0f : 0.0f);
            if (rect2.distanceSquared(context.mouse()) < 400.0f) {
                context.canvas().submitOutlineRect(rect2, 1.0f, -65536, -65536);
            }
            if (rect2.isInRectangle(context.mouse())) {
                return true;
            }
        }
        return widget.isHovered();
    }

    private boolean isHovered(MutableMouse mouse, Rectangle rectangle) {
        return rectangle.copy().expand(6.0f).isInRectangle(mouse);
    }

    private Component makeComponent(RectangleModification mod, boolean hovered, boolean addHoverEvent) {
        RectangleModification previousModification;
        String modName = TextFormat.SNAKE_CASE.toCamelCase(mod.state().name(), false);
        Component component = Component.newline().append(Component.text(modName, NamedTextColor.GRAY)).append(Component.text(": ", NamedTextColor.DARK_GRAY)).append(Component.text(mod.reason().reason(), NamedTextColor.WHITE)).append(Component.text(" (", NamedTextColor.GRAY)).append(Component.text(Float.valueOf(mod.from()), NamedTextColor.YELLOW)).append(Component.text(" -> ", NamedTextColor.GRAY)).append(Component.text(Float.valueOf(mod.to()), NamedTextColor.YELLOW)).append(Component.text(" | Δ ", NamedTextColor.GRAY)).append(Component.text(Float.valueOf(mod.to() - mod.from()), NamedTextColor.YELLOW)).append(Component.text(")", NamedTextColor.GRAY)).append(Component.newline()).append(Component.text("            from ", NamedTextColor.GRAY)).append(Component.text(mod.reason().source().getSimpleName(), NamedTextColor.YELLOW));
        LoadedAddon addon = mod.reason().sourceAddon();
        if (addon != null) {
            component = component.append(Component.text(" (Addon: ", NamedTextColor.GRAY)).append(Component.text(addon.info().getDisplayName(), NamedTextColor.YELLOW)).append(Component.text(")", NamedTextColor.GRAY));
        }
        if (hovered) {
            component = component.decorate(TextDecoration.ITALIC).decorate(TextDecoration.BOLD).decorate(TextDecoration.UNDERLINED);
        }
        if (addHoverEvent) {
            int max = this.expanded ? 5 : 15;
            Component hover = Component.empty().append(Component.text("All modifications (max. " + max + "):", NamedTextColor.GRAY, TextDecoration.BOLD));
            RectangleModification current = mod;
            int i = 0;
            do {
                hover = hover.append(Component.newline()).append(Component.text(" - ", NamedTextColor.WHITE)).append(Component.text(current.reason().reason(), NamedTextColor.GRAY)).append(Component.text(": ", NamedTextColor.DARK_GRAY)).append(Component.text(Float.valueOf(current.from()), NamedTextColor.YELLOW)).append(Component.text(" -> ", NamedTextColor.GRAY)).append(Component.text(Float.valueOf(current.to()), NamedTextColor.YELLOW)).append(Component.text(" <- ", NamedTextColor.GRAY)).append(Component.text(current.reason().source().getSimpleName(), NamedTextColor.YELLOW)).append(Component.text(" === ", NamedTextColor.DARK_GRAY)).append(Component.text("Frame", NamedTextColor.GRAY)).append(Component.text(": ", NamedTextColor.DARK_GRAY)).append(Component.text(Integer.valueOf(current.frame()), NamedTextColor.WHITE)).append(Component.text(" / From")).append(Component.text(": ", NamedTextColor.DARK_GRAY));
                if (this.expanded) {
                    StackTraceElement[] stackTrace = current.externalStackTrace();
                    for (int j = 0; j < Math.min(20, stackTrace.length); j++) {
                        hover = hover.append(Component.newline()).append(Component.text("   - ", NamedTextColor.GRAY)).append(stackTraceToComponent(stackTrace[j]));
                    }
                } else {
                    StackTraceElement trace = current.lastExternalTrace();
                    hover = hover.append(stackTraceToComponent(trace));
                }
                int i2 = i;
                i++;
                if (i2 >= max) {
                    break;
                }
                previousModification = current.getPreviousModification();
                current = previousModification;
            } while (previousModification != null);
            component = component.hoverEvent(HoverEvent.showText(hover));
        }
        return component;
    }

    private Component stackTraceToComponent(StackTraceElement element) {
        String[] className = element.getClassName().split("\\.");
        return Component.text(className[className.length - 1] + "." + element.getMethodName() + ":" + element.getLineNumber(), NamedTextColor.YELLOW);
    }

    private Rectangle renderVertical(ScreenContext context, Bounds bounds, RectangleModification mod) {
        if (mod.reason().isRenderOnly()) {
            return EMPTY;
        }
        float fromY = mod.from();
        float toY = mod.to();
        float x = bounds.getCenterX();
        return renderArrow(context, x, fromY, x, toY);
    }

    private Rectangle renderHorizontal(ScreenContext context, Bounds bounds, RectangleModification mod) {
        if (mod.reason().isRenderOnly()) {
            return EMPTY;
        }
        float fromX = mod.from();
        float toX = mod.to();
        float y = bounds.getCenterY();
        return renderArrow(context, fromX, y, toX, y);
    }

    private Rectangle renderArrow(ScreenContext context, float fromX, float fromY, float toX, float toY) {
        float f = (fromX != toX && fromX <= toX) ? -3.0f : ARROW_LENGTH;
        float ax = toX + f;
        float f2 = (fromY != toY && fromY <= toY) ? -3.0f : ARROW_LENGTH;
        float ay = toY + f2;
        float f3 = (fromX != toX && fromX > toX) ? ARROW_LENGTH : -3.0f;
        float ax1 = toX + f3;
        float f4 = (fromY != toY && fromY > toY) ? ARROW_LENGTH : -3.0f;
        float ay1 = toY + f4;
        ScreenCanvas renderState = context.canvas();
        renderState.submitLineGradient(fromX, fromY, toX, toY, ColorFormat.ARGB32.pack(7244288, 255), ColorFormat.ARGB32.pack(13434626, 187));
        renderState.submitLine(toX, toY, ax, ay, -65536);
        renderState.submitLine(toX, toY, ax1, ay1, -65536);
        return Rectangle.absolute(ax, ay, ax1 + (fromY == toY ? fromX < toX ? ARROW_LENGTH : -3.0f : 0.0f), ay1 + (fromX == toX ? fromY < toY ? ARROW_LENGTH : -3.0f : 0.0f));
    }

    @Override // net.labymod.api.client.gui.screen.widget.window.AbstractScreenWindowOverlay
    public void preRenderActivity(ScreenContext context, Activity activity) {
    }
}
