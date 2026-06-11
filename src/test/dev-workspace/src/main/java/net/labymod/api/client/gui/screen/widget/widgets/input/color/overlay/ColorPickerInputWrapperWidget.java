package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import java.util.Locale;
import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/ColorPickerInputWrapperWidget.class */
@AutoWidget
public class ColorPickerInputWrapperWidget<T extends Widget> extends VerticalListWidget<Widget> {
    private final String identifier;
    private final Supplier<T> widgetSupplier;
    private T widget;

    protected ColorPickerInputWrapperWidget(String identifier, Supplier<T> widgetSupplier) {
        this.identifier = identifier;
        this.widgetSupplier = widgetSupplier;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addId(this.identifier + "-input");
        this.widget = this.widgetSupplier.get();
        addChild(this.widget);
        DivWidget nameWrapper = new DivWidget();
        nameWrapper.addChild(ComponentWidget.text(this.identifier.toUpperCase(Locale.ENGLISH)));
        addChild(nameWrapper);
    }

    public T getWidget() {
        return this.widget;
    }
}
