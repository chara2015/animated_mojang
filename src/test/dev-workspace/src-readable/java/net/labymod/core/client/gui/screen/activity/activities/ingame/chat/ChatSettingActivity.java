package net.labymod.core.client.gui.screen.activity.activities.ingame.chat;

import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.configuration.settings.Setting;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/chat/ChatSettingActivity.class */
@Link(value = "activity/overlay/chat/settings.lss", priority = 64)
@AutoActivity
public class ChatSettingActivity extends SettingContentActivity {
    private final Runnable saveCallback;

    public ChatSettingActivity(Setting holder, Runnable saveCallback) {
        super(holder);
        this.saveCallback = saveCallback;
    }

    public ChatSettingActivity(Setting holder) {
        this(holder, null);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        if (this.saveCallback != null) {
            this.saveCallback.run();
        }
        super.onCloseScreen();
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public boolean allowCustomFont() {
        return false;
    }
}
