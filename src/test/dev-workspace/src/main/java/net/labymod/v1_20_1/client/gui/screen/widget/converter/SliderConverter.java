package net.labymod.v1_20_1.client.gui.screen.widget.converter;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/gui/screen/widget/converter/SliderConverter.class */
public class SliderConverter extends AbstractMinecraftWidgetConverter<epd, SliderWidget> {
    public SliderConverter() {
        super(MinecraftWidgetType.SLIDER);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public SliderWidget createDefault(epd source) {
        SliderWidget destination = new SliderWidget().withFormatter(() -> {
            return mapComponent(source.l());
        });
        SliderButtonAccessor accessor = (SliderButtonAccessor) source;
        destination.range(accessor.getMinValue(), accessor.getMaxValue());
        destination.steps(accessor.getSteps());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(epd source, SliderWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setVisible(source.s);
            destination.setPercentage((float) ((SliderButtonAccessor) source).getRawValue());
            destination.setDragging(((SliderButtonAccessor) source).isDragging());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(epd source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.l());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(epd source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.l());
    }
}
