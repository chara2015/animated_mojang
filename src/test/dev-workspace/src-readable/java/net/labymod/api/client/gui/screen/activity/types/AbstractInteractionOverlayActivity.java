package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.CameraLockController;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/types/AbstractInteractionOverlayActivity.class */
@AutoActivity
public abstract class AbstractInteractionOverlayActivity extends IngameOverlayActivity {
    private final CameraLockController cameraLockController;
    private boolean interactionOpen;
    private float initialYaw;
    private float initialPitch;

    protected abstract void renderInteractionOverlay(ScreenContext screenContext);

    protected abstract float getRadius();

    public AbstractInteractionOverlayActivity() {
        this(Laby.references().cameraLockController());
    }

    public AbstractInteractionOverlayActivity(CameraLockController cameraLockController) {
        this.cameraLockController = cameraLockController;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        float yawOffset = getYawOffset();
        float pitchOffset = getPitchOffset();
        Bounds bounds = bounds();
        float mouseX = bounds.getCenterX() - yawOffset;
        float mouseY = bounds.getCenterY() - pitchOffset;
        MutableMouse mouse = context.mouse();
        if (!isAcceptingInput()) {
            mouse.set(mouseX, mouseY);
        }
        Perspective perspective = this.labyAPI.minecraft().options().perspective();
        context.pushStack();
        Stack stack = context.stack();
        if (perspective == Perspective.FIRST_PERSON) {
            stack.translate(yawOffset, pitchOffset, 0.0f);
        }
        renderInteractionOverlay(context);
        super.render(context);
        if (perspective != Perspective.FIRST_PERSON) {
            ScreenCanvas renderState = context.canvas();
            renderState.submitRelativeRect(mouseX - 1.0f, (mouseY - 4.0f) - 1.0f, 1.0f, (4.0f * 2.0f) + 1.0f, Integer.MAX_VALUE);
            renderState.submitRelativeRect((mouseX - 4.0f) - 1.0f, mouseY - 1.0f, (4.0f * 2.0f) + 1.0f, 1.0f, Integer.MAX_VALUE);
        }
        context.popStack();
    }

    @Subscribe(126)
    public void onClientPlayerTurnEvent(ClientPlayerTurnEvent event) {
        if (!isInteractionOpen()) {
            return;
        }
        float yawOffset = getYawOffset();
        float pitchOffset = getPitchOffset();
        float offsetDistance = (float) Math.sqrt(MathHelper.square(yawOffset) + MathHelper.square(pitchOffset));
        float movementRadius = getRadius();
        if (offsetDistance > movementRadius) {
            float multiplier = movementRadius / offsetDistance;
            this.cameraLockController.setYaw(this.initialYaw - ((yawOffset / 2.0f) * multiplier));
            this.cameraLockController.setPitch(this.initialPitch - ((pitchOffset / 2.0f) * multiplier));
        }
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent event) {
        if (isVisible()) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onScreenOpen(ScreenOpenEvent event) {
        if (event.getScreen() != this && shouldCloseOnScreenOpen()) {
            closeInteraction();
        }
    }

    public boolean openInteraction() {
        if (this.cameraLockController.isLocked()) {
            return false;
        }
        this.cameraLockController.lock(CameraLockController.LockType.HEAD);
        this.initialYaw = this.cameraLockController.getYaw();
        this.initialPitch = this.cameraLockController.getPitch();
        this.interactionOpen = true;
        reload();
        return true;
    }

    public void closeInteraction() {
        if (!this.interactionOpen) {
            return;
        }
        this.interactionOpen = false;
        this.cameraLockController.unlock();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public boolean isVisible() {
        return this.interactionOpen;
    }

    public boolean isInteractionOpen() {
        return this.interactionOpen;
    }

    protected float getYawOffset() {
        float yawOffset = this.initialYaw - this.cameraLockController.getYaw();
        return yawOffset * getSensitivity();
    }

    protected float getPitchOffset() {
        float pitchOffset = this.initialPitch - this.cameraLockController.getPitch();
        return pitchOffset * getSensitivity();
    }

    protected float getSensitivity() {
        return 2.0f;
    }

    protected boolean shouldCloseOnScreenOpen() {
        return true;
    }
}
