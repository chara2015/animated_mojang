package net.labymod.core.labymodnet.widgetoptions.types;

import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/widgetoptions/types/SliderWidgetOption.class */
public class SliderWidgetOption extends WidgetOption {
    private static final String DEBOUNCE_ID = "slider-debounce";
    private final float[] offsetCoordinates;
    private final String[] sliders;

    public SliderWidgetOption(String optionName, int optionIndex) {
        super(optionName, optionIndex);
        this.offsetCoordinates = new float[3];
        this.sliders = optionName.substring(6).replaceAll("[\\[\\]]", "").split(",");
    }

    @Override // net.labymod.core.labymodnet.widgetoptions.WidgetOption
    protected void create(String data, Consumer<Widget> consumer) {
        for (String slider : this.sliders) {
            SliderWidget sliderWidget = createSlider(slider, data);
            if (sliderWidget != null) {
                consumer.accept(sliderWidget);
            }
        }
    }

    private SliderWidget createSlider(String slider, String data) {
        float currentValue;
        String[] sliderData = slider.split("=");
        String sliderKey = sliderData[0];
        try {
            String[] coordinates = data.split(";");
            for (int i = 0; i < 3; i++) {
                this.offsetCoordinates[i] = Float.parseFloat(coordinates[i]);
            }
            switch (sliderKey) {
                case "x":
                    currentValue = this.offsetCoordinates[0];
                    break;
                case "y":
                    currentValue = this.offsetCoordinates[1];
                    break;
                case "z":
                    currentValue = this.offsetCoordinates[2];
                    break;
                default:
                    return null;
            }
            String[] sliderValues = sliderData[1].split("<");
            float sliderMin = Float.parseFloat(sliderValues[0]);
            float sliderMax = Float.parseFloat(sliderValues[1]);
            SliderWidget sliderWidget = new SliderWidget(1.0f, value -> {
                switch (sliderKey) {
                    case "x":
                        this.offsetCoordinates[0] = value;
                        break;
                    case "y":
                        this.offsetCoordinates[1] = value;
                        break;
                    case "z":
                        this.offsetCoordinates[2] = value;
                        break;
                }
                setData(DEBOUNCE_ID, this.offsetCoordinates[0] + ";" + this.offsetCoordinates[1] + ";" + this.offsetCoordinates[2]);
            });
            sliderWidget.range(sliderMin, sliderMax);
            sliderWidget.setValue(currentValue, false);
            return sliderWidget;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.info("SliderWidgetOption#createSlider: invalid coordinates: " + data, new Object[0]);
            return null;
        }
    }
}
