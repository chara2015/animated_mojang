package net.labymod.api.configuration.settings.creator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.configuration.loader.annotation.SearchTag;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.SwitchableHandler;
import net.labymod.api.configuration.settings.SwitchableInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingExperimental;
import net.labymod.api.configuration.settings.annotation.SettingListener;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingRequiresExclude;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.configuration.settings.creator.accessor.SettingAccessorFactoryRegistry;
import net.labymod.api.configuration.settings.creator.availability.DevelopmentMemberAvailability;
import net.labymod.api.configuration.settings.creator.availability.ExcludeMemberAvailability;
import net.labymod.api.configuration.settings.creator.availability.MemberAvailability;
import net.labymod.api.configuration.settings.creator.availability.MemberAvailabilityContext;
import net.labymod.api.configuration.settings.creator.availability.OperatingSystemCompatibleMemberAvailability;
import net.labymod.api.configuration.settings.creator.availability.VersionCompatibleMemberAvailability;
import net.labymod.api.configuration.settings.creator.hotkey.DefaultHotkeyFactory;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyFactory;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.api.configuration.settings.creator.hotkey.SwitchSettingHotkeyFactory;
import net.labymod.api.configuration.settings.creator.visibility.VisibleProcessorRegistry;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.configuration.settings.type.SettingHeader;
import net.labymod.api.configuration.settings.type.SettingPermissionHolder;
import net.labymod.api.configuration.settings.type.list.ListSetting;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;
import net.labymod.api.event.labymod.config.SettingCreateEvent;
import net.labymod.api.revision.Revision;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.reflection.Reflection;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/SettingCreator.class */
public class SettingCreator {
    private static final Logging LOGGER = Logging.getLogger();
    private static final int DEFAULT_SPRITE_SIZE = 128;
    private final LabyAPI labyAPI;
    private final Config config;
    private final Class<? extends Config> configClass;
    private final VisibleProcessorRegistry visibleProcessorRegistry;
    private final SettingAccessorFactoryRegistry settingAccessorFactoryRegistry;
    private final List<MemberAvailability> memberAvailabilities = new ArrayList();
    private final List<SettingEntry> entries = new ArrayList();
    private final List<HotkeyFactory> hotkeyFactories = new ArrayList();
    private SpriteTexture spriteTexture;
    private SettingRequires parentSwitchable;
    private SettingOrder parentOrder;
    private CustomTranslation parentCustomTranslation;

    public SettingCreator(LabyAPI labyAPI, Config config) {
        this.labyAPI = labyAPI;
        this.config = config;
        this.configClass = config.getClass();
        this.visibleProcessorRegistry = new VisibleProcessorRegistry(this.labyAPI);
        this.settingAccessorFactoryRegistry = new SettingAccessorFactoryRegistry(this.labyAPI);
        registerDefaults();
    }

    private void registerDefaults() {
        this.memberAvailabilities.add(new ExcludeMemberAvailability());
        this.memberAvailabilities.add(new DevelopmentMemberAvailability(this.labyAPI));
        this.memberAvailabilities.add(new VersionCompatibleMemberAvailability(this.labyAPI));
        this.memberAvailabilities.add(new OperatingSystemCompatibleMemberAvailability());
        this.hotkeyFactories.add(new DefaultHotkeyFactory());
        this.hotkeyFactories.add(new SwitchSettingHotkeyFactory());
    }

    public List<Setting> createSettings(@Nullable Setting parent, SpriteTexture spriteTexture) {
        SpriteTexture configSpriteTexture = (SpriteTexture) this.configClass.getAnnotation(SpriteTexture.class);
        if (configSpriteTexture != null) {
            spriteTexture = configSpriteTexture;
        }
        this.spriteTexture = spriteTexture;
        this.parentSwitchable = (SettingRequires) Reflection.getAnnotation(this.configClass, SettingRequires.class);
        this.parentOrder = (SettingOrder) Reflection.getAnnotation(this.configClass, SettingOrder.class);
        this.parentCustomTranslation = (CustomTranslation) Reflection.getAnnotation(this.configClass, CustomTranslation.class);
        Reflection.getMembers(this.configClass, true, member -> {
            handleMember(parent, member);
        });
        this.entries.sort(Comparator.comparingInt(o -> {
            return o.setting().getOrderValue();
        }));
        List<Setting> settings = new ArrayList<>(this.entries.size());
        for (SettingEntry entry : this.entries) {
            if (entry.header() != null) {
                settings.add(entry.header());
            }
            settings.add(entry.setting());
        }
        this.entries.clear();
        applySwitchAccessors(settings);
        List<SettingListenerMethod> settingListeners = SettingListenerCollector.collect(this.configClass);
        if (settingListeners.isEmpty()) {
            return settings;
        }
        applyListeners(settings, settingListeners);
        return settings;
    }

    private void applySwitchAccessors(List<Setting> settings) {
        for (Setting setting : settings) {
            if (setting.isElement()) {
                SettingElement element = setting.asElement();
                if (element.getSwitchableInfo() != null) {
                    findTargetSwitchSetting(element, settings);
                }
            }
        }
    }

    private void findTargetSwitchSetting(SettingElement element, List<Setting> settings) {
        net.labymod.api.configuration.settings.annotation.SettingElement switchElement;
        Class<? extends SwitchableHandler> switchableClass;
        SwitchableHandler handler;
        for (Setting switchSetting : settings) {
            SwitchableInfo switchableInfo = element.getSwitchableInfo();
            if (switchSetting.isElement() && switchSetting.getId().equals(switchableInfo.getSwitchId())) {
                SettingElement switchSettingElement = switchSetting.asElement();
                switchableInfo.setSwitchAccessor(switchSettingElement.getAccessor());
                Annotation settingAnnotation = switchSettingElement.getAnnotation();
                if (settingAnnotation != null && (switchElement = (net.labymod.api.configuration.settings.annotation.SettingElement) settingAnnotation.annotationType().getAnnotation(net.labymod.api.configuration.settings.annotation.SettingElement.class)) != null && (switchableClass = switchElement.switchable()) != SwitchableHandler.class && (handler = Laby.references().switchableHandlerRegistry().getHandler(switchableClass)) != null) {
                    switchableInfo.setHandler(handler);
                    return;
                }
                return;
            }
        }
    }

    private void applyListeners(List<Setting> settings, List<SettingListenerMethod> listeners) {
        for (Setting setting : settings) {
            if (setting.isElement()) {
                for (SettingListenerMethod listener : listeners) {
                    if (listener.target().equals(setting.getId())) {
                        SettingListener.EventType eventType = listener.eventType();
                        if (eventType == SettingListener.EventType.RESET) {
                            setting.asElement().setResetListener(() -> {
                                listener.invoke(this.config, setting);
                            });
                        } else {
                            listener.invoke(this.config, setting);
                        }
                    }
                }
            }
        }
    }

    private <T extends Member & AnnotatedElement> void handleMember(@Nullable Setting parent, T member) {
        MemberInspector inspector = new MemberInspector(member);
        String namespace = this.labyAPI.getNamespace((Class<?>) this.configClass);
        MemberAvailabilityContext context = new MemberAvailabilityContext(namespace, inspector);
        for (MemberAvailability memberAvailability : this.memberAvailabilities) {
            boolean available = memberAvailability.isAvailable(context);
            if (!available) {
                return;
            }
        }
        SettingHeader header = createHeader(inspector);
        SettingEntry settingEntry = new SettingEntry(header, createElement(parent, inspector, namespace));
        registerHotkeyForSetting(settingEntry, inspector);
        this.entries.add(settingEntry);
    }

    private void registerHotkeyForSetting(SettingEntry entry, MemberInspector inspector) {
        Hotkey hotkey;
        String namespace = this.labyAPI.getNamespace((Class<?>) this.configClass);
        String displayName = this.labyAPI.getDisplayName((Class<?>) this.configClass);
        for (HotkeyFactory hotkeyFactory : this.hotkeyFactories) {
            if (hotkeyFactory.hasSettingAnnotation(inspector) && (hotkey = hotkeyFactory.create(inspector, displayName, entry, namespace)) != null) {
                HotkeyHolder.getInstance().registerHotkey(hotkey);
                return;
            }
        }
    }

    private void dumpIcon(@Nullable Setting parent, SettingElement settingElement) {
        Icon icon = settingElement.getIcon();
        if (icon == null || !IdeUtil.DUMP_SPRITE_ICONS) {
            return;
        }
        settingElement.setParent(parent);
        String locationPath = settingElement.getTranslationKey();
        String path = TextFormat.CAMEL_CASE.toSnakeCase(locationPath);
        IdeUtil.dumpSpriteIcons(path.replace("classic_pv_p", "classic_pvp"), icon);
    }

    private SettingElement createElement(@Nullable Setting parent, MemberInspector inspector, String namespace) {
        String id = inspector.getName();
        Icon icon = createIcon(namespace, inspector);
        SettingRequires switchable = (SettingRequires) inspector.orElse(SettingRequires.class, this.parentSwitchable);
        SettingOrder order = (SettingOrder) inspector.orElse(SettingOrder.class, this.parentOrder);
        SettingPermissionHolder permissionHolder = (SettingPermissionHolder) inspector.getOrElse(PermissionRequired.class, SettingPermissionHolder::new, new SettingPermissionHolder());
        byte orderValue = order == null ? (byte) 0 : order.value();
        String translationKey = buildTranslationKey(id, inspector);
        String[] tags = getTags(inspector);
        SwitchableInfo switchableInfo = createSwitchableInfo(id, switchable, inspector);
        SettingElement settingElement = new SettingElement(id, icon, translationKey, tags, permissionHolder, switchableInfo, orderValue);
        dumpIcon(parent, settingElement);
        settingElement.setVisibleSupplier(this.visibleProcessorRegistry.canSeeElement(inspector));
        settingElement.setExperimental(inspector.isAnnotationPresent(SettingExperimental.class));
        SettingAccessor accessor = this.settingAccessorFactoryRegistry.createAccessor(settingElement, inspector, this.config);
        if (inspector.isAnnotationPresent(ShowSettingInParent.class) && (parent instanceof SettingElement)) {
            SettingElement parentElement = (SettingElement) parent;
            parentElement.setSearchTags(tags);
            parentElement.setAdvancedAccessor(accessor);
            parentElement.setRequiredPermission(permissionHolder.getPermissionId());
            SettingInfo<?> settingInfo = new SettingInfo<>(this.config, (Member) inspector.member());
            WidgetRegistry widgetRegistry = this.labyAPI.widgetRegistry();
            parentElement.setWidgets(widgetRegistry.createWidgets(settingElement, settingInfo, accessor));
            if (((ShowSettingInParent) inspector.getAnnotation(ShowSettingInParent.class)).showOnlyInParent()) {
                return settingElement;
            }
        }
        if (inspector.isAnnotationPresent(ParentSwitch.class) && (parent instanceof SettingElement)) {
            SettingElement parentElement2 = (SettingElement) parent;
            parentElement2.setSearchTags(tags);
            parentElement2.setAdvancedAccessor(accessor);
            parentElement2.setRequiredPermission(permissionHolder.getPermissionId());
            SettingInfo<?> settingInfo2 = new SettingInfo<>(this.config, (Member) inspector.member());
            WidgetRegistry widgetRegistry2 = this.labyAPI.widgetRegistry();
            Widget[] widgets = widgetRegistry2.createWidgets(settingElement, settingInfo2, accessor);
            if (widgets == null) {
                Objects.requireNonNull(accessor);
                SwitchWidget controlButton = SwitchWidget.create((v1) -> {
                    r0.set(v1);
                });
                controlButton.setValue(((Boolean) accessor.get()).booleanValue());
                controlButton.addId("advanced-control-button");
                widgets = new Widget[]{controlButton};
            }
            parentElement2.setWidgets(widgets);
            if (((ParentSwitch) inspector.getAnnotation(ParentSwitch.class)).hide()) {
                return settingElement;
            }
        }
        WidgetRegistry widgetRegistry3 = this.labyAPI.widgetRegistry();
        settingElement.setParent(parent);
        settingElement.setWidgets(widgetRegistry3.createWidgets(settingElement, new SettingInfo<>(this.config, (Member) inspector.member()), accessor));
        if (accessor != null && List.class.isAssignableFrom(accessor.getType()) && settingElement.getWidgets() == null) {
            try {
                return new ListSetting(id, icon, translationKey, tags, permissionHolder, switchableInfo, orderValue, accessor);
            } catch (Exception exception) {
                LOGGER.error("Failed to create ListSetting", exception);
            }
        }
        settingElement.setAccessor(accessor);
        Revision revision = (Revision) inspector.get(IntroducedIn.class, introducedIn -> {
            return Laby.references().revisionRegistry().getRevision(namespace, introducedIn.value());
        });
        settingElement.setRevision(revision);
        initializeChildConfig(settingElement, inspector);
        Laby.fireEvent(new SettingCreateEvent(settingElement));
        SettingHandler handler = settingElement.handler();
        if (handler != null) {
            handler.created(settingElement);
        }
        return settingElement;
    }

    private void initializeChildConfig(SettingElement setting, MemberInspector inspector) {
        Config childConfig;
        AnnotatedElement annotatedElementMember = inspector.member();
        if (!(annotatedElementMember instanceof Field)) {
            return;
        }
        Field field = (Field) annotatedElementMember;
        if (!Config.class.isAssignableFrom(field.getType()) || (childConfig = (Config) Reflection.invokeGetterField(this.config, field)) == null) {
            return;
        }
        setting.addSettings(childConfig.toSettings(setting, this.spriteTexture));
    }

    @Nullable
    private SettingHeader createHeader(MemberInspector inspector) {
        SettingSection annotation = (SettingSection) inspector.getAnnotation(SettingSection.class);
        if (annotation == null) {
            return null;
        }
        String translationId = annotation.value();
        String sectionId = translationId.replace(".", "_");
        return new SettingHeader(sectionId, annotation.center(), annotation.translation(), translationId);
    }

    private SwitchableInfo createSwitchableInfo(String id, SettingRequires switchable, MemberInspector inspector) {
        if (switchable == null || id.equals(switchable.value()) || inspector.isAnnotationPresent(SettingRequiresExclude.class)) {
            return null;
        }
        return new SwitchableInfo(switchable);
    }

    @Nullable
    private Icon createIcon(String namespace, MemberInspector inspector) {
        SpriteSlot slot;
        if (this.spriteTexture == null || (slot = (SpriteSlot) inspector.getAnnotation(SpriteSlot.class)) == null) {
            return null;
        }
        ThemeTextureLocation location = Laby.references().resourceLocationFactory().createThemeTexture(namespace, "textures/" + getSpritePath(slot), 128, 128);
        return Icon.sprite(location, slot.x(), slot.y(), slot.size(), slot.size());
    }

    private String getSpritePath(SpriteSlot slot) {
        int page = slot.page();
        String suffix = page == 0 ? "" : "_" + page;
        String texturePath = this.spriteTexture.value();
        int lastDotIndex = texturePath.lastIndexOf(46);
        if (lastDotIndex != -1) {
            texturePath = texturePath.substring(0, lastDotIndex);
        }
        return texturePath + suffix + "." + this.spriteTexture.type();
    }

    private String[] getTags(MemberInspector inspector) {
        SearchTag annotation = (SearchTag) inspector.getAnnotation(SearchTag.class);
        if (annotation == null) {
            return new String[0];
        }
        return annotation.value();
    }

    private String buildTranslationKey(String id, MemberInspector inspector) {
        String translationKey = (String) inspector.getOrElse(CustomTranslation.class, (v0) -> {
            return v0.value();
        }, buildParentTranslationKey());
        if (translationKey != null && translationKey.endsWith(".")) {
            translationKey = translationKey + id;
        }
        return translationKey;
    }

    private String buildParentTranslationKey() {
        CustomTranslation translation = this.parentCustomTranslation;
        if (translation == null) {
            return null;
        }
        String translationKey = translation.value();
        if (!translationKey.endsWith(".")) {
            translationKey = translationKey + ".";
        }
        return translationKey;
    }
}
