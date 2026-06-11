package net.labymod.core.labyconnect.object.marker;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.world.object.AbstractWorldObject;
import net.labymod.api.client.world.object.CullVolume;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.labyconnect.object.marker.Marker;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/marker/MarkerObject.class */
public class MarkerObject extends AbstractWorldObject implements Marker {
    private static final float MAX_HEIGHT_LARGE = 15.0f;
    private static final float MAX_HEIGHT_SMALL = 5.0f;
    private static final float MAX_RADIUS = 2.0f;
    private final UUID owner;
    private final Entity targetEntity;
    private final long timestamp;
    private final boolean large;

    private MarkerObject(@NotNull UUID owner, @NotNull Entity targetEntity, boolean large) {
        this.timestamp = TimeUtil.getMillis();
        this.owner = owner;
        this.targetEntity = targetEntity;
        this.large = large;
    }

    private MarkerObject(@NotNull UUID owner, @NotNull DoubleVector3 location, boolean large) {
        super(location);
        this.timestamp = TimeUtil.getMillis();
        this.owner = owner;
        this.targetEntity = null;
        this.large = large;
    }

    @NotNull
    public static MarkerObject forEntity(@NotNull UUID owner, @NotNull Entity targetEntity, boolean large) {
        return new MarkerObject(owner, targetEntity, large);
    }

    @NotNull
    public static MarkerObject fixed(@NotNull UUID owner, @NotNull DoubleVector3 location, boolean large) {
        return new MarkerObject(owner, location, large);
    }

    @Override // net.labymod.api.labyconnect.object.marker.Marker
    public boolean isLarge() {
        return this.large;
    }

    @Override // net.labymod.api.labyconnect.object.marker.Marker
    @Nullable
    public Entity getTargetEntity() {
        return this.targetEntity;
    }

    @Override // net.labymod.api.client.world.object.AbstractWorldObject, net.labymod.api.client.world.object.WorldObject
    @NotNull
    public DoubleVector3 position() {
        if (this.targetEntity != null) {
            float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
            AxisAlignedBoundingBox box = this.targetEntity.axisAlignedBoundingBox();
            Position position = this.targetEntity.position();
            Position previousPosition = this.targetEntity.previousPosition();
            this.position.set(position.lerpX(previousPosition, partialTicks), position.lerpY(previousPosition, partialTicks) + box.getHeight(), position.lerpZ(previousPosition, partialTicks));
        }
        return super.position();
    }

    @Override // net.labymod.api.client.world.object.WorldObject
    @NotNull
    public CullVolume cullVolume() {
        DoubleVector3 position = position();
        float maxHeight = this.large ? MAX_HEIGHT_LARGE : MAX_HEIGHT_SMALL;
        return CullVolume.box(new AxisAlignedBoundingBox(position.getX() - 2.0d, position.getY(), position.getZ() - 2.0d, position.getX() + 2.0d, position.getY() + ((double) maxHeight), position.getZ() + 2.0d));
    }

    @Override // net.labymod.api.client.world.object.AbstractWorldObject, net.labymod.api.client.world.object.WorldObject
    @Nullable
    public Widget createWidget() {
        return new LabyMarkerWidget(this);
    }

    @Override // net.labymod.api.client.world.object.WorldObject
    public boolean shouldRemove() {
        return TimeUtil.getMillis() - this.timestamp >= getDurationMillis();
    }

    @Override // net.labymod.api.labyconnect.object.marker.Marker
    @NotNull
    public UUID getOwner() {
        return this.owner;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public long getDurationMillis() {
        return Laby.labyAPI().config().multiplayer().marker().duration().get().intValue() * SubmissionOrders.DEBUG;
    }

    public int getARGB() {
        return ColorFormat.ARGB32.pack(255, 255, 255, 255);
    }
}
