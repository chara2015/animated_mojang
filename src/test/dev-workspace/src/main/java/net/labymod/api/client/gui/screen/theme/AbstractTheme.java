package net.labymod.api.client.gui.screen.theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gfx.pipeline.renderer.text.Font;
import net.labymod.api.client.gfx.pipeline.renderer.text.Fonts;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.configuration.loader.ConfigLoader;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.metadata.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/AbstractTheme.class */
public abstract class AbstractTheme implements Theme {
    private static final AtomicInteger THEME_ID_COUNTER = new AtomicInteger(0);
    protected final int internalId;
    protected final String id;
    protected final Class<? extends ThemeConfig> configClass;
    protected final LabyAPI labyAPI;
    protected final ConfigLoader configLoader;
    protected final Map<String, ThemeRenderer<?>> widgetRenderers;
    protected final Map<MinecraftWidgetType, String> typeBindings;
    protected final List<ThemeEventListener> eventListeners;
    protected BackgroundRenderer backgroundRenderer;
    protected HoverBackgroundEffect hoverBackgroundRenderer;
    protected final ResourceLocationFactory resourceLocationFactory;
    protected final Map<BasicThemeFile, ThemeFile> resourceCache;
    protected final Metadata metadata;
    private final ThemeConfigHandler configHandler;
    private boolean resourcesLoaded;
    protected String displayName;

    public AbstractTheme(String id) {
        this(id, ThemeConfig.class);
    }

    public AbstractTheme(String id, Class<? extends ThemeConfig> configClass) {
        this.metadata = Metadata.create();
        this.internalId = THEME_ID_COUNTER.getAndIncrement();
        this.id = id;
        this.configClass = configClass;
        this.labyAPI = Laby.labyAPI();
        this.configLoader = new JsonConfigLoader(Constants.Files.CONFIGS);
        this.widgetRenderers = new HashMap();
        this.typeBindings = new HashMap();
        this.eventListeners = new ArrayList();
        this.resourceLocationFactory = Laby.references().resourceLocationFactory();
        this.resourceCache = new HashMap();
        this.configHandler = new ThemeConfigHandler(this);
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public void onPreLoad() {
        this.resourcesLoaded = true;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public void onPostLoad() {
        this.resourceCache.clear();
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public void onUnload() {
        for (ThemeEventListener eventListener : this.eventListeners) {
            this.labyAPI.eventBus().unregisterListener(eventListener);
        }
        this.widgetRenderers.clear();
        this.typeBindings.clear();
        this.eventListeners.clear();
        this.backgroundRenderer = null;
        this.hoverBackgroundRenderer = null;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    @NotNull
    public ThemeFile file(String namespace, String path) {
        BasicThemeFile key = new BasicThemeFile(this, namespace, path);
        ThemeFile result = this.resourceCache.get(key);
        if (result == null) {
            Map<BasicThemeFile, ThemeFile> map = this.resourceCache;
            ThemeFile themeFileCreateResource = createResource(key);
            result = themeFileCreateResource;
            map.put(key, themeFileCreateResource);
        }
        return result;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    @NotNull
    public Font font() {
        return Fonts.MINECRAFT;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    @Nullable
    public <T extends Widget> ThemeRenderer<T> getWidgetRendererByName(String rendererName) {
        return (ThemeRenderer) this.widgetRenderers.get(rendererName);
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public ThemeRenderer<?> getWidgetRendererByType(MinecraftWidgetType type) {
        String rendererName = this.typeBindings.get(type);
        if (rendererName == null) {
            return null;
        }
        return getWidgetRendererByName(rendererName);
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public boolean isResourcesLoaded() {
        return this.resourcesLoaded;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public <T extends Widget> void registerWidgetRenderer(ThemeRenderer<T> renderer) {
        renderer.setTheme(this);
        String name = renderer.getName();
        String namespace = this.labyAPI.getNamespace(renderer);
        if (!"labymod".equals(namespace)) {
            name = namespace + "/" + name;
        }
        this.widgetRenderers.put(name, renderer);
    }

    public void registerBackgroundRenderer(BackgroundRenderer renderer) {
        this.backgroundRenderer = renderer;
    }

    protected void registerHoverBackgroundRenderer(HoverBackgroundEffect renderer) {
        this.hoverBackgroundRenderer = renderer;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public void registerEventListener(ThemeEventListener listener) {
        this.eventListeners.add(listener);
        this.labyAPI.eventBus().registerListener(listener);
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public void bindType(MinecraftWidgetType type, String rendererName) {
        this.typeBindings.put(type, rendererName);
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public Metadata metadata() {
        return this.metadata;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public String getId() {
        return this.id;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public int getInternalId() {
        return this.internalId;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public Class<? extends ThemeConfig> getConfigClass() {
        return this.configClass;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    @Nullable
    public ThemeConfig getOrLoadThemeConfig() {
        return this.configHandler.getOrLoad();
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public void saveThemeConfig() {
        this.configHandler.save();
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    public String getDisplayName() {
        return this.displayName == null ? getId() : this.displayName;
    }

    protected void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    @Deprecated
    @Nullable
    public BackgroundRenderer getBackgroundRenderer() {
        return this.backgroundRenderer;
    }

    @Override // net.labymod.api.client.gui.screen.theme.Theme
    @Deprecated
    @Nullable
    public HoverBackgroundEffect getHoverBackgroundRenderer() {
        return this.hoverBackgroundRenderer;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AbstractTheme that = (AbstractTheme) object;
        if (this.internalId != that.internalId) {
            return false;
        }
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        int result = this.internalId;
        return (31 * result) + (this.id != null ? this.id.hashCode() : 0);
    }
}
