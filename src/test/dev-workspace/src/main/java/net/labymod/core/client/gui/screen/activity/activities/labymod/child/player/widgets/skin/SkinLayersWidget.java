package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TreeWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.options.SkinLayer;
import net.labymod.core.client.gui.screen.widget.widgets.customization.SkinLayerWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/skin/SkinLayersWidget.class */
@AutoWidget
public class SkinLayersWidget extends AbstractWidget<Widget> {
    private final Runnable updateModel;

    public SkinLayersWidget(Runnable updateModel) {
        this.updateModel = () -> {
            reInitialize();
            updateModel.run();
        };
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        VerticalListWidget<Widget> list = new VerticalListWidget<>();
        list.addId("layer-list");
        SkinLayerWidget hat = new SkinLayerWidget(SkinLayer.HAT);
        hat.setActionListener(this.updateModel);
        list.addChild(hat);
        SkinLayerWidget jacket = new SkinLayerWidget(SkinLayer.JACKET);
        jacket.setActionListener(this.updateModel);
        TreeWidget jacketTree = new TreeWidget(jacket);
        SkinLayerWidget leftSleeve = new SkinLayerWidget(SkinLayer.LEFT_SLEEVE);
        leftSleeve.setActionListener(this.updateModel);
        jacketTree.addChild(leftSleeve);
        SkinLayerWidget jacketBase = new SkinLayerWidget(SkinLayer.JACKET_BASE);
        jacketBase.setActionListener(this.updateModel);
        jacketTree.addChild(jacketBase);
        SkinLayerWidget rightSleeve = new SkinLayerWidget(SkinLayer.RIGHT_SLEEVE);
        rightSleeve.setActionListener(this.updateModel);
        jacketTree.addChild(rightSleeve);
        list.addChild(jacketTree);
        SkinLayerWidget pants = new SkinLayerWidget(SkinLayer.PANTS);
        pants.setActionListener(this.updateModel);
        TreeWidget pantsTree = new TreeWidget(pants);
        SkinLayerWidget leftPants = new SkinLayerWidget(SkinLayer.LEFT_PANTS_LEG);
        leftPants.setActionListener(this.updateModel);
        pantsTree.addChild(leftPants);
        SkinLayerWidget rightPants = new SkinLayerWidget(SkinLayer.RIGHT_PANTS_LEG);
        rightPants.setActionListener(this.updateModel);
        pantsTree.addChild(rightPants);
        list.addChild(pantsTree);
        SkinLayerWidget widget = new SkinLayerWidget(SkinLayer.CAPE);
        widget.setActionListener(this.updateModel);
        list.addChild(widget);
        addChild(list);
    }
}
