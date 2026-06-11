package net.labymod.api.client.gui.screen.widget.converter.exclusion;

import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/exclusion/WidgetExclusionStrategy.class */
public class WidgetExclusionStrategy implements ExclusionStrategy {
    private final Class<?> screenClass;
    private final Class<?>[] excludedWidgets;
    private boolean currentScreen;

    WidgetExclusionStrategy(Class<?> screenClass, Class<?>... excludedWidgets) {
        this.screenClass = screenClass;
        this.excludedWidgets = excludedWidgets;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy
    public boolean shouldExclude(Class<?> target) {
        if (Objects.equals(this.screenClass, target)) {
            this.currentScreen = true;
        }
        if (!this.currentScreen) {
            return false;
        }
        for (Class<?> excludedWidget : this.excludedWidgets) {
            if (excludedWidget.isAssignableFrom(target)) {
                return true;
            }
        }
        return false;
    }
}
