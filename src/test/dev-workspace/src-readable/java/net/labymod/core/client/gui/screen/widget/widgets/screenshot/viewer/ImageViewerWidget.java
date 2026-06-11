package net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/screenshot/viewer/ImageViewerWidget.class */
@AutoWidget
@Link("activity/screenshot/image-viewer.lss")
public class ImageViewerWidget extends SimpleWidget {
    private Icon icon;

    public ImageViewerWidget(Icon icon) {
        this.icon = icon;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        IconWidget iconWidget = new IconWidget(this.icon);
        iconWidget.addId("image");
        addChild(iconWidget);
    }

    public void display(Icon icon) {
        this.icon = icon;
        reInitialize();
    }
}
