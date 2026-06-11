package net.labymod.v1_21_11.client.gui.screen.widget.converter;

import java.util.List;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gui/screen/widget/converter/SliderConverter.class */
public class SliderConverter extends AbstractMinecraftWidgetConverter<AbstractSliderButton, SliderWidget> {
    public SliderConverter() {
        super(MinecraftWidgetType.SLIDER);
    }

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

    public void update(AbstractSliderButton source, SliderWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setVisible(source.visible);
            destination.setPercentage((float) ((SliderButtonAccessor) source).getRawValue());
            destination.setDragging(((SliderButtonAccessor) source).isDragging());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    public String getWidgetId(AbstractSliderButton source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.getMessage());
    }

    public List<String> getWidgetIds(AbstractSliderButton source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.getMessage());
    }
}
