package net.labymod.core.test;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.util.bounds.ModifyReason;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/RectangleTestActivity.class */
@AutoActivity
@Link("test/rectangle.lss")
public class RectangleTestActivity extends TestActivity {
    private static final ModifyReason REASON = ModifyReason.of("RectangleTestActivity");

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget first = new DivWidget();
        first.addId("first");
        first.bounds().setPosition(0.0f, 0.0f, REASON);
        first.bounds().setSize(10.0f, 1.0f, REASON);
        ((Document) this.document).addChild(first);
        DivWidget second = new DivWidget();
        second.addId("second");
        second.bounds().setPosition(0.0f, 0.0f + 1.0f, REASON);
        second.bounds().setSize(10.0f, 1.0f, REASON);
        ((Document) this.document).addChild(second);
    }
}
