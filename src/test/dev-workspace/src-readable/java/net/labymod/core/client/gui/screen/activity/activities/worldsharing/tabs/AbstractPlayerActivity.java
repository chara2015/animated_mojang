package net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/tabs/AbstractPlayerActivity.class */
@AutoActivity
@Link("activity/worldsharing/tabs/base.lss")
public abstract class AbstractPlayerActivity extends Activity {
    protected FlexibleContentWidget container;
    protected VerticalListWidget<Widget> list;
    public static final String I18N_PREFIX = "labymod.activity.worldsharing.";

    protected abstract void buildHeader(HorizontalListWidget horizontalListWidget);

    protected abstract void fillList(VerticalListWidget<Widget> verticalListWidget, boolean z);

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.container = (FlexibleContentWidget) new FlexibleContentWidget().addId("container");
        HorizontalListWidget header = (HorizontalListWidget) new HorizontalListWidget().addId("header");
        buildHeader(header);
        this.container.addContent(header);
        this.list = (VerticalListWidget) new VerticalListWidget().addId("entries");
        fillList(this.list, false);
        this.container.addContent(new ScrollWidget((VerticalListWidget<?>) this.list).addId("scroll-content"));
        ((Document) this.document).addChild(this.container);
    }
}
