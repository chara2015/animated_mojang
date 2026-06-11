package net.labymod.api.configuration.settings.type;

import java.lang.annotation.Annotation;
import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.loader.property.CustomRequires;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.SettingOverlayInfo;
import net.labymod.api.configuration.settings.SwitchableInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.revision.Revision;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/type/SettingElement.class */
public class SettingElement extends AbstractSettingRegistry {
    private final SettingPermissionHolder permissionHolder;
    private final byte orderValue;
    private String customTranslation;
    private String[] searchTags;
    private SwitchableInfo switchableInfo;
    private SettingHandler handler;
    private SettingOverlayInfo overlayInfo;
    private Widget[] widgets;
    private SettingAccessor accessor;
    private SettingAccessor advancedAccessor;
    private Annotation annotation;
    private Runnable resetListener;
    private BooleanSupplier visibleSupplier;
    private boolean extended;
    private Revision revision;

    public SettingElement(String id, Icon icon, String customTranslation, String[] searchTags, SettingPermissionHolder permissionHolder, SwitchableInfo switchableInfo, byte orderValue) {
        super(id, icon);
        this.permissionHolder = new SettingPermissionHolder();
        this.extended = false;
        this.revision = null;
        this.customTranslation = customTranslation;
        this.searchTags = searchTags;
        this.permissionHolder.set(permissionHolder);
        this.switchableInfo = switchableInfo;
        this.orderValue = orderValue;
    }

    public SettingElement(String id, Icon icon, String customTranslation, String[] searchTags) {
        this(id, icon, customTranslation, searchTags, null, null, (byte) 0);
    }

    public SettingAccessor getAccessor() {
        return this.accessor;
    }

    public void setAccessor(SettingAccessor accessor) {
        this.accessor = accessor;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public Runnable getResetListener() {
        return this.resetListener;
    }

    public void setResetListener(Runnable resetListener) {
        this.resetListener = resetListener;
    }

    public boolean isVisible() {
        BooleanSupplier visibilitySupplier;
        if (this.accessor != null && this.accessor.property() != null) {
            BooleanSupplier visibilitySupplier2 = this.accessor.property().getVisibilitySupplier();
            if (visibilitySupplier2 != null) {
                return visibilitySupplier2.getAsBoolean();
            }
            SettingAccessor advancedAccessor = this.accessor.setting().getAdvancedAccessor();
            if (advancedAccessor != null && (visibilitySupplier = advancedAccessor.property().getVisibilitySupplier()) != null) {
                return visibilitySupplier.getAsBoolean();
            }
        }
        return this.visibleSupplier == null || this.visibleSupplier.getAsBoolean();
    }

    public void setVisibleSupplier(BooleanSupplier visibleSupplier) {
        this.visibleSupplier = visibleSupplier;
    }

    public boolean isExtended() {
        return this.extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public Annotation getAnnotation() {
        return this.annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Widget[] getWidgets() {
        return this.widgets;
    }

    public void setWidgets(Widget[] widgets) {
        this.widgets = widgets;
    }

    @Nullable
    public Revision getRevision() {
        return this.revision;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public boolean isEnabled() {
        CustomRequires<Object> customRequires;
        if (!super.isEnabled()) {
            return false;
        }
        if (this.switchableInfo == null || this.switchableInfo.getSwitchAccessor() == null) {
            if (this.accessor != null && (customRequires = this.accessor.property().getCustomRequires()) != null) {
                return customRequires.isEnabled(this.accessor.get());
            }
            return true;
        }
        SettingAccessor accessor = this.switchableInfo.getSwitchAccessor();
        SettingElement setting = accessor.setting();
        if (!setting.isEnabled()) {
            return false;
        }
        Object value = accessor.get();
        boolean invert = this.switchableInfo.isInvert();
        if (value instanceof Boolean) {
            return invert != ((Boolean) value).booleanValue();
        }
        if (setting.advancedAccessor != null) {
            Object advancedValue = setting.advancedAccessor.get();
            if (advancedValue instanceof Boolean) {
                return invert != ((Boolean) advancedValue).booleanValue();
            }
        }
        if (this.switchableInfo.getHandler() != null) {
            return invert != this.switchableInfo.getHandler().isEnabled(this, value, this.switchableInfo);
        }
        CustomRequires<Object> switchable = accessor.property().getCustomRequires();
        if (switchable != null) {
            return invert != switchable.isEnabled(value);
        }
        LabyModLoader labyModLoader = Laby.labyAPI().labyModLoader();
        if (labyModLoader.isLabyModDevelopmentEnvironment() || labyModLoader.isAddonDevelopmentEnvironment()) {
            Laby.labyAPI().minecraft().crashGame("No CustomSwitchable was set for the config property \"" + accessor.getField().getName() + "\". (Use ConfigProperty#setCustomRequires(CustomRequires))", new RuntimeException());
            return false;
        }
        return true;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public boolean hasControlButton() {
        return this.advancedAccessor != null;
    }

    public SettingAccessor getAdvancedAccessor() {
        return this.advancedAccessor;
    }

    public void setAdvancedAccessor(SettingAccessor advancedAccessor) {
        this.advancedAccessor = advancedAccessor;
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    public String getTranslationKey() {
        return this.customTranslation == null ? super.getTranslationKey() : this.customTranslation;
    }

    public void setCustomTranslation(String customTranslation) {
        this.customTranslation = customTranslation;
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    public String[] getSearchTags() {
        return this.searchTags;
    }

    public void setSearchTags(String[] searchTags) {
        this.searchTags = searchTags;
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    @Nullable
    public String getRequiredPermission() {
        return this.permissionHolder.getPermissionId();
    }

    public void setRequiredPermission(@Nullable String requiredPermission) {
        this.permissionHolder.set(requiredPermission);
    }

    public SwitchableInfo getSwitchableInfo() {
        return this.switchableInfo;
    }

    public void setSwitchableInfo(SwitchableInfo switchableInfo) {
        this.switchableInfo = switchableInfo;
    }

    public byte getOrderValue() {
        return this.orderValue;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public SettingHandler handler() {
        return this.handler;
    }

    public void setHandler(SettingHandler handler) {
        this.handler = handler;
    }

    public SettingOverlayInfo getOverlayInfo() {
        return this.overlayInfo;
    }

    public void setOverlayInfo(SettingOverlayInfo overlayInfo) {
        this.overlayInfo = overlayInfo;
    }

    @Deprecated
    public void searchTags(String[] searchTags) {
        setSearchTags(searchTags);
    }
}
