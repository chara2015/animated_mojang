package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labyconnect/desktop/sections/LabyConnectEmptyActivity.class */
@Link("activity/labyconnect/laby-connect-empty.lss")
@AutoActivity
public class LabyConnectEmptyActivity extends Activity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget container = new DivWidget();
        container.addId("background");
        ((Document) this.document).addChild(container);
    }
}
