package net.labymod.api.client.gui.screen.widget.converter.exclusion;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/exclusion/ExclusionStrategy.class */
public interface ExclusionStrategy {
    boolean shouldExclude(Class<?> cls);

    static ExclusionStrategy screen(Class<?> screenClass) {
        return new ScreenExclusionStrategy(screenClass);
    }

    static ExclusionStrategy parent(@NotNull Class<?> parentClass) {
        return new ParentExclusionStrategy(parentClass);
    }

    static ExclusionStrategy widget(Class<?> screenClass, Class<?> widgetClass) {
        return new WidgetExclusionStrategy(screenClass, widgetClass);
    }

    static ExclusionStrategy widget(Class<?> screenClass, Class<?>... widgetClasses) {
        return new WidgetExclusionStrategy(screenClass, widgetClasses);
    }
}
