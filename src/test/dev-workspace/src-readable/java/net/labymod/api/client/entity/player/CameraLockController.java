package net.labymod.api.client.entity.player;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.event.client.entity.player.camera.CameraLockEvent;
import net.labymod.api.event.client.entity.player.camera.CameraUnlockEvent;
import net.labymod.api.event.client.render.camera.CameraRotationEvent;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/CameraLockController.class */
@Singleton
@Referenceable
public class CameraLockController {
    private boolean locked;
    private LockType lockType;
    private boolean unlimitedPitch;
    private float yaw;
    private float pitch;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/CameraLockController$LockType.class */
    public enum LockType {
        CAMERA,
        HEAD
    }

    @Inject
    public CameraLockController(EventBus eventBus) {
        eventBus.registerListener(this);
    }

    @Subscribe
    public void onClientPlayerTurnEvent(ClientPlayerTurnEvent event) {
        if (!this.locked) {
            return;
        }
        if (this.lockType == LockType.HEAD) {
            setYaw((float) (((double) this.yaw) + event.getX()));
            setPitch((float) (((double) this.pitch) + event.getY()));
        }
        if (event.getX() > 0.0d || event.getY() > 0.0d) {
            Laby.labyAPI().minecraft().requestChunkUpdate();
        }
        event.setCancelled(true);
    }

    @Subscribe
    public void onCameraRotationEvent(CameraRotationEvent event) {
        if (this.locked) {
            event.setYaw(this.yaw);
            event.setPitch(this.pitch);
        } else {
            setYaw(event.getYaw());
            setPitch(event.getPitch());
        }
    }

    public void lock(LockType lockType) {
        this.locked = true;
        this.lockType = lockType;
        this.unlimitedPitch = false;
        Laby.fireEvent(new CameraLockEvent(lockType));
    }

    public void lock(LockType lockType, float yaw, float pitch) {
        lock(lockType);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void unlock() {
        this.locked = false;
        this.unlimitedPitch = false;
        Laby.labyAPI().minecraft().requestChunkUpdate();
        Laby.fireEvent(new CameraUnlockEvent());
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        if (this.unlimitedPitch) {
            return;
        }
        if (this.pitch > 90.0f) {
            this.pitch = 90.0f;
        } else if (this.pitch < -90.0f) {
            this.pitch = -90.0f;
        }
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public LockType lockType() {
        return this.lockType;
    }

    public boolean isUnlimitedPitch() {
        return this.unlimitedPitch;
    }

    public void setUnlimitedPitch(boolean unlimitedPitch) {
        this.unlimitedPitch = unlimitedPitch;
    }
}
