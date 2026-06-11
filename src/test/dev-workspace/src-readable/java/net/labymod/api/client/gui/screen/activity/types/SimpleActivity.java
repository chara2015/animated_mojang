package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/types/SimpleActivity.class */
public abstract class SimpleActivity extends Activity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public boolean renderBackground(ScreenContext context) {
        return !shouldRenderBackground();
    }

    public boolean shouldRenderBackground() {
        return true;
    }
}
