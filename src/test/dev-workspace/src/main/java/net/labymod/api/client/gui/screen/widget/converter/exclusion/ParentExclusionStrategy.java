package net.labymod.api.client.gui.screen.widget.converter.exclusion;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/exclusion/ParentExclusionStrategy.class */
public class ParentExclusionStrategy implements ExclusionStrategy {
    private final Class<?> excludedParent;

    ParentExclusionStrategy(@NotNull Class<?> excludedParent) {
        this.excludedParent = excludedParent;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy
    public boolean shouldExclude(@NotNull Class<?> clazz) {
        return this.excludedParent.isAssignableFrom(clazz);
    }
}
