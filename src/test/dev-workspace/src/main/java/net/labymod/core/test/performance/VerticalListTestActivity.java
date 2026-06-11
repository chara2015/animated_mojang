package net.labymod.core.test.performance;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/performance/VerticalListTestActivity.class */
@Link("test/test-menu.lss")
@AutoActivity
public class VerticalListTestActivity extends TestActivity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        VerticalListWidget<Widget> listWidget = new VerticalListWidget<>();
        listWidget.addId("vertical-container");
        for (int index = 0; index < 250; index++) {
            DivWidget widget = new DivWidget();
            listWidget.addChild(widget, false);
        }
        ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) listWidget);
        ((Document) this.document).addChild(scrollWidget);
    }
}
