package net.labymod.api.client.gui.hud.hudwidget.text;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/text/TextLine.class */
public class TextLine {
    protected final Component keyComponent;
    protected final TextHudWidget<?> hudWidget;
    protected Component valueComponent;
    protected RenderableComponent renderableComponent;
    protected State state;
    protected Object lastValue;
    protected boolean floatingPointPosition;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/text/TextLine$State.class */
    public enum State {
        DISABLED,
        HIDDEN,
        VISIBLE
    }

    public TextLine(TextHudWidget<?> hudWidget, String key, Object value) {
        this(hudWidget, Component.text(key), value);
    }

    public TextLine(TextHudWidget<?> hudWidget, Component key, Object value) {
        this.hudWidget = hudWidget;
        this.keyComponent = key;
        this.state = State.VISIBLE;
        update(value);
        flushInternal();
    }

    public boolean update(Object value) {
        Component componentText;
        if (Objects.equals(this.lastValue, value)) {
            return false;
        }
        this.lastValue = value;
        if (value instanceof Component) {
            componentText = (Component) value;
        } else {
            componentText = Component.text(String.valueOf(value));
        }
        this.valueComponent = componentText;
        return true;
    }

    public boolean updateAndFlush(Object value) {
        if (update(value)) {
            flushInternal();
            return true;
        }
        return false;
    }

    protected void flushInternal() {
        TextHudWidgetConfig config = (TextHudWidgetConfig) this.hudWidget.getConfig();
        TextColor bracketColor = TextColor.color(config.bracketColor().get().get());
        TextColor labelColor = TextColor.color(config.labelColor().get().get());
        TextColor valueColor = TextColor.color(config.valueColor().get().get());
        Component keyComponent = updateColor(this.keyComponent, labelColor);
        Component valueComponent = updateColor(this.valueComponent, valueColor);
        Formatting formatting = config.formatting().get();
        Component componentLine = formatting.build(keyComponent, valueComponent, isLeftAligned(), bracketColor);
        this.renderableComponent = RenderableComponent.builder().disableCache().format(componentLine);
    }

    public void renderLine(ScreenContext context, float x, float y, float space, HudSize hudWidgetSize) {
        ScreenCanvas renderState = context.canvas();
        int flags = 1;
        if (this.floatingPointPosition) {
            flags = 1 | 4;
        }
        renderState.submitRenderableComponent(this.renderableComponent, x, y, -1, flags);
    }

    protected Component updateColor(Component component, TextColor otherColor) {
        TextColor color = component.getColor();
        return (color == null || !color.equals(otherColor)) ? component.color(otherColor) : component;
    }

    public void setFloatingPointPosition(boolean floatingPointPosition) {
        this.floatingPointPosition = floatingPointPosition;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State state() {
        return this.state;
    }

    public float getWidth() {
        return this.renderableComponent.getWidth();
    }

    public float getHeight() {
        return this.renderableComponent.getHeight();
    }

    protected boolean isLeftAligned() {
        return !this.hudWidget.anchor().isRight();
    }

    public <T extends TextHudWidgetConfig> T config() {
        return (T) this.hudWidget.getConfig();
    }

    @Nullable
    public RenderableComponent getRenderableComponent() {
        return this.renderableComponent;
    }

    @Deprecated
    public boolean isVisible() {
        return this.state == State.VISIBLE;
    }

    @Deprecated
    public void setVisible(boolean visible) {
        this.state = visible ? State.VISIBLE : State.HIDDEN;
    }

    @Deprecated
    public TextLine flush() {
        flushInternal();
        return this;
    }
}
