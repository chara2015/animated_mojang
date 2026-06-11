package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridFeedWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.labynet.models.textures.TextureResult;
import net.labymod.api.util.Debounce;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin.SkinPreviewWidget;
import net.labymod.core.labynet.DefaultLabyNetController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/SkinBrowseActivity.class */
@AutoActivity
@Link("activity/player/skin-browse.lss")
public class SkinBrowseActivity extends PlayerActivity.Child {
    private static final int PAGE_SIZE = 42;
    private static final Map<TextureResult.Order, SkinBrowserOrderCache> ORDER_CACHES = new HashMap();
    private final ListSession<SkinPreviewWidget> session;
    private final TilesGridFeedWidget<SkinPreviewWidget> feedWidget;
    private final SkinActivity skinActivity;
    private final TextFieldWidget searchField;
    private SkinBrowserOrderCache selectedCache;
    private SkinCacheCollection selectedCollection;
    private int page;

    static {
        for (TextureResult.Order order : TextureResult.Order.VALUES) {
            ORDER_CACHES.put(order, new SkinBrowserOrderCache(order));
        }
    }

    public SkinBrowseActivity(SkinActivity skinActivity) {
        super(skinActivity.playerActivity(), skinActivity.getTranslationKeyPrefix() + "browse.", skinActivity.getGroupIdentifier());
        this.selectedCache = ORDER_CACHES.get(TextureResult.Order.TRENDING);
        this.selectedCollection = this.selectedCache.collection("");
        this.skinActivity = skinActivity;
        this.session = new ListSession<>();
        this.feedWidget = new TilesGridFeedWidget<>(this::refreshFeed);
        this.feedWidget.addId("feed");
        this.feedWidget.doRefresh(false);
        this.searchField = new TextFieldWidget();
        this.searchField.addId("search-field");
        this.searchField.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchField.updateListener(string -> {
            Debounce.of("skins-browse-search", 300L, () -> {
                this.labyAPI.minecraft().executeOnRenderThread(() -> {
                    this.selectedCollection = this.selectedCache.collection(string);
                    this.session.setScrollPositionY(0.0f);
                    reload();
                });
            });
        });
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.page = 0;
        loadPage(this.page);
        FlexibleContentWidget flexibleContentWidget = new FlexibleContentWidget();
        flexibleContentWidget.addId("content");
        HorizontalListWidget contentHeader = new HorizontalListWidget();
        contentHeader.addId("content-header");
        ButtonWidget backButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
        backButton.addId("back-button");
        backButton.setPressable(() -> {
            this.playerActivity.displayScreen((PlayerActivity.Child) this.skinActivity);
        });
        contentHeader.addEntry(backButton);
        contentHeader.addEntry(ComponentWidget.i18n(this.translationKeyPrefix + "title"));
        contentHeader.addEntry(this.searchField);
        DropdownWidget<TextureResult.Order> orderDropdown = new DropdownWidget<>();
        orderDropdown.addId("order-dropdown");
        orderDropdown.addAll(TextureResult.Order.VALUES);
        orderDropdown.setSelected(this.selectedCache.order);
        orderDropdown.setTranslationKeyPrefix(this.translationKeyPrefix + "order");
        orderDropdown.setChangeListener(order -> {
            if (this.selectedCache.order != order) {
                this.selectedCache = ORDER_CACHES.get(order);
                this.selectedCollection = this.selectedCache.collection(this.searchField.getText());
                this.session.setScrollPositionY(0.0f);
                reload();
            }
        });
        contentHeader.addEntry(orderDropdown);
        flexibleContentWidget.addContent(contentHeader);
        flexibleContentWidget.addFlexibleContent(new ScrollWidget(this.feedWidget, this.session).addId("player-scroll"));
        ((Document) this.document).addChild(flexibleContentWidget);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    public boolean shouldHandleEscape() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.ESCAPE) {
            this.playerActivity.displayScreen((PlayerActivity.Child) this.skinActivity);
            return true;
        }
        return super.keyPressed(key, type);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity.Child
    protected boolean allowModifiedModel() {
        return true;
    }

    private boolean refreshFeed(TilesGridFeedWidget<SkinPreviewWidget> feedWidget, Consumer<SkinPreviewWidget> add) {
        int i = this.page + 1;
        this.page = i;
        return loadPage(i);
    }

    private boolean loadPage(int page) {
        SkinCache skinCache = this.selectedCollection.getOrLoadPage(page, cache -> {
            if (cache.textures.isEmpty()) {
                return;
            }
            this.labyAPI.minecraft().executeOnRenderThread(() -> {
                fillFeed(cache, true);
            });
        });
        if (skinCache == null || skinCache.textures.isEmpty()) {
            return false;
        }
        fillFeed(skinCache, false);
        return true;
    }

    private void fillFeed(SkinCache skinCache, boolean withDebounce) {
        for (Skin texture : skinCache.textures) {
            SkinPreviewWidget skinPreviewWidget = new SkinPreviewWidget(this.skinActivity, texture);
            if (this.feedWidget.isInitialized()) {
                this.feedWidget.addTileInitialized(skinPreviewWidget);
            } else {
                this.feedWidget.addTile(skinPreviewWidget);
            }
        }
        if (this.feedWidget.isInitialized()) {
            this.feedWidget.updateBounds();
        }
        if (!withDebounce) {
            this.feedWidget.doRefresh(skinCache.textures.size() == 42);
        } else {
            Debounce.of("skins-browse-refresh", 500L, () -> {
                this.labyAPI.minecraft().executeOnRenderThread(() -> {
                    this.feedWidget.doRefresh(skinCache.textures.size() == 42);
                });
            });
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/SkinBrowseActivity$SkinBrowserOrderCache.class */
    private static class SkinBrowserOrderCache {
        private final List<SkinCacheCollection> skinBrowserCaches = new ArrayList();
        private final SkinCacheCollection defaultBrowserCache;
        private final TextureResult.Order order;

        private SkinBrowserOrderCache(TextureResult.Order order) {
            this.order = order;
            this.defaultBrowserCache = new SkinCacheCollection(order, null);
        }

        public SkinCacheCollection collection(String query) {
            String trim = query.toLowerCase(Locale.ROOT).trim();
            if (trim.length() == 0) {
                return this.defaultBrowserCache;
            }
            for (SkinCacheCollection skinBrowserCache : this.skinBrowserCaches) {
                if (skinBrowserCache.search.equals(trim)) {
                    return skinBrowserCache;
                }
            }
            SkinCacheCollection skinBrowserCache2 = new SkinCacheCollection(this.order, trim);
            this.skinBrowserCaches.add(skinBrowserCache2);
            return skinBrowserCache2;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/SkinBrowseActivity$SkinCacheCollection.class */
    private static class SkinCacheCollection {
        private final List<SkinCache> skinCaches = new ArrayList();
        private final String search;
        private final TextureResult.Order order;

        private SkinCacheCollection(TextureResult.Order order, String search) {
            this.search = search;
            this.order = order;
        }

        private SkinCache getOrLoadPage(int page, Consumer<SkinCache> callback) {
            int offset = page * 42;
            for (SkinCache skinCache : this.skinCaches) {
                if (skinCache.offset == offset) {
                    return skinCache;
                }
            }
            LabyNetController labyNetController = Laby.references().labyNetController();
            ((DefaultLabyNetController) labyNetController).loadTextures(TextureResult.Type.SKIN, this.search, this.order, offset, 42, response -> {
                SkinCache skinCache2;
                if (response.isPresent()) {
                    skinCache2 = new SkinCache(offset, ((TextureResult) response.get()).getTextures());
                } else {
                    skinCache2 = new SkinCache(offset, new ArrayList());
                }
                this.skinCaches.add(skinCache2);
                callback.accept(skinCache2);
            });
            return null;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/SkinBrowseActivity$SkinCache.class */
    private static class SkinCache {
        private final int offset;
        private final List<Skin> textures;

        private SkinCache(int offset, List<Skin> textures) {
            this.offset = offset;
            this.textures = textures;
        }

        public List<Skin> getTextures() {
            return this.textures;
        }

        public int getOffset() {
            return this.offset;
        }
    }
}
