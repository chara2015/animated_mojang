package net.labymod.api.client.gui.screen.activity.activities;

import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/activities/WorkInProgressActivity.class */
@AutoActivity
public class WorkInProgressActivity extends SimpleActivity {
    private final String branchName;

    public WorkInProgressActivity(String branchName) {
        this.branchName = branchName;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        HorizontalListWidget list = new HorizontalListWidget();
        list.addId("centered");
        ComponentWidget componentWidget = ComponentWidget.component(Component.text("Work in progress", NamedTextColor.RED));
        componentWidget.addId("centered");
        list.addEntry(componentWidget);
        IconWidget pepeSad = new IconWidget(Textures.SpriteCommon.PEPE_SAD);
        pepeSad.addId("pepesad");
        list.addEntry(pepeSad);
        ((Document) this.document).addChild(list);
    }
}
