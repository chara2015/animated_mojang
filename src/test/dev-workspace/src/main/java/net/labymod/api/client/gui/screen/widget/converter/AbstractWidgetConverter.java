package net.labymod.api.client.gui.screen.widget.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.MinecraftWidgetBounds;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.WidgetIdentifier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.function.Mapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/AbstractWidgetConverter.class */
public abstract class AbstractWidgetConverter<T, K extends AbstractWidget<?>> implements Parent, LssVariableHolder {
    private static final ModifyReason COPY_MINECRAFT_BOUNDS = ModifyReason.of("copyMinecraftBounds");
    private final List<Mapper<T, K>> mappers = new ArrayList();
    protected final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();

    public abstract K createDefault(T t);

    public abstract void update(T t, K k, @NotNull WidgetReplacementStrategy widgetReplacementStrategy);

    public abstract String getName();

    protected AbstractWidgetConverter() {
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getParent() {
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Bounds bounds() {
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getRoot() {
        return null;
    }

    public K convert(T t) {
        Iterator<Mapper<T, K>> it = this.mappers.iterator();
        while (it.hasNext()) {
            K map = it.next().map(t);
            if (map != null) {
                return map;
            }
        }
        return (K) createDefault(t);
    }

    public void registerMapper(Mapper<T, K> mapper) {
        if (mapper == null) {
            return;
        }
        this.mappers.add(mapper);
    }

    @Deprecated(forRemoval = true, since = "4.1.0")
    public final String findWidgetId(T source) {
        if (source instanceof WidgetIdentifier) {
            return ((WidgetIdentifier) source).getIdentifier();
        }
        return getWidgetId(source);
    }

    public final List<String> findWidgetIds(T source) {
        if (source instanceof WidgetIdentifier) {
            WidgetIdentifier identifier = (WidgetIdentifier) source;
            return identifier.getIdentifiers();
        }
        return getWidgetIds(source);
    }

    @Deprecated(forRemoval = true, since = "4.1.0")
    @Nullable
    public String getWidgetId(T source) {
        return null;
    }

    public List<String> getWidgetIds(T source) {
        String widgetId = getWidgetId(source);
        if (widgetId == null) {
            return Collections.emptyList();
        }
        return List.of(widgetId);
    }

    public boolean mouseClicked(K widget, MutableMouse mouse, MouseButton mouseButton) {
        if (widget != null && widget.isHovered()) {
            return widget.mouseClicked(mouse, mouseButton);
        }
        return false;
    }

    public boolean mouseReleased(K widget, MutableMouse mouse, MouseButton mouseButton) {
        if (widget != null && widget.isHovered()) {
            return widget.mouseReleased(mouse, mouseButton);
        }
        return false;
    }

    public boolean mouseScrolled(K widget, MutableMouse mouse, double scrollDelta) {
        if (widget != null && widget.isHovered()) {
            return widget.mouseScrolled(mouse, scrollDelta);
        }
        return false;
    }

    public boolean mouseDragged(K widget, MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if (widget != null && widget.isHovered()) {
            return widget.mouseDragged(mouse, button, deltaX, deltaY);
        }
        return false;
    }

    public boolean keyPressed(K widget, Key key, InputType type) {
        if (widget == null) {
            return false;
        }
        return widget.keyPressed(key, type);
    }

    public boolean keyReleased(K widget, Key key, InputType type) {
        if (widget == null) {
            return false;
        }
        return widget.keyReleased(key, type);
    }

    public boolean charTyped(K widget, Key key, char character) {
        if (widget == null) {
            return false;
        }
        return widget.charTyped(key, character);
    }

    public boolean handlePreeditText(K widget, @Nullable PreeditText text) {
        if (widget == null) {
            return false;
        }
        return widget.handlePreeditText(text);
    }

    protected Component mapComponent(Object sourceComponent) {
        return this.componentMapper.fromMinecraftComponent(sourceComponent);
    }

    protected void copyBounds(T source, K destination) {
        MinecraftWidgetBounds widgetBounds = MinecraftWidgetBounds.self(source);
        if (widgetBounds == null) {
            return;
        }
        Bounds bounds = destination.bounds();
        bounds.setOuterPosition(widgetBounds.getBoundsX(), widgetBounds.getBoundsY(), COPY_MINECRAFT_BOUNDS);
        bounds.setOuterSize(widgetBounds.getBoundsWidth(), widgetBounds.getBoundsHeight(), COPY_MINECRAFT_BOUNDS);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractWidgetConverter<?, ?> that = (AbstractWidgetConverter) o;
        return Objects.equals(getName(), that.getName());
    }

    public int hashCode() {
        return getName().hashCode();
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public LssVariableHolder getParentVariableHolder() {
        return window();
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public Map<String, LssVariable> getLssVariables() {
        return window().getLssVariables();
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void updateLssVariable(LssVariable variable) {
        window().updateLssVariable(variable);
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void forceUpdateLssVariable(LssVariable variable) {
        window().forceUpdateLssVariable(variable);
    }

    private Window window() {
        return Laby.labyAPI().minecraft().minecraftWindow();
    }
}
