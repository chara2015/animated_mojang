package net.labymod.api.client.gui.screen.theme;

import java.io.IOException;
import net.labymod.api.Constants;
import net.labymod.api.configuration.exception.ConfigurationLoadException;
import net.labymod.api.configuration.loader.ConfigLoader;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/ThemeConfigHandler.class */
public class ThemeConfigHandler {
    private static final Logging LOGGER = Logging.getLogger();
    private final ConfigLoader configLoader = new JsonConfigLoader(Constants.Files.CONFIGS);
    private final Class<? extends ThemeConfig> configClass;
    private final String id;
    private boolean loaded;

    @Nullable
    private ThemeConfig config;

    public ThemeConfigHandler(Theme theme) {
        this.configClass = theme.getConfigClass();
        this.id = theme.getId();
    }

    @Nullable
    public ThemeConfig getOrLoad() {
        if (this.loaded) {
            return this.config;
        }
        if (this.config == null) {
            setThemeVariable();
            try {
                this.config = (ThemeConfig) this.configLoader.load(this.configClass);
            } catch (ConfigurationLoadException exception) {
                try {
                    LOGGER.error("Failed to load theme config for theme {}", this.id, exception);
                    this.configLoader.invalidate(this.configClass);
                    return getOrLoad();
                } catch (IOException ioException) {
                    LOGGER.error("Failed to invalidate theme config for theme {}", this.id, ioException);
                    this.config = null;
                }
            }
        }
        this.loaded = true;
        return this.config;
    }

    public void save() {
        if (this.config == null) {
            return;
        }
        setThemeVariable();
        this.configLoader.save(this.config);
    }

    private void setThemeVariable() {
        this.configLoader.setVariable("$THEME", this.id);
    }

    public String toString() {
        return "ThemeConfigHandler[id=" + this.id + ", configClass=" + getConfigClassName() + "]";
    }

    private String getConfigClassName() {
        if (this.configClass == null) {
            return "Do not have a config class";
        }
        return this.configClass.getName();
    }
}
