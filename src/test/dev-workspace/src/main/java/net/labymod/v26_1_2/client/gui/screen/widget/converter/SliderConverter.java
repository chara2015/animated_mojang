package net.labymod.v26_1_2.client.gui.screen.widget.converter;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/gui/screen/widget/converter/SliderConverter.class */
public class SliderConverter extends AbstractMinecraftWidgetConverter<AbstractSliderButton, SliderWidget> {
    public SliderConverter() {
        super(MinecraftWidgetType.SLIDER);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public SliderWidget createDefault(AbstractSliderButton source) {
        SliderWidget destination = new SliderWidget().withFormatter(() -> {
            return mapComponent(source.getMessage());
        });
        SliderButtonAccessor accessor = (SliderButtonAccessor) source;
        destination.range(accessor.getMinValue(), accessor.getMaxValue());
        destination.steps(accessor.getSteps());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(AbstractSliderButton source, SliderWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setVisible(source.visible);
            destination.setPercentage((float) ((SliderButtonAccessor) source).getRawValue());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(AbstractSliderButton source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.getMessage());
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public List<String> getWidgetIds(AbstractSliderButton source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.getMessage());
    }
}
