package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FoldingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.TextFormat;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticWidget;
import net.labymod.core.client.gui.screen.widget.widgets.customization.PlayerModelWidget;
import net.labymod.core.client.render.model.CosmeticModelFocus;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/CosmeticsActivity.class */
@AutoActivity
@Link("activity/player/cosmetics.lss")
public class CosmeticsActivity extends PlayerActivity.Child {
    private static final String IDENTIFIER = "cosmetics";
    private static final String TRANSLATION_KEY_PREFIX = "labymod.activity.customization.cosmetics.status.";
    private final ListSession<?> listSession;
    private final DefaultLabyModNetService labyModNetService;
    private final Map<String, Boolean> expandedCategories;
    private final TextFieldWidget searchFieldWidget;
    private final ComponentWidget statusWidget;
    private final CosmeticModelFocus cosmeticModelFocus;
    private final ItemTextureListener itemTextureListener;
    private VerticalListWidget<Widget> listWidget;
    private String previousQuery;
    private boolean showEnabled;

    public CosmeticsActivity(PlayerActivity playerActivity, String translationKeyPrefix) {
        super(playerActivity, translationKeyPrefix + "cosmetics.", IDENTIFIER);
        this.listSession = new ListSession<>();
        this.cosmeticModelFocus = new CosmeticModelFocus();
        this.itemTextureListener = playerActivity.itemTextureListener();
        this.expandedCategories = new HashMap();
        this.labyModNetService = (DefaultLabyModNetService) LabyMod.references().labyModNetService();
        this.statusWidget = ComponentWidget.empty();
        this.statusWidget.addId("status");
        Map<String, Boolean> expandedCategories = new HashMap<>();
        this.searchFieldWidget = new TextFieldWidget();
        this.searchFieldWidget.addId("search-field");
        this.searchFieldWidget.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchFieldWidget.updateListener(text -> {
            String trim = text.trim();
            if (this.previousQuery == null && !trim.isEmpty()) {
                this.previousQuery = trim;
                expandedCategories.clear();
                if (this.listWidget != null) {
                    for (Widget child : this.listWidget.getChildren()) {
                        if (child instanceof FoldingWidget) {
                            expandedCategories.put(((CharSequence) child.getIds().getFirst()).toString(), Boolean.valueOf(((FoldingWidget) child).isExpanded()));
                        }
                    }
                }
            } else if (this.previousQuery != null && trim.isEmpty()) {
                this.previousQuery = null;
                this.expandedCategories.clear();
                this.expandedCategories.putAll(expandedCategories);
            }
            updateStatus();
            initializeCosmetics();
        });
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity.Child
    public String getTranslationKeyPrefix() {
        return this.translationKeyPrefix;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        if (!this.showEnabled && this.searchFieldWidget.getText().trim().isEmpty()) {
            this.expandedCategories.clear();
            if (this.listWidget != null) {
                for (Widget child : this.listWidget.getChildren()) {
                    if (child instanceof FoldingWidget) {
                        this.expandedCategories.put(child.getIds().get(0).toString(), Boolean.valueOf(((FoldingWidget) child).isExpanded()));
                    }
                }
            }
        }
        super.initialize(parent);
        ((Document) this.document).addChild(this.statusWidget);
        FlexibleContentWidget flexibleContentWidget = new FlexibleContentWidget();
        flexibleContentWidget.addId("content");
        HorizontalListWidget contentHeader = new HorizontalListWidget();
        contentHeader.addId("content-header");
        contentHeader.addEntry(this.searchFieldWidget);
        ButtonWidget collapseAllButton = ButtonWidget.icon(Textures.SpriteCommon.SMALL_UP_SHADOW);
        collapseAllButton.addId("collapse-button", "icon-button");
        collapseAllButton.setHoverComponent(Component.translatable(this.translationKeyPrefix + "collapse", new Component[0]));
        collapseAllButton.setPressable(() -> {
            if (this.listWidget == null) {
                return;
            }
            for (Widget child2 : this.listWidget.getChildren()) {
                if (child2 instanceof FoldingWidget) {
                    ((FoldingWidget) child2).setExpanded(false);
                }
            }
        });
        contentHeader.addEntry(collapseAllButton);
        ButtonWidget expandAllButton = ButtonWidget.icon(Textures.SpriteCommon.SMALL_DOWN_SHADOW);
        expandAllButton.addId("expand-button", "icon-button");
        expandAllButton.setHoverComponent(Component.translatable(this.translationKeyPrefix + "expand", new Component[0]));
        expandAllButton.setPressable(() -> {
            if (this.listWidget == null) {
                return;
            }
            for (Widget child2 : this.listWidget.getChildren()) {
                if (child2 instanceof FoldingWidget) {
                    ((FoldingWidget) child2).setExpanded(true);
                }
            }
        });
        contentHeader.addEntry(expandAllButton);
        CheckBoxWidget onlyShowEnabled = new CheckBoxWidget();
        onlyShowEnabled.addId("only-show-enabled");
        onlyShowEnabled.setState(this.showEnabled ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED);
        onlyShowEnabled.setPressable(() -> {
            this.showEnabled = !this.showEnabled;
            initializeCosmetics();
        });
        ButtonWidget onlyShowEnabledButton = ButtonWidget.i18n(this.translationKeyPrefix + "onlyShowEnabled");
        onlyShowEnabledButton.addId("only-show-enabled-button");
        Objects.requireNonNull(onlyShowEnabled);
        onlyShowEnabledButton.setActionListener(onlyShowEnabled::onPress);
        onlyShowEnabledButton.addEntry(onlyShowEnabled);
        contentHeader.addEntry(onlyShowEnabledButton);
        flexibleContentWidget.addContent(contentHeader);
        this.listWidget = new VerticalListWidget<>();
        this.listWidget.addId("cosmetic-list");
        ScrollWidget scrollWidget = new ScrollWidget(this.listWidget, this.listSession);
        scrollWidget.addId("cosmetic-list-scroll", "player-scroll");
        flexibleContentWidget.addFlexibleContent(scrollWidget);
        ((Document) this.document).addChild(flexibleContentWidget);
        updateStatus();
        initializeCosmetics();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        super.render(context);
        this.cosmeticModelFocus.applyFocus(this.playerActivity.modelWidget());
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        boolean flag = super.mouseClicked(mouse, mouseButton);
        if (!flag) {
            closeOpenOptions();
            resetModelFocus();
        }
        return flag;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        PlayerModelWidget modelWidget = this.playerActivity.modelWidget();
        modelWidget.translation().set(0.0f, 0.0f, 0.0f);
        modelWidget.setMaxTranslation(0.0f, 0.0f);
        modelWidget.scale().set(1.0f, 1.0f, 1.0f);
        modelWidget.setMaxScale(1.0f);
        super.onCloseScreen();
    }

    private void updateStatus() {
        String translationKey;
        LabyModNetService.State state = this.labyModNetService.getState();
        String suffix = TextFormat.SNAKE_CASE.toCamelCase(state.name(), true);
        if (suffix.equals("loading")) {
            translationKey = "labymod.misc.loading";
        } else {
            translationKey = "labymod.activity.customization.cosmetics.status." + suffix;
        }
        this.statusWidget.setComponent(Component.translatable(translationKey, new Component[0]));
        boolean hasQuery = (this.searchFieldWidget == null || this.searchFieldWidget.getText().trim().isEmpty()) ? false : true;
        if (hasQuery) {
            this.statusWidget.setComponent(Component.translatable(this.translationKeyPrefix + "notFoundSearch", new Component[0]));
        } else {
            this.statusWidget.setComponent(Component.translatable(this.translationKeyPrefix + "notFound", new Component[0]));
        }
    }

    public CosmeticSettingsWidget getOpenSettings() {
        DivWidget modelWrapper = this.playerActivity.getModelExtraContainer();
        if (modelWrapper == null) {
            return null;
        }
        Class<CosmeticSettingsWidget> cls = CosmeticSettingsWidget.class;
        Objects.requireNonNull(CosmeticSettingsWidget.class);
        Widget existingSettings = modelWrapper.findFirstChildIf((v1) -> {
            return r1.isInstance(v1);
        });
        if (existingSettings instanceof CosmeticSettingsWidget) {
            return (CosmeticSettingsWidget) existingSettings;
        }
        return null;
    }

    public void closeOpenOptions() {
        CosmeticSettingsWidget existingSettings = getOpenSettings();
        if (existingSettings != null) {
            this.playerActivity.getModelExtraContainer().removeChild(existingSettings);
            existingSettings.onCloseSettings(existingSettings);
        }
    }

    public void displayOptions(CosmeticSettingsWidget widget) {
        closeOpenOptions();
        widget.onOpenSettings(widget);
        this.playerActivity.addWidgetToModelWrapper(widget);
    }

    private void initializeCosmetics() {
        int openSettingsId;
        LabyModNetService.State state = this.labyModNetService.getState();
        if (state != LabyModNetService.State.OK) {
            return;
        }
        this.listWidget.removeChildIf(widget -> {
            return true;
        });
        WidgetOptionService widgetOptionService = this.labyModNetService.widgetOptionService();
        List<Cosmetic> cosmetics = this.labyModNetService.getUserItems().getCosmetics();
        DefaultGameUserService gameUserService = (DefaultGameUserService) Laby.references().gameUserService();
        Map<String, TilesGridWidget<CosmeticWidget>> categoryWidgets = new LinkedHashMap<>();
        TilesGridWidget<CosmeticWidget> uncategorized = new TilesGridWidget<>();
        categoryWidgets.put("Uncategorized", uncategorized);
        String query = this.searchFieldWidget.getText().trim().toLowerCase(Locale.ROOT);
        CosmeticSettingsWidget openSettings = getOpenSettings();
        if (openSettings == null) {
            openSettingsId = -1;
        } else {
            openSettingsId = openSettings.cosmetic().getItemId();
        }
        for (Cosmetic cosmetic : cosmetics) {
            boolean matches = !this.showEnabled || cosmetic.isEnabled();
            if (matches) {
                matches = query.isEmpty() || StringUtil.toLowercase(cosmetic.getName()).contains(query);
            }
            if (matches) {
                CosmeticDefinition definition = gameUserService.cosmeticIndexService().getDefinitionById(cosmetic.getItemId());
                CosmeticWidget widget2 = new CosmeticWidget(this, widgetOptionService, cosmetic, definition != null ? definition.details() : null, this::onButtonPress);
                widget2.setItemTextureListener(this.itemTextureListener);
                if (openSettingsId != -1 && cosmetic.getItemId() == openSettingsId) {
                    widget2.onOpenSettings(openSettings);
                }
                if (definition == null || definition.details().getCategory() == null) {
                    uncategorized.addTile(widget2);
                } else {
                    categoryWidgets.computeIfAbsent(definition.details().getCategory(), s -> {
                        return new TilesGridWidget();
                    }).addTile(widget2);
                }
            }
        }
        for (Map.Entry<String, TilesGridWidget<CosmeticWidget>> entry : categoryWidgets.entrySet()) {
            int size = entry.getValue().getChildren().size();
            if (size != 0) {
                DivWidget divWidget = new DivWidget();
                divWidget.addId("category-wrapper");
                divWidget.addChild(ComponentWidget.text(getAsTitle(entry.getKey())));
                String identifier = "category-" + entry.getKey().toLowerCase(Locale.ROOT).replace("_", "-");
                Boolean expanded = this.expandedCategories.get(identifier);
                FoldingWidget foldingWidget = new FoldingWidget(divWidget, entry.getValue(), this.showEnabled || query.length() != 0 || (expanded != null ? expanded.booleanValue() : size < 6));
                foldingWidget.addId(identifier, "category");
                if (((Document) this.document).isInitialized()) {
                    this.listWidget.addChildInitialized(foldingWidget, false);
                } else {
                    this.listWidget.addChild(foldingWidget, false);
                }
            }
        }
        updateFilter();
    }

    private void onButtonPress(Cosmetic cosmetic) {
    }

    private void updateFilter() {
        AtomicInteger count = new AtomicInteger();
        String query = this.searchFieldWidget.getText().trim().toLowerCase(Locale.ROOT);
        forCosmeticWidgetRecursive(this.listWidget, cosmeticWidget -> {
            boolean matches = !this.showEnabled || cosmeticWidget.cosmetic().isEnabled();
            if (matches) {
                matches = query.length() == 0 || cosmeticWidget.cosmetic().getName().toLowerCase(Locale.ROOT).contains(query);
            }
            cosmeticWidget.setVisible(matches);
            if (matches) {
                count.incrementAndGet();
            }
        });
        this.statusWidget.setVisible(count.get() == 0);
    }

    private String getAsTitle(String text) {
        StringBuilder builder = new StringBuilder();
        String[] s = text.toLowerCase(Locale.ROOT).split(" ");
        for (String word : s) {
            builder.append(' ');
            if (word.length() != 0) {
                builder.append(Character.toUpperCase(word.charAt(0)));
                builder.append(word.substring(1));
            }
        }
        return builder.substring(1);
    }

    public void updateModelFocus(CosmeticDetails itemDetails, Cosmetic cosmetic) {
        GameUserItem userItem = ((DefaultGameUser) Laby.references().gameUserService().clientGameUser()).getUserData().getItem(cosmetic.getItemId());
        this.cosmeticModelFocus.updateModelFocus(this.playerActivity.modelWidget(), itemDetails, cosmetic, userItem);
    }

    public void resetModelFocus() {
        this.cosmeticModelFocus.reset(this.playerActivity.modelWidget());
    }

    public int getOpenSettingsId() {
        CosmeticSettingsWidget openSettings = getOpenSettings();
        if (openSettings == null) {
            return -1;
        }
        return openSettings.cosmetic().getItemId();
    }

    @Subscribe
    public void refreshCosmetics(LabyModNetRefreshEvent event) {
        reload();
    }

    @Subscribe
    public void onUserDataUpdate(UserUpdateDataEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        GameUser gameUser = event.gameUser();
        if (!gameUser.getUniqueId().equals(this.uniqueId) || this.labyModNetService.getState() != LabyModNetService.State.OK) {
            return;
        }
        DefaultGameUser defaultGameUser = (DefaultGameUser) gameUser;
        List<GameUserItem> items = defaultGameUser.getUserData().getItems();
        List<GameUserItem> unknownCosmetics = new ArrayList<>(items);
        forCosmeticWidgetRecursive(this.listWidget, widget -> {
            Cosmetic widgetCosmetic = widget.cosmetic();
            int itemId = widgetCosmetic.getItemId();
            boolean found = false;
            Iterator i$ = items.iterator();
            while (true) {
                if (!i$.hasNext()) {
                    break;
                }
                GameUserItem item = (GameUserItem) i$.next();
                if (item.identifier() == itemId) {
                    unknownCosmetics.remove(item);
                    found = true;
                    break;
                }
            }
            if (widget.isWaitingForResponse()) {
                return;
            }
            widget.setCosmeticActive(found);
        });
        if (!unknownCosmetics.isEmpty()) {
            this.labyModNetService.reload();
        }
    }

    @Subscribe
    public void onLabyConnectStateUpdate(LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.HELLO || event.state() == LabyConnectState.LOGIN) {
            return;
        }
        reload();
    }

    private void forCosmeticWidgetRecursive(Widget widget, Consumer<CosmeticWidget> cosmeticWidget) {
        if (widget == null) {
            return;
        }
        for (Widget child : widget.getChildren()) {
            Widget actualWidget = child;
            if (actualWidget instanceof WrappedWidget) {
                actualWidget = ((WrappedWidget) actualWidget).childWidget();
            }
            if (actualWidget instanceof FoldingWidget) {
                actualWidget = ((FoldingWidget) actualWidget).contentWidget();
            }
            if (actualWidget instanceof CosmeticWidget) {
                cosmeticWidget.accept((CosmeticWidget) actualWidget);
            } else {
                forCosmeticWidgetRecursive(actualWidget, cosmeticWidget);
            }
        }
    }
}
