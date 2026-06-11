package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.util.ColorUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/ColorPickerPlateWidget.class */
@AutoWidget
public class ColorPickerPlateWidget extends FlexibleContentWidget {
    private final ColorData colorData;
    private DivWidget previewWidget;

    protected ColorPickerPlateWidget(ColorData colorData) {
        this.colorData = colorData;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        TilesGridWidget<DivWidget> tilesGridWidget = new TilesGridWidget<>();
        for (TextColor defaultColor : ColorUtil.DEFAULT_COLORS) {
            DivWidget divColor = new DivWidget();
            int backgroundColor = defaultColor.value() | (-16777216);
            divColor.backgroundColor().set(Integer.valueOf(backgroundColor));
            divColor.setPressable(() -> {
                this.colorData.setRgb(backgroundColor);
            });
            tilesGridWidget.addTile(divColor);
        }
        addFlexibleContent(tilesGridWidget);
        DivWidget previewWrapper = new DivWidget();
        previewWrapper.addId("preview-stencil-wrapper");
        this.previewWidget = new DivWidget();
        this.previewWidget.addId("preview");
        previewWrapper.addChild(this.previewWidget);
        addContent(previewWrapper);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        if (this.previewWidget != null) {
            this.previewWidget.backgroundColor().set(Integer.valueOf(this.colorData.getActualColor().get()));
        }
        super.render(context);
    }
}
