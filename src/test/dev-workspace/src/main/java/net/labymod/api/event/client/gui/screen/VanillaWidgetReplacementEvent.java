package net.labymod.api.event.client.gui.screen;

import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/VanillaWidgetReplacementEvent.class */
public class VanillaWidgetReplacementEvent extends DefaultCancellable implements Event {
    private final AbstractWidget<?> widget;

    @Nullable
    private WidgetReplacementStrategy replacementStrategy;

    public VanillaWidgetReplacementEvent(AbstractWidget<?> widget) {
        this.widget = widget;
    }

    public AbstractWidget<?> getWidget() {
        return this.widget;
    }

    @Nullable
    public WidgetReplacementStrategy getReplacementStrategy() {
        return this.replacementStrategy;
    }

    public void setReplacementStrategy(WidgetReplacementStrategy replacementStrategy) {
        this.replacementStrategy = replacementStrategy;
        setCancelled(true);
    }

    public void setReplacement(Supplier<AbstractWidget<?>> replacement) {
        setReplacementStrategy(new WidgetReplacementStrategy(replacement, WidgetReplacementStrategy.ReplacementBehavior.all()));
    }

    public void setReplacementStrategy(Supplier<AbstractWidget<?>> replacement, WidgetReplacementStrategy.ReplacementBehavior... behaviors) {
        setReplacementStrategy(new WidgetReplacementStrategy(replacement, WidgetReplacementStrategy.ReplacementBehavior.of(behaviors)));
    }
}
