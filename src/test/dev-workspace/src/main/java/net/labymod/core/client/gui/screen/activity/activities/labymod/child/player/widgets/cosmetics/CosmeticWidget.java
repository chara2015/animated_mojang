package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.action.Switchable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.Debounce;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.CosmeticsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.labymodnet.models.ChangeResponse;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.metadata.util.ItemMetadataUtil;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/cosmetics/CosmeticWidget.class */
@AutoWidget
public class CosmeticWidget extends FlexibleContentWidget {
    private static final LabyModNetService LABY_MOD_NET_SERVICE = LabyMod.references().labyModNetService();
    private static final String DEBOUNCE_ID = "cosmetic-toggle-debounce";
    private static final String INVALID_OPTIONS_TRANSLATION_KEY = "labymod.activity.customization.cosmetics.invalidOptions";
    private static final String DASHBOARD_ONLY_TRANSLATION_KEY = "labymod.activity.customization.cosmetics.dashboardOnly";
    private final WidgetOptionService widgetOptionService;
    private final CosmeticsActivity cosmeticsActivity;
    private final Cosmetic cosmetic;
    private final CosmeticDetails itemDetails;

    @Nullable
    private final Consumer<Cosmetic> cosmeticUpdateListener;
    private ItemTextureListener itemTextureListener;
    private SwitchWidget switchWidget;
    private CosmeticSettingsWidget cosmeticSettingsWidget;
    private boolean waitingForResponse;
    private boolean invalidCosmeticData;
    private List<WidgetOption> options = Collections.emptyList();
    private final CosmeticWidgetSettingsListener listener = new CosmeticWidgetSettingsListener(this);

    public CosmeticWidget(CosmeticsActivity cosmeticsActivity, WidgetOptionService widgetOptionService, Cosmetic cosmetic, CosmeticDetails itemDetails, @Nullable Consumer<Cosmetic> cosmeticUpdateListener) {
        this.cosmetic = cosmetic;
        this.itemDetails = itemDetails;
        this.widgetOptionService = widgetOptionService;
        this.cosmeticsActivity = cosmeticsActivity;
        this.cosmeticUpdateListener = cosmeticUpdateListener;
        this.lazy = true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        Icon icon;
        Component component;
        super.initialize(parent);
        setCosmeticActive(this.cosmetic.isEnabled());
        int id = this.cosmetic.getItemId();
        boolean dashboardOnly = id == 32 || id == 36 || id == 0 || id == 22;
        if (this.options.isEmpty()) {
            this.options = this.widgetOptionService.getOptions(this.cosmetic, () -> {
                if (dashboardOnly) {
                    return;
                }
                this.invalidCosmeticData = true;
            });
        }
        DivWidget iconWrapper = new DivWidget();
        iconWrapper.addId("icon-wrapper");
        IconWidget icon2 = new IconWidget(this.cosmetic.icon());
        icon2.addId("icon");
        icon2.setCleanupOnDispose(true);
        iconWrapper.addChild(icon2);
        boolean invalidCosmeticData = dashboardOnly || hasInvalidCosmeticData();
        if (invalidCosmeticData) {
            icon = Textures.SpriteCommon.EXCLAMATION_MARK_DARK;
        } else {
            icon = Textures.SpriteCommon.SETTINGS;
        }
        IconWidget wrappedIcon = new IconWidget(icon);
        wrappedIcon.addId(invalidCosmeticData ? "error-icon" : "settings-icon");
        if (invalidCosmeticData) {
            if (dashboardOnly) {
                component = Component.translatable(DASHBOARD_ONLY_TRANSLATION_KEY, new Component[0]);
            } else {
                component = Component.translatable(INVALID_OPTIONS_TRANSLATION_KEY, new Component[0]);
            }
            wrappedIcon.setHoverComponent(component);
            wrappedIcon.setVisible(true);
        }
        iconWrapper.addChild(wrappedIcon);
        addFlexibleContent(iconWrapper);
        FlexibleContentWidget infoWrapper = new FlexibleContentWidget();
        infoWrapper.addId("info-wrapper");
        ComponentWidget name = ComponentWidget.text(this.cosmetic.getName());
        name.addId("name");
        infoWrapper.addFlexibleContent(name);
        this.switchWidget = SwitchWidget.text("", "", this::updateCosmeticEnabled);
        this.switchWidget.setValue(this.cosmetic.isEnabled());
        this.switchWidget.addId("switch");
        infoWrapper.addContent(this.switchWidget);
        addContent(infoWrapper);
    }

    public void setCosmeticActive(boolean active) {
        this.cosmetic.setEnabled(active);
        if (this.switchWidget != null && this.switchWidget.getValue() != active) {
            this.switchWidget.setValue(active);
            this.switchWidget.refreshActionTime();
        }
        if (this.cosmeticSettingsWidget != null) {
            this.cosmeticSettingsWidget.setCosmeticEnabled(active);
        }
        if (active) {
            addId("active");
        } else {
            removeId("active");
        }
        if (this.cosmeticsActivity.getOpenSettingsId() == this.cosmetic.getItemId()) {
            if (active) {
                this.cosmeticsActivity.updateModelFocus(this.itemDetails, this.cosmetic);
            } else {
                this.cosmeticsActivity.resetModelFocus();
            }
        }
        GameUser user = Laby.references().gameUserService().clientGameUser();
        LabyMod.references().shopItemLayer().resetAnimations(user, true);
    }

    public void updateCosmeticEnabled(boolean enabled) {
        updateCosmeticEnabled(enabled, false);
    }

    public void updateCosmeticEnabled(boolean enabled, boolean skipListener) {
        setCosmeticActive(enabled);
        this.waitingForResponse = true;
        if (!skipListener && this.cosmeticUpdateListener != null) {
            this.cosmeticUpdateListener.accept(this.cosmetic);
        }
        if (enabled) {
            displayCosmeticOptions(false);
        }
        ItemMetadataUtil.updateGameUser(this.cosmetic, this.itemTextureListener);
        Debounce.of("cosmetic-toggle-debounce" + this.cosmetic.getId(), 1000L, () -> {
            LABY_MOD_NET_SERVICE.toggleCosmetic(this.cosmetic, enabled, changeResponse -> {
                this.labyAPI.minecraft().executeOnRenderThread(() -> {
                    handleToggleResponse(changeResponse);
                });
            });
        });
    }

    public void onCloseSettings(CosmeticSettingsWidget cosmeticSettingsWidget) {
        setAttributeState(AttributeState.ACTIVE, false);
        this.cosmeticSettingsWidget = null;
    }

    public void onOpenSettings(CosmeticSettingsWidget cosmeticSettingsWidget) {
        setAttributeState(AttributeState.ACTIVE, true);
        this.cosmeticSettingsWidget = cosmeticSettingsWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        if (hasInvalidCosmeticData()) {
            return false;
        }
        displayCosmeticOptions(true);
        return true;
    }

    private void displayCosmeticOptions(boolean playSound) {
        if (playSound) {
            this.labyAPI.minecraft().sounds().playButtonPress();
        }
        this.cosmeticsActivity.updateModelFocus(this.itemDetails, this.cosmetic);
        CosmeticSettingsWidget openSettings = this.cosmeticsActivity.getOpenSettings();
        if (openSettings != null && openSettings.cosmetic().getId() == this.cosmetic.getId()) {
            return;
        }
        this.cosmeticsActivity.displayOptions(new CosmeticSettingsWidget(this.cosmetic, this.options, this.listener));
    }

    private void handleToggleResponse(ChangeResponse changeResponseResult) {
        this.waitingForResponse = false;
        if (changeResponseResult == null || !changeResponseResult.isDone()) {
            updateCosmeticEnabled(false);
            WidgetOption.pushNotification(changeResponseResult);
        }
    }

    public boolean isWaitingForResponse() {
        return this.waitingForResponse;
    }

    public boolean hasInvalidCosmeticData() {
        return this.invalidCosmeticData;
    }

    public CosmeticsActivity cosmeticsActivity() {
        return this.cosmeticsActivity;
    }

    public Cosmetic cosmetic() {
        return this.cosmetic;
    }

    public CosmeticDetails itemDetails() {
        return this.itemDetails;
    }

    public void setItemTextureListener(ItemTextureListener itemTextureListener) {
        this.itemTextureListener = itemTextureListener;
    }

    public void updateModelFocus(Cosmetic cosmetic) {
        CosmeticsActivity activity = cosmeticsActivity();
        if (activity.getOpenSettingsId() == cosmetic.getItemId()) {
            activity.updateModelFocus(itemDetails(), cosmetic);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/cosmetics/CosmeticWidget$CosmeticWidgetSettingsListener.class */
    private static final class CosmeticWidgetSettingsListener extends Record implements CosmeticSettingsWidget.CosmeticSettingsListener {
        private final CosmeticWidget widget;

        private CosmeticWidgetSettingsListener(CosmeticWidget widget) {
            this.widget = widget;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CosmeticWidgetSettingsListener.class), CosmeticWidgetSettingsListener.class, "widget", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/cosmetics/CosmeticWidget$CosmeticWidgetSettingsListener;->widget:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/cosmetics/CosmeticWidget;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CosmeticWidgetSettingsListener.class), CosmeticWidgetSettingsListener.class, "widget", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/cosmetics/CosmeticWidget$CosmeticWidgetSettingsListener;->widget:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/cosmetics/CosmeticWidget;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CosmeticWidgetSettingsListener.class, Object.class), CosmeticWidgetSettingsListener.class, "widget", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/cosmetics/CosmeticWidget$CosmeticWidgetSettingsListener;->widget:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/cosmetics/CosmeticWidget;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public CosmeticWidget widget() {
            return this.widget;
        }

        @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
        public void onOpenSettings(CosmeticSettingsWidget widget) {
            this.widget.onOpenSettings(widget);
        }

        @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
        public void onCloseSettings(CosmeticSettingsWidget widget) {
            this.widget.onCloseSettings(widget);
        }

        @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
        @Nullable
        public Switchable onSwitch() {
            CosmeticWidget cosmeticWidget = this.widget;
            Objects.requireNonNull(cosmeticWidget);
            return cosmeticWidget::updateCosmeticEnabled;
        }

        @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
        public void onDataUpdate(Cosmetic cosmetic) {
            ItemMetadataUtil.updateGameUser(cosmetic, null);
            this.widget.updateModelFocus(cosmetic);
        }

        @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget.CosmeticSettingsListener
        public boolean shouldSendRemoteRequest() {
            return true;
        }
    }
}
