package net.labymod.core.client.gui.screen.theme;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeRendererParser;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/DefaultThemeRendererParser.class */
@Singleton
@Implements(ThemeRendererParser.class)
public class DefaultThemeRendererParser implements ThemeRendererParser {
    private final ThemeService themeService;

    @Inject
    public DefaultThemeRendererParser(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Override // net.labymod.api.client.gui.screen.theme.ThemeRendererParser
    public <T extends Widget> ThemeRenderer<T> parse(String value) {
        String name = value;
        Theme theme = this.themeService.currentTheme();
        if (value.contains(":")) {
            String[] entries = value.split(":", 2);
            name = entries[1];
            theme = this.themeService.getThemeByName(entries[0]);
        }
        return theme.getWidgetRendererByName(name);
    }
}
