package net.labymod.v1_20_4.client.gui.screen.widget.converter;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/gui/screen/widget/converter/StringConverter.class */
public class StringConverter extends AbstractMinecraftWidgetConverter<eyn, ComponentWidget> {
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ComponentWidget createDefault(eyn source) {
        ComponentWidget destination = ComponentWidget.component(mapComponent(source.x()));
        destination.setFocused(source.aI_());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(eyn source, ComponentWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setFocused(source.aI_());
            destination.setComponent(mapComponent(source.x()));
            if (source instanceof StringWidgetAccessor) {
                StringWidgetAccessor accessor = (StringWidgetAccessor) source;
                if (accessor.isBasedOnTextWidth()) {
                    source.l(evi.O().h.a(source.x()));
                }
            }
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(eyn source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.x());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(eyn source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.x());
    }
}
