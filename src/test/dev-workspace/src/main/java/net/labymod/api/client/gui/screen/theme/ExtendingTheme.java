package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/ExtendingTheme.class */
public abstract class ExtendingTheme extends AbstractTheme {

    @NotNull
    private final Theme parentTheme;

    public ExtendingTheme(String id, String parentTheme, Class<? extends ThemeConfig> configClass) {
        super(id, configClass);
        this.parentTheme = Laby.labyAPI().themeService().getThemeByName(parentTheme);
        if (this.parentTheme == null) {
            throw new IllegalArgumentException("Parent theme not found: " + parentTheme);
        }
    }

    public ExtendingTheme(String id, String parentTheme) {
        this(id, parentTheme, ThemeConfig.class);
    }

    public ExtendingTheme(String id, Class<? extends ThemeConfig> configClass) {
        this(id, VanillaTheme.ID, configClass);
    }

    public ExtendingTheme(String id) {
        this(id, (Class<? extends ThemeConfig>) ThemeConfig.class);
    }

    @NotNull
    public Theme parentTheme() {
        return this.parentTheme;
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    public void onPreLoad() {
        super.onPreLoad();
        this.parentTheme.onPreLoad();
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    public void onPostLoad() {
        super.onPostLoad();
        this.parentTheme.onPostLoad();
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    public void onUnload() {
        super.onUnload();
        this.parentTheme.onUnload();
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public ThemeFile createResource(BasicThemeFile file) {
        ThemeFile resource = super.createResource(file);
        if (resource.exists() || !isResourcesLoaded()) {
            return resource;
        }
        return this.parentTheme.createResource(file);
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    @Nullable
    public <T extends Widget> ThemeRenderer<T> getWidgetRendererByName(String rendererName) {
        ThemeRenderer<T> renderer = super.getWidgetRendererByName(rendererName);
        if (renderer != null) {
            return renderer;
        }
        return this.parentTheme.getWidgetRendererByName(rendererName);
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    public ThemeRenderer<?> getWidgetRendererByType(MinecraftWidgetType type) {
        ThemeRenderer<?> renderer = super.getWidgetRendererByType(type);
        if (renderer != null) {
            return renderer;
        }
        return this.parentTheme.getWidgetRendererByType(type);
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    @Nullable
    public HoverBackgroundEffect getHoverBackgroundRenderer() {
        HoverBackgroundEffect renderer = super.getHoverBackgroundRenderer();
        if (renderer != null) {
            return renderer;
        }
        return this.parentTheme.getHoverBackgroundRenderer();
    }

    @Override // net.labymod.api.client.gui.screen.theme.AbstractTheme, net.labymod.api.client.gui.screen.theme.Theme
    @Nullable
    public BackgroundRenderer getBackgroundRenderer() {
        BackgroundRenderer renderer = super.getBackgroundRenderer();
        if (renderer != null) {
            return renderer;
        }
        return this.parentTheme.getBackgroundRenderer();
    }
}
