package net.labymod.api.client.gui.screen.widget.converter;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.render.Renderable;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/WidgetWatcher.class */
public class WidgetWatcher<W extends AbstractWidget<?>> implements Renderable {
    private AbstractWidgetConverter<?, ?> widgetConverter;
    private Object lastSourceContent;
    private W widget;
    private final WidgetConverterRegistry registry = Laby.references().widgetConverterRegistry();
    private boolean initialized = false;
    private boolean hasReplacement = false;
    private WidgetReplacementStrategy replacementStrategy = WidgetReplacementStrategies.DEFAULT;

    public void update(Object source, Object sourceContent) {
        if (!this.initialized || hasChanged(sourceContent)) {
            initialize(source);
        }
        if (this.widget != null) {
            this.registry.sync(this, source, this.widget);
        }
        this.lastSourceContent = sourceContent;
    }

    private boolean hasChanged(Object sourceContent) {
        return !Objects.equals(sourceContent, this.lastSourceContent);
    }

    private void initialize(Object source) {
        this.initialized = true;
        this.registry.findConverter(source.getClass()).ifPresent(abstractWidgetConverter -> {
            this.widgetConverter = abstractWidgetConverter;
            this.widget = (W) this.registry.convertWidget(source, abstractWidgetConverter, replacementStrategy -> {
                this.hasReplacement = true;
                this.replacementStrategy = replacementStrategy;
            });
            this.registry.registerWatcher(this);
        });
    }

    @Override // net.labymod.api.client.render.Renderable
    public boolean render(Stack stack, MutableMouse mouse, float tickDelta) {
        if (this.widget == null) {
            return false;
        }
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, mouse, tickDelta, context -> {
            this.widget.render(context);
        });
        return true;
    }

    @Nullable
    public W getWidget() {
        return this.widget;
    }

    @Nullable
    public AbstractWidgetConverter<?, ?> getWidgetConverter() {
        return this.widgetConverter;
    }

    public boolean hasReplacement() {
        return this.hasReplacement;
    }

    public WidgetReplacementStrategy getReplacementStrategy() {
        return this.replacementStrategy;
    }

    public void invalidate() {
        this.lastSourceContent = null;
        this.initialized = false;
    }

    public String toString() {
        return "WidgetWatcher{widget=" + String.valueOf(this.widget) + ", lastSourceContent=" + String.valueOf(this.lastSourceContent) + ", initialized=" + this.initialized + ", hasReplacement=" + this.hasReplacement + ", replacementStrategy=" + String.valueOf(this.replacementStrategy) + "}";
    }
}
