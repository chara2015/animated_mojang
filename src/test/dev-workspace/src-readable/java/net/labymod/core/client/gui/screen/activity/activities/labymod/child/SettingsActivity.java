package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.CategoryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.HrWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.configuration.settings.type.SettingGroup;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.ConfigurationSaveEvent;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.I18n;
import net.labymod.api.util.collection.Lists;
import net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/SettingsActivity.class */
@Link("activity/settings.lss")
@AutoActivity
public class SettingsActivity extends AbstractSidebarActivity {
    private final AbstractSettingRegistry registry;
    private Setting defaultSetting;

    @Nullable
    private Setting selectedSetting;
    private Setting previousSetting;
    private Widget temporarySettingWidget;
    private Setting lastFilter;

    public SettingsActivity(AbstractSettingRegistry registry) {
        this.registry = registry;
        updateScreen();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.screenRendererWidget.getScreen() == null) {
            updateScreen();
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity
    public void onCategoryListInitialize(VerticalListWidget<Widget> categoryList) {
        boolean searching = !this.searchWidget.getText().trim().isEmpty();
        List<Setting> addonCategories = new ArrayList<>();
        this.registry.forEach(category -> {
            if ((category instanceof RootSettingRegistry) && ((RootSettingRegistry) category).isAddon()) {
                String namespace = ((RootSettingRegistry) category).getNamespace();
                this.labyAPI.addonService().getAddon(namespace).ifPresent(addon -> {
                    addonCategories.add(category);
                });
                return;
            }
            if (this.defaultSetting == null) {
                this.defaultSetting = category;
            }
            if (this.selectedSetting == null) {
                this.selectedSetting = category;
                this.previousSetting = category;
                updateScreen();
            }
            categoryList.addChild(createCategory(category, searching));
        });
        if (addonCategories.isEmpty()) {
            return;
        }
        HrWidget addonHrWidget = (HrWidget) new HrWidget().addId("addon");
        categoryList.addChild(addonHrWidget);
        categoryList.addChild(ComponentWidget.i18n("labymod.activity.settings.addons.name").addId("sub-category"));
        addonCategories.sort((a, b) -> {
            String aName = I18n.translate(a.getTranslationKey() + ".name", new Object[0]);
            String bName = I18n.translate(b.getTranslationKey() + ".name", new Object[0]);
            return aName.compareTo(bName);
        });
        for (Setting addonCategory : addonCategories) {
            categoryList.addChild(createCategory(addonCategory, searching));
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity
    protected void initializeSidebarFooter(DivWidget widget) {
        super.initializeSidebarFooter(widget);
    }

    @Subscribe
    public void laby(LabyConnectStateUpdateEvent event) {
        if (event.state() == LabyConnectState.PLAY) {
            this.labyAPI.minecraft().executeOnRenderThread(this::reload);
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity
    public void onSearchUpdateListener(String searchContent) {
        if (updateScreen()) {
            reload();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        Laby.fireEvent(new ConfigurationSaveEvent());
    }

    private void resetSearch() {
        if (!(this.selectedSetting instanceof SettingGroup)) {
            this.selectedSetting = this.selectedSetting == null ? this.defaultSetting : this.selectedSetting;
            displayScreen((Activity) this.selectedSetting.createActivityLazy());
            return;
        }
        Setting previousSetting = this.previousSetting;
        if (previousSetting instanceof SettingGroup) {
            this.selectedSetting = this.defaultSetting;
            displayScreen((Activity) this.selectedSetting.createActivityLazy());
        } else {
            this.selectedSetting = previousSetting;
            displayScreen((Activity) previousSetting.createActivityLazy());
        }
    }

    private boolean updateScreen() {
        String text = this.searchWidget.getText().trim();
        if (text.isEmpty()) {
            this.searchWidget.setEditable(true);
            if (this.selectedSetting == null) {
                return true;
            }
            if (this.temporarySettingWidget != null) {
                this.screenRendererWidget.removeChild(this.temporarySettingWidget);
                this.temporarySettingWidget = null;
            }
            resetSearch();
            return true;
        }
        if (text.length() < 2) {
            displayScreen((Activity) null);
            if (this.temporarySettingWidget != null) {
                this.screenRendererWidget.removeChild(this.temporarySettingWidget);
            }
            this.temporarySettingWidget = createEmptySetting(text, "tooShort");
            this.screenRendererWidget.addChildInitialized(this.temporarySettingWidget);
            return true;
        }
        List<Setting> collectedSettings = this.registry.collect(setting -> {
            return filterSettings(text, setting);
        });
        List<Setting> settings = Lists.newDistinctArrayList(collectedSettings, true);
        if (settings.isEmpty()) {
            displayScreen((Activity) null);
            if (this.temporarySettingWidget != null) {
                this.screenRendererWidget.removeChild(this.temporarySettingWidget);
            }
            this.temporarySettingWidget = createEmptySetting(text, "noResults");
            this.screenRendererWidget.addChildInitialized(this.temporarySettingWidget);
            return false;
        }
        if (this.temporarySettingWidget != null) {
            this.screenRendererWidget.removeChild(this.temporarySettingWidget);
            this.temporarySettingWidget = null;
        }
        SettingGroup group = SettingGroup.named(Component.text(text)).of(settings).filtered(true);
        if (!(this.selectedSetting instanceof SettingGroup)) {
            this.previousSetting = this.selectedSetting;
        }
        this.selectedSetting = group;
        SettingContentActivity activity = group.createActivityLazy();
        activity.screenCallback(setting2 -> {
            if (setting2 == group) {
                this.lastFilter = null;
                this.searchWidget.setEditable(true);
                return group;
            }
            Setting parent = setting2.parent();
            if (isLabyModRootSetting(parent) && this.lastFilter != null && !isLabyModRootSetting(this.lastFilter.parent())) {
                this.lastFilter = null;
                this.searchWidget.setEditable(true);
                return group;
            }
            if (this.lastFilter == null && !isLabyModRootSetting(setting2)) {
                this.lastFilter = setting2;
                this.searchWidget.setEditable(false);
            } else if (this.lastFilter != null && setting2 == this.lastFilter.parent()) {
                this.lastFilter = null;
                this.searchWidget.setEditable(true);
                return group;
            }
            this.searchWidget.setEditable(false);
            return setting2;
        });
        displayScreen((Activity) activity);
        return true;
    }

    private boolean isLabyModRootSetting(Setting setting) {
        if (!(setting instanceof RootSettingRegistry)) {
            return false;
        }
        RootSettingRegistry rootSettingRegistry = (RootSettingRegistry) setting;
        return rootSettingRegistry.getNamespace().equals("labymod");
    }

    private boolean filterSettings(String searchTerm, Setting setting) {
        if (!setting.isElement()) {
            return false;
        }
        if (CharSequences.containsLowercase(I18n.translate(setting.getTranslationKey() + ".name", new Object[0]), searchTerm)) {
            return true;
        }
        for (String s : setting.getSearchTags()) {
            if (CharSequences.containsLowercase(s, searchTerm)) {
                return true;
            }
        }
        if (CharSequences.containsLowercase(I18n.translate(setting.getTranslationKey() + ".description", new Object[0]), searchTerm)) {
            return true;
        }
        return false;
    }

    private CategoryWidget createCategory(Setting setting, boolean searching) {
        CategoryWidget widget = new CategoryWidget(setting);
        widget.setEnabled(this.selectedSetting != setting || searching);
        widget.setActive(this.selectedSetting == setting && !searching);
        widget.setPressable(() -> {
            setSelectedSetting(setting);
            reload();
        });
        return widget;
    }

    public void setSelectedSetting(Setting setting) {
        this.selectedSetting = setting;
        this.lastFilter = null;
        this.searchWidget.setEditable(true);
        this.searchWidget.setText("");
        this.searchWidget.setFocused(false);
        updateScreen();
    }

    private Widget createEmptySetting(String query, String translationKey) {
        FlexibleContentWidget content = new FlexibleContentWidget();
        content.addId("content");
        HorizontalListWidget header = new HorizontalListWidget();
        header.addId("setting-header");
        ComponentWidget title = ComponentWidget.text(query);
        title.addId("title");
        header.addEntry(title);
        content.addContent(header);
        DivWidget infoWrapper = new DivWidget();
        infoWrapper.addId("info-wrapper");
        ComponentWidget info = ComponentWidget.component(Component.translatable("labymod.activity.settings.search." + translationKey, new Component[0]));
        info.addId("info");
        infoWrapper.addChild(info);
        content.addFlexibleContent(infoWrapper);
        return content;
    }
}
