package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/ColorPickerInputWidget.class */
@AutoWidget
public class ColorPickerInputWidget extends FlexibleContentWidget {
    private static final Pattern HEX_PATTERN = Pattern.compile("[0-9A-Fa-f]");
    private final ColorData colorData;
    private ColorPickerInputWrapperWidget<TextFieldWidget> hexInputWidget;
    private ColorPickerInputWrapperWidget<CheckBoxWidget> chromaInputWidget;
    private ColorPickerInputWrapperWidget<SliderWidget> chromaSpeedInputWidget;
    private Integer lastHexValue;

    protected ColorPickerInputWidget(ColorData colorData) {
        this.colorData = colorData;
        colorData.addUpdateListener(this, this::update);
        setHoverCursor(CursorTypes.POINTING_HAND, true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        boolean chroma = this.colorData.enabledChroma();
        boolean chromaSpeed = this.colorData.enabledChromaSpeed();
        FlexibleContentWidget contentWidget = this;
        if (chroma && chromaSpeed) {
            contentWidget = new FlexibleContentWidget();
            contentWidget.addId("input-wrapper");
            addContent(contentWidget);
        } else {
            addId("standalone");
        }
        this.hexInputWidget = hexWidget();
        contentWidget.addFlexibleContent(this.hexInputWidget);
        if (chroma) {
            this.chromaInputWidget = chromaWidget();
            contentWidget.addContent(this.chromaInputWidget);
            if (chromaSpeed) {
                this.chromaSpeedInputWidget = chromaSpeedInputWidget();
                addContent(this.chromaSpeedInputWidget);
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (this.chromaSpeedInputWidget == null) {
        }
    }

    private ColorPickerInputWrapperWidget<TextFieldWidget> hexWidget() {
        return new ColorPickerInputWrapperWidget<>("hex", () -> {
            TextFieldWidget textFieldWidget = new TextFieldWidget();
            textFieldWidget.validator(this::validateHex);
            updateHex(textFieldWidget);
            return textFieldWidget;
        });
    }

    private ColorPickerInputWrapperWidget<CheckBoxWidget> chromaWidget() {
        return new ColorPickerInputWrapperWidget<>(ItemMetadata.RGB_KEY, () -> {
            CheckBoxWidget checkBoxWidget = new CheckBoxWidget();
            updateChroma(checkBoxWidget);
            checkBoxWidget.setPressable(() -> {
                this.colorData.setChroma(checkBoxWidget.state() == CheckBoxWidget.State.CHECKED);
            });
            return checkBoxWidget;
        });
    }

    private ColorPickerInputWrapperWidget<SliderWidget> chromaSpeedInputWidget() {
        return new ColorPickerInputWrapperWidget<>("speed", () -> {
            ColorData colorData = this.colorData;
            Objects.requireNonNull(colorData);
            SliderWidget sliderWidget = new SliderWidget(0.25f, colorData::setChromaSpeed).range(0.25f, 5.0f);
            sliderWidget.setValue(this.colorData.getActualColor().getChromaSpeed());
            return sliderWidget;
        });
    }

    private void update() {
        if (this.hexInputWidget == null || this.hexInputWidget.getWidget() == null) {
            return;
        }
        updateHex((TextFieldWidget) this.hexInputWidget.getWidget());
        if (this.chromaInputWidget != null) {
            updateChroma((CheckBoxWidget) this.chromaInputWidget.getWidget());
        }
    }

    private void updateHex(TextFieldWidget widget) {
        int value = 16777215 & this.colorData.getColor().get();
        if (this.lastHexValue != null && value == this.lastHexValue.intValue()) {
            return;
        }
        this.lastHexValue = Integer.valueOf(value);
        widget.setText(String.format(Locale.ROOT, "#%06X", Integer.valueOf(value)), true);
        setHexState(true);
    }

    private void updateChroma(CheckBoxWidget widget) {
        widget.setState(this.colorData.getActualColor().isChroma() ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED);
    }

    private boolean validateHex(String content) {
        if (this.hexInputWidget == null || this.hexInputWidget.getWidget() == null) {
            return false;
        }
        StringBuilder accepted = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '#') {
                if (i == 0) {
                    accepted.append(c);
                } else {
                    return false;
                }
            } else if (HEX_PATTERN.matcher(String.valueOf(c)).matches()) {
                accepted.append(c);
            } else {
                return false;
            }
        }
        String content2 = accepted.toString();
        if (content2.length() > 0 && content2.toCharArray()[0] != '#') {
            content2 = "#" + content2;
        }
        boolean valid = false;
        if (content2.length() == 7) {
            try {
                int newValue = Integer.decode(content2).intValue();
                this.lastHexValue = Integer.valueOf(newValue);
                this.colorData.setValue(newValue);
                valid = true;
            } catch (NumberFormatException e) {
            }
        }
        setHexState(valid);
        if (content2.length() != 0) {
            if (content2.length() > (accepted.toString().toCharArray()[0] == '#' ? 7 : 6)) {
                return false;
            }
        }
        return true;
    }

    private void setHexState(boolean valid) {
        if (valid) {
            this.hexInputWidget.addId("valid");
            this.hexInputWidget.removeId("invalid");
        } else {
            this.hexInputWidget.addId("invalid");
            this.hexInputWidget.removeId("valid");
        }
    }
}
