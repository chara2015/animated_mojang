package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.AlphaSelectorWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.HueSelectorWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.ShadeSelectorWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/ColorPickerSelectorWidget.class */
@AutoWidget
public class ColorPickerSelectorWidget extends AbstractWidget<Widget> {
    private final ColorData colorData;

    protected ColorPickerSelectorWidget(ColorData colorData) {
        this.colorData = colorData;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget shadeWrapper = new DivWidget();
        shadeWrapper.addId("stencil-wrapper", "shade-stencil-wrapper");
        shadeWrapper.addChild(new ShadeSelectorWidget(this.colorData).addId("color-selector"));
        addChild(shadeWrapper);
        if (this.colorData.enabledAlpha()) {
            DivWidget alphaWrapper = new DivWidget();
            alphaWrapper.addId("stencil-wrapper", "alpha-stencil-wrapper");
            alphaWrapper.addChild(new AlphaSelectorWidget(this.colorData).addId("color-selector"));
            addChild(alphaWrapper);
            addId("with-alpha");
        }
        DivWidget hueWrapper = new DivWidget();
        hueWrapper.addId("stencil-wrapper", "hue-stencil-wrapper");
        hueWrapper.addChild(new HueSelectorWidget(this.colorData).addId("color-selector"));
        addChild(hueWrapper);
    }
}
