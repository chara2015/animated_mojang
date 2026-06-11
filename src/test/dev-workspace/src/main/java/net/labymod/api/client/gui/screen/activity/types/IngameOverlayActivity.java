package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/types/IngameOverlayActivity.class */
public abstract class IngameOverlayActivity extends Activity {
    public abstract boolean isVisible();

    public IngameOverlayActivity() {
    }

    public IngameOverlayActivity(boolean handleStyleSheet) {
        super(handleStyleSheet);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        if (isVisible()) {
            super.render(context);
        }
    }

    public boolean isAcceptingInput() {
        return false;
    }

    public boolean isRenderedHidden() {
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public boolean allowCustomFont() {
        return false;
    }

    public int getPriority() {
        return 0;
    }
}
