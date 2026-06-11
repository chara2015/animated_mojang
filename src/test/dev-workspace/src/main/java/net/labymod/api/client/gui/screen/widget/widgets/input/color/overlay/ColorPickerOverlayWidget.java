package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/ColorPickerOverlayWidget.class */
@AutoWidget
public class ColorPickerOverlayWidget extends VerticalListWidget<Widget> {
    private final ColorData colorData;

    public ColorPickerOverlayWidget(ColorData colorData) {
        this.colorData = colorData;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(new ColorPickerPlateWidget(this.colorData));
        addChild(new ColorPickerSelectorWidget(this.colorData));
        addChild(new ColorPickerInputWidget(this.colorData));
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        super.mouseClicked(mouse, mouseButton);
        return true;
    }
}
