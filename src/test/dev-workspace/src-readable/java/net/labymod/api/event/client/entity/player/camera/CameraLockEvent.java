package net.labymod.api.event.client.entity.player.camera;

import net.labymod.api.client.entity.player.CameraLockController;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/camera/CameraLockEvent.class */
public class CameraLockEvent implements Event {
    private final CameraLockController.LockType lockType;

    public CameraLockEvent(CameraLockController.LockType lockType) {
        this.lockType = lockType;
    }

    public CameraLockController.LockType getLockType() {
        return this.lockType;
    }
}
