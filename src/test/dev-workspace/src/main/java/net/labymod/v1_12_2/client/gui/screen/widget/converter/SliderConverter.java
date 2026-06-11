package net.labymod.v1_12_2.client.gui.screen.widget.converter;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.converter.WidgetReplacementStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.v1_12_2.client.gui.GuiSliderAccessor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/screen/widget/converter/SliderConverter.class */
public class SliderConverter extends AbstractMinecraftWidgetConverter<bja, SliderWidget> {
    public SliderConverter() {
        super(MinecraftWidgetType.SLIDER);
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public SliderWidget createDefault(bja source) {
        SliderWidget destination = new SliderWidget().withFormatter(() -> {
            return Component.text(source.j);
        });
        destination.range(((GuiSliderAccessor) source).getMinValue(), ((GuiSliderAccessor) source).getMaxValue());
        destination.steps(((GuiSliderAccessor) source).getStep());
        copyBounds(source, destination);
        return destination;
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public void update(bja source, SliderWidget destination, @NotNull WidgetReplacementStrategy replacementStrategy) {
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_STATES)) {
            destination.setVisible(source.m);
            destination.setDragging(((GuiSliderAccessor) source).isDragging());
            destination.setValue(((GuiSliderAccessor) source).getValue());
        }
        if (replacementStrategy.isSet(WidgetReplacementStrategy.ReplacementBehavior.KEEP_BOUNDS)) {
            copyBounds(source, destination);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getWidgetId(bja source) {
        return String.valueOf(source.k);
    }
}
