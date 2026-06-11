package net.labymod.core.test.styleorder;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/styleorder/StyleOrderTestActivity.class */
@AutoActivity
@Link("test/style/style-test-activity.lss")
public class StyleOrderTestActivity extends TestActivity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        StyleOrderTestWidget styled = new StyleOrderTestWidget();
        styled.addId("styled");
        ((Document) this.document).addChild(styled);
        DivWidget normal = new DivWidget();
        normal.addId("normal");
        ((Document) this.document).addChild(normal);
    }
}
