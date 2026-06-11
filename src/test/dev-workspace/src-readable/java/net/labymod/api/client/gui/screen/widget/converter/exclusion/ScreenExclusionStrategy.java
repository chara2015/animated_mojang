package net.labymod.api.client.gui.screen.widget.converter.exclusion;

import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/exclusion/ScreenExclusionStrategy.class */
public class ScreenExclusionStrategy implements ExclusionStrategy {
    private final Class<?> excludedScreen;

    ScreenExclusionStrategy(Class<?> excludedScreen) {
        this.excludedScreen = excludedScreen;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy
    public boolean shouldExclude(Class<?> target) {
        return Objects.equals(target, this.excludedScreen);
    }
}
