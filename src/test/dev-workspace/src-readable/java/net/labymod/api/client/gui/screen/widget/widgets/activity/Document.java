package net.labymod.api.client.gui.screen.widget.widgets.activity;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/Document.class */
@AutoWidget
public class Document extends AbstractWidget<Widget> {
    private static final AttributeState ROOT = AttributeState.staticState("ROOT", 0);
    private final Activity activity;

    public Document(Activity activity) {
        this.activity = activity;
        addId(HumanoidModel.BODY_NAME);
        super.setStaticAttributeState(ROOT);
    }

    public Activity activity() {
        return this.activity;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        handleChildSize(this);
    }

    private void handleChildSize(Widget widget) {
        for (Widget child : widget.getChildren()) {
            handleChildSize(child);
        }
        widget.handleAttributes();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        updateMediaRules(this);
        super.onBoundsChanged(previousRect, newRect);
    }

    private void updateMediaRules(Widget widget) {
        for (Widget child : widget.getChildren()) {
            child.applyMediaRules(false);
            updateMediaRules(child);
        }
    }
}
