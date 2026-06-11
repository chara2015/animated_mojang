package net.labymod.v1_21_10.client.gui.screen.widget.converter;

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
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/gui/screen/widget/converter/StringConverter.class */
public class StringConverter extends AbstractMinecraftWidgetConverter<gey, ComponentWidget> {
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public ComponentWidget createDefault(gey source) {
        ComponentWidget destination = ComponentWidget.component(mapComponent(source.A()));
        destination.setFocused(source.aP_());
        copyBounds(source, destination);
        applyStringWidget(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(gey source, ComponentWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setFocused(source.aP_());
            destination.setComponent(mapComponent(source.A()));
            applyStringWidget(source, destination);
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(gey source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.A());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(gey source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.A());
    }

    private void applyStringWidget(gey source, ComponentWidget destination) {
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
