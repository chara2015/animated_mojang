package net.labymod.v26_1_1.client.gui.screen.widget.converter;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.minecraft.client.gui.components.StringWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/gui/screen/widget/converter/StringConverter.class */
public class StringConverter extends AbstractMinecraftWidgetConverter<StringWidget, ComponentWidget> {
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ComponentWidget createDefault(StringWidget source) {
        ComponentWidget destination = ComponentWidget.component(mapComponent(source.getMessage()));
        destination.setFocused(source.isFocused());
        copyBounds(source, destination);
        applyStringWidget(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(StringWidget source, ComponentWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setFocused(source.isFocused());
            destination.setComponent(mapComponent(source.getMessage()));
            applyStringWidget(source, destination);
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(StringWidget source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.getMessage());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(StringWidget source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.getMessage());
    }

    private void applyStringWidget(StringWidget source, ComponentWidget destination) {
        if (!(source instanceof StringWidgetAccessor)) {
            return;
        }
        StringWidgetAccessor accessor = (StringWidgetAccessor) source;
        if (accessor.isClipped()) {
            destination.overflowStrategy().set(TextOverflowStrategy.CLIP);
        }
        float maxWidth = accessor.getMaxWidth();
        destination.sizes().setSize(SizeType.MAX, WidgetSide.WIDTH, WidgetSize.fixed(maxWidth));
        destination.textColor().set(Integer.valueOf(accessor.getTextColor()));
    }
}
