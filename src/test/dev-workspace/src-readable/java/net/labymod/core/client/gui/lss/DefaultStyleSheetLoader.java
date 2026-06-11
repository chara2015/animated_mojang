package net.labymod.core.client.gui.lss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.lss.style.reader.LssParser;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.core.client.resources.PathResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/DefaultStyleSheetLoader.class */
@Singleton
@Implements(StyleSheetLoader.class)
public class DefaultStyleSheetLoader implements StyleSheetLoader {
    private static final Logging LOGGER = Logging.getLogger();
    private final Map<ThemeFile, StyleSheet> cache = new HashMap();
    private final List<ThemeFile> modifiedStyleSheets = new ArrayList();

    @Inject
    public DefaultStyleSheetLoader() {
    }

    @Override // net.labymod.api.client.gui.lss.StyleSheetLoader
    public void invalidate() {
        this.cache.clear();
    }

    public void onDevTick() {
        for (ThemeFile file : this.cache.keySet()) {
            ResourceLocation resource = file.toResourceLocation();
            if (resource instanceof PathResourceLocation) {
                PathResourceLocation resourceLocation = (PathResourceLocation) resource;
                if (resourceLocation.isModified()) {
                    this.modifiedStyleSheets.add(file);
                }
            }
        }
        boolean hasModifiedStyleSheets = !this.modifiedStyleSheets.isEmpty();
        for (ThemeFile modifiedStyleSheet : this.modifiedStyleSheets) {
            StyleSheet styleSheet = load(modifiedStyleSheet, true);
            if (styleSheet != null) {
                LOGGER.info("Stylesheet {} has been reloaded", modifiedStyleSheet.toResourceLocation());
            }
        }
        this.modifiedStyleSheets.clear();
        if (hasModifiedStyleSheets) {
            invalidate();
            ((DefaultThemeService) Laby.references().themeService()).reloadActivities();
        }
    }

    @Override // net.labymod.api.client.gui.lss.StyleSheetLoader
    public StyleSheet load(ThemeFile file) {
        return load(file, false);
    }

    private StyleSheet load(ThemeFile file, boolean reload) {
        StyleSheet cached;
        if (!reload && (cached = this.cache.get(file)) != null) {
            return cached;
        }
        try {
            LssParser parser = new LssParser(file);
            StyleSheet styleSheet = parser.parse();
            if (styleSheet != null) {
                this.cache.put(file, styleSheet);
            }
            return styleSheet;
        } catch (Exception exception) {
            LOGGER.error("Can't load lss file of {} theme: {} ({}: {})", file.theme().getId(), file.getName(), exception.getClass().getName(), exception.getMessage());
            return null;
        }
    }
}
