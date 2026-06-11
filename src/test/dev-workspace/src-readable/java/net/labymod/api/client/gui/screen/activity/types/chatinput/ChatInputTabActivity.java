package net.labymod.api.client.gui.screen.activity.types.chatinput;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/types/chatinput/ChatInputTabActivity.class */
public abstract class ChatInputTabActivity<T extends Widget> extends Activity {
    protected T contentWidget;

    public boolean isHovered() {
        return ((Document) this.document).isHovered() && this.contentWidget != null && this.contentWidget.isHovered();
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public boolean allowCustomFont() {
        return false;
    }
}
