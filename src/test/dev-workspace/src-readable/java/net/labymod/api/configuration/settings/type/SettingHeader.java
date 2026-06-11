package net.labymod.api.configuration.settings.type;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.util.I18n;
import org.spongepowered.include.com.google.common.collect.Lists;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/type/SettingHeader.class */
public class SettingHeader extends AbstractSetting {
    private final String translationId;
    private final String customTranslation;
    private final boolean center;

    public SettingHeader(String id, boolean center, String customTranslation, String translationId) {
        super(id, null);
        this.center = center;
        this.customTranslation = customTranslation;
        this.translationId = translationId;
    }

    @Override // net.labymod.api.configuration.settings.Setting
    public String getTranslationId() {
        return "header." + this.translationId;
    }

    @Override // net.labymod.api.configuration.settings.type.AbstractSetting, net.labymod.api.configuration.settings.Setting
    public String getTranslationKey() {
        if (this.customTranslation.isEmpty()) {
            return super.getTranslationKey();
        }
        return this.customTranslation + "." + getTranslationId();
    }

    public boolean isCenter() {
        return this.center;
    }

    public List<Component> getRows() {
        String translation;
        String translationKey = getTranslationKey();
        String defaultTranslationKey = translationKey + ".name";
        String defaultTranslation = I18n.getTranslation(defaultTranslationKey, new Object[0]);
        List<Component> components = Lists.newArrayList();
        if (defaultTranslation != null) {
            String[] split = defaultTranslation.split("\\n");
            for (String row : split) {
                components.add(Component.text(row));
            }
            return components;
        }
        for (int i = 0; i < Integer.MAX_VALUE && (translation = I18n.getTranslation(translationKey + ".row" + i, new Object[0])) != null; i++) {
            components.add(Component.text(translation));
        }
        if (components.isEmpty()) {
            components.add(Component.translatable(defaultTranslationKey, new Component[0]));
        }
        return components;
    }

    public Pressable pressable() {
        return Pressable.NOOP;
    }
}
