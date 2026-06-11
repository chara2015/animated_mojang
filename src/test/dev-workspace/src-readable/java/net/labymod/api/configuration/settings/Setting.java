package net.labymod.api.configuration.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.labymod.config.SettingInitializeEvent;
import net.labymod.api.service.Identifiable;
import net.labymod.api.service.Registry;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.io.Filter;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/Setting.class */
public interface Setting extends Identifiable, Registry<Setting> {
    public static final String CONFIG_CHANGE_IDENTIFIER = "LabyModSetting";

    Icon getIcon();

    Component displayName();

    @Nullable
    Component getDescription();

    boolean hasAdvancedButton();

    Setting parent();

    String getPath();

    String getTranslationKey();

    String[] getSearchTags();

    @Nullable
    String getRequiredPermission();

    boolean isInitialized();

    boolean isExperimental();

    void setExperimental(boolean z);

    default boolean isEnabled() {
        SettingHandler handler = handler();
        return handler == null || handler.isEnabled(this);
    }

    default boolean hasControlButton() {
        return false;
    }

    @Deprecated
    default List<Setting> getSettings() {
        return values();
    }

    default String getTranslationId() {
        return getId();
    }

    default SettingContentActivity createActivity() {
        return new SettingContentActivity(this, false);
    }

    default SettingContentActivity createActivityLazy() {
        return new SettingContentActivity(this, true);
    }

    default void initialize() {
        forEach((v0) -> {
            v0.initialize();
        });
        Laby.fireEvent(new SettingInitializeEvent(this));
        SettingHandler handler = handler();
        if (handler != null) {
            handler.initialized(this);
        }
    }

    default Optional<Setting> findSetting(CharSequence path) {
        return findSetting(CharSequences.split(path, "\\."));
    }

    default Optional<Setting> findSetting(CharSequence[] nodes) {
        String node = CharSequences.toString(nodes[0]);
        if (nodes.length == 1 && Objects.equals(getId(), node)) {
            return Optional.of(this);
        }
        for (KeyValue<Setting> element : getElements()) {
            Setting setting = element.getValue();
            if (Objects.equals(setting.getId(), node)) {
                if (nodes.length == 1) {
                    return Optional.of(setting);
                }
                CharSequence[] newNode = new CharSequence[nodes.length - 1];
                CollectionHelper.copyOfRange(nodes, newNode, 1, nodes.length);
                return setting.findSetting(newNode);
            }
        }
        return Optional.empty();
    }

    @Deprecated
    default Setting findSetting(String path) {
        return findSetting((CharSequence) path).orElse(null);
    }

    @Deprecated
    default Setting findSetting(String[] nodes) {
        Collection<CharSequence> collection = CollectionHelper.map(nodes, node -> {
            return node;
        });
        return findSetting((CharSequence[]) collection.toArray(new CharSequence[0])).orElse(null);
    }

    default boolean isElement() {
        return this instanceof SettingElement;
    }

    default SettingElement asElement() {
        return (SettingElement) this;
    }

    default boolean hasParent() {
        return parent() != null;
    }

    default boolean isHoldable() {
        return true;
    }

    default List<Setting> collect(Filter<Setting> filter) {
        List<Setting> settings = new ArrayList<>();
        for (KeyValue<Setting> element : getElements()) {
            Setting setting = element.getValue();
            if (filter.matches(setting)) {
                settings.add(setting);
            }
            settings.addAll(setting.collect(filter));
        }
        return settings;
    }

    @Nullable
    default SettingHandler handler() {
        return null;
    }

    default boolean isResettable() {
        if (!isElement()) {
            return false;
        }
        SettingElement settingElement = asElement();
        if (settingElement.getResetListener() != null) {
            return true;
        }
        SettingAccessor accessor = settingElement.getAccessor();
        return (accessor == null || accessor.property().isDefaultValue()) ? false : true;
    }

    default void reset() {
        SettingHandler handler = handler();
        if (handler != null) {
            handler.reset(this);
        }
        for (KeyValue<Setting> subElement : getElements()) {
            Setting subSetting = subElement.getValue();
            if (subSetting.isElement()) {
                subSetting.reset();
            }
        }
        if (!isElement()) {
            return;
        }
        SettingElement settingElement = asElement();
        Runnable resetListener = settingElement.getResetListener();
        if (resetListener != null) {
            resetListener.run();
            return;
        }
        SettingAccessor accessor = settingElement.getAccessor();
        if (accessor != null) {
            accessor.property().reset();
        }
    }
}
