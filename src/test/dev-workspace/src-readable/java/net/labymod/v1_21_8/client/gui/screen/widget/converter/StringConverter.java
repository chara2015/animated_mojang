package net.labymod.v1_21_8.client.gui.screen.widget.converter;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/gui/screen/widget/converter/StringConverter.class */
public class StringConverter extends AbstractMinecraftWidgetConverter<fyx, ComponentWidget> {
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ComponentWidget createDefault(fyx source) {
        ComponentWidget destination = ComponentWidget.component(mapComponent(source.A()));
        destination.setFocused(source.aM_());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(fyx source, ComponentWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setFocused(source.aM_());
            destination.setComponent(mapComponent(source.A()));
            if (source instanceof StringWidgetAccessor) {
                StringWidgetAccessor accessor = (StringWidgetAccessor) source;
                if (accessor.isBasedOnTextWidth()) {
                    source.h(fue.R().h.a(source.A()));
                }
            }
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(fyx source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.A());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(fyx source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.A());
    }
}
