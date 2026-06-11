package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/labyconnect/friends/LabyConnectEntryWidget.class */
@AutoWidget
public abstract class LabyConnectEntryWidget extends SimpleWidget {
    protected ParentScreen contentDisplay;

    public abstract void select();

    public LabyConnectEntryWidget(ParentScreen contentDisplay) {
        this.contentDisplay = contentDisplay;
        addId("labyconnect-entry");
        this.lazy = true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
    }

    public void updateContentDisplay(ParentScreen contentDisplay) {
        this.contentDisplay = contentDisplay;
    }

    public void displayContentActivity(Activity activity) {
        this.contentDisplay.displayScreen(activity);
    }
}
