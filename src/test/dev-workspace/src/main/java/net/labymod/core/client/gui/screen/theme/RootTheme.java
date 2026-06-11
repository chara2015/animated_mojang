package net.labymod.core.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.AbstractTheme;
import net.labymod.api.client.gui.screen.theme.ThemeConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/RootTheme.class */
public abstract class RootTheme extends AbstractTheme {
    public RootTheme(String name) {
        super(name);
    }

    public RootTheme(String name, Class<? extends ThemeConfig> configClass) {
        super(name, configClass);
    }
}
