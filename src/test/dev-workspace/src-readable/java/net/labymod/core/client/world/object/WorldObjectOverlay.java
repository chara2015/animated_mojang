package net.labymod.core.client.world.object;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.world.object.WorldObject;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/object/WorldObjectOverlay.class */
@AutoActivity
public class WorldObjectOverlay extends IngameOverlayActivity {
    private static final ModifyReason WORLD_OBJECT_POSITION = ModifyReason.of("WorldObjectPosition");
    private final Map<WorldObject, Widget> objects = new HashMap();

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(@NotNull Parent parent) {
        super.initialize(parent);
        for (Widget widget : this.objects.values()) {
            ((Document) this.document).addChild(widget);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(@NotNull ScreenContext context) {
        for (Map.Entry<WorldObject, Widget> entry : this.objects.entrySet()) {
            updateState(entry.getKey(), entry.getValue());
        }
        super.render(context);
    }

    private void updateState(@NotNull WorldObject object, @NotNull Widget widget) {
        widget.setVisible(object.shouldRenderInOverlay());
        Entity cam = this.labyAPI.minecraft().getCameraEntity();
        if (cam == null) {
            return;
        }
        float yaw = getYaw(cam, object);
        float clientYaw = MathHelper.wrapDegrees(cam.getRotationYaw());
        float yawOffset = MathHelper.wrapDegrees((clientYaw - yaw) + 180.0f);
        Window window = this.labyAPI.minecraft().minecraftWindow();
        Bounds windowBounds = window.bounds();
        float x = (windowBounds.getWidth() / 2.0f) + ((windowBounds.getWidth() / 180.0f) * yawOffset);
        float y = windowBounds.getHeight() - widget.bounds().getHeight(BoundsType.OUTER);
        widget.bounds().setOuterPosition(x, y, WORLD_OBJECT_POSITION);
    }

    public float getYaw(@NotNull Entity cam, @NotNull WorldObject object) {
        DoubleVector3 objectLocation = object.position();
        DoubleVector3 previousObjectLocation = object.previousPosition();
        double objectX = MathHelper.lerp(objectLocation.getX(), previousObjectLocation.getX());
        double objectZ = MathHelper.lerp(objectLocation.getZ(), previousObjectLocation.getZ());
        Position position = cam.position();
        Position previousPosition = cam.previousPosition();
        double playerX = MathHelper.lerp(position.getX(), previousPosition.getX());
        double playerZ = MathHelper.lerp(position.getZ(), previousPosition.getZ());
        double xDiff = objectX - playerX;
        double zDiff = objectZ - playerZ;
        double distanceXZ = Math.sqrt((xDiff * xDiff) + (zDiff * zDiff));
        float yaw = (float) Math.toDegrees(Math.acos(distanceXZ == 0.0d ? 0.0d : xDiff / distanceXZ));
        if (zDiff < 0.0d) {
            yaw += Math.abs(180.0f - yaw) * 2.0f;
        }
        float yaw2 = yaw - 90.0f;
        if (yaw2 < 0.0f) {
            yaw2 += 360.0f;
        }
        return yaw2;
    }

    public void addObject(@NotNull WorldObject object) {
        Widget widget = object.createWidget();
        if (widget == null) {
            return;
        }
        ((Document) this.document).addChildInitialized(widget);
        this.objects.put(object, widget);
        updateState(object, widget);
    }

    public void removeObject(@NotNull WorldObject object) {
        Widget widget = this.objects.remove(object);
        if (widget != null) {
            ((Document) this.document).removeChild(widget);
        }
    }

    @NotNull
    public Map<WorldObject, Widget> getObjects() {
        return this.objects;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity
    public boolean isVisible() {
        return !this.objects.isEmpty();
    }
}
