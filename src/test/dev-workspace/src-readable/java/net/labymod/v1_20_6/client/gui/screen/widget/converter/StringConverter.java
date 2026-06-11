package net.labymod.v1_20_6.client.gui.screen.widget.converter;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/gui/screen/widget/converter/StringConverter.class */
public class StringConverter extends AbstractMinecraftWidgetConverter<fin, ComponentWidget> {
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ComponentWidget createDefault(fin source) {
        ComponentWidget destination = ComponentWidget.component(mapComponent(source.y()));
        destination.setFocused(source.aH_());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(fin source, ComponentWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setFocused(source.aH_());
            destination.setComponent(mapComponent(source.y()));
            if (source instanceof StringWidgetAccessor) {
                StringWidgetAccessor accessor = (StringWidgetAccessor) source;
                if (accessor.isBasedOnTextWidth()) {
                    source.k(ffh.Q().h.a(source.y()));
                }
            }
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(fin source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.y());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(fin source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.y());
    }
}
