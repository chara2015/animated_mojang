package net.labymod.core.client.gui.lss;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.meta.BulkLinkMeta;
import net.labymod.api.client.gui.lss.meta.LinkMeta;
import net.labymod.api.client.gui.lss.meta.LinkMetaList;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.service.CustomServiceLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/DefaultLinkMetaLoader.class */
@Singleton
@Implements(LinkMetaLoader.class)
public class DefaultLinkMetaLoader implements LinkMetaLoader {
    private final LabyAPI labyAPI;
    private final ResourcesReloadWatcher resourcesReloadWatcher;
    private final Map<Class<?>, LinkMetaList> linkMetas = new HashMap();
    private final StyleSheetLoader styleSheetLoader = Laby.references().styleSheetLoader();

    @Inject
    public DefaultLinkMetaLoader(LabyAPI labyAPI, EventBus eventBus, ResourcesReloadWatcher resourcesReloadWatcher) {
        this.labyAPI = labyAPI;
        this.resourcesReloadWatcher = resourcesReloadWatcher;
        eventBus.registerListener(this);
    }

    @Subscribe
    public void loadMetas(ServiceLoadEvent event) {
        CustomServiceLoader<BulkLinkMeta> loader = event.load(BulkLinkMeta.class, CustomServiceLoader.ServiceType.CROSS_VERSION);
        for (BulkLinkMeta meta : loader) {
            meta.getLinks().forEach(this::handleLinks);
        }
    }

    private void handleLinks(Class<?> clazz, List<LinkMeta> links) {
        LinkMetaList linkMetaList = new LinkMetaList();
        this.linkMetas.put(clazz, linkMetaList);
        for (LinkMeta link : links) {
            linkMetaList.getLinks().add(new LinkReference(this.labyAPI.getNamespace(link.getDefiner()), link.getPath(), link.getPriority()));
        }
        Collections.sort(linkMetaList.getLinks());
        if (this.labyAPI.isFullyInitialized()) {
            this.resourcesReloadWatcher.addOrExecuteInitializeListener(() -> {
                loadStyleSheets(linkMetaList);
            });
        }
    }

    private void loadStyleSheets(LinkMetaList meta) {
        Theme theme = this.labyAPI.themeService().currentTheme();
        for (LinkReference link : meta.getLinks()) {
            this.styleSheetLoader.load(link.toThemeFile(theme).normalize());
        }
    }

    @Override // net.labymod.api.client.gui.lss.meta.LinkMetaLoader
    public void loadStyleSheets() {
        for (LinkMetaList meta : this.linkMetas.values()) {
            loadStyleSheets(meta);
        }
    }

    @Override // net.labymod.api.client.gui.lss.meta.LinkMetaLoader
    public void injectMeta(Activity activity) {
        LinkMetaList linkMetaList = getMeta(activity.getClass());
        if (linkMetaList == null) {
            throw new IllegalStateException("Missing @AutoActivity annotation on activity " + activity.getClass().getName() + " or loading ActivityMeta too early");
        }
        Class<?> cls = activity.getClass();
        Objects.requireNonNull(activity);
        loadMeta(cls, activity::addStyle);
    }

    @Override // net.labymod.api.client.gui.lss.meta.LinkMetaLoader
    public void loadMeta(Class<?> clazz, Consumer<StyleSheet> consumer) {
        LinkMetaList linkMetaList = getMeta(clazz);
        if (linkMetaList == null) {
            return;
        }
        for (LinkReference link : linkMetaList.getLinks()) {
            StyleSheet styleSheet = link.loadStyleSheet();
            if (styleSheet != null) {
                consumer.accept(styleSheet);
            }
        }
    }

    @Override // net.labymod.api.client.gui.lss.meta.LinkMetaLoader
    public LinkMetaList getMeta(Class<?> clazz) {
        return this.linkMetas.get(clazz);
    }
}
