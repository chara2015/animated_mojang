package net.labymod.api.client.gui.screen.activity.activities.labymod.child;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingHeaderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.type.SettingGroup;
import net.labymod.api.configuration.settings.type.SettingHeader;
import net.labymod.api.configuration.settings.type.list.ListSetting;
import net.labymod.api.configuration.settings.util.SettingActivitySupplier;
import net.labymod.api.event.labymod.config.SettingWidgetInitializeEvent;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/activities/labymod/child/SettingContentActivity.class */
@Links({@Link("tabbed.lss"), @Link(value = "activity/settings.lss", priority = -64)})
@AutoActivity
public class SettingContentActivity extends Activity {
    private final Setting originHolder;
    private Map<String, ListSession<?>> sessions;
    private Setting currentHolder;
    private Function<Setting, Setting> screenCallback;
    private HeaderType headerType;
    private Runnable closeHandler;
    private final boolean lazy;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/activities/labymod/child/SettingContentActivity$HeaderType.class */
    public enum HeaderType {
        NONE,
        SCROLL,
        FIXED,
        SCROLL_IN_CHILDREN,
        FIXED_IN_CHILDREN
    }

    public SettingContentActivity(Setting holder) {
        this(holder, true);
    }

    public SettingContentActivity(Setting holder, boolean lazy) {
        if (!holder.isInitialized()) {
            throw new IllegalStateException("Setting is not initialized yet!");
        }
        this.originHolder = holder;
        this.sessions = new HashMap();
        this.currentHolder = holder;
        this.headerType = HeaderType.FIXED;
        this.lazy = lazy;
    }

    public SettingContentActivity screenCallback(Consumer<Setting> screenCallback) {
        this.screenCallback = setting -> {
            screenCallback.accept(setting);
            return setting;
        };
        return this;
    }

    public SettingContentActivity screenCallback(Function<Setting, Setting> screenCallback) {
        this.screenCallback = screenCallback;
        return this;
    }

    public SettingContentActivity closeHandler(Runnable closeHandler) {
        this.closeHandler = closeHandler;
        return this;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("content", "header-" + this.headerType.name().toLowerCase(Locale.ROOT));
        HorizontalListWidget header = new HorizontalListWidget();
        if (this.headerType != HeaderType.NONE) {
            header = new HorizontalListWidget();
            header.addId("setting-header");
            boolean hasParent = this.currentHolder.hasParent() && this.currentHolder.parent().isHoldable();
            if (this.closeHandler != null || hasParent || (!isOriginHolder() && isOriginFiltered())) {
                ButtonWidget backButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
                backButton.addId("back-button");
                if (this.currentHolder == this.originHolder) {
                    backButton.addId("back-button-origin");
                }
                if (this.currentHolder.hasParent() && (this.currentHolder.parent() instanceof ListSetting)) {
                    backButton.addId("back-button-list-entry");
                }
                backButton.setPressable(() -> {
                    if (hasParent) {
                        this.currentHolder = this.currentHolder.parent();
                    } else {
                        this.currentHolder = this.originHolder;
                        if (this.closeHandler != null) {
                            this.closeHandler.run();
                        }
                    }
                    if (this.screenCallback != null) {
                        this.currentHolder = this.screenCallback.apply(this.currentHolder);
                    }
                    if (this.currentHolder != null) {
                        reload();
                    }
                });
                header.addEntry(backButton);
            }
            Icon icon = this.currentHolder.getIcon();
            if (icon != null) {
                IconWidget iconWidget = new IconWidget(icon);
                iconWidget.addId("parent-icon");
                header.addEntry(iconWidget);
            }
            Component titleComponent = this.currentHolder.displayName();
            if (titleComponent != null) {
                ComponentWidget title = ComponentWidget.component(titleComponent);
                title.addId("title");
                header.addEntry(title);
            }
            if (this.currentHolder instanceof ListSetting) {
                ButtonWidget addButton = ButtonWidget.icon(Textures.SpriteCommon.SMALL_ADD_WITH_SHADOW);
                addButton.addId("add-button");
                addButton.setPressable(() -> {
                    ListSetting list = (ListSetting) this.currentHolder;
                    this.currentHolder = list.createNew();
                    if (this.screenCallback != null) {
                        this.currentHolder = this.screenCallback.apply(this.currentHolder);
                    }
                    if (this.currentHolder != null) {
                        reload();
                    }
                });
                header.addEntry(addButton).alignment().set(HorizontalAlignment.RIGHT);
            }
            if (this.headerType == HeaderType.FIXED || (this.headerType == HeaderType.FIXED_IN_CHILDREN && this.currentHolder.hasParent() && (this.currentHolder.parent().isHoldable() || (this.currentHolder.parent() instanceof ListSetting)))) {
                container.addContent(header);
            }
        }
        ListSession<?> session = this.sessions.computeIfAbsent(this.currentHolder.getPath(), function -> {
            return new ListSession();
        });
        if (this.currentHolder.isElement()) {
            SettingElement settingElement = this.currentHolder.asElement();
            SettingHandler handler = settingElement.handler();
            Activity activity = null;
            if (settingElement.getWidgets() != null && (settingElement.getWidgets()[0] instanceof SettingActivitySupplier)) {
                activity = ((SettingActivitySupplier) settingElement.getWidgets()[0]).activity(settingElement);
            } else if (handler != null && handler.opensActivity(settingElement)) {
                activity = handler.activity(settingElement);
            }
            if (activity != null) {
                ScreenRendererWidget screenRendererWidget = new ScreenRendererWidget();
                screenRendererWidget.addId("addon-activity-renderer");
                screenRendererWidget.displayScreen(activity);
                container.addFlexibleContent(screenRendererWidget);
                ((Document) this.document).addChild(container);
                return;
            }
        }
        VerticalListWidget<Widget> settingsList = new VerticalListWidget<>();
        settingsList.addId("list");
        if (this.headerType == HeaderType.SCROLL || (this.headerType == HeaderType.SCROLL_IN_CHILDREN && this.currentHolder.hasParent() && (this.currentHolder.parent().isHoldable() || (this.currentHolder.parent() instanceof ListSetting)))) {
            settingsList.addChild(header);
        }
        List<Widget> list = new ArrayList<>();
        Setting parentSetting = this.currentHolder;
        for (KeyValue<Setting> element : this.currentHolder.getElements()) {
            Setting setting = element.getValue();
            if (setting.isElement() && !setting.hasAdvancedButton()) {
                SettingElement settingElement2 = setting.asElement();
                if (settingElement2.hasControlButton() || settingElement2.getWidgets() != null) {
                }
            }
            if (setting instanceof SettingHeader) {
                SettingHeader settingHeader = (SettingHeader) setting;
                List<Component> rows = settingHeader.getRows();
                for (int i = 0; i < rows.size(); i++) {
                    Component row = rows.get(i);
                    SettingHeaderWidget settingHeaderWidget = new SettingHeaderWidget(row, settingHeader.pressable());
                    if (rows.size() > 1) {
                        if (i == 0) {
                            settingHeaderWidget.addId("header-first");
                        }
                        if (i == rows.size() - 1) {
                            settingHeaderWidget.addId("header-last");
                        }
                    } else {
                        settingHeaderWidget.addId("header-single");
                    }
                    list.add(settingHeaderWidget.addId("header-" + (settingHeader.isCenter() ? "center" : "left")));
                }
            } else {
                list.add(new SettingWidget(setting, this.lazy, layer -> {
                    this.currentHolder = layer != null ? layer : parentSetting;
                    if (this.screenCallback != null) {
                        this.currentHolder = this.screenCallback.apply(this.currentHolder);
                    }
                    if (this.currentHolder != null) {
                        reload();
                    }
                }));
            }
        }
        this.labyAPI.eventBus().fire(new SettingWidgetInitializeEvent((ParentScreen) getParent(), this.currentHolder, list));
        for (Widget settingWidget : list) {
            settingsList.addChild(settingWidget);
        }
        ScrollWidget scrollWidget = new ScrollWidget(settingsList, session);
        scrollWidget.addId("scroll");
        if (this.headerType == HeaderType.FIXED) {
            scrollWidget.addId("header-spacing");
        }
        container.addFlexibleContent(scrollWidget);
        ((Document) this.document).addChild(container);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity
    public boolean displayPreviousScreen() {
        return false;
    }

    public void setHeaderType(HeaderType headerType) {
        this.headerType = headerType;
    }

    public void setSessions(Map<String, ListSession<?>> sessions) {
        this.sessions = sessions;
    }

    private boolean isOriginHolder() {
        return this.currentHolder == this.originHolder;
    }

    public Setting getCurrentHolder() {
        return this.currentHolder;
    }

    public Setting getOriginHolder() {
        return this.originHolder;
    }

    private boolean isOriginFiltered() {
        return (this.originHolder instanceof SettingGroup) && ((SettingGroup) this.originHolder).isFiltered();
    }
}
