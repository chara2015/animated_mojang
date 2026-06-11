package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.listener;

import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/listener/CosmeticsItemTextureListener.class */
public class CosmeticsItemTextureListener implements ItemTextureListener {
    private State state = State.SUCCESS;
    private long lastStateUpdate;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/listener/CosmeticsItemTextureListener$State.class */
    public enum State {
        BEGIN,
        SUCCESS,
        ERROR
    }

    @Override // net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener
    public void onBegin() {
        setState(State.BEGIN);
    }

    @Override // net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener
    public void onSuccess() {
        setState(State.SUCCESS);
    }

    @Override // net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener
    public void onError() {
        setState(State.ERROR);
    }

    public boolean isTimeout(long time) {
        return this.lastStateUpdate + time < TimeUtil.getCurrentTimeMillis();
    }

    public State state() {
        return this.state;
    }

    private void setState(State state) {
        this.state = state;
        this.lastStateUpdate = TimeUtil.getCurrentTimeMillis();
    }
}
