package net.labymod.api.client.gui.screen.theme;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.text.Font;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.metadata.Metadata;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/Theme.class */
public interface Theme {
    void onPreLoad();

    void onPostLoad();

    void onUnload();

    boolean isResourcesLoaded();

    <T extends Widget> void registerWidgetRenderer(ThemeRenderer<T> themeRenderer);

    void registerEventListener(ThemeEventListener themeEventListener);

    void bindType(MinecraftWidgetType minecraftWidgetType, String str);

    Metadata metadata();

    String getId();

    @ApiStatus.Internal
    int getInternalId();

    Class<? extends ThemeConfig> getConfigClass();

    @Nullable
    ThemeConfig getOrLoadThemeConfig();

    void saveThemeConfig();

    String getDisplayName();

    @Nullable
    <T extends Widget> ThemeRenderer<T> getWidgetRendererByName(String str);

    @Nullable
    ThemeRenderer<?> getWidgetRendererByType(MinecraftWidgetType minecraftWidgetType);

    @NotNull
    ThemeFile file(String str, String str2);

    @NotNull
    Font font();

    @Deprecated
    @Nullable
    BackgroundRenderer getBackgroundRenderer();

    @Deprecated
    @Nullable
    HoverBackgroundEffect getHoverBackgroundRenderer();

    default ThemeFile createResource(BasicThemeFile file) {
        return ThemeFile.create(this, file.getNamespace(), file.getPath());
    }

    @Deprecated
    default String getName() {
        return getId();
    }

    default String getNamespace() {
        return Laby.labyAPI().getNamespace(this);
    }

    default String getDirectoryPath() {
        return String.format(Locale.ROOT, "themes/%s/", getId());
    }

    default ResourceLocation resource(String namespace, String path) {
        return file(namespace, path).toResourceLocation();
    }
}
