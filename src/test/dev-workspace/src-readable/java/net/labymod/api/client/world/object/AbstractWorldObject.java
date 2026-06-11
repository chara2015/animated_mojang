package net.labymod.api.client.world.object;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/AbstractWorldObject.class */
public abstract class AbstractWorldObject implements WorldObject {
    protected final DoubleVector3 position;

    protected AbstractWorldObject() {
        this(new FloatVector3(0.0f, 0.0f, 0.0f));
    }

    protected AbstractWorldObject(@NotNull FloatVector3 location) {
        this(new DoubleVector3(location));
    }

    protected AbstractWorldObject(@NotNull DoubleVector3 position) {
        this.position = position;
    }

    @Override // net.labymod.api.client.world.object.WorldObject
    @NotNull
    public DoubleVector3 position() {
        return this.position;
    }

    @Override // net.labymod.api.client.world.object.WorldObject
    @Nullable
    public Widget createWidget() {
        return null;
    }
}
