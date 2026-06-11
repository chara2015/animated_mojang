package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.DefaultEntryRenderer;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.AddonProfileActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.MarketplaceActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.MyAddonsActivity;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.FlintSortBy;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/AddonsActivity.class */
@Links({@Link("activity/flint/addons.lss"), @Link("activity/sidebar-activity.lss")})
@AutoActivity
public class AddonsActivity extends Activity {
    private final TextFieldWidget searchWidget;
    private final DropdownWidget<FlintSortBy> sortDropdownWidget;

    @Nullable
    private ButtonWidget trendingButton;

    @Nullable
    private VerticalListWidget<Widget> categoryList;
    private boolean pseudoReload;
    private final FlintController flintController = LabyMod.references().flintController();
    private final FlintDownloader flintDownloader = LabyMod.references().flintDownloader();
    private final MyAddonsActivity myAddonsActivity = new MyAddonsActivity(this.flintController);
    private final MarketplaceActivity marketplaceActivity = new MarketplaceActivity(this.flintController);
    private final ScreenRendererWidget screenRendererWidget = (ScreenRendererWidget) new ScreenRendererWidget().addId("screen-renderer");

    public AddonsActivity() {
        this.screenRendererWidget.setPreviousScreenHandler(screen -> {
            return screen instanceof AddonProfileActivity;
        });
        this.screenRendererWidget.displayScreen(this.marketplaceActivity);
        this.screenRendererWidget.addDisplayListener(screen2 -> {
            reload();
        });
        this.searchWidget = new TextFieldWidget();
        this.searchWidget.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchWidget.addId("search-widget");
        this.searchWidget.updateListener(text -> {
            ScreenInstance screen3 = this.screenRendererWidget.getScreen();
            if (screen3 instanceof MyAddonsActivity) {
                this.myAddonsActivity.search(text);
                if (this.pseudoReload) {
                    this.pseudoReload = false;
                    return;
                }
                return;
            }
            boolean reload = false;
            if (text.isEmpty()) {
                this.marketplaceActivity.trending();
                reload = true;
            } else if (!this.marketplaceActivity.isSearch(text)) {
                this.marketplaceActivity.search(text);
                reload = true;
            } else if (this.pseudoReload) {
                reload = true;
            }
            if (reload && !(screen3 instanceof AddonProfileActivity)) {
                if (this.pseudoReload) {
                    this.pseudoReload = false;
                } else {
                    reload();
                }
            }
            if (screen3 instanceof AddonProfileActivity) {
                this.screenRendererWidget.displayScreen(this.marketplaceActivity);
            }
        });
        this.sortDropdownWidget = DropdownWidget.create(this.marketplaceActivity.getSortBy(), selected -> {
            this.marketplaceActivity.setSortBy(selected);
            reload();
        });
        DefaultEntryRenderer<?> renderer = (DefaultEntryRenderer) this.sortDropdownWidget.entryRenderer();
        renderer.setTranslationKeyPrefix("labymod.addons.store.filter");
        this.sortDropdownWidget.addId("input-widget");
        this.sortDropdownWidget.addAll(FlintSortBy.getValues());
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        String query;
        super.initialize(parent);
        ((Document) this.document).getChildren().clear();
        String text = this.searchWidget.getText();
        if (this.screenRendererWidget.getScreen() instanceof MyAddonsActivity) {
            query = this.myAddonsActivity.getSearchQuery();
        } else {
            query = this.marketplaceActivity.getSearchQuery();
        }
        if (!text.equals(query)) {
            this.pseudoReload = true;
            this.searchWidget.setText(query);
        }
        FlexibleContentWidget container = (FlexibleContentWidget) new FlexibleContentWidget().addId("container");
        FlexibleContentWidget header = (FlexibleContentWidget) new FlexibleContentWidget().addId("header");
        addTabWidget(header);
        ButtonWidget storeButton = (ButtonWidget) ButtonWidget.component(getScreenName(this.marketplaceActivity, false)).addId("store-button");
        setButtonEnabled(storeButton, this.marketplaceActivity);
        storeButton.setPressable(() -> {
            openScreen(this.marketplaceActivity);
        });
        ButtonWidget installedButton = (ButtonWidget) ButtonWidget.component(getScreenName(this.myAddonsActivity, false)).addId("installed-button");
        setButtonEnabled(installedButton, this.myAddonsActivity);
        installedButton.setPressable(() -> {
            openScreen(this.myAddonsActivity);
        });
        header.addContent(storeButton);
        header.addContent(installedButton);
        container.addContent(header);
        container.addFlexibleContent(this.screenRendererWidget);
        ((Document) this.document).addChild(container);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> files) {
        for (Path file : files) {
            String fileName = file.getFileName().toString();
            if (fileName.endsWith(".jar")) {
                DefaultAddonService addonService = DefaultAddonService.getInstance();
                AddonLoader addonLoader = addonService.addonLoader();
                AddonPreparer addonPreparer = addonLoader.addonPreparer();
                try {
                    InstalledAddonInfo addonInfo = addonLoader.loadAddonInfo(file);
                    if (!addonService.getAddon(addonInfo.getNamespace()).isPresent()) {
                        addonPreparer.loadAddon(addonInfo, AddonPreparer.AddonPrepareContext.RUNTIME);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.fileDropped(mouse, files);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
    }

    private void addTabWidget(FlexibleContentWidget widget) {
        ScreenInstance screen = this.screenRendererWidget.getScreen();
        if (screen instanceof MyAddonsActivity) {
            ComponentWidget component = ComponentWidget.component(getScreenName(screen, true));
            component.addId("title");
            widget.addContent(component);
            widget.addFlexibleContent(this.searchWidget);
            return;
        }
        widget.addContent(this.sortDropdownWidget);
        widget.addFlexibleContent(this.searchWidget);
    }

    private void setButtonEnabled(ButtonWidget widget, ScreenInstance screenInstance) {
        ScreenInstance screen = this.screenRendererWidget.getScreen();
        boolean enabled = screen != screenInstance;
        if ((screenInstance instanceof MarketplaceActivity) && (screen instanceof AddonProfileActivity)) {
            enabled = false;
        }
        widget.setEnabled(enabled);
        widget.setActive(!enabled);
    }

    private boolean openScreen(ScreenInstance screenInstance) {
        ScreenInstance screen = this.screenRendererWidget.getScreen();
        if (screen == screenInstance) {
            return false;
        }
        this.screenRendererWidget.displayScreen(screenInstance);
        return true;
    }

    private Component getScreenName(ScreenInstance screenInstance, boolean title) {
        String key = title ? "title" : "name";
        if (screenInstance instanceof MyAddonsActivity) {
            return Component.translatable("labymod.addons.category.myAddons." + key, new Component[0]);
        }
        return Component.translatable("labymod.addons.category.store." + key, new Component[0]);
    }
}
