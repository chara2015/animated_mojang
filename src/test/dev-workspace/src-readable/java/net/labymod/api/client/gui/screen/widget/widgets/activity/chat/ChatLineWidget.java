package net.labymod.api.client.gui.screen.widget.widgets.activity.chat;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.FadingWidget;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/chat/ChatLineWidget.class */
@AutoWidget
public class ChatLineWidget extends FadingWidget {
    public ChatLineWidget(Widget widget, long duration) {
        super(widget, TimeUtil.getMillis() + duration);
    }
}
