package net.labymod.api.client.render.model.geometry.shapes;

import net.labymod.api.Laby;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeType;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/PlaneShape.class */
public interface PlaneShape extends Shape {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/PlaneShape$Builder.class */
    @Referenceable
    public interface Builder {
        @NotNull
        Builder origin(float f, float f2, float f3);

        @NotNull
        Builder size(float f, float f2);

        @NotNull
        Builder normal(@NotNull Direction direction);

        @NotNull
        Builder doubleSided(boolean z);

        @NotNull
        Builder textureOffset(int i, int i2);

        @NotNull
        Builder textureSize(int i, int i2);

        @NotNull
        PlaneShape build();
    }

    float getWidth();

    float getHeight();

    @NotNull
    Direction getNormalDirection();

    boolean isDoubleSided();

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    default ShapeType getType() {
        return ShapeType.PLANE;
    }

    @NotNull
    static Builder builder() {
        return Laby.references().planeShapeBuilder();
    }
}
