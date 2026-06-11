package net.labymod.api.client.world.object.snapshot;

import net.labymod.api.laby3d.renderer.snapshot.AbstractLabySnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/snapshot/AbstractWorldObjectSnapshot.class */
public abstract class AbstractWorldObjectSnapshot extends AbstractLabySnapshot implements WorldObjectSnapshot {
    private final double x;
    private final double y;
    private final double z;
    private final int lightCoords;

    protected AbstractWorldObjectSnapshot(double x, double y, double z, int lightCoords, @NotNull Extras extras) {
        super(extras);
        this.x = x;
        this.y = y;
        this.z = z;
        this.lightCoords = lightCoords;
    }

    @Override // net.labymod.api.client.world.object.snapshot.WorldObjectSnapshot
    public double x() {
        return this.x;
    }

    @Override // net.labymod.api.client.world.object.snapshot.WorldObjectSnapshot
    public double y() {
        return this.y;
    }

    @Override // net.labymod.api.client.world.object.snapshot.WorldObjectSnapshot
    public double z() {
        return this.z;
    }

    @Override // net.labymod.api.client.world.object.snapshot.WorldObjectSnapshot
    public int lightCoords() {
        return this.lightCoords;
    }
}
