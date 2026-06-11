package net.labymod.core.client.gui.screen.activity.activities.singleplayer;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/singleplayer/SingleplayerOverlay.class */
@AutoActivity
public class SingleplayerOverlay extends AbstractLayerActivity {
    public SingleplayerOverlay(ScreenInstance parentScreen) {
        super(parentScreen);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity, net.labymod.api.client.gui.screen.activity.types.SimpleActivity
    public boolean shouldRenderBackground() {
        return true;
    }
}
