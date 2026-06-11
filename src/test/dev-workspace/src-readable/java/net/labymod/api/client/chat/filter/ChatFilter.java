package net.labymod.api.client.chat.filter;

import java.util.UUID;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.property.ConvertListener;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.type.list.ListSettingConfig;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/filter/ChatFilter.class */
public class ChatFilter extends Config implements ListSettingConfig {
    private final ConfigProperty<UUID> id;
    private final ConfigProperty<UUID> tabId;

    @TextFieldWidget.TextFieldSetting
    @CustomTranslation("labymod.chattab.filters.filter.name")
    private final ConfigProperty<String> name;

    @TagInputWidget.TagInputSetting
    @CustomTranslation("labymod.chattab.filters.filter.includeWords")
    private final ConfigProperty<TagInputWidget.TagCollection> includeTags;

    @TagInputWidget.TagInputSetting
    @CustomTranslation("labymod.chattab.filters.filter.excludeWords")
    private final ConfigProperty<TagInputWidget.TagCollection> excludeTags;

    @Deprecated
    private final ConfigProperty<String> includeWords;

    @Deprecated
    private final ConfigProperty<String> excludeWords;

    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.shouldChangeBackground")
    private final ConfigProperty<Boolean> shouldChangeBackground;

    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires("shouldChangeBackground")
    @CustomTranslation("labymod.chattab.filters.filter.backgroundColor")
    private final ConfigProperty<Integer> backgroundColor;

    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.shouldPlaySound")
    private final ConfigProperty<Boolean> shouldPlaySound;

    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.shouldHideMessage")
    private final ConfigProperty<Boolean> shouldHideMessage;

    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.shouldFilterTooltip")
    private final ConfigProperty<Boolean> shouldFilterTooltip;

    @SwitchWidget.SwitchSetting
    @CustomTranslation("labymod.chattab.filters.filter.caseSensitive")
    private final ConfigProperty<Boolean> caseSensitive;
    private final ConfigProperty<Boolean> advanced;
    private final ConfigProperty<String> includeRegEx;
    private final ConfigProperty<String> excludeRegEx;

    public ChatFilter() {
        this(UUID.randomUUID(), UUID.randomUUID());
    }

    public ChatFilter(UUID id, UUID tabId) {
        this.name = new ConfigProperty<>("Filter");
        this.includeTags = new ConfigProperty<>(new TagInputWidget.TagCollection());
        this.excludeTags = new ConfigProperty<>(new TagInputWidget.TagCollection());
        this.includeWords = (ConfigProperty) new ConfigProperty("").addChangeListener(ConvertListener.of(this.includeTags, value -> {
            convertOldFormat(value, this.includeTags);
            return true;
        }));
        this.excludeWords = (ConfigProperty) new ConfigProperty("").addChangeListener(ConvertListener.of(this.excludeTags, value2 -> {
            convertOldFormat(value2, this.excludeTags);
            return true;
        }));
        this.shouldChangeBackground = new ConfigProperty<>(false);
        this.backgroundColor = new ConfigProperty<>(0);
        this.shouldPlaySound = new ConfigProperty<>(false);
        this.shouldHideMessage = new ConfigProperty<>(false);
        this.shouldFilterTooltip = new ConfigProperty<>(false);
        this.caseSensitive = new ConfigProperty<>(false);
        this.advanced = new ConfigProperty<>(false);
        this.includeRegEx = new ConfigProperty<>("");
        this.excludeRegEx = new ConfigProperty<>("");
        this.id = new ConfigProperty<>(id);
        this.tabId = new ConfigProperty<>(tabId);
    }

    public UUID id() {
        return this.id.get();
    }

    public UUID tabId() {
        return this.tabId.get();
    }

    public TagInputWidget.TagCollection getIncludedTags() {
        return this.includeTags.get();
    }

    public TagInputWidget.TagCollection getExcludedTags() {
        return this.excludeTags.get();
    }

    public ConfigProperty<String> name() {
        return this.name;
    }

    public ConfigProperty<Boolean> shouldChangeBackground() {
        return this.shouldChangeBackground;
    }

    public ConfigProperty<Integer> backgroundColor() {
        return this.backgroundColor;
    }

    public ConfigProperty<Boolean> shouldPlaySound() {
        return this.shouldPlaySound;
    }

    public ConfigProperty<Boolean> shouldHideMessage() {
        return this.shouldHideMessage;
    }

    public ConfigProperty<Boolean> shouldFilterTooltip() {
        return this.shouldFilterTooltip;
    }

    public ConfigProperty<Boolean> caseSensitive() {
        return this.caseSensitive;
    }

    public ConfigProperty<Boolean> advanced() {
        return this.advanced;
    }

    public ConfigProperty<String> includeRegEx() {
        return this.includeRegEx;
    }

    public ConfigProperty<String> excludeRegEx() {
        return this.excludeRegEx;
    }

    @Override // net.labymod.api.configuration.settings.type.list.ListSettingConfig
    @NotNull
    public Component entryDisplayName() {
        if (this.name.isDefaultValue() || this.name.get().isEmpty()) {
            Component include = null;
            Component exclude = null;
            if (!this.includeWords.isDefaultValue()) {
                include = Component.text(this.includeWords.get(), NamedTextColor.GREEN);
            }
            if (!this.excludeWords.isDefaultValue()) {
                exclude = Component.text(this.excludeWords.get(), NamedTextColor.RED);
            }
            if (include != null && exclude != null) {
                return include.append(Component.text(" | ", NamedTextColor.WHITE)).append(exclude);
            }
            if (include != null) {
                return include;
            }
            if (exclude != null) {
                return exclude;
            }
        }
        return Component.text(this.name.get());
    }

    @Override // net.labymod.api.configuration.settings.type.list.ListSettingConfig
    @NotNull
    public Component newEntryTitle() {
        return Component.translatable("labymod.chattab.filters.new", new Component[0]);
    }

    @Override // net.labymod.api.configuration.settings.type.list.ListSettingConfig
    public boolean isInvalid() {
        return this.includeTags.get().isEmpty() && this.excludeTags.get().isEmpty() && this.includeWords.get().isEmpty() && this.excludeWords.get().isEmpty();
    }

    private void convertOldFormat(String value, ConfigProperty<TagInputWidget.TagCollection> property) {
        if (value.isEmpty()) {
            return;
        }
        TagInputWidget.TagCollection tags = new TagInputWidget.TagCollection();
        for (String filter : value.split(";")) {
            if (!filter.trim().isEmpty()) {
                tags.add(filter);
            }
        }
        property.set(tags);
    }
}
