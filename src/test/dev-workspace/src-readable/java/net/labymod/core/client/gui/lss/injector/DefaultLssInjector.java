package net.labymod.core.client.gui.lss.injector;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.injector.LssInjector;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.theme.ExtendingTheme;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gui.screen.widget.StyledWidget;
import net.labymod.api.models.Implements;
import net.labymod.api.util.collection.Lists;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/injector/DefaultLssInjector.class */
@Singleton
@Implements(LssInjector.class)
public class DefaultLssInjector implements LssInjector {
    private final LabyAPI labyAPI;
    private final Set<Injector> injectors = new HashSet();

    @Inject
    public DefaultLssInjector(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Override // net.labymod.api.client.gui.lss.injector.LssInjector
    public void registerStyleSheet(@NotNull Object instance, @NotNull String fileName, @NotNull String targetFilePath) {
        Objects.requireNonNull(instance, "Instance cannot be null");
        Objects.requireNonNull(fileName, "File name cannot be null");
        Objects.requireNonNull(targetFilePath, "Target file path cannot be null");
        String namespace = this.labyAPI.getNamespace(instance);
        if (namespace.equals("labymod")) {
            throw new UnsupportedOperationException("Invalid instance, please specify the main instance of your addon!");
        }
        addStyleSheetInjector(instance, namespace, fileName, targetFilePath);
    }

    @Override // net.labymod.api.client.gui.lss.injector.LssInjector
    public void registerWidget(@NotNull Object instance, @NotNull String fileName, @NotNull String selectorTypeName) {
        Objects.requireNonNull(instance, "Instance cannot be null");
        Objects.requireNonNull(fileName, "File name cannot be null");
        Objects.requireNonNull(selectorTypeName, "Selector type name cannot be null");
        String namespace = this.labyAPI.getNamespace(instance);
        if (namespace.equals("labymod")) {
            throw new UnsupportedOperationException("Invalid instance, please specify the main instance of your addon!");
        }
        addWidgetInjector(instance, namespace, fileName, selectorTypeName);
    }

    @Override // net.labymod.api.client.gui.lss.injector.LssInjector
    public boolean unregister(@NotNull Object instance, @NotNull String fileName) {
        Objects.requireNonNull(instance, "Instance cannot be null");
        Objects.requireNonNull(fileName, "File name cannot be null");
        Injector matchedInjector = null;
        Iterator<Injector> it = this.injectors.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Injector injector = it.next();
            if (injector.holder == instance && injector.fileName().equals(fileName)) {
                matchedInjector = injector;
                break;
            }
        }
        if (Objects.nonNull(matchedInjector)) {
            return this.injectors.remove(matchedInjector);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.lss.injector.LssInjector
    @NotNull
    public List<StyleSheet> getMatchedStyleSheetInjectors(@NotNull StyleSheet styleSheet) {
        Objects.requireNonNull(styleSheet, "Style sheet cannot be null");
        List<StyleSheet> list = null;
        for (Injector injector : this.injectors) {
            if (injector.matches(styleSheet)) {
                if (Objects.isNull(list)) {
                    list = Lists.newArrayList();
                }
                list.add(injector.styleSheet());
            }
        }
        return Objects.isNull(list) ? Collections.emptyList() : list;
    }

    @Override // net.labymod.api.client.gui.lss.injector.LssInjector
    @NotNull
    public List<StyleSheet> getMatchedWidgetInjectors(@NotNull StyledWidget styledWidget) {
        Objects.requireNonNull(styledWidget, "Styled widget cannot be null");
        List<StyleSheet> list = null;
        for (Injector injector : this.injectors) {
            if (injector.matches(styledWidget)) {
                if (list == null) {
                    list = Lists.newArrayList();
                }
                list.add(injector.styleSheet());
            }
        }
        return list == null ? Collections.emptyList() : list;
    }

    public void addStyleSheetInjector(Object holder, String namespace, String fileName, String targetFilePath) {
        this.injectors.add(new Injector(this.labyAPI, holder, namespace, fileName, targetFilePath, null));
    }

    public void addWidgetInjector(Object holder, String namespace, String fileName, String typeName) {
        this.injectors.add(new Injector(this.labyAPI, holder, namespace, fileName, null, typeName));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/injector/DefaultLssInjector$Injector.class */
    public static class Injector {
        private static final StyleSheetLoader STYLE_SHEET_LOADER = Laby.references().styleSheetLoader();
        private final ThemeService themeService;
        private final Object holder;
        private final String namespace;
        private final String fileName;
        private final String targetIdentifier;
        private final String targetFilePath;
        private ThemeFile currentThemeFile;
        private Theme currentTheme;
        private StyleSheet styleSheet;

        private Injector(LabyAPI labyAPI, Object holder, String namespace, String fileName, String targetFilePath, String typeName) {
            this.holder = holder;
            this.fileName = fileName;
            this.namespace = namespace;
            this.themeService = labyAPI.themeService();
            if (targetFilePath == null) {
                this.targetIdentifier = typeName;
                this.targetFilePath = null;
            } else if (targetFilePath.contains(":")) {
                String[] targetFile = targetFilePath.split(":");
                this.targetIdentifier = targetFile[0];
                this.targetFilePath = "lss/" + targetFile[1];
            } else {
                this.targetIdentifier = "labymod";
                this.targetFilePath = "lss/" + targetFilePath;
            }
        }

        public String fileName() {
            return this.fileName;
        }

        public String targetIdentifier() {
            return this.targetIdentifier;
        }

        public String targetFilePath() {
            return this.targetFilePath;
        }

        public boolean isStyleSheetInjector() {
            return this.targetFilePath == null;
        }

        public StyleSheet styleSheet() {
            if (this.styleSheet == null || this.themeService.currentTheme() != this.currentTheme) {
                this.styleSheet = loadStyleSheet();
            }
            return this.styleSheet;
        }

        public boolean matches(StyleSheet styleSheet) {
            if (!isStyleSheetInjector()) {
                return false;
            }
            ThemeFile file = styleSheet.file();
            return file.getNamespace().equals(this.targetIdentifier) && file.getPath().equals(this.targetFilePath);
        }

        public boolean matches(StyledWidget styledWidget) {
            if (isStyleSheetInjector()) {
                return false;
            }
            return styledWidget.getTypeName().equals(this.targetIdentifier);
        }

        private StyleSheet loadStyleSheet() {
            this.currentTheme = this.themeService.currentTheme();
            return loadStyleSheet(this.currentTheme);
        }

        private StyleSheet loadStyleSheet(Theme theme) {
            ThemeFile themeFile;
            if (this.currentThemeFile == null) {
                themeFile = ThemeFile.create(this.themeService.currentTheme(), this.namespace, "lss/" + this.fileName);
            } else {
                themeFile = this.currentThemeFile.forTheme(theme);
            }
            this.currentThemeFile = themeFile;
            StyleSheet styleSheet = STYLE_SHEET_LOADER.load(themeFile);
            if (styleSheet == null && (theme instanceof ExtendingTheme)) {
                return loadStyleSheet(((ExtendingTheme) theme).parentTheme());
            }
            return styleSheet;
        }
    }
}
